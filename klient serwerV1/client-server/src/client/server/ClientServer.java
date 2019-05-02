/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.server;
import java.net.*;
import java.io.*;
import java.util.Random;
import java.util.Vector;
/**
 *
 * @author Szymon
 */public class ClientServer extends Thread {

   
   public ClientServer(int port) throws IOException {

      //serverSocket.setSoTimeout(10000);
   }
   int size=30000;
   public void run() {
    Vector<Double> liczby=new Vector<Double>();
    for(int i=0; i<size;++i)
    {
        liczby.add((double)i);
    }
     Random rand = new Random();
    while(true)
    {
        try
        {
       int n = rand.nextInt(size);
       System.out.println(liczby.get(n));
       this.wait(100);
        }
        catch(Exception e){};
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
