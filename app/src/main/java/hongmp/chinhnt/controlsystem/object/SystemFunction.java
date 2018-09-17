package hongmp.chinhnt.controlsystem.object;

import org.w3c.dom.Node;

import java.io.Serializable;

public class SystemFunction implements Serializable{
    private int port;
    private String name;
    private String code;
    private String node;

    public SystemFunction(int port, String name, String code, String node) {
        this.port = port;
        this.name = name;
        this.code = code;
        this.node = node;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getNode(){return node;}
}
