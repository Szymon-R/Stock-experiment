package serwer;

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

public class Serwer {

  // The server socket.
  private static ServerSocket serverSocket = null;
  // The client socket.
  private static Socket clientSocket = null;

  // This chat server can accept up to maxClientsCount clients' connections.
  private static final int maxClientsCount = 80;
  private static final clientThread[] clients = new clientThread[maxClientsCount];
  
  public static void main(String args[]) {
    BlockingQueue<String> MOSI= new LinkedBlockingQueue<String>(maxClientsCount);
    BlockingQueue<String> MISO= new LinkedBlockingQueue<String>(maxClientsCount);
    
    System.out.println("Starting Server API");
    Serwer_API SA1=new Serwer_API(MISO,MOSI);
    
    SA1.addWindowListener(new WindowAdapter() 
    {
        public void windowClosing(WindowEvent we) 
        {
            int result = JOptionPane.showConfirmDialog(SA1,"Jesteś pewien, że chcesz zamknąc program?", "Potwierdzenie",JOptionPane.YES_NO_OPTION);
            if(result == JOptionPane.YES_OPTION)
                SA1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            else if(result == JOptionPane.NO_OPTION)
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

    /*
     * Create a client socket for each connection and pass it to a new client
     * thread.
     */
    SA1.get_client_info();
    while (true) {
      try {
        System.out.println("Starting listening");
        clientSocket = serverSocket.accept();
        System.out.println("New client found");
        int i = 0;
        for (i = 0; i < maxClientsCount; i++) {
          if (clients[i] == null) {
            (clients[i] = new clientThread(clientSocket, clients,Integer.toString(i+1),MOSI,MISO)).start();
            break;
          }
        }
        if (i == maxClientsCount) {
          PrintStream os = new PrintStream(clientSocket.getOutputStream());
          os.println("Server too busy. Try later.");
          os.close();
          clientSocket.close();
        }
      } catch (IOException e) {
        System.out.println(e);
      }
    }
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
  private final clientThread[] clients;
  private int maxClientsCount;
  private String ID;
  BlockingQueue<String> input_queue;
  BlockingQueue<String> output_queue;
  
  public clientThread(Socket clientSocket, clientThread[] clients, String ID,BlockingQueue<String> input_queue,BlockingQueue<String> output_queue) {
    this.clientSocket = clientSocket;
    this.clients = clients;
    this.ID=ID;
    this.input_queue=input_queue;
    this.output_queue=output_queue;
    maxClientsCount = clients.length;
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
      System.out.println("Co jest");
      try
      {
        System.out.println("Rozpoczynanie wysyłania");
        System.out.println("Nie czekam");
        os.writeUTF(data); 
        System.out.println("Wysłano");
        return true;
      }
      catch(Exception e)
      {
        return false;
      }
  }
 
  public void run()  {
    System.out.println("Rozpoczynanie wątku");
    int maxClientsCount = this.maxClientsCount;
    clientThread[] threads = this.clients;

    try {
      /*
       * Create input and output streams for this client.
       */

      inFromClient = clientSocket.getInputStream();
      outToClient = clientSocket.getOutputStream();
      os=new DataOutputStream(outToClient);
      is=new DataInputStream(inFromClient);

//waiting for client to send name and surname

      output_queue.put(get_ID());
           
      input_queue.take();
      
      System.out.println(ID+": Sending data");
      String data="GOGO";
      while(!write_data(os,data));
      System.out.println(ID+": Question1 start");
      
      while (true) 
      {
            if(is.available()!=0)
            {
                System.out.println("Coś przyszło, wrzucam na kolejkę");
                output_queue.put(ID+": "+is.readUTF());
                break;
            }
      }
      
      
   /*   while (true) 
      {
            if(is.available()!=0)
            {
                System.out.println("Coś przyszło, wrzucam na kolejkę");
                output_queue.put(ID+": "+is.readUTF());
                break;
            }
      }*/
      
      input_queue.take();
      System.out.println(ID+": Sending data");
      while(!write_data(os,data));
      System.out.println(ID+": Question3 start");
      
      while (true) 
      {
            if(is.available()!=0)
            {
                System.out.println("Coś przyszło, wrzucam na kolejkę");
                output_queue.put(ID+": "+is.readUTF());
                break;
            }
      }
            input_queue.take();
      System.out.println(ID+": Sending data");
      while(!write_data(os,data));
      System.out.println(ID+": Question4 start");
      
      while (true) 
      {
            if(is.available()!=0)
            {
                System.out.println("Coś przyszło, wrzucam na kolejkę");
                output_queue.put(ID+": "+is.readUTF());
                break;
            }
      }

      System.out.println(ID+": Question5 start");
      
/*      while (true) 
      {
            if(is.available()!=0)
            {
                System.out.println("Coś przyszło, wrzucam na kolejkę");
                output_queue.put(ID+": "+is.readUTF());
                break;
            }
      }*/
      input_queue.take();
      System.out.println(ID+": Sending data");
      while(!write_data(os,data));
      System.out.println(ID+": Question6 start");
      
      while (true) 
      {
            if(is.available()!=0)
            {
                System.out.println("Coś przyszło, wrzucam na kolejkę");
                output_queue.put(ID+": "+is.readUTF());
                break;
            }
      }
   /*   System.out.println(ID+": Question7 start");
      
      while (true) 
      {
            if(is.available()!=0)
            {
                System.out.println("Coś przyszło, wrzucam na kolejkę");
                output_queue.put(ID+": "+is.readUTF());
                break;
            }
      }*/
      input_queue.take();
      System.out.println(ID+": Sending data");
      while(!write_data(os,data));
      System.out.println(ID+": Question8 start");
      while (true) 
      {
            if(is.available()!=0)
            {
                System.out.println("Coś przyszło, wrzucam na kolejkę");
                output_queue.put(ID+": "+is.readUTF());
                break;
            }
      }
      input_queue.take();
      System.out.println(ID+": Sending data");
      while(!write_data(os,data));
   /*   System.out.println(ID+": Question9 start");
      while (true) 
      {
            if(is.available()!=0)
            {
                System.out.println("Coś przyszło, wrzucam na kolejkę");
                output_queue.put(ID+": "+is.readUTF());
                break;
            }
      }*/
 /*     while (true) 
      {    
            if(is.available()!=0)
            {
                System.out.println("Coś przyszło, wrzucam na kolejkę");
                output_queue.put(ID+": "+is.readUTF());
                break;
            }
      }*/
    //Tu client czeka na pierwsze rezultaty
     String temp;
     System.out.println("Czekam na wiadomosc z API");
     while(true)
     {
        temp=input_queue.take();
        System.out.println("Z API przyszło "+temp);
        if(substract_ID(temp).equals(ID))
        {
            while(!write_data(os,temp));
            break;
        }
         System.out.println(substract_ID(temp)+" nie pasuje do "+ID);
        input_queue.put(temp);
         sleep(100);
     }

      System.out.println(ID+": Question10 start");
      while (true) 
      {    
            if(is.available()!=0)
            {
                System.out.println("Coś przyszło, wrzucam na kolejkę");
                output_queue.put(ID+": "+is.readUTF());
                break;
            }
      }
        input_queue.take();
        System.out.println(ID+": Sending data");
        while(!write_data(os,data));
        sleep(10000);
       System.out.println("Czekam na wiadomosc z API");
       while(true)
       {
          temp=input_queue.take();
          System.out.println("Z API przyszło "+temp);
          if(substract_ID(temp).equals(ID))
          {
              while(!write_data(os,temp));
              break;
          }
           System.out.println(substract_ID(temp)+" nie pasuje do "+ID);
          input_queue.put(temp);
           sleep(100);
       }
      
  
      is.close();
      os.close();
      clientSocket.close();
    } catch (Exception e) {
    }
  }
  
  public String substract_ID(String data)
  {
      int temp;
      temp=data.indexOf(':');
      return(data.substring(0,temp));
  }
}