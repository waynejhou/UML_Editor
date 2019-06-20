package uml_editor.utils.sessions;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import uml_editor.views.components.UMLPanel;
import uml_editor.views.shapes.GroupShape;
import uml_editor.views.shapes.JointPoint;
import uml_editor.views.shapes.creators.ILineShapeCreator;
import uml_editor.views.shapes.lines.LineShape;

public class CreateLineShapeSession extends UMLSession {

    public CreateLineShapeSession(ILineShapeCreator creator) {
        _creator = creator;
    }

    private ILineShapeCreator _creator;
    private JointPoint _MouseHoveringJointPoint;
    private JointPoint _SelectedJointPoint;
    private List<JointPoint> _GroupedJointPoints = new LinkedList<JointPoint>();
    private LineShape _previewLineShape;
    private GroupShape _draggingGroupShape;

    /**
     * 如果 "Create Line Shape Session" 被解除或啟動
     */
    @Override
    public void setHost(UMLPanel value) {
        if (value == null) { // 被解除
            for (var jpt : getAllJointPoints()) {
                jpt.setIsSelected(false);
                jpt.setIsVisible(false);
            }
            H.DynamicShapes.clear();
            H.requestUpdateStaticground();
            H.requestUpdateDynamicground();
            H.repaint();
            H.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            super.setHost(value);
        } else { // 啟動
            super.setHost(value);
            for (var jpt : getAllJointPoints()) {
                jpt.setIsVisible(true);
            }
            H.requestUpdateStaticground();
            H.requestUpdateDynamicground();
            H.repaint();
            H.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        }
    }

    /**
     * 滑鼠漫遊事件：<br>
     * - 高亮(候補要被選的)連接點
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        var o = H.getOrigin();
        var mpt = e.getPoint();
        // 高亮滑鼠移動過的連接點
        var hoveringJointPoint = getPointOverJointPoint(mpt.x - o.x, mpt.y - o.y);
        // 如果高亮的連接點變更，或是不高亮了
        if (_MouseHoveringJointPoint != hoveringJointPoint) {
            // 高亮連接點
            if (hoveringJointPoint != null) {
                hoveringJointPoint.setIsHovering(true);
                H.DynamicJointPoints.add(hoveringJointPoint);
            }
            // 不高亮連接點
            if (_MouseHoveringJointPoint != null) {
                _MouseHoveringJointPoint.setIsHovering(false);
                if(!_MouseHoveringJointPoint.getIsSelected())
                H.DynamicJointPoints.remove(_MouseHoveringJointPoint);
            }
            _MouseHoveringJointPoint = hoveringJointPoint;
            H.requestUpdateDynamicground();
        }
        if (H.isRequestedUpdate())
            H.repaint();
    }

    /**
     * 滑鼠按下事件：<br>
     * - 選取單一連接點<br>
     * 以及以下事件的開始動作：<br>
     * - 框選多重連接點<br>
     * - 新增線條的啟動（與選取單一物件同時執行）<br>
     */
    @Override
    public void mousePressed(MouseEvent e) {
        var o = H.getOrigin();
        var mpt = e.getPoint();
        if(_SelectedJointPoint!=null) { // 取消之前選取的連接點
            _SelectedJointPoint.setIsSelected(false);
            H.DynamicShapes.remove(_SelectedJointPoint);
            H.LineShapeCollection.getShapes().stream()// 相連的線都 強調
            .filter(x->
            x.getFromJoint() == _SelectedJointPoint||
            x.getToJoint() == _SelectedJointPoint)
            .forEach(x->{x.setIsSelected(false);});
            _SelectedJointPoint = null;
        }
        if(!_GroupedJointPoints.isEmpty()) { // 取消之前的已框選連接點
            _GroupedJointPoints.stream().forEach(x->x.setIsSelected(false));
            _GroupedJointPoints.clear();
            H.DynamicShapes.removeAll(_GroupedJointPoints);
            H.requestUpdateDynamicground();
            H.requestUpdateStaticground();
        }
        if (_MouseHoveringJointPoint != null && _SelectedJointPoint!=_MouseHoveringJointPoint) {
            //  選取單一連接點，前提是有高亮中的連接點
            _SelectedJointPoint = _MouseHoveringJointPoint;
            _SelectedJointPoint.setIsSelected(true);
            H.DynamicJointPoints.add(_SelectedJointPoint);
            H.LineShapeCollection.getShapes().stream()// 相連的線都 強調
            .filter(x->
            x.getFromJoint() == _SelectedJointPoint||
            x.getToJoint() == _SelectedJointPoint)
            .forEach(x->{x.setIsSelected(true);});
            // 新增線條的啟動，指定起始連接點
            _previewLineShape = _creator.cr8Shape();
            _previewLineShape.setFromJoint(_SelectedJointPoint);
            _previewLineShape.setPt2(mpt.x - o.x, mpt.y - o.y);
            _previewLineShape.setIsVisible(true);
            H.DynamicShapes.add(_previewLineShape);
            H.requestUpdateDynamicground();
            H.requestUpdateStaticground();
        }else{
            _draggingGroupShape = new GroupShape(); // 預看框選框框
            H.DynamicShapes.add(_draggingGroupShape);
            _draggingGroupShape.setIsVisible(true);
            _draggingGroupShape.setPt1(mpt.x - o.x, mpt.y - o.y);
        }
        if (H.isRequestedUpdate())
            H.repaint();
    }

    /**
     * 滑鼠拖動事件：<br>
     * - 框選多重物件中<br>
     * - 新增線條的拖動<br>
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (_previewLineShape != null) {
            var o = H.getOrigin();
            var mpt = e.getPoint();
            _previewLineShape.setPt2(mpt.x - o.x, mpt.y - o.y);
            var hoveringJpt = getPointOverJointPoint(mpt.x - o.x, mpt.y - o.y);
            if (hoveringJpt == _previewLineShape.getFromJoint())
                hoveringJpt = null;
            // 如果高亮的連接點變更，或是不高亮了
            if (_MouseHoveringJointPoint != hoveringJpt) {
                // 高亮連接點
                if (hoveringJpt != null) {
                    hoveringJpt.setIsHovering(true);
                    H.DynamicJointPoints.add(hoveringJpt);
                }
                // 不高亮連接點
                if (_MouseHoveringJointPoint != null) {
                    _MouseHoveringJointPoint.setIsHovering(false);
                    if(!_MouseHoveringJointPoint.getIsSelected())
                    H.DynamicJointPoints.remove(_MouseHoveringJointPoint);
                }
                _MouseHoveringJointPoint = hoveringJpt;
                H.requestUpdateDynamicground();
            }
            H.requestUpdateDynamicground();
        }
        if (_draggingGroupShape != null) {
            var o = H.getOrigin();
            var mpt = e.getPoint();
            _draggingGroupShape.setPt2(mpt.x - o.x, mpt.y - o.y);
            H.requestUpdateDynamicground();
        }
        if (H.isRequestedUpdate())
            H.repaint();

    }
    
    /**
     * 滑鼠放開事件：<br>
     * - 框選多重連接點<br>
     * - 新增線條的結束<br>
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if(_MouseHoveringJointPoint!=null) {
            _previewLineShape.setToJoint(_MouseHoveringJointPoint);
            H.LineShapeCollection.addShapes(_previewLineShape);
            H.DynamicShapes.remove(_previewLineShape);
            _MouseHoveringJointPoint.setIsHovering(false);
            H.DynamicShapes.remove(_MouseHoveringJointPoint);
            _MouseHoveringJointPoint = null;
        }else {
            H.DynamicShapes.remove(_previewLineShape);
            _previewLineShape = null;
        }
        
        if (_draggingGroupShape != null) {// 選取框住
            _GroupedJointPoints.addAll(
                    getAllJointPoints()
                    .stream()
                    .filter(x ->
                    _draggingGroupShape.getIsIncluded(x))
                    .collect(Collectors.toList()));
            _GroupedJointPoints.stream().forEach(x->x.setIsSelected(true));
            _draggingGroupShape.setIsVisible(false);
            H.DynamicShapes.remove(_draggingGroupShape);
            H.DynamicJointPoints.addAll(_GroupedJointPoints);
            _draggingGroupShape = null;
        }
        H.requestUpdateDynamicground();
        H.requestUpdateStaticground();
        H.repaint();
    }

    /**
     * 尋找滑鼠點之下的連接點
     * @param x 滑鼠點X
     * @param y 滑鼠點Y
     * @return 連接點(找不到是 null)
     */

    public JointPoint getPointOverJointPoint(int x, int y) {
        for (var jpt : getAllJointPoints())
            if (jpt.contains(x,y))
                return jpt;
        return null;
    }
    
    /**
     * 取得所有的連接點
     * @return 所有的連接點
     */
    List<JointPoint> getAllJointPoints(){
        return H.CanBeJointedShapeCollection
                .getShapes().stream()
                .flatMap(x->x.getAllJointPoints().stream())
                .collect(Collectors.toList());
    }
}
