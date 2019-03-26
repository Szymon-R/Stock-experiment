/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwer;

/**
 *
 * @author Szymon
 */
public class Section {
         public double min;
        public double max;
        Section(double percentage)
        {
            min=2854-2854*percentage;
            max=2854+2854*percentage;
        }
}
