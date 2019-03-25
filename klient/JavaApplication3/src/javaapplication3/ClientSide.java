/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import javax.swing.JOptionPane;
import org.jfree.io.IOUtils;

/**
 *
 * @author Szymon
 */
public class ClientSide 
{
    Socket client;
    OutputStream outToServer;
    DataOutputStream out;
    InputStream inFromServer;
    DataInputStream in;
    double one_investment=100000;
    double max_invests=5;
    public int findAvaliable(String name,int first, int last)
    {
        for(int i=first; i<=last;++i)
        {
        try {
            new Socket(name, i).close();
            return i;
            } catch (IOException ex) {
                System.out.println("Zajete");
                continue; // try next port
            }
        }
        return 0;
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
    public int disconnect()
    {
        try{
        client.close();}
        catch(IOException ex) 
        {
            
        }
               
        return 1;
    }
    public boolean validIP (String ip) {
    try {
        if ( ip == null || ip.isEmpty() ) {
            return false;
        }

        String[] parts = ip.split( "\\." );
        if ( parts.length != 4 ) {
            return false;
        }

        for ( String s : parts ) {
            int i = Integer.parseInt( s );
            if ( (i < 0) || (i > 255) ) {
                return false;
            }
        }
        if ( ip.endsWith(".") ) {
            return false;
        }
           
        return true;
    } catch (NumberFormatException nfe) {
        return false;
    }
}
    public boolean send_data(String data)
    {
        try
        {
            out.writeUTF(data);
            return true;
        }
        catch (IOException e)
        {
            return false;
        }
    }
    
     public String read_data(int timeout)
    {
        String data;
        try
        {
            while(true){
            if(in.available()>=4)
            {
                System.out.println("Cos przyszlo");
                data=in.readUTF();
                return data;
            }
            }
        }
        catch (Exception e)
        {
            return "";
        }

    }
}
