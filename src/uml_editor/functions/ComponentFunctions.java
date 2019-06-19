package uml_editor.functions;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Panel;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BoxLayout;

import uml_editor.utils.Pair;
import uml_editor.utils.Setter;
import uml_editor.utils.Style;

public abstract class ComponentFunctions {
    public static <T> T cr8Comp(Class<T> type, Style style, Setter...setters){
        try {
            T ret = type.getDeclaredConstructor().newInstance();
            if(style!=null)setStyle(ret, style);
            if(setters!=null)setSetters(ret, setters);
            return ret;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> void setStyle(T component, Style style) {
        setSetters(component, style.getAttributes());
    }
    public static <T> void setSetters(T component, Setter...attributes) {
        for(var attr : attributes) {
            boolean founded = false;
            Exception exception = null;
            for (var m : component.getClass().getMethods()) {
                if(m.getName().equals("set"+attr.getName())) {
                    try {
                        m.invoke(component, attr.getValues());
                        founded = true;
                        break;
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        exception =e;
                        continue;
                    }
                }
                if(m.getName().equals("add"+attr.getName())) {
                    try {
                        m.invoke(component, attr.getValues());
                        founded = true;
                        break;
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        exception =e;
                        continue;
                    }
                }
            }
            if(founded==false) {
                System.err.println("setter "+attr.getName()+" method not found in " + component.getClass());
                exception.printStackTrace();
            }
        }
    }

    public static Panel cr8Panel(Component...components) {
        var ret = new Panel();
        for(var com :components) {
            ret.add(com);
        }
        return ret;
    }
    @SafeVarargs
    public static Panel cr8BorderPanel(Pair<String, Component>...components) {
        var ret = new Panel(new BorderLayout());
        for(var com :components) {
            ret.add(com.two, com.one);
        }
        return ret;
    }
    public static Panel cr8BoxPanel(int axis, Component...components) {
        var ret = new Panel();
        ret.setLayout(new BoxLayout(ret, axis));
        for(var com :components) {
            ret.add(com);
        }
        return ret;
    }

}