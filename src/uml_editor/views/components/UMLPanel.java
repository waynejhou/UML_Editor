package uml_editor.views.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import uml_editor.utils.ShapeList;
import uml_editor.utils.sessions.UMLSession;
import uml_editor.views.shapes.BaseShape;
import uml_editor.views.shapes.CanBeJointedShape;
import uml_editor.views.shapes.GroupShape;
import uml_editor.views.shapes.JointPoint;
import uml_editor.views.shapes.lines.LineShape;

public class UMLPanel extends WComponent {
    private static final AlphaComposite _composite = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f);

    public UMLPanel() {
        setSession(UMLSession.SelectSession);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    requestUpdateStaticground();
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    requestUpdateDynamicground();
                    repaint();
                }
            }
        });
    }

    public boolean isRequestedUpdate() {
        return _isForceUpdDg || _isForceUpdSg;
    }

    public void requestUpdateDynamicground() {
        _isForceUpdDg = true;
    }

    public void requestUpdateStaticground() {
        _isForceUpdSg = true;
    }

    private UMLSession _Session = null;

    public UMLSession getSession() {
        return _Session;
    }

    public void setSession(UMLSession value) {
        if (_Session != null) {
            this.removeMouseListener(_Session);
            this.removeMouseMotionListener(_Session);
            this.removeMouseWheelListener(_Session);
            _Session.setHost(null);
        }
        if (value != null) {
            this.addMouseListener(value);
            this.addMouseMotionListener(value);
            this.addMouseWheelListener(value);
        }
        value.setHost(this);
        _Session = value;
        // System.out.println("setSession - " + value.toString());
        return;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(getBackgroundBuffer(), 0, 0, null);
        g.drawImage(getStaticgroundBuffer(), 0, 0, null);
        g.drawImage(getDynamicgroundBuffer(), 0, 0, null);
    }

    private BufferedImage _BgBuffer = null;

    private BufferedImage getBackgroundBuffer() {
        if (_BgBuffer != null && !getIsResized(_BgBuffer))
            return _BgBuffer;
        _BgBuffer = getClearImg();
        Graphics2D g = (Graphics2D) _BgBuffer.getGraphics();
        var o = getOrigin();
        g.setColor(applyBackgroundColor(g.getColor()));
        g.fillRect(0, 0, _BgBuffer.getWidth(), _BgBuffer.getHeight());
        g.setColor(applyForegroundColor(g.getColor()));
        g.drawLine(o.x, 0, o.x, getHeight());
        g.drawLine(0, o.y, getWidth(), o.y);
        return _BgBuffer;
    }

    private BufferedImage _SgBuffer = null;

    private boolean _isForceUpdSg = false;

    private BufferedImage getStaticgroundBuffer() {
        if (_SgBuffer != null && !_isForceUpdSg && !getIsResized(_SgBuffer))
            return _SgBuffer;
        if (_SgBuffer == null || getIsResized(_SgBuffer))
            _SgBuffer = getClearImg();
        else
            clearBuf(_SgBuffer);
        drawShapeOnStaticGround();
        _isForceUpdSg = false;
        return _SgBuffer;
    }

    private void drawShapeOnStaticGround() {
        Graphics2D g = (Graphics2D) _SgBuffer.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        var o = getOrigin();
        for (BaseShape shape : CanBeJointedShapeCollection.getShapes()) {
            if (!DynamicShapes.contains(shape))
                shape.StartToDraw(g, o);
        }
        for (BaseShape shape : LineShapeCollection.getShapes()) {
            if (!DynamicShapes.contains(shape))
                shape.StartToDraw(g, o);
        }

    }

    private BufferedImage _DgBuffer = null;

    private boolean _isForceUpdDg = false;

    private BufferedImage getDynamicgroundBuffer() {
        if (_DgBuffer != null && !_isForceUpdDg && !getIsResized(_DgBuffer))
            return _DgBuffer;
        if (_DgBuffer == null || getIsResized(_DgBuffer))
            _DgBuffer = getClearImg();
        else
            clearBuf(_DgBuffer);
        drawShapeOnDynamicGround();
        _isForceUpdDg = false;
        return _DgBuffer;
    }

    private void drawShapeOnDynamicGround() {
        Graphics2D g = (Graphics2D) _DgBuffer.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        var o = getOrigin();
        for (BaseShape shape : DynamicShapes) {
            shape.StartToDraw(g, o);
        }
        for (JointPoint jpt : DynamicJointPoints) {
            var p = new Point(jpt.getOwner().getX() + o.x, jpt.getOwner().getY() + o.y);
            System.out.println(jpt.getOwner());
            jpt.StartToDraw(g, p);
        }
        for (BaseShape shape : PreviewedShapes) {
            shape.StartToDraw(g, o);
        }

    }

    private void clearBuf(BufferedImage img) {
        Graphics2D g2d = (Graphics2D) img.getGraphics();
        var oc = g2d.getComposite();
        g2d.setComposite(_composite);
        g2d.setColor(new Color(0, 0, 0, 0));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setComposite(oc);
    }

    private BufferedImage getClearImg() {
        return new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
    }

    private boolean getIsResized(BufferedImage buf) {
        return buf.getWidth() != getWidth() || buf.getHeight() != getHeight();
    }

    public Point getOrigin() {
        return new Point(getWidth() / 2, getHeight() / 2);
    }

    public ShapeList<BaseShape> ShapeCollection = new ShapeList<BaseShape>();
    public ShapeList<CanBeJointedShape> CanBeJointedShapeCollection = new ShapeList<CanBeJointedShape>();
    public ShapeList<GroupShape> GroupShapeCollection = new ShapeList<GroupShape>();
    public ShapeList<LineShape> LineShapeCollection = new ShapeList<LineShape>();
    public Set<BaseShape> DynamicShapes = new HashSet<BaseShape>();
    public Set<JointPoint> DynamicJointPoints = new HashSet<JointPoint>();
    public Set<BaseShape> PreviewedShapes = new HashSet<BaseShape>();

    public void SaveState() throws IOException {
        FileOutputStream foStream = new FileOutputStream("save.dat");
        ObjectOutputStream objoStream = new ObjectOutputStream(foStream);
        objoStream.writeObject(ShapeCollection);
        objoStream.writeObject(CanBeJointedShapeCollection);
        objoStream.writeObject(GroupShapeCollection);
        objoStream.writeObject(LineShapeCollection);
    }
    @SuppressWarnings("unchecked")
    public void LoadState() throws IOException, ClassNotFoundException {
        FileInputStream fiStream = new FileInputStream("save.dat");
        ObjectInputStream objiStream = new ObjectInputStream(fiStream);
        ShapeCollection = (ShapeList<BaseShape>) objiStream.readObject();
        CanBeJointedShapeCollection = (ShapeList<CanBeJointedShape>) objiStream.readObject();
        GroupShapeCollection = (ShapeList<GroupShape>) objiStream.readObject();
        LineShapeCollection = (ShapeList<LineShape>) objiStream.readObject();
    }
    
    
}
