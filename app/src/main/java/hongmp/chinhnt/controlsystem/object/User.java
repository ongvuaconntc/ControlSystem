package hongmp.chinhnt.controlsystem.object;


import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String log_list;
    private String session_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getLog_list() {
        return log_list;
    }

    public void setLog_list(String log_list) {
        this.log_list = log_list;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
