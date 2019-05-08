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
import java.util.Random;
import java.util.Vector;

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
    int client_count;
  public clientThread(String ID,int client_count)
  {
    this.client_count=client_count;
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
      
      Random rand = new Random();


    System.out.println("Rozpoczynanie wątku");

    try {
      int stop=rand.nextInt(client_count);
      int wait_range=5000;
      int wait=rand.nextInt(wait_range);
      
      connect(6002, "192.168.1.6");
      String double_data='@'+"data"+'#'+'@'+"[0-10%]"+'#';
      String single_data='@'+"data"+'#';
      
      Vector<String> names=new Vector<String>();
      Vector<String> tak_nie=new Vector<String>();
      Vector<String> stocks=new Vector<String>();
      
      names.add("Andrzej Kowalski");
      names.add("Monika Brodka");
      names.add("Michał Wiśniewski");
      names.add("Karol Strasburger");
      names.add("Monty Python");
      int n = rand.nextInt(5);
      Thread.sleep(wait);
      write_data(out,"*2@"+names.get(n)+"#");
      if(stop==1)
          stop();
      n=rand.nextInt(2);
      tak_nie.add("Tak");
      tak_nie.add("Nie");
      wait=rand.nextInt(wait_range);
      Thread.sleep(wait);
      
      write_data(out,"*3@"+tak_nie.get(n)+"#");
      if(stop==2)
          stop();
      
      n=rand.nextInt(2);
      String odp1="*4@Zdążyłbym się wycofać#";
      String odp2="*4@Straciłbym większość#";
            wait=rand.nextInt(wait_range);
      Thread.sleep(wait);
      if(n==1)
          write_data(out,odp1);
      if(n==0)
          write_data(out,odp2);
      
      n=rand.nextInt(10);
      n*=1000;
      n+=3000;
      String temp=Integer.toString(n);
            wait=rand.nextInt(200);
      Thread.sleep(wait);
      write_data(out,"*5@"+temp+"#");
      if(stop==3)
          stop();
      n=rand.nextInt(2);
      odp1="*6@Więcej#";
      odp2="*6@Mniej#";
            wait=rand.nextInt(wait_range);
      Thread.sleep(wait);
      if(n==1)
          write_data(out,odp1);
      if(n==0)
          write_data(out,odp2);
      
      if(stop==4)
          stop();
      n=rand.nextInt(1000);
      n+=2500;
                  wait=rand.nextInt(wait_range);
      Thread.sleep(wait);
      write_data(out,"*7@"+Integer.toString(n)+"#");
      
      String text="";
      n=rand.nextInt(3);
      if(n==0)
      text="[more than 50%]";
      if(n==1)
      text="[11-20%]";
      if(n==2)
      text="[0-10%]";
                  wait=rand.nextInt(wait_range);
      Thread.sleep(wait);
      write_data(out,"*8@"+text+"#");

     
      String received;               
      while (true) 
      {
        if(in.available()!=0)
        {
            received=in.readUTF();
            System.out.println(received);
            if(received.equals("GoStock"))
            break;
        }
        Thread.sleep(50);
      }
      
      
      
     n=rand.nextInt(6); 
     stocks.add("*9@,Coca Cola,46.58,40000.0,;,Google,1170.00,40000.0,;,Apple,188.88,20000.0,;#");
     stocks.add("*9@,Coca Cola,46.58,10000.0,;,Google,1170.00,20000.0,;,Apple,188.88,70000.0,;#");
     stocks.add("*9@,Tesla,46.58,10000.0,;,Facebook,1170.00,20000.0,;,Procter&Gamble,188.88,70000.0,;#");
     stocks.add("*9@,SunPower,46.58,10000.0,;,GoPro,1170.00,20000.0,;,Starbucks,188.88,70000.0,;#");
     stocks.add("*9@,EBay,46.58,10000.0,;,Groupon,1170.00,20000.0,;,Intel,188.88,70000.0,;#");
     stocks.add("*9@,Microsoft,46.58,10000.0,;,Amazon,1170.00,20000.0,;,Procter&Gamble,188.88,70000.0,;#");
      wait=rand.nextInt(wait_range);
      Thread.sleep(wait);
      write_data(out,stocks.get(n));
      
      while (true) 
      {
        if(in.available()!=0)
        {
            System.out.println("Wynik: "+in.readUTF());
            break;
        }
        Thread.sleep(50);
      }
      
      n=rand.nextInt(client_count);
                  wait=rand.nextInt(wait_range);
      Thread.sleep(wait);
      write_data(out,"*10@"+Integer.toString(n+1)+"#");
      
      while (true) 
      {
        if(in.available()!=0)
        {
            System.out.println("Punkty: "+in.readUTF());
            break;
        }
        Thread.sleep(50);
      }
  
      in.close();
      out.close();

    } catch (Exception e) {
        System.out.println(e);
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



