package test;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.net.ServerSocket;
import java.nio.charset.Charset;
import java.util.concurrent.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/*
 * A chat server that delivers public and private messages.
 */

public class Test {
    
    
  // This chat server can accept up to maxClientsCount clients' connections.
     int maxClientsCount = 50;
     clientThread[] clients = new clientThread[maxClientsCount];

    public static void main(String args[]) {

       Test t1=new Test();
    
        int portNumber = 6002;
        System.out.println("Opening port "+ portNumber);

            for (int i = 0; i < t1.maxClientsCount; i++) 
            {
              if (t1.clients[i] == null) 
              {
                t1.clients[i] = new clientThread(Integer.toString(i+1));
                t1.clients[i].start();
              }
            }
    }
}