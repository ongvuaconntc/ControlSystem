package hongmp.chinhnt.controlsystem.object;


import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String password;
    private int role;
    private String log_list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getLog_list() {
        return log_list;
    }

    public void setLog_list(String log_list) {
        this.log_list = log_list;
    }
}
