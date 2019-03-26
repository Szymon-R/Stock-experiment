/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwer;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Szymon
 */
public class Quote_price {
    String name;
    String symbol;
    URL historical_url;
    URL update_url;
    String current_price="1";
    int invested;
    public Quote_price(String name,String symbol, String update_url)
    {
        this.name=name;
        this.symbol=symbol;
        try
        {
            this.update_url=new URL(update_url);
        }
        catch(IOException exp)
        {
            System.out.println("Incorrect link to webpage");
        }
    }
    public String get_name()
    {
        return this.name;
    }
        public String get_symbol()
    {
        return this.symbol;
    }
   
   
    
    public void update_value()
    {
        int lines_counter=1;
        String temp_value="";
        //System.setProperty("http.agent", "Chrome");
        try
        {
            URLConnection urlcon=update_url.openConnection();
            InputStreamReader inStream =new InputStreamReader(urlcon.getInputStream());
            BufferedReader buff=new BufferedReader(inStream);
            String line="";
            for(int i=0; i<900;++i)
            {
                buff.readLine();
                ++lines_counter;
            }
            line=buff.readLine();
            while(line!=null)
            {                
                if(line.contains("qwidget_lastsale"))
                {
                    int start_position;
                    int end_position;
                    start_position=line.indexOf("$");
                    end_position=line.indexOf("</div>");
                    temp_value=line.substring(start_position+1, end_position);
                    current_price=temp_value;
                    System.out.println(current_price);
                    break;
                }
                line =buff.readLine();
                ++lines_counter;
            }
        }
        catch(IOException exp)
        {
            System.out.println("Error reading data from url");
        }
    }        

    
}
