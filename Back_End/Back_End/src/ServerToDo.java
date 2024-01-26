import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class ServerToDo {
    public static void main(String[] args) {
      try(ServerSocket serverSocket1=new ServerSocket(8080)){
          System.out.println("Server started." +"\n"+
                  "Listening for massages.");
          while (true){
             try
                     (Socket client=serverSocket1.accept();) {

                 System.out.println("Debug: got new massage " + client.toString());
                 InputStreamReader isr1=new InputStreamReader(client.getInputStream());
                 BufferedReader br1=new BufferedReader(isr1);
                 StringBuilder request1=new StringBuilder();
                 String line1="";
                 line1= br1.readLine();
                 line1.isBlank();
                 while (!line1.isBlank()){
                     request1.append(line1+"\r\n");
                     line1=br1.readLine();
                 }
                 System.out.println("--REQUEST--");
                 System.out.println(request1.toString());
                 OutputStream clientOutput1=client.getOutputStream();
                 clientOutput1.write(("HTTP/1.1 200 OK\r\n").getBytes());
                 clientOutput1.write(("Hello World").getBytes());
                 clientOutput1.flush();




                 client.close();

             }
          }
      } catch (IOException e) {
          throw new RuntimeException(e);
      }
    }
}
