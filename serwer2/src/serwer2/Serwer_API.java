/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serwer2;

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
    synchro syn;
    Vector<clientImage> clients=new Vector<clientImage>();
    Vector<Section> sections=new Vector<Section>();
    Vector<Quote_price> quotes=new Vector<Quote_price>();
    public Serwer_API(synchro syn) 
    {
        this.syn=syn;
        sections.add(new Section(0.0526));
        sections.add(new Section(0.0877));
        sections.add(new Section(50));
        this.input_queue=input_queue;
        this.output_queue=output_queue;
        initComponents();
        model=(DefaultTableModel) jTable1.getModel();
        try
        {jTextField2.setText(jTextField2.getText()+InetAddress.getLocalHost().getHostAddress());}
        catch(Exception e)
        {
            System.out.println(e);
        };
        start_processing();
        
        
        
        
        
        
        
        
        
        
        
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
    public String get_answer_id(String data)
    {
        int index1;
        int index2;
        index1=data.indexOf('*');
        index2=data.indexOf('@');
        return(data.substring(index1+1,index2));
    }
    public void process_answer(String data)
    {
        String temp;
        
        int row;
        int position;
        try
        {
            int column=Integer.parseInt(get_answer_id(data)); 
            System.out.println("Process answer : "+column);
            System.out.println("Coś jest w kolejce");
            row=find_row(subtract_ID(data));
            System.out.println("row: "+row);
            position=find_client(subtract_ID(data));
            System.out.println("position: "+position);
            //System.out.println(get_answer(data));
            if(row==0||position==0)
            {
                System.out.println("Nie znaleziono ID");
                return;
            }
            else
            {
                System.out.println("Wrzucanie do tablicy");
                System.out.println("To trafia do listy odpowiedzi: "+get_answer(data));
                clients.get(position-1).answers.add(get_answer(data));
                model.setValueAt(get_answer(data), row-1, column);
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
                    if(counter==client_count)
                    {
                        step2=0;
                        counter=0;
                        return;
                    }
                    System.out.println("Process answer: "+counter);
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
                          System.out.println("counter: "+counter);
                          counter=0;
                          step2=0;
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
            return Double.compare(b.income, a.income);
        } 
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
            out.write("Imię i nazwisko\t");
            out.write("Czy inwestujesz na giełdzie?\t");
            out.write("Czy straciłbyś na bitcoinie?\t");
            out.write("Ile będzie zarabiać absolwent?\t");
            out.write("Czy będziesz zarabiał więcej?\t");
            out.write("Zarobki po odliczeniu ZUSu\t");
            out.write("Przedział błędu\t");
            out.write("Inwestycja\t");
            out.write("Którą pozycję zająłeś\t");
            out.write("Przychód\t");
            out.write("Rzeczywista pozycja\t");
            out.write("Liczba punktów\t");
            out.write("\n");
            for(int i=0; i<client_count;++i)
            {
                out.write("\n");
                for(int j=0; j<clients.get(i).answers.size();++j)
                {
                   // System.out.println(clients.get(i).answers.get(j));
                    out.write(clients.get(i).answers.get(j));
                    out.write("\t");
                }
                out.write(Double.toString(clients.get(i).income));
                out.write("\t");
                out.write(clients.get(i).reality);
                out.write("\t");
                out.write(Double.toString(clients.get(i).grade1+clients.get(i).grade2));
                out.write("\t");
            }
            out.close();
        }
        catch(IOException e){};
    }
     

  

    public void get_prices()
    {
        //dane "zmieniające się" są zebrane z dnia 29.04
        //dane historyczne są z 30 ostatnich aż do kursu otwarcia z dnia 29.04
        //dane wynikowe są z zamknięcia dnia 30.04. Okres inwwestycji to końcówka dnia 29.04 i cały dzień 30.04
        quotes.add(new Quote_price("Coca Cola","KO","https://www.nasdaq.com/symbol/ko/historical","https://www.nasdaq.com/symbol/ko/real-time"));
        quotes.get(quotes.size()-1).current_price=Double.toString(48.72);
        quotes.add(new Quote_price("Google","GOOG","https://www.nasdaq.com/symbol/goog/historical","https://www.nasdaq.com/symbol/goog/real-time"));
        quotes.get(quotes.size()-1).current_price=Double.toString(1185.4);
        quotes.add(new Quote_price("Apple","AAPL","https://www.nasdaq.com/symbol/aapl/historical","https://www.nasdaq.com/symbol/aapl/real-time")); 
        quotes.get(quotes.size()-1).current_price=Double.toString(211.75);
        quotes.add(new Quote_price("Facebook","FB","https://www.nasdaq.com/symbol/fb/historical","https://www.nasdaq.com/symbol/fb/real-time"));
        quotes.get(quotes.size()-1).current_price=Double.toString(195.47);
        quotes.add(new Quote_price("Tesla","TSLA","https://www.nasdaq.com/symbol/TSLA/historical","https://www.nasdaq.com/symbol/TSLA/real-time"));
        quotes.get(quotes.size()-1).current_price=Double.toString(255.03);
        quotes.add(new Quote_price("Amazon","AMZN","https://www.nasdaq.com/symbol/AMZN/historical","https://www.nasdaq.com/symbol/AMZN/real-time"));
        quotes.get(quotes.size()-1).current_price=Double.toString(1962.46);
        quotes.add(new Quote_price("Microsoft","MSFT","https://www.nasdaq.com/symbol/MSFT/historical","https://www.nasdaq.com/symbol/MSFT/real-time"));
        quotes.get(quotes.size()-1).current_price=Double.toString(128.9);
        quotes.add(new Quote_price("Intel","INTC","https://www.nasdaq.com/symbol/INTC/historical","https://www.nasdaq.com/symbol/INTC/real-time"));
        quotes.get(quotes.size()-1).current_price=Double.toString(51.75);
        quotes.add(new Quote_price("Groupon","GRPN","https://www.nasdaq.com/symbol/GRPN/historical","https://www.nasdaq.com/symbol/GRPN/real-time"));
        quotes.get(quotes.size()-1).current_price=Double.toString(3.59);
        quotes.add(new Quote_price("EBay","EBAY","https://www.nasdaq.com/symbol/EBAY/historical","https://www.nasdaq.com/symbol/EBAY/real-time"));
        quotes.get(quotes.size()-1).current_price=Double.toString(38.28);
        quotes.add(new Quote_price("Starbucks","SBUX","https://www.nasdaq.com/symbol/SBUX/historical","https://www.nasdaq.com/symbol/SBUX/real-time"));
        quotes.get(quotes.size()-1).current_price=Double.toString(78.05);
        quotes.add(new Quote_price("GoPro","GPRO","https://www.nasdaq.com/symbol/GPRO/historical","https://www.nasdaq.com/symbol/GPRO/real-time"));
        quotes.get(quotes.size()-1).current_price=Double.toString(6.03);
        quotes.add(new Quote_price("SunPower","SPWR","https://www.nasdaq.com/symbol/SPWR/historical","https://www.nasdaq.com/symbol/SPWR/real-time"));
        quotes.get(quotes.size()-1).current_price=Double.toString(7.54);
        quotes.add(new Quote_price("General Motors","GM","https://www.nasdaq.com/symbol/gm/historical","https://www.nasdaq.com/symbol/gm/real-time"));
        quotes.get(quotes.size()-1).current_price=Double.toString(38.8);
        quotes.add(new Quote_price("Procter&Gamble","PG","https://www.nasdaq.com/symbol/pg/historical","https://www.nasdaq.com/symbol/pg/real-time"));
        quotes.get(quotes.size()-1).current_price=Double.toString(106.08);

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    }
    @SuppressWarnings("unchecked")
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Połączony", "Imię i naziwsko", "Czy inwestujesz na giełdzie", "Czy wycofałbyś się z bitcoina", "Ile będziesz zarabiał", "Czy więcej niż inni", "Kwota netto", "Przedział błędu", "Inwestycja", "Pozycja", "Rzeczywistość"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setMinimumSize(new java.awt.Dimension(210, 300));
        jTable1.setName(""); // NOI18N
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(20);
            jTable1.getColumnModel().getColumn(1).setMinWidth(60);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(60);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(60);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);
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
        }

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel2.setText("Tablica postępu");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        jLabel1.setText("Liczba uczestników");

        jTextField1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("0");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton3.setText("Roześlij wynik wszystkim");
        jButton3.setToolTipText("");
        jButton3.setActionCommand("Roześlij wyniki wszystkim");
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

        jButton1.setText("Zezwól wszystkim");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton5.setText("Zezwól jednemu");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Roześlij wynik jednemu");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton2.setText("Roześlij punkty");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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
            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

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

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
            Object[] options = {"Tak","Nie"};
            int n = JOptionPane.showOptionDialog(this,
             "Czy liczba użytkowników jest poprawna?",
             "Sprawdzenie",
             JOptionPane.YES_NO_OPTION,
             JOptionPane.QUESTION_MESSAGE,
             null,     //do not use a custom Icon
             options,  //the titles of buttons
             options[0]); //default button title
            get_prices();  
            if(n==0)
            {
                for(int i=0; i<clients.size();++i)
                {
                    int ile=clients.get(i).answers.size();
                    if(clients.get(i).answers.size()==8)
                    {
                        clients.get(i).income=0;
                        clients.get(i).read_investments(clients.get(i).answers.get(7));
                        clients.get(i).calculate_income(quotes);
                        for(int j=0; j<clients.get(i).invest.size();++j)
                        {
                            clients.get(i).income+=Double.parseDouble(clients.get(i).invest.get(j).income);
                        }
                    }

                }

                for(int i=0;i<clients.size();++i)
                {
                       String answer="R:"+Double.toString(clients.get(i).income)+";";
                       answer+=jTextField1.getText()+"#";
                       clients.get(i).output.add(answer);
                } 
                 write_data();
            }
            else if(n==1)
                return;
         


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        write_data();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        for(int i=0;i<clients.size();++i)
        {
            clients.get(i).output.add("GoStock");
        }        
        write_data();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try
        {
        
        int row=jTable1.getSelectedRow();
        String temp=(String)model.getValueAt(row, 0);
        int value=find_client(temp);
        clients.get(value-1).output.add("GoStock");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
         try
         {
                    if(quotes.size()==0) 
            get_prices();    
            int row=jTable1.getSelectedRow();
            String temp=(String)model.getValueAt(row, 0);
            int value=find_client(temp);
            int ile=clients.get(value-1).answers.size();
            clients.get(value-1).income=0;
            if(clients.get(value-1).answers.size()==8)
            {
                clients.get(value-1).read_investments(clients.get(value-1).answers.get(7));
                clients.get(value-1).calculate_income(quotes);
                for(int j=0; j<clients.get(value-1).invest.size();++j)
                {
                    clients.get(value-1).income+=Double.parseDouble(clients.get(value-1).invest.get(j).income);
                }
            }
           String answer="R:"+Double.toString(clients.get(value-1).income)+";";
           answer+=jTextField1.getText()+"#";
           clients.get(value-1).output.add(answer); 
         }
         catch(Exception e)
         {
             System.out.println(e);
         }


    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
     try
     {
        grade_answers();
        int row;
        for(int i=0; i<clients.size();++i)
        {
            row=find_row(clients.get(i).ID);
            model.setValueAt(i+1, row-1, 11);
        }
        
        for(int i=0; i<clients.size();++i)
        {
            double points=clients.get(i).grade1+clients.get(i).grade2;
            points=Math.round(points);
            if(points>3.0)
                points=3.0;
            clients.get(i).output.add("L"+Double.toString(points));
        }
         write_data();
     }
     catch(Exception e)
     {
         System.out.println(e);
     }

    }//GEN-LAST:event_jButton2ActionPerformed
    
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
                    if(clients.get(i).answers.size()>=6)
                    {
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

            }
            //Druga część oceniania
            SortByIncome sorting = new SortByIncome();
            Collections.sort(clients, sorting);
            int position;
            int reality;
            int difference;
            for(int i=0; i<clients.size();++i)
            {
                if(clients.get(i).answers.size()>=9)
                {
                    position=Integer.parseInt(clients.get(i).answers.get(8));
                    reality=i+1;
                    difference=Math.abs(position-reality);
                  //  clients.get(i).answers.add(Double.toString(clients.get(i).income));
                    clients.get(i).reality=Integer.toString(reality);
                    for(int j=0; j<=6;++j)
                    {
                        if(difference==j)
                        {
                           clients.get(i).grade2=(2-0.3*j);
                           break;
                        }
                    }
                }

            }
        write_data();
        }
        
        catch (Exception e){System.out.println(e);};
    }
    

    public int get_percentage(int which)
    {
        if(clients.get(which).answers.size()>=6)
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
        return 0;
    }
    public void new_client(BlockingQueue<String> input,BlockingQueue<String> output,String ID)
    {
        try
        {
            clientImage cl=new clientImage(input,output);
            cl.set_ID(ID);
            clients.add(cl);
            model.addRow(new Object[]{});
            model.setValueAt(clients.lastElement().ID,clients.size()-1,0);
            model.setValueAt("Połączony",clients.size()-1,1);
            ++client_count;
            jTextField1.setText(Integer.toString(client_count));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
                
    }
    public void start_processing()
    {
        System.out.println("Wchodzimy do funkcji");
          Thread thread = new Thread() {
          public void run() 
          {
              System.out.println("Watek leci");
              String data;
              while(true)
              {
                try 
                {
                    for(int i=0;i<clients.size();++i)
                    {
                      if(clients.get(i).input.isEmpty()==false)
                      {
                          data=clients.get(i).input.take();
                          process_answer(data);
                      }
                    }
                }
                catch (Exception e) 
                {
                    System.out.println(e);
                }
              }
          }
      };
      thread.start();
  
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
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
