/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sic_xe_assembler;

import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class AssemblyResults extends javax.swing.JFrame {
    
    public AssemblyResults() {
        
    }
    
    public void run()
    {
        this.setVisible(true);
        initComponents();
        setTitle("SIC/XE Assembler");
        setIconImage(new ImageIcon(getClass().getResource("Icon.png")).getImage());
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    public void displayResults(ArrayList<Instruction> instructions, ArrayList<Symbol> symbols)
    {
        for(int i = 0; i<instructions.size();i++)
        {           
            String test = "" + i;
            if(!instructions.get(i).address.isEmpty())
                test = test + "\t" + instructions.get(i).address;
            if(!instructions.get(i).comment.isEmpty())
                test = test + "\t" + instructions.get(i).comment;
            if(!instructions.get(i).label.isEmpty())
                test = test + "\t" + instructions.get(i).label;
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
        DefaultTableModel model = new DefaultTableModel(new String[] { "Label", "Content", "Size" },0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        jTable1.setModel(model);        
        jTable1.setFocusable(false);
        for(int i = 0; i<symbols.size();i++)
        {
            if(symbols.get(i).size==0)
                model.addRow(new Object[]{symbols.get(i).name,symbols.get(i).data,symbols.get(i).type});
            else
                model.addRow(new Object[]{symbols.get(i).name,symbols.get(i).data,symbols.get(i).size + " * " + symbols.get(i).type});
        }
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

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 120, 220, 590));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setMargin(new java.awt.Insets(10, 10, 10, 10));
        jScrollPane1.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 623, 591));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Symbol Table");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(673, 90, 220, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Source Code");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 620, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
