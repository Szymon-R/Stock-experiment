/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwer;

import static java.awt.image.ImageObserver.ERROR;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Szymon
 */

public class Serwer_API extends javax.swing.JFrame {
    BlockingQueue<String> input_queue;
    BlockingQueue<String> output_queue;
    int step1=1;
    int step2=1;
    public int client_count=0;
    int counter=0;
    DefaultTableModel model;
    Vector<clientImage> clients=new Vector<clientImage>();
    Vector<Quote_price> quotes=new Vector<Quote_price>();
    public Serwer_API(BlockingQueue<String> input_queue, BlockingQueue<String> output_queue) 
    {
        this.input_queue=input_queue;
        this.output_queue=output_queue;
        initComponents();
    }
    
    public String subtract_ID(String data)
    {
        String temp;
        int index=data.indexOf(':');
        temp=data.substring(0, index);
        System.out.println("Substract ID: "+temp);
        return temp;
    }
    
    public int find_row(String ID)
    {
        System.out.println("find row");
        for(int i=0;i<client_count;++i)
        {
            System.out.println(model.getValueAt(i, 0));
            if(model.getValueAt(i, 0).equals(ID))
                return i+1;
        }
        return 0;
    }
    public int find_client(String ID)
    {
        System.out.println("find client");
        for(int i=0;i<client_count;++i)
        {
            if(clients.get(i).ID.equals(ID))
                return i+1;
        }
        return 0;
    }
    
    public String get_answer(String data)
    {
        int index1;
        int index2;
        index1=data.indexOf('@');
        index2=data.indexOf('#');
        return(data.substring(index1+1,index2));
    }
    public void process_answer(int column)
    {
        String temp;
        
        int row;
        int position;
            try
            {
                if(!input_queue.isEmpty())
                {
                    System.out.println("Coś jest w kolejce");
                    temp=input_queue.take();
                    System.out.println("pobrane z kolejki: "+temp);
                    row=find_row(subtract_ID(temp));
                    position=find_client(subtract_ID(temp));
                    System.out.println(get_answer(temp));
                    System.out.println("row: "+row);
                    System.out.println("position: "+position);
                    if(row==0||position==0)
                        System.out.println("Nie znaleziono ID");
                    else
                    {
                      System.out.println("Wrzucanie do tablicy");
                      System.out.println("To trafia do listy odpowiedzi: "+get_answer(temp));
                      clients.get(position-1).answers.add(get_answer(temp));
                      if(column==9)
                      {
                          clients.get(position-1).read_investments(get_answer(temp));
                      }
                      model.setValueAt(get_answer(temp), row-1, column);
                      ++counter;
                      if(counter==client_count)
                      {
                          counter=0;
                          step2=0;
                      }     
                    }
                }
                Thread.sleep(100);
            }
            catch(Exception e){};
    }
    
    class SortByIncome implements Comparator<clientImage> 
    { 
        public int compare(clientImage a, clientImage b) 
        { 
            return Double.compare(a.income, b.income);
        } 
    } 

    public void get_client_info()
    {

      Thread thread = new Thread()
      {
        public void run()
        {
        String data;
        model=(DefaultTableModel) jTable1.getModel();
        while(step1==1)
        {
            try
            {
                if(!input_queue.isEmpty())
                {
                    data=input_queue.take();
                    clients.add(new clientImage());
                    clients.lastElement().set_ID(subtract_ID(data));

                    model.addRow(new Object[]{});
                    model.setValueAt(clients.lastElement().ID,clients.size()-1,0);
                    model.setValueAt("Połączony",clients.size()-1,1);
                    ++client_count;
                    jTextField1.setText(Integer.toString(client_count));
                }
                Thread.sleep(100);
            }
            catch (Exception e)
            {
                ;
            }
        }
        for(int i=0; i<client_count;++i)
        {
            try
              {output_queue.put("GOGO");}
            catch(Exception e){};
        }
        while(step2==1)
        {
            process_answer(2);
        }
        
   /*     for(int i=0; i<client_count;++i)
        {
            try
              {output_queue.put("GOGO");}
            catch(Exception e){};
        }*/
        step2=1;
        while(step2==1)
        {
            process_answer(3);
        }
        
        for(int i=0; i<client_count;++i)
        {
            try
              {output_queue.put("GOGO");}
            catch(Exception e){};
        }
        step2=1;
        while(step2==1)
        {
            process_answer(4);
        }
        
        for(int i=0; i<client_count;++i)
        {
            try
              {output_queue.put("GOGO");}
            catch(Exception e){};
        }
        step2=1;
        while(step2==1)
        {
            process_answer(5);
        }
       /*         for(int i=0; i<client_count;++i)
        {
            try
              {output_queue.put("GOGO");}
            catch(Exception e){};
        }*/
        step2=1;
        while(step2==1)
        {
            process_answer(6);
        }
        for(int i=0; i<client_count;++i)
        {
            try
              {output_queue.put("GOGO");}
            catch(Exception e){};
        }
        step2=1;
        while(step2==1)
        {
            process_answer(7);
        }
      /*  for(int i=0; i<client_count;++i)
        {
            try
              {output_queue.put("GOGO");}
            catch(Exception e){};
        }*/
        step2=1;
        while(step2==1)
        {
            process_answer(8);
        }
        for(int i=0; i<client_count;++i)
        {
            try
              {output_queue.put("GOGO");}
            catch(Exception e){};
        }
        step2=1;
        while(step2==1)
        {
            process_answer(9);
        }
        step2=1;
        while(step2==1)
        {
            process_answer(10);
        }
        step2=1;
        while(step2==1)
        {
            process_answer(11);
        }
         for(int i=0; i<client_count;++i)
        {
            try
              {output_queue.put("GOGO");}
            catch(Exception e){};
        }
        
        try
            {sleep(10000);;}
        catch(Exception e){};

        //musi być dodane ID
        for(int i=0; i<client_count;++i)
        {
            try
              {output_queue.put("1:12345");}
            catch(Exception e){};
        }
        //rozsyłanie wyników
        
        }
      };


    thread.start();

        
    }
    public void write_data()
    {
        System.out.println("Zapisywanie do pliku");
        SimpleDateFormat formatter = new SimpleDateFormat("dd;MM;yyyy__HH.mm.ss");  
        Date date = new Date();  
        System.out.println();  
        try
        {
            String name="Wyniki_"+formatter.format(date).toString()+".xls";
            System.out.println(name);
            BufferedWriter writer = new BufferedWriter(new FileWriter(name));
            System.out.println("Otwarto plik");
            for(int i=0; i<client_count;++i)
            {
                writer.write("\n");
                for(int j=0; j<clients.get(i).answers.size();++j)
                {
                    System.out.println(clients.get(i).answers.get(j));
                    writer.write(clients.get(i).answers.get(j));
                    writer.write("\t");
                }
            }
        writer.close();
        }
        catch(IOException e){};

    }    

    public void get_prices()
    {
        quotes.add(new Quote_price("Coca Cola","KO","https://www.nasdaq.com/symbol/ko/real-time"));
        quotes.add(new Quote_price("Google","GOOG","https://www.nasdaq.com/symbol/goog/real-time"));
        quotes.add(new Quote_price("Apple","AAPL","https://www.nasdaq.com/symbol/aapl/real-time"));
        for(int i=0; i<quotes.size();++i)
        {
            System.out.println("Pobiernie informacji o "+quotes.get(i).get_name());
            quotes.get(i).update_value();
        }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Połączony", "Płeć", "Czy inwestujesz na giełdzie", "Czy wycofałbyś się z bitcoina", "Ile będziesz zarabiał", "Czy więcej niż inni", "Kwota netto", "Przedział błędu", "Inwestycja", "Zysk z inwestycji", "Pozycja"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setMinimumSize(new java.awt.Dimension(210, 300));
        jTable1.setName(""); // NOI18N
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(30);
            jTable1.getColumnModel().getColumn(1).setMinWidth(60);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(60);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(60);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(3).setMinWidth(150);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(150);
            jTable1.getColumnModel().getColumn(4).setMinWidth(150);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(200);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(6).setMinWidth(80);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(80);
            jTable1.getColumnModel().getColumn(7).setMinWidth(80);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(7).setMaxWidth(80);
            jTable1.getColumnModel().getColumn(11).setResizable(false);
        }

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel2.setText("Tablica postępu");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        jButton2.setText("START");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Liczba uczestników");

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("0");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("Dalej");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Roześlij zysk");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Zapisz dane");
        jButton4.setToolTipText("");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jButton2.getAccessibleContext().setAccessibleDescription("");

        jPanel1.add(jPanel3, java.awt.BorderLayout.LINE_END);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1066, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.step1=0;
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        step2=0;
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        System.out.println("Pobieranie kursów akcji");
        String temp="";
        get_prices();
        for(int i=0; i<clients.size();++i)
        {
            clients.get(i).calculate_income(quotes);
        }
        for(int i=0; i<clients.size();++i)
        {
            clients.get(i).calculate_income(quotes);
            temp+=clients.get(i).ID+":";
            temp+=Double.toString(clients.get(i).income);
            temp+=';';
            temp+=Integer.toString(client_count)+'#';
            System.out.println("Serwer api wysyła "+temp);
            try
              {output_queue.put(temp);}
            catch(Exception e){};
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        write_data();
    }//GEN-LAST:event_jButton4ActionPerformed




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
