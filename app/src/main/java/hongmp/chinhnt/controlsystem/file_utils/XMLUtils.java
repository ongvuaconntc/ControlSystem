package hongmp.chinhnt.controlsystem.file_utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class XMLUtils {
    static public void writeToLocal(String path,String xml){
        try {
            File dir = new File("/data/data/hongmp.chinhnt.controlsystem/files/");
            if(!dir.exists()) {
                // do something here
                dir.mkdirs();
            }
            String fileName ="/data/data/hongmp.chinhnt.controlsystem/files/"+path;
            System.out.println("try to write to "+fileName);
            File file = new File(fileName);
            FileOutputStream fis = new FileOutputStream(file);
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter br = new BufferedWriter(isr);

            br.write(xml);
            br.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
