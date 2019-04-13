/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.awt.image.ImageObserver.ERROR;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;



public class Serwer_API extends javax.swing.JFrame {

        
    BlockingQueue<String> input_queue;
    BlockingQueue<String> output_queue;
    int step1=1;
    int step2=1;
    public int client_count=0;
    int counter=0;
    boolean protector1=true;
    DefaultTableModel model;
    Vector<clientImage> clients=new Vector<clientImage>();
    Vector<Section> sections=new Vector<Section>();
    Vector<Quote_price> quotes=new Vector<Quote_price>();
    public Serwer_API(BlockingQueue<String> input_queue, BlockingQueue<String> output_queue) 
    {

        sections.add(new Section(0.1));
        sections.add(new Section(0.2));
        sections.add(new Section(50));
        this.input_queue=input_queue;
        this.output_queue=output_queue;
        initComponents();
        try
        {jTextField2.setText(jTextField2.getText()+InetAddress.getLocalHost().getHostAddress());}
        catch(Exception e){};
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
 public void process_answer(int column1,int column2)
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
                      if(column1==9)
                      {
                          clients.get(position-1).read_investments(get_answer(temp));
                      }
                      System.out.println("Wrzucanie do tablicy");
                      System.out.println("To trafia do listy odpowiedzi: "+get_answer(temp));
                      clients.get(position-1).answers.add(get_answer(temp));
                      model.setValueAt(get_answer(temp), row-1, column1);
                      
                      if(column2==9)
                      {
                          clients.get(position-1).read_investments(get_answer(temp));
                      }
                      temp=truncate_string(temp);
                      System.out.println("Wrzucanie do tablicy");
                      System.out.println("To trafia do listy odpowiedzi: "+get_answer(temp));
                      clients.get(position-1).answers.add(get_answer(temp));
                      model.setValueAt(get_answer(temp), row-1, column2);
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
        jTextArea1.setText(jTextArea1.getText()+"Pytanie 1\n");
        step2=1;
        while(step2==1)
        {
            process_answer(2,3);
        }
        
        for(int i=0; i<client_count;++i)
        {
            try
              {output_queue.put("GOGO");}
            catch(Exception e){};
        }
        jTextArea1.setText(jTextArea1.getText()+"Pytanie 2\n");
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
        jTextArea1.setText(jTextArea1.getText()+"Pytanie 3\n");
        step2=1;
        while(step2==1)
        {
            process_answer(5,6);
        }
        for(int i=0; i<client_count;++i)
        {
            try
              {output_queue.put("GOGO");}
            catch(Exception e){};
        }
        jTextArea1.setText(jTextArea1.getText()+"Pytanie 4\n");
        step2=1;
        while(step2==1)
        {
            process_answer(7,8);
        }
        for(int i=0; i<client_count;++i)
        {
            try
              {output_queue.put("GOGO");}
            catch(Exception e){};
        }
        jTextArea1.setText(jTextArea1.getText()+"Pytanie 5, giełdowe\n");
        step2=1;
        while(step2==1)
        {
            process_answer(9);
        }
        for(int i=0; i<client_count;++i)
        {
            try
              {output_queue.put("GOGO");}
            catch(Exception e){};
        }
    /*    jTextArea1.setText(jTextArea1.getText()+"Pytanie 6\n");
        step2=1;
        while(step2==1)
        {
            process_answer(10);
        }*/
        jTextArea1.setText(jTextArea1.getText()+"Pytanie 7\n");
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
            {sleep(5000);}
        catch(Exception e){};

        //musi być dodane ID
        grade_answers();
        for(int i=0; i<client_count;++i)
        {
            String sending="";
            try
              {
                sending=(clients.get(i).ID+":");
                sending+=Double.toString(clients.get(i).grade1+clients.get(i).grade2);
                output_queue.put(sending);
              }
            catch(Exception e){};
        }
        //rozsyłanie wyników
        
        }
      };


    thread.start();

        
    }

    public String truncate_string(String data)
    {
        int index2;
        index2=data.indexOf('#');
        return(data.substring(index2+1,data.length()));
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
             Writer out = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(name), "UTF-8"));
            System.out.println("Otwarto plik");
            for(int i=0; i<client_count;++i)
            {
                out.write("\n");
                for(int j=0; j<clients.get(i).answers.size();++j)
                {
                    System.out.println(clients.get(i).answers.get(j));
                    out.write(clients.get(i).answers.get(j));
                    out.write("\t");
                }
            }
            out.close();
        }
              catch(IOException e){};
    }
     

  

    public void get_prices()
    {

        quotes.add(new Quote_price("Coca Cola","KO","https://www.nasdaq.com/symbol/ko/historical","https://www.nasdaq.com/symbol/ko/real-time"));
        quotes.add(new Quote_price("Google","GOOG","https://www.nasdaq.com/symbol/goog/historical","https://www.nasdaq.com/symbol/goog/real-time"));
        quotes.add(new Quote_price("Apple","AAPL","https://www.nasdaq.com/symbol/aapl/historical","https://www.nasdaq.com/symbol/aapl/real-time")); 
        quotes.add(new Quote_price("Facebook","FB","https://www.nasdaq.com/symbol/fb/historical","https://www.nasdaq.com/symbol/fb/real-time"));
        quotes.add(new Quote_price("Tesla","TSLA","https://www.nasdaq.com/symbol/TSLA/historical","https://www.nasdaq.com/symbol/TSLA/real-time"));
        quotes.add(new Quote_price("Amazon","AMZN","https://www.nasdaq.com/symbol/AMZN/historical","https://www.nasdaq.com/symbol/AMZN/real-time"));
        quotes.add(new Quote_price("Microsoft","MSFT","https://www.nasdaq.com/symbol/MSFT/historical","https://www.nasdaq.com/symbol/MSFT/real-time"));
        quotes.add(new Quote_price("Intel","INTC","https://www.nasdaq.com/symbol/INTC/historical","https://www.nasdaq.com/symbol/INTC/real-time"));
        quotes.add(new Quote_price("Groupon","GRPN","https://www.nasdaq.com/symbol/GRPN/historical","https://www.nasdaq.com/symbol/GRPN/real-time"));
        quotes.add(new Quote_price("EBay","EBAY","https://www.nasdaq.com/symbol/EBAY/historical","https://www.nasdaq.com/symbol/EBAY/real-time"));
        quotes.add(new Quote_price("Starbucks","SBUX","https://www.nasdaq.com/symbol/SBUX/historical","https://www.nasdaq.com/symbol/SBUX/real-time"));
        quotes.add(new Quote_price("GoPro","GRPRO","https://www.nasdaq.com/symbol/GPRO/historical","https://www.nasdaq.com/symbol/GPRO/real-time"));
        quotes.add(new Quote_price("SunPower","SPWR","https://www.nasdaq.com/symbol/SPWR/historical","https://www.nasdaq.com/symbol/SPWR/real-time"));
        quotes.add(new Quote_price("General Motors","GM","https://www.nasdaq.com/symbol/gm/historical","https://www.nasdaq.com/symbol/gm/real-time"));
        quotes.add(new Quote_price("Procter&Gamble ","PG","https://www.nasdaq.com/symbol/pg/historical","https://www.nasdaq.com/symbol/pg/real-time"));
        
        final SwingWorker<Boolean, Void> worker1 =  new SwingWorker<Boolean, Void>() {

                @Override
                protected Boolean doInBackground() throws Exception {

                    for(int i=0; i<quotes.size();++i)
                    {
                        System.out.println("Pobiernie informacji o "+quotes.get(i).get_name());
                        quotes.get(i).update_value();
                        jTextArea1.setText(jTextArea1.getText()+quotes.get(i).get_name()+ " "+quotes.get(i).current_price+"\n");
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
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Połączony", "Płeć", "Czy inwestujesz na giełdzie", "Czy wycofałbyś się z bitcoina", "Ile będziesz zarabiał", "Czy więcej niż inni", "Kwota netto", "Przedział błędu", "Inwestycja", "Zysk z inwestycji", "Pozycja", "Rzeczywistość"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, true, true, true
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

        jButton3.setText("Roześlij wynik");
        jButton3.setActionCommand("Roześlij wyniki");
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

        jTextField2.setText("IP serwera: ");
        jTextField2.setToolTipText("");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Status eksperymentu:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jButton1.setText("Liczba uczestników -1");
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
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(jTextField2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
        );

        jButton2.getAccessibleContext().setAccessibleDescription("");

        jPanel1.add(jPanel3, java.awt.BorderLayout.LINE_END);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1123, Short.MAX_VALUE)
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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       
        if(protector1)
        {
            System.out.println("Pobieranie kursów akcji");
            jTextArea1.setText(jTextArea1.getText()+"Pobieranie kursów akcji\n");
            String temp="";
            get_prices();
            for(int i=0; i<clients.size();++i)
            {
                clients.get(i).calculate_income(quotes);
                for(int j=0; j<clients.get(i).invest.size();++j)
                {
                    clients.get(i).income+=Double.parseDouble(clients.get(i).invest.get(j).income);
                }

            }
            for(int i=0; i<clients.size();++i)
            {
                try
                {
                    temp=clients.get(i).ID+":";
                    temp+=Double.toString(clients.get(i).income);
                    temp+=';';
                    temp+=Integer.toString(client_count)+'#';
                    System.out.println("Serwer api wysyła "+temp);
                    output_queue.put(temp);
                }
                catch(Exception e){};
            }
            protector1=false;
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        write_data();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        client_count-=1;
        jTextField1.setText(Integer.toString(client_count));
    }//GEN-LAST:event_jButton1ActionPerformed
    
    public void grade_answers()
    {
        try
        {
            double answer=0;
            int counter;
            int section;
            for(int i=0; i<clients.size();++i)
            {
                //tu jest kwota którą ktoś wpisał
                answer=Double.parseDouble(clients.get(i).answers.get(5));
                
                //do któego przedziału myśli, że się łapie
                section=get_percentage(i);
                if(section==0)
                    continue;
                section-=1;
                //do któego przedziału łapie się wpisana kwota
                counter=0;
                for(counter=0; counter<sections.size();++counter)
                {
                    if(answer>sections.get(counter).min&&answer<sections.get(counter).max)
                        break;
                }
                if(section==counter)
                    clients.get(i).grade1=(1.5-counter*0.5);
                else
                    clients.get(i).grade1=0;
            }
            //Druga część oceniania
            SortByIncome sorting = new SortByIncome();
            Collections.sort(clients, sorting);
            int position;
            int reality;
            int difference;
            for(int i=0; i<clients.size();++i)
            {
                position=Integer.parseInt(clients.get(i).answers.get(8));
                reality=i+1;
                difference=Math.abs(position-reality);
                clients.get(i).answers.add(Double.toString(clients.get(i).income));
                clients.get(i).answers.add(Integer.toString(reality));
                for(int j=0; j<4;++j)
                {
                    if(difference==j)
                    {
                       clients.get(i).grade2=(2-0.5*j);
                       if((clients.get(i).grade2+clients.get(i).grade1)<2)
                       {
                           clients.get(i).grade2=1;
                           clients.get(i).grade1=1;
                       }
                       break;
                    }
                }
            }
        write_data();
        }
        
        catch (Exception e){};
    }
    

    public int get_percentage(int which)
    {
        String answer=clients.get(which).answers.get(6);
        if(answer.equals("[0-10%]"))
            return 1;
        else if(answer.equals("[11-20%]"))
            return 2;
        else if(answer.equals("[more than 50%]"))
            return 3;
        else
            return 0;
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
