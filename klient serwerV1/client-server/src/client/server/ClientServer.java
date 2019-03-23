/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.server;
import java.net.*;
import java.io.*;
/**
 *
 * @author Szymon
 */public class ClientServer extends Thread {
   private ServerSocket serverSocket;
   
   public ClientServer(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      //serverSocket.setSoTimeout(10000);
   }

   public void run() {
       String input;
      while(true) {
         try {
            System.out.println("Waiting for client on port " + 
               serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());
            
            //System.out.println(in.readUTF());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF("Looks like we are connected");
            input=in.readUTF();
            System.out.println(input);
            server.close();
            
         } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         } catch (IOException e) {
            e.printStackTrace();
            break;
         }
      }
   }


    /**
     * @param args the command line arguments
     */
    public static void main(String [] args) {
     
      try {
         Thread t = new ClientServer(6666);
         t.start();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
