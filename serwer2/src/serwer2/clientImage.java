/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwer2;

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Szymon
 */
public class clientImage {
    
    public clientImage(BlockingQueue<String> input,BlockingQueue<String> output)
    {
        this.input=input;
        this.output=output;
        income=-100000;
    }
    
    String ID;
    String name;
    String surname;
    double income;
    String reality="";
    double grade1=0;
    double grade2=0;
    Vector<String> answers=new Vector<String>(); 
    Vector<Investment> invest=new Vector<Investment>();
    BlockingQueue<String> input;
    BlockingQueue<String> output;
    

    
    public void set_ID(String data) throws Exception
    {
        this.ID=data;
    }
    public void set_name(String data) throws Exception
    {
        int index1;
        int index2;
        index1=data.indexOf('@');
        index2=data.indexOf(';');
        this.name=(data.substring(index1+1,index2));
        index1=data.indexOf('#');
        this.surname=(data.substring(index2+1,index1));
    }
    public void read_investments(String data)
    {
        int counter=0;
        invest.clear();
        while(!"".equals(data))
        {
            invest.add(new Investment());
            data=invest.get(counter).parse_data(data);
            ++counter;
        }
    }
    public void calculate_income(Vector<Quote_price> quotes)
    {
        String sym1;
        double old=1;
        double neww=1;
        double inv=1;
        double result=1;
   //     Random rand = new Random();
        for(int i=0; i<invest.size();++i)
        {
            sym1=invest.get(i).name;
            
            for(int j=0; j<quotes.size();++j)
            {
                String sym2=quotes.get(j).get_name();
                if(sym2.equals(sym1))
                {
                    invest.get(i).new_price=quotes.get(j).current_price;
                    old=Double.parseDouble(invest.get(i).old_price);
                    neww=Double.parseDouble(invest.get(i).new_price);
                    inv=Double.parseDouble(invest.get(i).invested);
                    result=inv/old*neww-inv;
                    invest.get(i).income=Double.toString(result);
      //              this.income+=(result+rand.nextInt(50)); 
                }
            }
        }
        
        
    }
}
