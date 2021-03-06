/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimerTask;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.text.Font;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.util.Timer;
import javax.swing.JFrame;
import javax.swing.JTextPane;

/**
 *
 * @author Szymon
 */
public class stock_screen extends javax.swing.JFrame {

    List<Quote> quotes;
    int current = 0;
    double one_investment;
    double max_invests;
    int current_invests = 0;
    ClientSide ClientSide1;
    boolean stop = false;
    boolean flag = true;
    Timer t;
    wait_screen ws=new wait_screen();
    String pytanie = "Masz do wyboru 15 spółek notowanych na\n"
            + "amerykańskiej giełdzie. Dla każdej spółki\n"
            + "przedstawione są dane historyczne notowań\n"
            + "z ostatnich 30 dni oraz wyświetlany jest bieżący\n"
            + "kurs akcji. Twoim zadaniem jest zainwestowanie\n"
            + "100 000zł w jedną, wybraną spółkę, tak by\n"
            + "osiągnąć jak największy zysk z inwestycji.";

    public stock_screen(ClientSide ClientSide1) {
        this.ClientSide1 = ClientSide1;
        this.one_investment = ClientSide1.one_investment;
        this.max_invests=ClientSide1.max_invests;
     
        quotes = new ArrayList<Quote> ();
        quotes.add(new Quote("Coca Cola","KO","https://www.nasdaq.com/symbol/ko/historical","https://www.nasdaq.com/symbol/ko/real-time"));
        quotes.add(new Quote("Google","GOOG","https://www.nasdaq.com/symbol/goog/historical","https://www.nasdaq.com/symbol/goog/real-time"));
        quotes.add(new Quote("Apple","AAPL","https://www.nasdaq.com/symbol/aapl/historical","https://www.nasdaq.com/symbol/aapl/real-time")); 
        quotes.add(new Quote("Facebook","FB","https://www.nasdaq.com/symbol/fb/historical","https://www.nasdaq.com/symbol/fb/real-time"));
        quotes.add(new Quote("Tesla","TSLA","https://www.nasdaq.com/symbol/TSLA/historical","https://www.nasdaq.com/symbol/TSLA/real-time"));
        quotes.add(new Quote("Amazon","AMZN","https://www.nasdaq.com/symbol/AMZN/historical","https://www.nasdaq.com/symbol/AMZN/real-time"));
        quotes.add(new Quote("Microsoft","MSFT","https://www.nasdaq.com/symbol/MSFT/historical","https://www.nasdaq.com/symbol/MSFT/real-time"));
        quotes.add(new Quote("Intel","INTC","https://www.nasdaq.com/symbol/INTC/historical","https://www.nasdaq.com/symbol/INTC/real-time"));
        quotes.add(new Quote("Groupon","GRPN","https://www.nasdaq.com/symbol/GRPN/historical","https://www.nasdaq.com/symbol/GRPN/real-time"));
        quotes.add(new Quote("EBay","EBAY","https://www.nasdaq.com/symbol/EBAY/historical","https://www.nasdaq.com/symbol/EBAY/real-time"));
        quotes.add(new Quote("Starbucks","SBUX","https://www.nasdaq.com/symbol/SBUX/historical","https://www.nasdaq.com/symbol/SBUX/real-time"));
        quotes.add(new Quote("GoPro","GRPRO","https://www.nasdaq.com/symbol/GPRO/historical","https://www.nasdaq.com/symbol/GPRO/real-time"));
        quotes.add(new Quote("SunPower","SPWR","https://www.nasdaq.com/symbol/SPWR/historical","https://www.nasdaq.com/symbol/SPWR/real-time"));
        quotes.add(new Quote("General Motors","GM","https://www.nasdaq.com/symbol/gm/historical","https://www.nasdaq.com/symbol/gm/real-time"));
        quotes.add(new Quote("Procter&Gamble ","PG","https://www.nasdaq.com/symbol/pg/historical","https://www.nasdaq.com/symbol/pg/real-time"));
           
        
        initComponents();
        jTextArea1.setText(pytanie);
        combo_box_init();
        init_update();
        run_update();
        display_plot();
        run_timer(20*60);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 644, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 610, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(0, 102, 204));
        jPanel3.setPreferredSize(new java.awt.Dimension(305, 609));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("Tłumaczenie polecenia\nTłumaczenie polecenia\nTłumaczenie polecenia\nTłumaczenie polecenia\nTłumaczenie polecenia\nTłumaczenie polecenia\nTłumaczenie polecenia\nTłumaczenie polecenia");
        jScrollPane1.setViewportView(jTextArea1);

        jTextField1.setEditable(false);
        jTextField1.setText("0");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Obecny kurs akcji");

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Wybór spółki");

        jButton2.setText("Zainwestuj");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Anuluj inwestycję");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField2.setEditable(false);
        jTextField2.setText("Zainwestowana kwota: 0");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Pozostały czas");

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(6);
        jTextArea2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTextArea2.setRows(1);
        jScrollPane2.setViewportView(jTextArea2);

        jButton1.setText("Dalej");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(73, 73, 73)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2)))
                                .addComponent(jLabel3)
                                .addComponent(jTextField2))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 86, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        display_plot();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        
        if(current_invests<max_invests)
        {
            current_invests+=1;
            quotes.get(current).invested+=1;
            jTextField2.setText("Zainwestowana kwota: "+quotes.get(current).invested*one_investment);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Wykorzystano wszystkie środki");
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       if(quotes.get(current).invested>0)
       {
           quotes.get(current).invested-=1;
           current_invests-=1;
           jTextField2.setText("Zainwestowana kwota: "+quotes.get(current).invested*one_investment);
       }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
            if(jTextField1.getText()==null)
            {
                JOptionPane.showMessageDialog(this, "Wpisana wartość jest nieprawidłowa","Nieprawidłowy wybór",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            try
            {
                Double.parseDouble(jTextField1.getText());
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(this, "Wpisana wartość jest nieprawidłowa","Nieprawidłowy wybór",JOptionPane.INFORMATION_MESSAGE);
                return;
            }


            int n = JOptionPane.showConfirmDialog(
                    this,
                    "Czy jesteś pewien, że chcesz przejść dalej?",
                    "Potiwerdzenie",
                    JOptionPane.YES_NO_OPTION);
            if(n==0)
            {
                this.setEnabled(false);                             
                    String sending="@";
                    Quote single;
                    for(int i=0; i<quotes.size();++i)
                    {
                        single=quotes.get(i);
                        if(single.invested!=0)
                        {
                            sending+=","+single.name+","+single.current_price+","+single.invested*one_investment+",;";
                        }
                    }
                    sending+="#";
                    ClientSide1.send_data(sending);
                    wait_for_response();
                    stop=true;
            }
    }//GEN-LAST:event_jButton1ActionPerformed

    public void run_update()
    {
        final SwingWorker<Boolean, Void> worker1 =  new SwingWorker<Boolean, Void>() {
        @Override
        protected Boolean doInBackground() throws Exception {
            while(!stop)
            {
                quotes.get(current).update_value();
                jTextField1.setText(quotes.get(current).current_price);
            }
            return true;
        }

        // Can safely update the GUI from this method.
        @Override
        protected void done() {

        }
        };  
        worker1.execute();
    }
    
    public void init_update()
    {
        for(int i=0; i<quotes.size();++i)
        {
            quotes.get(i).update_value();
        }
    }
    public void run_timer(int seconds)
    {
        t=new Timer();
        TimerTask timerTask =new TimerTask()
        {
            int remaining=seconds;
            public void run()
            {
                remaining-=1;
               // System.out.println(remaining);
                jTextArea2.setText(Integer.toString(remaining));
                if(remaining==0)
                {
                                
                    String sending="@";
                    Quote single;
                    for(int i=0; i<quotes.size();++i)
                    {
                        single=quotes.get(i);
                        if(single.invested!=0)
                        {
                            sending+=","+single.name+","+single.current_price+","+single.invested*one_investment+",;";
                        }
                    }
                    sending+="#";
                    stop=true;
                    ClientSide1.send_data(sending);
                    t.cancel();
                    wait_for_response();
            };
                }
        };
        t.schedule(timerTask,1000,1000);
    }
        

void combo_box_init()
{
    int quotes_number=50;
    for(int i=0; i<quotes.size();++i)
    {
        jComboBox1.addItem(quotes.get(i).get_name());
        quotes.get(i).get_historical_data(quotes_number);
    }
}
private void display_plot()
{
 
    jPanel2.removeAll();
    current=jComboBox1.getSelectedIndex();
    System.out.println(current);
    XYSeries series = new XYSeries("XYGraph"); 
    /*
    for(int i=0; i<quotes.get(current).quotes.size();++i)
    {
        //series.add(i,quotes.get(current).quotes.get(i).get_value());
        series.add(1,2*i);
    }*/
    quotes.get(0).modify_dates();
   List<Double> numbers = new ArrayList<Double>();
    if(quotes.get(current).quotes.size()==0)
        return;
    
    int size=quotes.get(current).quotes.size();
    String dates[] = new String[quotes.get(current).quotes.size()];
    for(int i=0; i<quotes.get(current).quotes.size();++i)
    {
        series.add(i,quotes.get(current).quotes.get(size-i-1).get_value());
        numbers.add(quotes.get(current).quotes.get(i).get_value());
        dates[size-i-1]=quotes.get(current).quotes.get(i).get_date();
    }
    SymbolAxis sa = new SymbolAxis("Data[mm/dd/yyyy]",dates);
    
    XYSeriesCollection dataset = new XYSeriesCollection();
    dataset.addSeries(series);
   
    // Generate the graph
    JFreeChart chart = ChartFactory.createXYLineChart(
       quotes.get(current).get_name(), // Title
       "Data", // x-axis Label
       "Kurs akcji [zł]", // y-axis Label
       dataset, // Dataset
       PlotOrientation.VERTICAL, // Plot Orientation
       false, // Show Legend
       true, // Use tooltips
       false );// Configure chart to generate URLs?

    XYPlot xyPlot = (XYPlot) chart.getPlot();
    xyPlot.setDomainCrosshairVisible(true);
    xyPlot.setRangeCrosshairVisible(true);
    xyPlot.setDomainAxis(sa);
    sa.setAttributedLabel("Data [mm/dd/yyyy]");
    
    
    
    XYItemRenderer renderer = xyPlot.getRenderer();
    renderer.setSeriesPaint(0, Color.blue);

    org.jfree.chart.axis.ValueAxis rangeAxis = xyPlot.getRangeAxis();
    
    double max=Collections.max(numbers);
    double min=Collections.min(numbers);
    rangeAxis.setRange(min-(max/10), max+(max/10));
    //rangeAxis.setAutoRange(true);

    jPanel2.setLayout(new java.awt.BorderLayout());
    ChartPanel CP = new ChartPanel(chart);
    jPanel2.add(CP,BorderLayout.CENTER);
    jPanel2.validate();
    jTextField2.setText("Zainwestowana kwota: "+quotes.get(current).invested*one_investment);
}
public void wait_for_response()
{
    final SwingWorker<Boolean, Void> worker1 =  new SwingWorker<Boolean, Void>() {

    @Override
    protected Boolean doInBackground() throws Exception {
        t.cancel();
        String lala;
        lala=ClientSide1.read_data(1000);
        System.out.println("Z serwera przyszło "+lala);
        return true;
    }

        // Can safely update the GUI from this method.
        @Override
        protected void done() {
            ws.dispose();
            Question5 qs5=new Question5(ClientSide1);
            qs5.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    int result = JOptionPane.showConfirmDialog(qs5, "Jesteś pewien, że chcesz zamknąc program?", "Potwierdzenie", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        qs5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    } else if (result == JOptionPane.NO_OPTION) {
                        qs5.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    }
                }
            });
            dispose();
            setVisible(false);
            qs5.setLocationRelativeTo(null);
            qs5.setVisible(true);
        }

    };
    this.setEnabled(false);
    ws.setLocationRelativeTo(this);
    ws.setVisible(true);
    worker1.execute();
}
            
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(stock_screen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(stock_screen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(stock_screen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(stock_screen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ClientSide cs=new ClientSide();
                new stock_screen(cs).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
