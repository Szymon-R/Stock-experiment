/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Szymon
 */
public class StockTry {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException{
        int quotes_number=50;
        Quote Coca_Cola=new Quote("Coca Cola","KO","https://www.nasdaq.com/symbol/ko/historical","https://www.nasdaq.com/symbol/ko/real-time");
        Coca_Cola.get_historical_data(quotes_number);
        Coca_Cola.update_value();
      //  Quote Google=new Quote("Google","https://www.nasdaq.com/symbol/goog/historical");
       // Google.get_historical_data(quotes_number);
        
    }
}
