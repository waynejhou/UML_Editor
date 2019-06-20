package uml_editor.views.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.LinkedList;

import uml_editor.resources.ColorSetter;

public class WRadioButton extends WComponent {
    static HashMap<String, LinkedList<WRadioButton>> _Group = new HashMap<String, LinkedList<WRadioButton>>();
     
    LinkedList<ActionListener> _Actions = new LinkedList<ActionListener>();
    
    private WRadioButton _this;
    public WRadioButton() {
        _this = this;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setIsHovering(true);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setIsHovering(false);
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setIsActing(true);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setIsActing(false);
                if(getIsToggle()==false)
                    setIsToggle(!getIsToggle());
                repaint();
            }
        });
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                repaint();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == ' ') {
                    setIsActing(true);
                    _this.repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == ' ') {
                    setIsActing(false);
                    if(getIsToggle()==false)
                        setIsToggle(!getIsToggle());
                    _this.repaint();
                }
            }
        });
        this.setFocusable(true);
        RegisterGroup();
    }

    public void addActionListener(ActionListener action) {
        _Actions.add(action);
    }
    public void removeActionListener(ActionListener action) {
        _Actions.remove(action);
    }
    
    private Object _Label = "";

    public Object getLabel() {
        return _Label;
    }

    public void setLabel(String value) {
        _Label = value;
    }
    public void setLabel(Image value) {
        _Label = value;
    }

    private boolean _IsToggle = false;

    public boolean getIsToggle() {
        return _IsToggle;
    }

    public void setIsToggle(boolean value) {
        if(value==true) {
            for (WRadioButton b : _Group.get(getToogleGroup())) {
                b.setIsToggle(false);
                b.repaint();
            }
        }
        _IsToggle = value;
        for(var act : _Actions) {
            act.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_FIRST, ""));
        }
    }
    
    private String _ToogleGroup = "";

    public String getToogleGroup() {
        return _ToogleGroup;
    }

    public void setToogleGroup(String value) {
        DeRegisterGroup();
        _ToogleGroup = value;
        RegisterGroup();
    }
    
    void RegisterGroup() {
        if(!_Group.containsKey(getToogleGroup())){
            _Group.put(getToogleGroup(), new LinkedList<WRadioButton>());
        }
        if(!_Group.get(getToogleGroup()).contains(this)) {
            _Group.get(getToogleGroup()).add(this);
        }
    }
    void DeRegisterGroup() {
        if(!_Group.containsKey(getToogleGroup())){
            return;
        }
        if(_Group.get(getToogleGroup()).contains(this)) {
            _Group.get(getToogleGroup()).remove(this);
        }
        if(_Group.get(getToogleGroup()).size()==0 ) {
            _Group.remove(getToogleGroup());
        }
    }
    
    private ColorSetter _ToggroundSetter = (c->Color.green);

    public ColorSetter getToggroundSetter() {
        return _ToggroundSetter;
    }

    public void setToggroundSetter(ColorSetter value) {
        _ToggroundSetter = value;
    }
    
    public Color getToggroundColor() {
        return getToggroundSetter().Set(new Color(0));
    }
    public Color applyToggroundColor(Color color) {
        return getToggroundSetter().Set(color);
    }
    
    @Override
    public void paint(Graphics g) {
        var g2d = (Graphics2D) g;
        paintBackground(g2d);
        paintLabel(g2d);
        paintFocus(g2d);
    }

    private void paintFocus(Graphics2D g2d) {
        if (isFocusOwner()) {
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[] { 1f }, 0));
            g2d.drawRect(2, 2, getWidth() - 5, getHeight() - 5);
        }
    }

    private void paintBackground(Graphics2D g) {
        g.setColor(getBackgroundSetter().Set(getBackground()));
        g.fillRect(0, 0, getWidth(), getHeight());
        if (getIsToggle()) {
            g.setColor(applyToggroundColor(g.getColor()));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        if (getIsHovering()) {
            g.setColor(applyHovergroundColor(g.getColor()));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        if (getIsActing()) {
            g.setColor(applyActingroundColor(g.getColor()));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void paintLabel(Graphics2D g) {
        g.setColor(getForeground());

        if(getLabel() instanceof Image) {
            var img = (Image)getLabel();
            var h = Math.max( Math.min(img.getHeight(null),getHeight()/2), 0);
            int w = Math.max( Math.min(img.getWidth(null),getWidth()/2), 0);
            var p = getLabelPositionByAlignments(new Dimension(w, h));
            g.drawImage(img,p.x, p.y, w, h, null);
        }else {
            var m = g.getFontMetrics();
            var h = m.getHeight();
            int w = m.stringWidth(getLabel().toString());
            var p = getLabelPositionByAlignments(new Dimension(w, h));
            g.setColor(applyFontColor(g.getColor()));
            g.drawString(getLabel().toString(), p.x, p.y);
        }
        
        
    }
}
