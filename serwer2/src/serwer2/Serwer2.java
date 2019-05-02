package serwer2;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.nio.charset.Charset;
import java.util.concurrent.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/*
 * A chat server that delivers public and private messages.
 */

public class Serwer2 {

  // The server socket.
  private static ServerSocket serverSocket = null;
  // The client socket.
  private static Socket clientSocket = null;

  // This chat server can accept up to maxClientsCount clients' connections.
  private static final int maxClientsCount = 80;
  private static final clientThread[] clients = new clientThread[maxClientsCount];
 
  public static void main(String args[]) {
 
    Semaphore s = new Semaphore(1);
    System.out.println("Starting Server API");
    synchro syn=new synchro();
    Serwer_API SA1=new Serwer_API(syn);
    int message_buff=10;
    SA1.addWindowListener(new WindowAdapter() 
    {
        public void windowClosing(WindowEvent we) 
        {
            int result = JOptionPane.showConfirmDialog(SA1,"Jesteś pewien, że chcesz zamknąc program?", "Potwierdzenie",JOptionPane.YES_NO_OPTION);
            if(result == JOptionPane.YES_OPTION)
                SA1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            else if(result == JOptionPane.NO_OPTION)
                SA1.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            else if(result==JOptionPane.CLOSED_OPTION)
                SA1.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
    });
    
    java.awt.EventQueue.invokeLater(new Runnable() {
    public void run() 
    {
        SA1.setVisible(true);
    }
    });
    
    int portNumber = 6002;
    System.out.println("Opening port "+ portNumber);
    try {
      serverSocket = new ServerSocket(portNumber);
    } catch (IOException e) {
      System.out.println(e);
    }

    

    
    
    
      Thread thread = new Thread() {
          public void run() 
          {
              String data;
              try 
              {
                while(true)
                {
                    System.out.println("Starting listening");
                    clientSocket = serverSocket.accept();
                    System.out.println("New client found");
                    int i = 0;
                    for (i = 0; i < maxClientsCount; i++) {
                      if (clients[i] == null) 
                      {
                        BlockingQueue<String> MOSI= new LinkedBlockingQueue<String>(message_buff);
                        BlockingQueue<String> MISO= new LinkedBlockingQueue<String>(message_buff);
                        String ID=Integer.toString(i+1);
                        SA1.new_client(MISO, MOSI,ID);
                       (clients[i] = new clientThread(clientSocket,ID,MOSI,MISO,syn)).start();
                        break;
                      }
                    }
                    if (i == maxClientsCount) {
                      PrintStream os = new PrintStream(clientSocket.getOutputStream());
                      os.println("Server too busy. Try later.");
                      os.close();
                      clientSocket.close();
                    }
                }
              }
              catch (Exception e) 
              {
                  System.out.println(e);
              }
          }
      };
      thread.start();
  
  }
}

/*
 * The chat client thread. This client thread opens the input and the output
 * streams for a particular client, ask the client's name, informs all the
 * clients connected to the server about the fact that a new client has joined
 * the chat room, and as long as it receive data, echos that data back to all
 * other clients. The thread broadcast the incoming messages to all clients and
 * routes the private message to the particular client. When a client leaves the
 * chat room this thread informs also all the clients about that and terminates.
 */
class clientThread extends Thread {

  private DataInputStream is = null;
  OutputStream outToClient;
  DataOutputStream os;
  InputStream inFromClient;
  DataInputStream in;
  private Socket clientSocket = null;
  private int maxClientsCount;
  BlockingQueue<String> copy;
  private String ID;
  BlockingQueue<String> input_queue;
  BlockingQueue<String> output_queue;
  synchro syn;
  static Semaphore semaphore = new Semaphore(1);
  public clientThread(Socket clientSocket,String ID,BlockingQueue<String> input_queue,BlockingQueue<String> output_queue,synchro syn) {
    this.clientSocket = clientSocket;
    this.ID=ID;
    this.syn=syn;
    this.input_queue=input_queue;
    this.output_queue=output_queue;
  }
  public String get_ID()
  {
      try
      {
          return(ID+":");
      }
      catch(Exception e)
      {
          return "";
      }
  }
  public boolean write_data(DataOutputStream os, String data)
  {

      try
      {
        System.out.println("Rozpoczynanie wysyłania");
        os.writeUTF(data); 
        System.out.println("Wysłano");
        return true;
      }
      catch(Exception e)
      {
        return false;
      }
  }
 
  public void run()  
  {
    try
    {
      inFromClient = clientSocket.getInputStream();
      outToClient = clientSocket.getOutputStream();
      os=new DataOutputStream(outToClient);
      is=new DataInputStream(inFromClient);

        while(true)
        {
        
            String data;
            if(!input_queue.isEmpty())
            {
                data=input_queue.take();
                while(!write_data(os,data));
            }

            if(is.available()!=0)
            {
                String temp=is.readUTF();
                System.out.println("Serwer: Coś przyszło, wrzucam na kolejkę "+temp);
                output_queue.put(ID+": "+temp);
            }
        
        this.sleep(100);
        }
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
  }
}