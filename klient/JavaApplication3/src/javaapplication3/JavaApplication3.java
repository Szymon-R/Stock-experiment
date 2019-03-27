/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Szymon
 */
public class JavaApplication3 {

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
            java.util.logging.Logger.getLogger(name_screen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(name_screen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(name_screen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(name_screen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                IP_Screen ip=new IP_Screen();
                ip.setLocationRelativeTo(null);
                ip.setVisible(true);
                ip.addWindowListener(new WindowAdapter() 
                {
                    public void windowClosing(WindowEvent we) 
                    {
                    int result = JOptionPane.showConfirmDialog(ip,"Jesteś pewien, że chcesz zamknąc program?", "Potwierdzenie",JOptionPane.YES_NO_OPTION);
                    if(result == JOptionPane.YES_OPTION)
                        ip.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    else if(result == JOptionPane.NO_OPTION)
                        ip.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    }
                });
            }
        });
    }
    
}
