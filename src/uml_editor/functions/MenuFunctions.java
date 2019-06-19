package uml_editor.functions;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionListener;

public abstract class MenuFunctions {
    public static Menu cr8Menu(String header, MenuItem...menuItems) {
        var ret = new Menu(header);
        for(var mi : menuItems) {
            ret.add(mi);
        }
        return ret;
    }
    public static MenuBar cr8MenuBar(Menu... menus) {
        var ret = new MenuBar();
        for(var m : menus) {
            ret.add(m);
        }
        return ret;
    }
    public static MenuItem cr8MenuItem(String header) {
        return new MenuItem(header);
    }
    public static MenuItem cr8MenuItem(String header, ActionListener... actions) {
        var ret = new MenuItem(header);
        for(var act : actions) {
            ret.addActionListener(act);
        }
        return ret;
    }
}
