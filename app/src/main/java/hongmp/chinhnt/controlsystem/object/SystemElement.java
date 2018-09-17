package hongmp.chinhnt.controlsystem.object;

import java.io.Serializable;
import java.util.ArrayList;

public class SystemElement implements Serializable {
    String name;
    ArrayList<SystemFunction> listFunctions;

    public SystemElement(String name, ArrayList<SystemFunction> listFunctions) {
        this.name = name;
        this.listFunctions = listFunctions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<SystemFunction> getListFunctions() {
        return listFunctions;
    }

    public String getAppendFunctions(){
        StringBuilder builder=new StringBuilder();
        for (SystemFunction x : listFunctions){
            builder.append(x.getName());
            builder.append(" \n");
        }

        return builder.toString();
    }

    public void setListFunctions(ArrayList<SystemFunction> listFunctions) {
        this.listFunctions = listFunctions;
    }
}
