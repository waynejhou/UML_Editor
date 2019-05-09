package uml_editor.interfaces;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Panel;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import javax.swing.BoxLayout;

import uml_editor.Pair;
import wayneUI.Setter;
import wayneUI.Style;

public abstract class ComponentFactory {
    public static <T> T newComponent(Class<T> type) {
        try {
            T ret = type.getDeclaredConstructor().newInstance();
            return ret;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            return null;
        }
    }
    public static <T> T newComponent(Class<T> type, Setter...setters) {
        try {
            T ret = type.getDeclaredConstructor().newInstance();
            setSetters(ret, setters);
            return ret;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            return null;
        }
    }
    public static <T> T newComponent(Class<T> type, Style style) {
        try {
            T ret = type.getDeclaredConstructor().newInstance();
            setStyle(ret, style);
            return ret;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            return null;
        }
    }
    public static <T> T newComponent(Class<T> type, Style style, Setter...setters){
        try {
            T ret = type.getDeclaredConstructor().newInstance();
            setStyle(ret, style);
            setSetters(ret, setters);
            return ret;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            return null;
        }
    }

    public static <T> void setStyle(T component, Style style) {
        setSetters(component, style.getAttributes());
    }
    public static <T> void setSetters(T component, Setter...attributes) {
        for(var attr : attributes) {
            boolean founded = false;
            for (var m : component.getClass().getMethods()) {
                if(m.getName().equals("set"+attr.getName())) {
                    try {
                        m.invoke(component, attr.getValues());
                        founded = true;
                        break;
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        continue;
                    }
                }
                if(m.getName().equals("add"+attr.getName())) {
                    try {
                        m.invoke(component, attr.getValues());
                        founded = true;
                        break;
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        continue;
                    }
                }
            }
            if(founded==false) {
                System.err.println("setter "+attr.getName()+"method not found");
            }
        }
    }

    public static Panel newPanel(Component...components) {
        var ret = new Panel();
        for(var com :components) {
            ret.add(com);
        }
        return ret;
    }
    public static Panel newBorderPanel(Pair<String, Component>...components) {
        var ret = new Panel(new BorderLayout());
        for(var com :components) {
            ret.add(com.two, com.one);
        }
        return ret;
    }
    public static Panel newBoxPanel(int axis, Component...components) {
        var ret = new Panel();
        ret.setLayout(new BoxLayout(ret, axis));
        for(var com :components) {
            ret.add(com);
        }
        return ret;
    }

}