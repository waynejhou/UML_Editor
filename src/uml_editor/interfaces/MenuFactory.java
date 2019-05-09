package uml_editor.interfaces;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionListener;

public abstract class MenuFactory {
    public static Menu newMenu(String header) {
        return new Menu(header);
    }
    public static Menu newMenu(String header, MenuItem...menuItems) {
        var ret = new Menu(header);
        for(var mi : menuItems) {
            ret.add(mi);
        }
        return ret;
    }
    public static MenuBar newMenuBar() {
        return new MenuBar();
    }
    public static MenuBar newMenuBar(Menu... menus) {
        var ret = new MenuBar();
        for(var m : menus) {
            ret.add(m);
        }
        return ret;
    }
    public static MenuItem newMenuItem(String header) {
        return new MenuItem(header);
    }

    public static MenuItem newMenuItem(String header, ActionListener... actions) {
        var ret = new MenuItem(header);
        for(var act : actions) {
            ret.addActionListener(act);
        }
        return ret;
    }
}
