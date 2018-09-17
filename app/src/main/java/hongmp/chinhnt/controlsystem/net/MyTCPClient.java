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

public class MyTCPClient {

    public Socket socket;
    OutputStream output;
    InputStream input;
    private Scanner scanner;
    public MyTCPClient(InetAddress serverAddress, int serverPort) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
        this.scanner = new Scanner(System.in);
        input = socket.getInputStream();

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

        return list;
     }

     public void removeFunction(String node,String function){

     }


}
