/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwer;

import java.util.Vector;


public class Investment {
    private String symbol;
    private String old_price;
    private String new_price;
    private String invested;
    private String income;
    

    public String parse_data(String data)
    {
      String[] results = data.split( ",\\s*" );
      if(results.length!=0)
      {
          symbol=results[1];
          old_price=results[2];
          invested=results[3];
          int position=data.indexOf(';');
          data=data.substring(position+1,data.length());
          return data;
      }
      return "";
    }
    
    
        public static void main(String args[]) {

        //</editor-fold>
        Investment inv =new Investment();
        String data=",Coca Cola,45.93,200000,;,Google,1203.60,200000,;,Apple,191.00,100000,;";

    }
}

