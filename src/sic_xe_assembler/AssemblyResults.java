/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sic_xe_assembler;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class AssemblyResults extends javax.swing.JFrame {
    
    SIC_XE_Assembler ass;
    
    public AssemblyResults(SIC_XE_Assembler ass) {
        this.ass = ass;
    }
    
    public void run()
    {
        this.setVisible(true);
        initComponents();
        setTitle("SIC/XE Assembler: Assembly");
        setIconImage(new ImageIcon(getClass().getResource("Icon.png")).getImage());
        setResizable(false);
        setLocationRelativeTo(null);
        this.requestFocus();
        this.setAutoRequestFocus(true);
        jLabel1.setFocusable(false);
        jLabel2.setFocusable(false);
        jScrollPane1.setFocusable(false);
        jScrollPane2.setFocusable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public void displayResults(ArrayList<Instruction> instructions, ArrayList<Symbol> symbols)
    {
        DefaultTableModel model = new DefaultTableModel(new String[] { "Label", "Address" },0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };        
        
        if(!jTextArea1.getText().equals(""))
        {
            jTextArea1.setText("");
            for(int i =0; i<model.getRowCount();i++)
            {
                model.removeRow(i);
            }
        }
        
        for(int i = 0; i<instructions.size();i++)
        {           
            String test = "" + i;
            if(!instructions.get(i).address.isEmpty())
                test = test + "\t" + instructions.get(i).address;
            else
                test = test+ "\t";            
            if(!instructions.get(i).comment.isEmpty())
                test = test + "\t" + instructions.get(i).comment;
            if(!instructions.get(i).label.isEmpty())
                test = test + "\t" + instructions.get(i).label;
            else
                test = test+ "\t";            
            if(!instructions.get(i).opcode.isEmpty())
                test = test + "\t" + instructions.get(i).opcode;
            if(!instructions.get(i).operand1.isEmpty())
                test = test + "\t" + instructions.get(i).operand1;
            if(!instructions.get(i).operand2.isEmpty())
                test = test + "\t" + instructions.get(i).operand2;
            jTextArea1.append(test + "\n");
            if(!instructions.get(i).Error.isEmpty())
                jTextArea1.append("\t"+instructions.get(i).Error + "\n");
        }
        jTable1.setModel(model);        
        jTable1.setFocusable(false);
        for(int i = 0; i<symbols.size();i++)
        {
                model.addRow(new Object[]{ symbols.get(i).name , symbols.get(i).address});
        }
        jTextArea1.append("\n\nPRESS ENTER KEY TO CONTINUE...");
        update(this.getGraphics());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(960, 770));
        setSize(new java.awt.Dimension(960, 770));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Label", "Content", "Size"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
        }

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 120, 270, 590));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setMargin(new java.awt.Insets(10, 10, 10, 10));
        jScrollPane1.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 580, 591));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Symbol Table");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(673, 90, 220, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Source Code");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 580, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        this.setVisible(false);
    }//GEN-LAST:event_formKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
