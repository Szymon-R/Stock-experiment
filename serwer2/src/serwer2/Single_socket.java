/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwer2;
import java.net.*;
import java.io.*;
/**
 *
 * @author Szymon
 */
public class Single_socket extends Thread{
    

   private ServerSocket serverSocket;
   
   public Single_socket(int port) throws IOException {
      serverSocket = new ServerSocket(port);
   }

   public void run() {
      while(true) {
         try {
            System.out.println("Waiting for client on port " + 
               serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
           
            System.out.println("Just connected on port " + serverSocket.getLocalPort());
            DataInputStream in = new DataInputStream(server.getInputStream());
            
            try {
               // server.wait(10000);
                Thread.sleep(10000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //in.readUTF();
            //System.out.println(in.readUTF());
           // DataOutputStream out = new DataOutputStream(server.getOutputStream());
           // out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
           //    + "\nGoodbye!");
          //  server.close();
            
         } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         } catch (IOException e) {
            e.printStackTrace();
            break;
         }
      }
   }
   
}