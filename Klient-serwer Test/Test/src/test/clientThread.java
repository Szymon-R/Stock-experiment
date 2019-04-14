/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Szymon
 */

class clientThread extends Thread {

  private String ID;

    Socket client;
    OutputStream outToServer;
    DataOutputStream out;
    InputStream inFromServer;
    DataInputStream in;
  public clientThread(String ID)
  {
    this.ID=ID;
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

    try {

      connect(6002, "192.168.1.6");
      String double_data='@'+"data"+'#'+'@'+"[0-10%]"+'#';
      String single_data='@'+"data"+'#';
      

      while (true) 
      {
        if(in.available()!=0)
        {
            System.out.println(ID+" "+in.readUTF());
            break;
        }
        Thread.sleep(50);
      }
      System.out.print(ID+":"+" 1\n");
      write_data(out, double_data);
      
      System.out.print(ID+":"+" 2\n");
      while (true) 
      {
        if(in.available()!=0)
        {
            System.out.println(ID+" "+in.readUTF());
            break;
        }
        Thread.sleep(50);
      } 
      System.out.print(ID+":"+" 2\n");
      write_data(out, single_data);
      

      while (true) 
      {
        if(in.available()!=0)
        {
            System.out.println(ID+" "+in.readUTF());
            break;
        }
        Thread.sleep(50);
      } 
      System.out.print(ID+":"+" 3\n");
      write_data(out, double_data);
      
      
      
      
      
      while (true) 
      {
        if(in.available()!=0)
        {
            System.out.println(in.readUTF());
            break;
        }
        Thread.sleep(50);
      }
      System.out.print(ID+":"+" 4\n");
      write_data(out, '@'+"2850"+'#'+'@'+"[0-10%]"+'#');
      
      while (true) 
      {
        if(in.available()!=0)
        {
            System.out.println(in.readUTF());
            break;
        }
        Thread.sleep(50);
      }
     

      
      String stock="@,Coca Cola,46.58,200000.0,;,Google,1170.00,200000.0,;,Apple,188.88,100000.0,;#";
      System.out.print(ID+":"+" 5\n");
      write_data(out, stock);
      
      while (true) 
      {
        if(in.available()!=0)
        {
            System.out.println(in.readUTF());
            break;
        }
        Thread.sleep(50);
      }
      
      while (true) 
      {
        if(in.available()!=0)
        {
            System.out.println(in.readUTF());
            break;
        }
        Thread.sleep(50);
      }
   
      System.out.print(ID+":"+" 6\n");
      write_data(out, '@'+ID+'#');
      
      while (true) 
      {
        if(in.available()!=0)
        {
            System.out.println(in.readUTF());
            break;
        }
        Thread.sleep(50);
      }

      while (true) 
      {
        if(in.available()!=0)
        {
            System.out.println("Punkty:"+ in.readUTF());
            break;
        }
        Thread.sleep(50);
      }
      
      
      
  
      in.close();
      out.close();

    } catch (Exception e) {
    }
    return;
  }
  
  public String substract_ID(String data)
  {
      int temp;
      temp=data.indexOf(':');
      return(data.substring(0,temp));
  }
  
    public int connect(int port, String name)
    {
        try{
            System.out.println("Tworzenie socketa");
            client=new Socket(name, port);
            client.setSoTimeout(1000);
            outToServer = client.getOutputStream();
            out = new DataOutputStream(outToServer);
            inFromServer = client.getInputStream();
            in = new DataInputStream(inFromServer);
            System.out.println("Utworzono socket");
            return 1;
        }
        catch (IOException ex) {
            ;
        }
        return 0;
    }
}



