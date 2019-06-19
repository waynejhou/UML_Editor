package uml_editor.utils;

public class Style {
   
    public Style(Setter...attributes ) {
        setAttributes(attributes);
    }
    
    private Setter[] _Attributes = null;

    public Setter[] getAttributes() {
        return _Attributes;
    }

    public void setAttributes(Setter[] value) {
        _Attributes = value;
    }
}
