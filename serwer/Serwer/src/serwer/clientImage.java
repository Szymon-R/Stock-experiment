/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwer;

import java.util.Vector;

/**
 *
 * @author Szymon
 */
public class clientImage {
    String ID;
    String name;
    String surname;
    Vector<String> answers=new Vector<String>(); 
    
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

}
