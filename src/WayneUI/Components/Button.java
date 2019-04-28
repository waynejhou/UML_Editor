package wayneUI.components;

import java.awt.AWTKeyStroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import javax.swing.SwingUtilities;

import wayneUI.enums.HorizontalAlignment;
import wayneUI.enums.VerticalAlignment;

public class Button extends java.awt.Panel {
    private Button _this;

    public Button() {
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
                repaint();
            }
        });
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setIsFocused(true);
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                setIsFocused(false);
                repaint();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e);
                if (e.getKeyChar() == ' ') {
                    setIsActing(true);
                    _this.repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println(e);
                if (e.getKeyChar() == ' ') {
                    setIsActing(false);
                    _this.repaint();
                }
            }
        });
        ((Component) this).setFocusable(true);
        setBackground();
    }

    public Button(String label) {
        this();
        setLabel(label);
    }

    public Button(String label, int width, int height) {
        this();
        setLabel(label);
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
    }

    public static FocusTraversalPolicy getFocusTraversalPolicyAncestor(Component c) {
        for (Container p = c.getParent(); p != null; p = p.getParent()) {
            if (p.getFocusTraversalPolicy() != null) {
                return p.getFocusTraversalPolicy();
            }
        }
        return null;
    }

    private String _Label = "";

    public String getLabel() {
        return _Label;
    }

    public void setLabel(String value) {
        _Label = value;
    }

    private HorizontalAlignment _HorizontalAlignment = HorizontalAlignment.Center;

    public HorizontalAlignment getHorizontalAlignment() {
        return _HorizontalAlignment;
    }

    public void setHorizontalAlignment(HorizontalAlignment value) {
        _HorizontalAlignment = value;
    }

    private VerticalAlignment _VerticalAlignment = VerticalAlignment.Center;

    public VerticalAlignment getVerticalAlignment() {
        return _VerticalAlignment;
    }

    public void setVerticalAlignment(VerticalAlignment value) {
        _VerticalAlignment = value;
    }

    private Color _Hoverground = Color.lightGray;

    public Color getHoverground() {
        return _Hoverground;
    }

    public void setHoverground(Color value) {
        _Hoverground = value;
    }

    private Color _Actionground = Color.gray;

    public Color getActionground() {
        return _Actionground;
    }

    public void setActionground(Color value) {
        _Actionground = value;
    }

    private Point getLabelPositionByAlignments(Dimension stringDimension) {
        int setx = 0, sety = 0;
        switch (getHorizontalAlignment()) {
        case Left:
            setx = 0;
            break;
        case Center:
            setx = (getWidth() / 2 - stringDimension.width / 2);
            break;
        case Right:
            setx = getWidth() - stringDimension.width;
            break;
        }
        switch (getVerticalAlignment()) {
        case Top:
            sety = stringDimension.height;
            break;
        case Center:
            sety = (getHeight() / 2 + stringDimension.height / 2);
            break;
        case Bottom:
            sety = getHeight();
            break;
        }
        return new Point(setx, sety);
    }

    private Boolean _IsHovering = false;

    public Boolean getIsHovering() {
        return _IsHovering;
    }

    public void setIsHovering(Boolean value) {
        _IsHovering = value;
    }

    private Boolean _IsActing = false;

    public Boolean getIsActing() {
        return _IsActing;
    }

    public void setIsActing(Boolean value) {
        _IsActing = value;
    }

    private Boolean _IsFocused = false;

    public Boolean getIsFocused() {
        return _IsFocused;
    }

    public void setIsFocused(Boolean value) {
        _IsFocused = value;
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
        // g.setColor(getBackground());
        if (getIsHovering()) {
            g.setColor(getHoverground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        if (getIsActing()) {
            g.setColor(getActionground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void paintLabel(Graphics2D g) {
        g.setColor(getForeground());
        var m = g.getFontMetrics();
        var h = m.getHeight();
        var w = m.stringWidth(getLabel());
        var p = getLabelPositionByAlignments(new Dimension(w, h));
        g.drawString(getLabel(), p.x, p.y);
    }
}
