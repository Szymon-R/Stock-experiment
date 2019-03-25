/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwer;

import java.util.Random;
import java.util.Vector;

/**
 *
 * @author Szymon
 */
public class clientImage {
    String ID;
    String name;
    String surname;
    double income;
    Vector<String> answers=new Vector<String>(); 
    Vector<Investment> invest=new Vector<Investment>();
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
        double old;
        double neww;
        double inv;
        double result;
        Random rand = new Random();
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
                    this.income+=(result+rand.nextInt(50)); 
                }
            }
        }
        
        
    }
}
