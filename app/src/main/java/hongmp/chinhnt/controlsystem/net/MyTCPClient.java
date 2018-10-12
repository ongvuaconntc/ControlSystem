package hongmp.chinhnt.controlsystem.net;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import hongmp.chinhnt.controlsystem.object.SystemElement;
import hongmp.chinhnt.controlsystem.object.SystemFunction;

public class MyTCPClient {

    public Socket socket;
    OutputStream output;
    InputStream input;
    private Scanner scanner;
    public MyTCPClient(InetAddress serverAddress, int serverPort) {
        try {
            this.socket = new Socket(serverAddress, serverPort);
            this.scanner = new Scanner(System.in);
            input = socket.getInputStream();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void readData() throws IOException {
        while (true){
            byte[] data=new byte[100000];
            int res=input.read(data);
        }

    }

    public void writeData(byte[] data) throws IOException {
         output = socket.getOutputStream();
         output.write(data);
         output.close();
     }

     public ArrayList<SystemElement> requestStatus(){
        ArrayList<SystemElement> list=new ArrayList<>();

         SystemFunction function7 = new SystemFunction(1 + "", "Hẹn giờ bật nóng lạnh_", "cửa cửa", "Node2");
         SystemFunction function8 = new SystemFunction(1 + "", "Bật điều hòa_", "cửa cửa", "Node2");
         SystemFunction function9 = new SystemFunction(1 + "", "Bật camera_", "cửa cửa", "Node2");

         ArrayList<SystemFunction> functions_=new ArrayList<>();
         functions_.add(function8);
         functions_.add(function9);
         functions_.add(function7);

         SystemElement element2=new SystemElement("Node2",functions_);
        list.add(element2);
        return list;
     }

     public void removeFunction(String node,String function){

     }


}
