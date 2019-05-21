/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sic_xe_assembler;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author user
 */
public class ObjectCodeDisplay extends javax.swing.JFrame {

    /**
     * Creates new form ObjectCodeDisplay
     */
    public ObjectCodeDisplay() {
        initComponents();
    }

    public void run()
    {
        this.setVisible(true);
        initComponents();
        setTitle("SIC/XE Assembler: Object Code");
        setIconImage(new ImageIcon(getClass().getResource("Icon.png")).getImage());
        setResizable(false);
        setLocationRelativeTo(null);
        this.requestFocus();
        this.setAutoRequestFocus(true);
        jLabel1.setFocusable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public void displayResults(ArrayList<ObjectCode.ObjectInstruction> objinst, String programname, String startAddress, String lastAddress)
    {       
        String length = Integer.toHexString(0x1000000 | (Integer.parseInt(lastAddress,16) - Integer.parseInt(startAddress,16))).substring(1);
        int start = Integer.parseInt(startAddress,16);
        int end = Integer.parseInt(lastAddress, 16);
        startAddress = Integer.toHexString(0x1000000 | start).substring(1);
        String Hrecord = "H^"+programname+"^"+startAddress+"^"+length;
        String Erecord = "E^"+startAddress;
        jTextArea2.append(Hrecord.toUpperCase()+"\n");
        int i=0;
        while(objinst.get(i).format!=5 && i<objinst.size()-1)
        {
            i++;
        }
        int declarations = i;
        int proglength = declarations;
        int instlength = proglength; 
        double y = Math.ceil((float)instlength/10);
        System.out.println((float)instlength/10);
        System.out.println(y);
        int z=0;
        while(z < y)
        {	
            int textlength;
            if(instlength>=10)
            	textlength=10*3;		
            else
            	textlength = instlength*3;
            System.out.println(textlength);            
            String foo = Integer.toHexString(textlength);
            foo = "00".substring(foo.length())+foo;
            String record = "T^"+objinst.get(0+z*10).address + "^" + foo;
            textlength = textlength/3;
            int j=0;
            for(j=j+z*10;j<textlength+10*z;j++)
            {
                record = record +"^"+ objinst.get(j).result;
            }	
            jTextArea2.append(record.toUpperCase()+"\n");
            instlength -= textlength;
            z++;
        }
        
        if(i<objinst.size())
        {
            String record = "";
            int record_length = (objinst.size()-i)*3;
            record = "T^"+objinst.get(i).address;            
            record = record + "^" + Integer.toHexString(0x100 | record_length).substring(1);
            for(int j = i; j<objinst.size();j++)
            {
                record = record + "^" + objinst.get(j).result;
            }
            record = record + "\n";
            jTextArea2.append(record.toUpperCase());
        }
        jTextArea2.append(Erecord.toUpperCase()+"\n");
        this.run();
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 540));
        setSize(new java.awt.Dimension(700, 540));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ObjectCode Display");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 50, 630, -1));

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Consolas", 0, 15)); // NOI18N
        jTextArea2.setRows(10);
        jTextArea2.setTabSize(3);
        jTextArea2.setFocusable(false);
        jTextArea2.setMargin(new java.awt.Insets(10, 10, 10, 10));
        jScrollPane2.setViewportView(jTextArea2);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 590, 380));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        this.dispose();
    }//GEN-LAST:event_formKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}
