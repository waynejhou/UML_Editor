package uml_editor.utils;


import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import uml_editor.views.shapes.BaseShape;

public class ShapeList<T extends BaseShape>{

    private LinkedList<T> Shapes = new LinkedList<T>();
    private int maxDepth = 0;
    
    public List<T> getShapes() {
        return Collections.unmodifiableList(Shapes);
    }

    @SuppressWarnings("unchecked")
    public void addShapes(T... shapes) {
        addShapes(Arrays.asList(shapes));
    }

    public void addShapes(List<T> shapes) {
        for (var s : shapes) {
            s.setDepth(maxDepth++);
            Shapes.add(s);
        }
    }
    
    @SuppressWarnings("unchecked")
    public void setShapes(T... shapes) {
        setShapes(Arrays.asList(shapes));
    }

    public void setShapes(List<T> shapes) {
        clearShapes();
        addShapes(shapes);
    }
    
    @SuppressWarnings("unchecked")
    public void removeShapes(T... shapes) {
        removeShapes(Arrays.asList(shapes));
    }

    public void removeShapes(List<T> shapes) {
        for (var s : shapes) {
            Shapes.remove(s);
        }
    }
    public void removeBaseShapes(BaseShape... shapes) {
        for (var s : shapes) {
            Shapes.remove(s);
        }
    }

    public void removeBaseShapes(List<BaseShape> shapes) {
        for (var s : shapes) {
            Shapes.remove(s);
        }
    }
    
    public void clearShapes() {
        Shapes.clear();
        maxDepth = 0;
    }
    


}
