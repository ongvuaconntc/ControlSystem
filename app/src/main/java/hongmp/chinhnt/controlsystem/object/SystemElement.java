package hongmp.chinhnt.controlsystem.object;

import java.io.Serializable;
import java.util.ArrayList;

public class SystemElement implements Serializable {
    String name;
    String id;
    String xml;

    public SystemElement(String name, String id, String xml) {
        this.name = name;
        this.id = id;
        this.xml = xml;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return xml;
    }

    public void setCode(String code) {
        this.xml = code;
    }


}
