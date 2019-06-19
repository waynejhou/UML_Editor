package uml_editor.utils;

import java.util.Arrays;

public class Setter {
    public Setter(String name, Object... values) {
        setName(name);
        setValues(values);
    }
    
    private String _Name = "";
    public String getName() {
        return _Name;
    }
    public void setName(String value) {
        _Name = value;
    }
    
    private Object[] _Values = null;
    public Object[] getValues() {
        return _Values;
    }
    public void setValues(Object[] values) {
        _Values = values;
    }

    public Class[] getValuesClasses() {
        return Arrays.stream(_Values)
                .map(x->x.getClass().getSimpleName().contains("$$Lambda$")?x.getClass().getInterfaces()[0] :x.getClass())
                .toArray(Class[]::new);
    }
}
