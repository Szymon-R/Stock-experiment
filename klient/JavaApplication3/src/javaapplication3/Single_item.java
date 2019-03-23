/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;
import java.io.IOException;
import java.util.*;
import java.text.*;
/**
 *
 * @author Szymon
 */
public class Single_item {
    String date;
    double open_price;

    
    public Single_item(String date, double price)
    {
        this.date=date;
        this.open_price=price;
    }
    public void set_date(String date)
    {
        this.date=date;
    }
    double get_value()
    {
       return this.open_price;
    }
    String get_date()
    {
        return date;
    }
    public void set_open_price(double price)
    {
        this.open_price=price;
    }
}
