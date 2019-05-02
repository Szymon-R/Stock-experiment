/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 *
 * @author Szymon
 */
public class Quote {
    String name;
    String symbol;
    ArrayList<Single_item> quotes = new ArrayList<Single_item> ();
    Vector<Double> prices=new Vector<Double>();
    
    URL historical_url;
    URL update_url;
    String current_price;
    int invested;
    public Quote(String name,String symbol, String historical_url, String update_url)
    {
        this.name=name;
        this.symbol=symbol;
        try
        {
            this.historical_url=new URL(historical_url);
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
   private Date yesterday(int count)
   {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-count);
        return cal.getTime();
   }
   private String getYesterdayDateString(int count) 
   {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(yesterday(count));
   }
   public void modify_dates()
   {
       String temp1;
       for(int i=0; i<quotes.size();++i)
       {
           temp1=quotes.get(i).get_date().replaceAll("/", ".");
           temp1=temp1.replaceAll("\\s+","");
       }
   }
    public void get_historical_data(int records_number, int back)
    {
        int found=0;
        System.out.println("Pobieranie danych historycznych");
        int data_counter=0;
        int lines_counter=1;
        int date_line_pointer=1348;
        int value_line_pointer=1351;
        String temp_data="";
        String temp_value="";
        String start_date;

        start_date=getYesterdayDateString(back);

        System.out.println(start_date);
        try
        {
            URLConnection urlcon=historical_url.openConnection();
            InputStreamReader inStream =new InputStreamReader(urlcon.getInputStream());
            BufferedReader buff=new BufferedReader(inStream);
            String line =buff.readLine();
            for(int i=0; i<1100;++i)
            {
                ++lines_counter;
            }
           // start_date="03/22/2019";
            while(line!=null)
            {
                if(line.contains(start_date))
                {
                    date_line_pointer=lines_counter;
                    found=1;
                    break;
                }
                line =buff.readLine();
                ++lines_counter;
            }
            value_line_pointer=date_line_pointer+3;
            while(line!=null)
            {
             //   System.out.print(lines_counter+" ");
               // System.out.println(line);
                
                if(lines_counter==date_line_pointer)
                {
                    temp_data=line;
                    if(!(line.contains("/")||line.contains(":")))
                    {
                        System.out.println("Przerywamy");
                        break;
                    }
                    else if(records_number!=0&&records_number==data_counter)
                        break;
                }
                if(lines_counter==value_line_pointer)
                {
                    date_line_pointer+=21;
                    value_line_pointer+=21;
                    double temp;
                    try
                    {
                        temp_value=line;
                        temp=Double.parseDouble(temp_value);
                        quotes.add(new Single_item(temp_data,temp));
                      // System.out.println(quotes.get(data_counter).get_value());
                    }
                    catch(Exception e)
                    {
                        
                    }

                    ++data_counter;
                }
                line =buff.readLine();
                //System.out.println(lines_counter+") "+line);
                ++lines_counter;
            }
            if(found==1)
                modify_dates();
            else
               get_historical_data(records_number,back+1);
        }
        catch(IOException exp)
        {
            System.out.println("Error reading data from url");
        }
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
            String line =buff.readLine();
            for(int i=0; i<900;++i)
            {
                line =buff.readLine();
            }
            while(line!=null)
            {
               // System.out.print(lines_counter+" ");
              //  System.out.println(line);
                
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

    public void download_data()
    {
        System.out.println("Zapisywanie do pliku");
        System.out.println();  
        try
        {
            String file=name+".txt";
            Writer out = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(name), "UTF-8"));
            System.out.println("Otwarto plik");

            Timer t=new Timer();
            
            TimerTask timerTask =new TimerTask()
            {
                int remaining=40*60;
                public void run()
                {
                    try
                    {
                        System.out.println(name+" "+Integer.toString(remaining));
                        remaining-=1;
                        update_value();
                        //prices.add(100.0);
                        out.write("prices.add(");
                        out.write(current_price);
                        out.write(");\n");
                        out.flush();
                        if(remaining<=0)
                        {
                            out.close();
                            t.cancel();
                            return;
                        }
                    }
                    catch(IOException e){System.out.println(e);};
                }

            
            };
            t.schedule(timerTask,3000,3000);
        }
              catch(IOException e){System.out.println(e);};
    }
    
}


