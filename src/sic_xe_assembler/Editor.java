/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sic_xe_assembler;

import java.awt.Desktop;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Editor extends javax.swing.JFrame {

    File f;
    SIC_XE_Assembler ass;
    
    public Editor(SIC_XE_Assembler ass) {
        this.ass = ass;
    }
    
    public void run()
    {
        this.setVisible(true);
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        jTextArea1.setMargin(new Insets(10,10,10,10));
    }

    public File exportFile()
    {
        return f;
    }    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();

        jDialog1.setLocation(jTextArea1.getLocation());
        jDialog1.setResizable(false);
        jDialog1.setSize(new java.awt.Dimension(530, 270));
        jDialog1.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Warning:");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jDialog1.getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 490, 60));

        jButton1.setText("Apply");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jDialog1.getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 200, 90, 30));

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jDialog1.getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 200, 80, 30));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic_xe_assembler/Warning.png"))); // NOI18N
        jDialog1.getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 510, 70));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1020, 700));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SIC/XE Assembler");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 15, 960, 50));

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Consolas", 0, 15)); // NOI18N
        jTextArea1.setRows(10);
        jScrollPane1.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 960, 520));

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("New");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Open");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Save As");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Assembly");

        jMenuItem4.setText("Assemble");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Simulation");

        jMenuItem5.setText("jMenuItem5");
        jMenu4.add(jMenuItem5);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files(.txt)","txt");
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null);
        f = chooser.getSelectedFile();
        if(f.canRead())
        {
            ass.f = f;
            System.out.println(ass.f.getAbsolutePath());
            try {
                displayFile();
            } catch (IOException ex) {
                Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            jDialog1.setVisible(true);
            jButton1.setVisible(false);
            jButton2.setVisible(true);
            jDialog1.setTitle("File does not exist"); 
            jDialog1.setLocationRelativeTo(null);
            jLabel2.setText("File specified does not exist. Please try again");
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed
    
    private void displayFile() throws FileNotFoundException, IOException{
        jTextArea1.setText("");
        BufferedReader br = new BufferedReader(new FileReader(f)); 
        String st; 
        System.out.println(f.getAbsolutePath());
        while ((st = br.readLine()) != null) 
        {
            jTextArea1.append(st+"\n");
        }
    }
  
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        if(jTextArea1.getText().equals(""))
        {
            jTextArea1.setText("");
        }
        else
        {
            jButton1.setVisible(true);
            jButton2.setVisible(true);
            jDialog1.setTitle("New File");
            jDialog1.setVisible(true);   
            jDialog1.setLocationRelativeTo(null);
            jLabel2.setText("Changes made might not be saved. Do you wish to discard Changes?");
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jDialog1.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jDialog1.setVisible(false);
        jTextArea1.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        if(jTextArea1.getText().equals(""))
        {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files(.txt)","txt");
            chooser.setFileFilter(filter);
            chooser.showOpenDialog(null);
            f = chooser.getSelectedFile();   
            ass.f = f;
            System.out.println(ass.f.getAbsolutePath());
        }
        else
        {
            saveFile();
        }
        
        if(f.canRead())
        {
            try {
                ass.Assemble();
            } catch (IOException ex) {
                Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            jDialog1.setVisible(true);
            jButton1.setVisible(false);
            jButton2.setVisible(true);
            jDialog1.setTitle("File not specified"); 
            jDialog1.setLocationRelativeTo(null);
            jLabel2.setText("File was not specified. Please try again");
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        saveFile();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void saveFile(){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files(.txt)","txt");
        fileChooser.setFileFilter(filter);            
        int retval = fileChooser.showSaveDialog(jMenuItem3);
        if (retval == JFileChooser.APPROVE_OPTION) 
        {
            File file = fileChooser.getSelectedFile();
            if (!file.canRead()) {
                System.out.println(file.getAbsolutePath());
                if (!file.getName().toLowerCase().endsWith(".txt")) 
                {
                    try {
                        String filename = file.getAbsolutePath() + ".txt";
                        System.out.println(filename);
                        file = new File (filename);
                        FileWriter write = new FileWriter(filename, true);
                        BufferedWriter outFile = null;
                        outFile = new BufferedWriter(new FileWriter(filename));
                        jTextArea1.write(outFile);
                    } catch (IOException ex) {
                        Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }            
            }
            else
            {
                String filename = file.getAbsolutePath();
                BufferedWriter outFile = null;
                try {
                    outFile = new BufferedWriter(new FileWriter(filename, false));           
                    jTextArea1.write(outFile);
                } catch (IOException ex) {
                    Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                f = file;
                ass.f=file;
            } catch (Exception e) {
            }   
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
