package uml_editor.utils.sessions;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.Collectors;

import uml_editor.views.components.UMLPanel;
import uml_editor.views.shapes.BaseShape;
import uml_editor.views.shapes.CanBeJointedShape;
import uml_editor.views.shapes.GroupShape;
import uml_editor.views.shapes.lines.LineShape;

public final class SelectSession extends UMLSession {

    private BaseShape _MouseHoveringShape;
    private BaseShape _SelectedShape;
    private GroupShape _DraggingGroupShape;
    private GroupShape _GroupedShape;

    private int _dragTempPtX;
    private int _dragTempPtY;
    private int _draggedElePtX;
    private int _draggedElePtY;

    /**
     * 如果 "Selection Session" 被解除
     */
    @Override
    public void setHost(UMLPanel value) {
        if (value == null) {

            for (BaseShape shape : H.DynamicShapes) {
                shape.setIsSelected(false);
                shape.setIsHovering(false);
            }
            H.DynamicShapes.clear();
            H.requestUpdateDynamicground();
            H.requestUpdateStaticground();
            H.repaint();
        }
        super.setHost(value);
    }

    /**
     * 滑鼠漫遊事件：<br>
     * - 高亮(候補要被選的)物件
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        var o = H.getOrigin();
        var mpt = e.getPoint();
        // 高亮滑鼠移動過的東西
        var hoveringShape = getPointOverShape(mpt.x - o.x, mpt.y - o.y);
        // 如果高亮的物件變更，或是不高亮了
        if (_MouseHoveringShape != hoveringShape) {
            // 高亮物件
            if (hoveringShape != null) {
                hoveringShape.setIsHovering(true);
                H.DynamicShapes.add(hoveringShape);
            }
            // 不高亮上個物件
            if (_MouseHoveringShape != null) {
                _MouseHoveringShape.setIsHovering(false);
                if (!_MouseHoveringShape.getIsSelected())
                    H.DynamicShapes.remove(_MouseHoveringShape);
            }
            _MouseHoveringShape = hoveringShape;
            H.requestUpdateDynamicground();
        }
        if (H.isRequestedUpdate())
            H.repaint();
    }

    
    /**
     * 滑鼠按下事件：<br>
     * - 選取單一物件<br>
     * 以及以下事件的開始動作：<br>
     * - 框選多重物件<br>
     * - 拖動選擇的物件（與選取單一物件同時執行）<br>
     */
    @Override
    public void mousePressed(MouseEvent e) {
        var o = H.getOrigin();
        var mpt = e.getPoint();

        if (_SelectedShape != null) { // 取消之前選取的物件
            deselectShape();
            H.requestUpdateDynamicground();
            H.requestUpdateStaticground();
        }
        if(_GroupedShape!=null &&
                (_MouseHoveringShape==null ||
                (_MouseHoveringShape!=null&&_GroupedShape!=_MouseHoveringShape))) {
                _GroupedShape.setGroupedShapes(null);
                _GroupedShape=null;
        }
        if (_MouseHoveringShape != null) {
            // 選取單一物件，前提是有高亮中的物件
            selectShape(_MouseHoveringShape);
            H.requestUpdateDynamicground();
            H.requestUpdateStaticground();
            _dragTempPtX = mpt.x;
            _dragTempPtY = mpt.y;
            _draggedElePtX = _SelectedShape.getX();
            _draggedElePtY = _SelectedShape.getY();
            H.CanBeJointedShapeCollection.getShapes().stream().filter(x->x.getOwner()==_SelectedShape).forEach(x->H.DynamicShapes.add(x));
        }else {
            _DraggingGroupShape = new GroupShape();
            _DraggingGroupShape.setPt1(mpt.x-o.x,mpt.y-o.y);
            _DraggingGroupShape.setIsVisible(true);
            H.DynamicShapes.add(_DraggingGroupShape);
        }
        
        if (H.isRequestedUpdate())
            H.repaint();
    }

    /**
     * 滑鼠拖動事件：<br>
     * - 框選多重物件中<br>
     * - 拖動選擇的物件中<br>
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        
        if (_SelectedShape != null) {// 拖動選擇的物件
            var o = H.getOrigin();
            var mpt = e.getPoint();
            _SelectedShape.setX((mpt.x - _dragTempPtX) + _draggedElePtX);
            _SelectedShape.setY((mpt.y - _dragTempPtY) + _draggedElePtY);
            H.requestUpdateDynamicground();
        }
        if(_DraggingGroupShape!=null) {
            var o = H.getOrigin();
            var mpt = e.getPoint();
            _DraggingGroupShape.setPt2(mpt.x-o.x,mpt.y-o.y);
            H.requestUpdateDynamicground();
        }
        if (H.isRequestedUpdate())
            H.repaint();
    }

    /**
     * 滑鼠放開事件：<br>
     * - 框選多重物件結束<br>
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if(_DraggingGroupShape!=null) {
            H.DynamicShapes.remove(_DraggingGroupShape);
            var grouped = H.CanBeJointedShapeCollection.getShapes().stream().filter(x->_DraggingGroupShape.getIsIncluded(x)).collect(Collectors.toList());
            if(grouped.size()>=1) {
                if(grouped.size()==1) {
                    selectShape(grouped.get(0));
                }else {
                    _GroupedShape = new GroupShape();
                    _GroupedShape.setGroupedShapes(new ArrayList<BaseShape>(grouped));
                    _GroupedShape.setIsVisible(true);
                    _GroupedShape.init();
                    selectShape(_GroupedShape);
                }
                
                H.requestUpdateDynamicground();
                H.requestUpdateStaticground();
            }
            _DraggingGroupShape = null;
        }
        if(_SelectedShape!=null) {
            H.CanBeJointedShapeCollection.getShapes().stream().filter(x->x.getOwner()==_SelectedShape).forEach(x->H.DynamicShapes.remove(x));
        }
        H.requestUpdateDynamicground();
        H.requestUpdateStaticground();
        H.repaint();
    }

    /**
     * 尋找滑鼠點之下的物件
     * 
     * @param x 滑鼠點X
     * @param y 滑鼠點Y
     * @return 物件(找不到是 null)
     */
    public BaseShape getPointOverShape(int x, int y) {
        if(_GroupedShape!=null&&_GroupedShape.contains(x,y))
            return _GroupedShape;
        var gl = new LinkedList<GroupShape>(H.GroupShapeCollection.getShapes());
        Collections.reverse(gl);
        for (var shape : gl)
            if (shape.contains(x, y))
                return shape;
        var cl = new LinkedList<CanBeJointedShape>(H.CanBeJointedShapeCollection.getShapes());
        Collections.reverse(cl);
        for (var shape : H.CanBeJointedShapeCollection.getShapes())
            if (shape.contains(x, y)&&shape.getOwner()==null)
                return shape;
        var ll = new LinkedList<LineShape>(H.LineShapeCollection.getShapes());
        Collections.reverse(ll);
        for (var shape : H.LineShapeCollection.getShapes())
            if (shape.contains(x, y))
                return shape;
        return null;
    }

    private void selectShape(BaseShape shape) {
        _SelectedShape = shape;
        _SelectedShape.setIsSelected(true);
        H.DynamicShapes.add(_SelectedShape);
        var grouped = H.CanBeJointedShapeCollection.getShapes().stream()
                .filter(x->x.getOwner()==_SelectedShape).collect(Collectors.toList());
        if(grouped.size()>0) {
            H.LineShapeCollection.getShapes().stream()// 相連的線都 強調 + 動態移動
            .filter(x -> grouped.contains(x.getFromJoint().getOwner())
                    || grouped.contains(x.getToJoint().getOwner()))
            .forEach(x -> {
                H.DynamicShapes.add(x);
                x.setIsSelected(true);
            });
        }else {
            H.LineShapeCollection.getShapes().stream()// 相連的線都 強調 + 動態移動
            .filter(x -> x.getFromJoint().getOwner() == _SelectedShape
                    || x.getToJoint().getOwner() == _SelectedShape)
            .forEach(x -> {
                H.DynamicShapes.add(x);
                x.setIsSelected(true);
            });
        }
    }
    private void deselectShape() {
        _SelectedShape.setIsSelected(false);
        var grouped = H.CanBeJointedShapeCollection.getShapes().stream()
                .filter(x->x.getOwner()==_SelectedShape).collect(Collectors.toList());
        if(grouped.size()>0) {
            H.LineShapeCollection.getShapes().stream()// 相連的線都 強調 + 動態移動
            .filter(x -> grouped.contains(x.getFromJoint().getOwner())
                    || grouped.contains(x.getToJoint().getOwner()))
            .forEach(x -> {
                H.DynamicShapes.remove(x);
                x.setIsSelected(false);
            });
        }else {
            H.LineShapeCollection.getShapes().stream()// 相連的線都 強調 + 動態移動
            .filter(x -> x.getFromJoint().getOwner() == _SelectedShape
                    || x.getToJoint().getOwner() == _SelectedShape)
            .forEach(x -> {
                H.DynamicShapes.remove(x);
                x.setIsSelected(false);
            });
        }
        H.DynamicShapes.remove(_SelectedShape);
        _SelectedShape = null;
    }
    
    public void setAName(String context) {
        if (_SelectedShape == null)
            return;
        _SelectedShape.setContext(context);
        H.requestUpdateDynamicground();
        H.repaint();
    }
    
    public String getAName() {
        if (_SelectedShape == null)
            return "";
        if (_SelectedShape.getContext() == null)
            return "";
        return _SelectedShape.getContext().toString();
    }

    public void setAGroup() {
        if (_GroupedShape!=null) {
            H.GroupShapeCollection.addShapes(_GroupedShape);
            _GroupedShape = null;
        }
    }

    public void unsetAGroup() {
        H.GroupShapeCollection.removeBaseShapes(_SelectedShape);
        H.requestUpdateDynamicground();
        H.requestUpdateStaticground();
        H.repaint();
    }
}
