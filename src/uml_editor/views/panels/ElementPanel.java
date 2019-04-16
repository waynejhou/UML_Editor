package uml_editor.views.panels;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import uml_editor.views.components.elements.AssociationLineElement;
import uml_editor.views.components.elements.ClassElement;
import uml_editor.views.components.elements.CompositionLineElement;
import uml_editor.views.components.elements.Element;
import uml_editor.views.components.elements.GeneralizationLineElement;
import uml_editor.views.components.elements.GroupElement;
import uml_editor.views.components.elements.JointElement;
import uml_editor.views.components.elements.LineElement;
import uml_editor.views.components.elements.UseCaseElement;
import uml_editor.views.components.elements.interfaces.ICanBeJointed;
import uml_editor.views.panels.enums.EditorMode;

public class ElementPanel extends JPanel implements MouseListener, MouseMotionListener {

	public ElementPanel() {
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void paint(Graphics g) {
		if (!_isFirstPainted) {
			_isFirstPainted = true;
			OnFirstPaint();
		}
		g.drawImage(getBackGround(), 0, 0, null);
		g.drawImage(getStaticGround(), 0, 0, null);
		g.drawImage(getDynamicGround(), 0, 0, new Color(0, 0, 0, 0), null);
	}

	private boolean _isFirstPainted = false;

	private void OnFirstPaint() {

	}

	private AlphaComposite _composite = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f);
	private BufferedImage _backGnd = null;

	private BufferedImage getBackGround() {
		if (_backGnd != null && _backGnd.getWidth() == getWidth() && _backGnd.getHeight() == getHeight()) {
			return _backGnd;
		}
		_backGnd = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		var g = (Graphics2D) _backGnd.getGraphics();
		var origin = getOrigin();
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(165, 165, 165));
		g.drawRect(5, 5, getWidth() - 10, getHeight() - 10);
		g.drawLine(origin.x, 0, origin.x, getHeight());
		g.drawLine(0, origin.y, getWidth(), origin.y);
		return _backGnd;
	}

	boolean _isForceUpdStGnd = false;
	private BufferedImage _stGnd = null;

	private BufferedImage getStaticGround() {
		if (_stGnd != null && !_isForceUpdStGnd && _stGnd.getWidth() == getWidth()
				&& _stGnd.getHeight() == getHeight()) {
			return _stGnd;
		}
		if (_stGnd == null || _stGnd.getWidth() != getWidth() || _stGnd.getHeight() != getHeight()) {
			_stGnd = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		} else {
			Graphics2D g2d = (Graphics2D) _stGnd.getGraphics();
			var oc = g2d.getComposite();
			g2d.setComposite(_composite);
			g2d.setColor(new Color(0, 0, 0, 0));
			g2d.fillRect(0, 0, getWidth(), getHeight());
			g2d.setComposite(oc);
		}
		var g = (Graphics2D) _stGnd.getGraphics();
		var origin = getOrigin();
		ArrayList<Element> reversed = new ArrayList<>(_elements);
		Collections.reverse(reversed);
		for (var e : reversed) {
			if (e != _now_mouseOveringElement && e != _now_selectedElement) {
				if(_now_selectedElement instanceof GroupElement) {
					if(!((GroupElement)_now_selectedElement).getGroupedElements().contains(e)) {
						e.StartToDraw((Graphics2D) g, origin);
					}
				}else {
					e.StartToDraw((Graphics2D) g, origin);
				}
				
				//e.DrawInfo((Graphics2D) g, origin);
			}

		}
		/*
		 * for (var e : _groups) { e.StartToDraw((Graphics2D) g, origin); }
		 */
		_isForceUpdStGnd = false;
		return _stGnd;
	}

	boolean _isForceUpdDynGnd = false;
	private BufferedImage _dynGnd = null;

	private BufferedImage getDynamicGround() {
		if (_dynGnd != null && !_isForceUpdDynGnd && _dynGnd.getWidth() == getWidth()
				&& _dynGnd.getHeight() == getHeight()) {
			return _dynGnd;
		}
		if (_dynGnd == null || _dynGnd.getWidth() != getWidth() || _dynGnd.getHeight() != getHeight()) {
			_dynGnd = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		} else {
			Graphics2D g2d = (Graphics2D) _dynGnd.getGraphics();
			var oc = g2d.getComposite();
			g2d.setComposite(_composite);
			g2d.setColor(new Color(0, 0, 0, 0));
			g2d.fillRect(0, 0, getWidth(), getHeight());
			g2d.setComposite(oc);
		}
		var g = (Graphics2D) _dynGnd.getGraphics();
		var origin = getOrigin();
		if (_dynElement != null) {
			if (_dynElement instanceof JointElement) {
				_dynElement.StartToDraw((Graphics2D) g,
						new Point(((JointElement) _dynElement).getOwner().getX() + origin.x,
								((JointElement) _dynElement).getOwner().getY() + origin.y));
			} else if (_dynElement instanceof LineElement) {
				_dynElement.StartToDraw((Graphics2D) g, origin);
			} else {
				_dynElement.StartToDraw((Graphics2D) g, origin);
			}

		}
		if (_now_selectedElement != null) {
			if (_now_selectedElement instanceof JointElement) {
				_now_selectedElement.StartToDraw((Graphics2D) g,
						new Point(((JointElement) _now_selectedElement).getOwner().getX() + origin.x,
								((JointElement) _now_selectedElement).getOwner().getY() + origin.y));
			}else if (_now_selectedElement instanceof GroupElement) {
				for(var ge: ((GroupElement) _now_selectedElement).getGroupedElements()) {
					ge.StartToDraw((Graphics2D) g, origin);
				}
				_now_selectedElement.StartToDraw((Graphics2D) g, origin);
			}
			else {
				_now_selectedElement.StartToDraw((Graphics2D) g, origin);
			}
		}
		if (_now_mouseOveringElement != null) {
			if (_now_mouseOveringElement instanceof JointElement) {
				_now_mouseOveringElement.StartToDraw((Graphics2D) g,
						new Point(((JointElement) _now_mouseOveringElement).getOwner().getX() + origin.x,
								((JointElement) _now_mouseOveringElement).getOwner().getY() + origin.y));
			} else {
				_now_mouseOveringElement.StartToDraw((Graphics2D) g, origin);
			}
		}

		_isForceUpdDynGnd = false;
		return _dynGnd;
	}

	private EditorMode _mode = null;

	public EditorMode getMode() {
		return _mode;
	}

	public void setMode(EditorMode value) {
		if (_mode != null) {
			switch (_mode) {
			case _select:
				selectionSession_De_Activate();
				break;
			case _class:
				break;
			case _association:
			case _generalization:
			case _composition:
				newLineSession_De_Activate();
				break;
			default:
				break;
			}
		}
		_mode = value;
		switch (_mode) {
		case _select:
			break;
		case _class:
			break;
		case _association:
		case _generalization:
		case _composition:
			newLineSession_Activate();
			break;
		default:
			break;
		}
	}

	private Point getOrigin() {
		return new Point(getWidth() / 2, getHeight() / 2);
	}

	@Override
	public String toString() {
		var ret = "Debug Info";
		return ret;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (_mode) {
		case _class:
		case _user_case:
			newShapeElementSession_MMoved();
			break;
		case _select:
			selectionSession_MMoved(e.getPoint());
			break;
		case _association:
		case _generalization:
		case _composition:
			newLineSessionn_MMoved(e.getPoint());
			break;
		default:
			break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (_mode) {
		case _class:
		case _user_case:
			if (e.getButton() == MouseEvent.BUTTON1) {
				newShapeElementSession_LMPressed(e.getPoint(), _mode);
			}
			break;
		case _select:
			if (e.getButton() == MouseEvent.BUTTON1) {
				selectionSession_LMPressed(e.getPoint());
			}
			break;
		case _association:
		case _generalization:
		case _composition:
			if (e.getButton() == MouseEvent.BUTTON1) {
				newLineSession_LMPressed(e.getPoint(), _mode);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		switch (_mode) {
		case _class:
		case _user_case:
			newShapeElementSession_MDragged(e.getPoint(), _mode);
			break;
		case _select:
			selectionSession_MDragged(e.getPoint());
			break;
		case _association:
		case _generalization:
		case _composition:
			newLineSession_MDragged(e.getPoint());
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (_mode) {
		case _class:
		case _user_case:
			if (e.getButton() == MouseEvent.BUTTON1) {
				newShapeElementSession_LMReleased(e.getPoint(), _mode);
			}
			break;
		case _select:
			if (e.getButton() == MouseEvent.BUTTON1) {
				selectionSession_LMReleased(e.getPoint());
			}
			break;
		case _association:
		case _generalization:
		case _composition:
			if (e.getButton() == MouseEvent.BUTTON1) {
				newLineSession_LMReleased(e.getPoint());
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	private ArrayList<Element> _elements = new ArrayList<Element>();

	private List<? extends ICanBeJointed> getCanBeJointElements() {
		return _elements.stream().filter(x -> x instanceof ICanBeJointed).map(x -> (ICanBeJointed) x)
				.collect(Collectors.toList());
	}

	private List<JointElement> getJoints() {
		return getCanBeJointElements().stream().map(x -> (x).getAllJointElements()).flatMap(List::stream)
				.collect(Collectors.toList());
	}

	private ArrayList<Element> _groupingElements = new ArrayList<Element>();
	private ArrayList<GroupElement> _groups = new ArrayList<GroupElement>();

	private Element _dynElement = null;

	private Element _now_mouseOveringElement = null;
	private Element _now_selectedElement = null;

	private void newShapeElementSession_MMoved() {

	}

	private void newShapeElementSession_LMPressed(Point mpt, EditorMode mode) {
		var o = getOrigin();
		if (mode == EditorMode._class) {
			_dynElement = new ClassElement();
			_dynElement.setIsVisible(true);
			_dynElement.setPt1(mpt.x - o.x, mpt.y - o.y);
		}
		if (mode == EditorMode._user_case) {
			_dynElement = new UseCaseElement();
			_dynElement.setIsVisible(true);
			_dynElement.setPt1(mpt.x - o.x, mpt.y - o.y);
		}

	}

	private void newShapeElementSession_MDragged(Point mpt, EditorMode mode) {
		var o = getOrigin();
		if (_dynElement != null) {
			_dynElement.setPt2(mpt.x - o.x, mpt.y - o.y);
			_isForceUpdDynGnd = true;
			update(getGraphics());
		}
	}

	private void newShapeElementSession_LMReleased(Point mpt, EditorMode mode) {
		var o = getOrigin();
		_dynElement.setPt2(mpt.x - o.x, mpt.y - o.y);
		for (var ele : _elements) {
			ele.incDepth();
		}
		_dynElement.setDepth(0);
		_dynElement.init();
		_elements.add(_dynElement);
		Collections.sort(_elements, (l, r) -> l.getDepth() - r.getDepth());
		_dynElement = null;
		_isForceUpdStGnd = true;
		_isForceUpdDynGnd = true;
		update(getGraphics());
	}

	private void selectionSession_De_Activate() {
		if (_now_mouseOveringElement != null) {
			_now_mouseOveringElement.setIsMouseOver(false);
			_now_mouseOveringElement = null;
		}
		if (_now_selectedElement != null) {
			_now_selectedElement.setIsSelected(false);
			_now_selectedElement = null;
		}
		if (_groupingElements.size() > 0) {
			for (var gele : _groupingElements) {
				gele.setIsSelected(false);
			}
			_groupingElements.clear();
		}
		_isForceUpdStGnd = true;
		_isForceUpdDynGnd = true;
		update(getGraphics());
	}

	private void selectionSession_MMoved(Point mpt) {
		var o = getOrigin();
		Element _post_mouseOveringElement = null;
		for (var ele : _elements) {
			if (ele.isPointIn(mpt.x - o.x, mpt.y - o.y)) {
				_post_mouseOveringElement = ele;
				break;
			}
		}
		if (_now_mouseOveringElement != _post_mouseOveringElement) {
			if (_now_mouseOveringElement != null) {
				_now_mouseOveringElement.setIsMouseOver(false);
				_now_mouseOveringElement = null;
			}
			if (_post_mouseOveringElement != null) {
				_now_mouseOveringElement = _post_mouseOveringElement;
				_now_mouseOveringElement.setIsMouseOver(true);
			}
			_isForceUpdDynGnd = true;
			update(getGraphics());
		}
	}

	private boolean _dragingElementStart = false;
	private boolean _groupingElementStart = false;
	private int _dragTempPtX = 0;
	private int _dragTempPtY = 0;
	private int _draggedElePtX = 0;
	private int _draggedElePtY = 0;

	private void selectionSession_LMPressed(Point mpt) {
		if (_now_selectedElement != null) {
			_now_selectedElement.setIsSelected(false);
			_now_selectedElement = null;
			_isForceUpdStGnd = true;
			_isForceUpdDynGnd = true;
		}
		if (_groupingElements.size() > 0) {
			for (var gele : _groupingElements) {
				gele.setIsSelected(false);
			}
			_groupingElements.clear();
		}
		if (_now_mouseOveringElement != null) {
			_now_mouseOveringElement.setIsSelected(true);
			_now_selectedElement = _now_mouseOveringElement;
			_isForceUpdStGnd = true;
			_isForceUpdDynGnd = true;
			_dragTempPtX = mpt.x;
			_dragTempPtY = mpt.y;
			_draggedElePtX = _now_selectedElement.getX();
			_draggedElePtY = _now_selectedElement.getY();
			_dragingElementStart = true;
		} else {
			var o = getOrigin();
			_dynElement = new GroupElement();
			_dynElement.setIsVisible(true);
			_dynElement.setPt1(mpt.x - o.x, mpt.y - o.y);
			_groupingElementStart = true;
		}
		if (_isForceUpdStGnd)
			update(getGraphics());
	}

	private void selectionSession_MDragged(Point mpt) {
		if (_dragingElementStart) {
			_now_selectedElement.setX((mpt.x - _dragTempPtX) + _draggedElePtX);
			_now_selectedElement.setY((mpt.y - _dragTempPtY) + _draggedElePtY);
			_isForceUpdDynGnd = true;
			update(getGraphics());
		}
		if (_groupingElementStart) {
			var o = getOrigin();
			_dynElement.setPt2(mpt.x - o.x, mpt.y - o.y);
			_isForceUpdDynGnd = true;
			update(getGraphics());
		}
	}

	private void selectionSession_LMReleased(Point mpt) {
		_dragingElementStart = false;

		/*
		 * _dynElement.setPt2(mpt.x - o.x, mpt.y - o.y); for (var ele : _elements) {
		 * ele.incDepth(); }
		 */
		// _dynElement.setDepth(0);
		// _dynElement.init();
		// _elements.add(_dynElement);
		// Collections.sort(_elements, (l, r) -> l.getDepth() - r.getDepth());
		/*
		 * for(var e : getCanBeJointElements()) {
		 * if(((GroupElement)_dynElement).getIsIncluded(ele)) }
		 */
		if (_groupingElementStart) {
			GroupElement gDynEle = (GroupElement) _dynElement;
			for (var jele : _elements) {
				if (gDynEle.getIsIncluded((Element) jele)) {
					((Element) jele).setIsSelected(true);
					_groupingElements.add(((Element) jele));
				}
			}
			_groupingElementStart = false;
		}
		_dynElement = null;
		_isForceUpdStGnd = true;
		_isForceUpdDynGnd = true;
		update(getGraphics());

	}

	private void newLineSession_Activate() {
		for (var jele : getJoints())
			jele.setIsVisible(true);
	}

	private void newLineSession_De_Activate() {
		for (var jele : getJoints())
			jele.setIsVisible(false);
	}

	private void newLineSessionn_MMoved(Point mpt) {
		var o = getOrigin();
		Element _post_mouseOveringElement = null;
		for (var jele : getJoints()) {
			if (jele.isPointIn(mpt.x - o.x, mpt.y - o.y)) {
				_post_mouseOveringElement = jele;
				break;
			}
		}
		if (_now_mouseOveringElement != _post_mouseOveringElement) {
			if (_now_mouseOveringElement != null) {
				_now_mouseOveringElement.setIsMouseOver(false);
				_now_mouseOveringElement = null;
			}
			if (_post_mouseOveringElement != null) {
				_now_mouseOveringElement = _post_mouseOveringElement;
				_now_mouseOveringElement.setIsMouseOver(true);
			}
			_isForceUpdDynGnd = true;
			update(getGraphics());
		}
	}

	private void newLineSession_LMPressed(Point mpt, EditorMode mode) {
		if (_now_selectedElement != null) {
			_now_selectedElement.setIsSelected(false);
			_now_selectedElement = null;
			_isForceUpdStGnd = true;
			_isForceUpdDynGnd = true;
		}
		if (_now_mouseOveringElement != null) {
			_now_mouseOveringElement.setIsSelected(true);
			_now_selectedElement = _now_mouseOveringElement;
			_isForceUpdStGnd = true;
			_isForceUpdDynGnd = true;
			if (mode == EditorMode._association) {
				var line = new AssociationLineElement();
				line.setFromJoint((JointElement) _now_selectedElement);
				_dynElement = line;
				_dynElement.setIsVisible(true);
			}
			if (mode == EditorMode._generalization) {
				var line = new GeneralizationLineElement();
				line.setFromJoint((JointElement) _now_selectedElement);
				_dynElement = line;
				_dynElement.setIsVisible(true);
			}
			if (mode == EditorMode._composition) {
				var line = new CompositionLineElement();
				line.setFromJoint((JointElement) _now_selectedElement);
				_dynElement = line;
				_dynElement.setIsVisible(true);
			}

		}
		if (_isForceUpdStGnd)
			update(getGraphics());
	}

	private void newLineSession_MDragged(Point mpt) {
		var o = getOrigin();
		Element _post_mouseOveringElement = null;
		for (var jele : getJoints()) {
			if (jele.isPointIn(mpt.x - o.x, mpt.y - o.y)) {
				_post_mouseOveringElement = jele;
				break;
			}
		}
		if (_now_mouseOveringElement != _post_mouseOveringElement) {
			if (_now_mouseOveringElement != null) {
				_now_mouseOveringElement.setIsMouseOver(false);
				_now_mouseOveringElement = null;
			}
			if (_post_mouseOveringElement != null) {
				_now_mouseOveringElement = _post_mouseOveringElement;
				_now_mouseOveringElement.setIsMouseOver(true);
			}
			_isForceUpdDynGnd = true;
			update(getGraphics());
		}
		if (_dynElement != null) {
			_dynElement.setPt2(mpt.x - o.x, mpt.y - o.y);
			_isForceUpdDynGnd = true;
			update(getGraphics());
		}
	}

	private void newLineSession_LMReleased(Point mpt) {
		if (_now_selectedElement != null) {
			_now_selectedElement.setIsSelected(false);
			_now_selectedElement = null;
			_isForceUpdStGnd = true;
			_isForceUpdDynGnd = true;
		}
		if (_now_mouseOveringElement != null) {
			_now_mouseOveringElement.setIsSelected(true);
			_now_selectedElement = _now_mouseOveringElement;
			_isForceUpdStGnd = true;
			_isForceUpdDynGnd = true;
			if (_dynElement != null) {
				((LineElement) _dynElement).setToJoint((JointElement) _now_selectedElement);
				for (var ele : _elements) {
					ele.incDepth();
				}
				_dynElement.setDepth(0);
				_dynElement.init();
				_elements.add(_dynElement);
				Collections.sort(_elements, (l, r) -> l.getDepth() - r.getDepth());
				_dynElement = null;
			}

		} else {
			if (_dynElement != null) {
				_dynElement.setIsVisible(false);
				_isForceUpdStGnd = true;
				_isForceUpdDynGnd = true;
				_dynElement = null;
			}

		}
		if (_isForceUpdStGnd)
			update(getGraphics());
	}

	public void setAGroup() {
		if (_groupingElements.size() > 1) {
			GroupElement gDynEle = new GroupElement();
			for (var ele : _groupingElements) {
				ele.setOwner(gDynEle);
			}
			gDynEle.setGroupedElements(new ArrayList<Element>(_groupingElements));
			_groups.add(gDynEle);
			gDynEle.setIsVisible(true);
			gDynEle.setDepth(0);
			for (var ele : _elements) {
				ele.incDepth();
			}
			_elements.add(gDynEle);
			gDynEle.init();
			Collections.sort(_elements, (l, r) -> l.getDepth() - r.getDepth());
			for(var ge:_groupingElements) {
				ge.setIsSelected(false);
			}
			_groupingElements.clear();
			_isForceUpdStGnd = true;
			update(getGraphics());
		}
	}
	
	public String getAName() {
		if(_now_selectedElement!=null) {
			return _now_selectedElement.getContext();
		}
		return null;
	}
	
	public void setAName(String context) {
		if(_now_selectedElement!=null) {
			_now_selectedElement.setContext(context);
			_isForceUpdStGnd = true;
			_isForceUpdDynGnd = true;
			update(getGraphics());
		}
	}
	
	public void unsetAGroup() {
		if(_now_selectedElement instanceof GroupElement) {
			_elements.remove(_now_selectedElement);
			for(var ele : _elements) {
				if(ele instanceof GroupElement) {
					if(((GroupElement) ele).getGroupedElements().contains(_now_selectedElement)) {
						((GroupElement) ele).getGroupedElements().remove(_now_selectedElement);
					}
					if(((GroupElement) ele).getGroupedElements().size()==0) {
						_elements.remove(ele);
					}
				}
			}
			_now_selectedElement.setIsSelected(false);
			_now_selectedElement = null;
			_isForceUpdStGnd = true;
			_isForceUpdDynGnd = true;
			update(getGraphics());
		}
	}
}
