/*
 * NewSearchPlayer.java
 *
 * Created on May 13, 2006, 9:48 PM
 */

package poppitplayer.gui;

/**
 *
 * @author  datacomm
 */
public class NewSearchPlayer extends javax.swing.JFrame {
    
    /** Creates new form NewSearchPlayer */
    public NewSearchPlayer() {
        this.startForm = new Start();
        initComponents();
    }
    
    /** Creates new form NewSearchPlayer */
    public NewSearchPlayer(Start aForm) {
        this.startForm = aForm;
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabelType = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jCheckBoxDepth = new javax.swing.JCheckBox();
        jCheckBoxNodes = new javax.swing.JCheckBox();
        jTextFieldDepth = new javax.swing.JTextField();
        jLabelDepth = new javax.swing.JLabel();
        jTextFieldNodes = new javax.swing.JTextField();
        jLabelNodes = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("New Search Player Options");
        jLabelType.setText("Search Type");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Depth-First Search", "Breadth-First Search" }));

        jCheckBoxDepth.setText("Max Search Depth");
        jCheckBoxDepth.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBoxDepth.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCheckBoxDepth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxDepthActionPerformed(evt);
            }
        });

        jCheckBoxNodes.setText("Max Search Nodes");
        jCheckBoxNodes.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBoxNodes.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCheckBoxNodes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxNodesActionPerformed(evt);
            }
        });

        jTextFieldDepth.setEditable(false);
        jTextFieldDepth.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jTextFieldDepth.setText("6");

        jLabelDepth.setLabelFor(jTextFieldDepth);
        jLabelDepth.setText("levels");
        jLabelDepth.setEnabled(false);

        jTextFieldNodes.setEditable(false);
        jTextFieldNodes.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jTextFieldNodes.setText("200000");

        jLabelNodes.setLabelFor(jTextFieldNodes);
        jLabelNodes.setText("nodes");
        jLabelNodes.setEnabled(false);

        jButton1.setText("OK");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(124, 124, 124)
                        .add(jButton1))
                    .add(layout.createSequentialGroup()
                        .add(88, 88, 88)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jComboBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelType)))
                    .add(layout.createSequentialGroup()
                        .add(97, 97, 97)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(jTextFieldNodes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 53, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabelNodes))
                            .add(jCheckBoxDepth)
                            .add(layout.createSequentialGroup()
                                .add(jTextFieldDepth, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabelDepth))
                            .add(jCheckBoxNodes))))
                .addContainerGap(96, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .add(jLabelType)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jComboBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(22, 22, 22)
                .add(jCheckBoxDepth)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextFieldDepth, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabelDepth))
                .add(18, 18, 18)
                .add(jCheckBoxNodes)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelNodes)
                    .add(jTextFieldNodes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(19, 19, 19)
                .add(jButton1)
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBoxNodesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxNodesActionPerformed
        if (this.jCheckBoxNodes.isSelected()){
            //this.jCheckBoxNodes.setSelected(true);
            this.jLabelNodes.setEnabled(true);
            this.jTextFieldNodes.setEditable(true);
            this.useMaxNodes = true;
        }else{
            //this.jCheckBoxNodes.setSelected(false);
            this.jLabelNodes.setEnabled(false);
            this.jTextFieldNodes.setEditable(false);
            this.useMaxNodes = false;
        }
    }//GEN-LAST:event_jCheckBoxNodesActionPerformed

    private void jCheckBoxDepthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxDepthActionPerformed
        if (this.jCheckBoxDepth.isSelected()){
            //this.jCheckBoxDepth.setSelected(true);
            this.jLabelDepth.setEnabled(true);
            this.jTextFieldDepth.setEditable(true);
            this.useMaxDepth = true;
        }else{
            //this.jCheckBoxDepth.setSelected(false);
            this.jLabelDepth.setEnabled(false);
            this.jTextFieldDepth.setEditable(false);
            this.useMaxDepth = false;
        }
    }//GEN-LAST:event_jCheckBoxDepthActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewSearchPlayer().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBoxDepth;
    private javax.swing.JCheckBox jCheckBoxNodes;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabelDepth;
    private javax.swing.JLabel jLabelNodes;
    private javax.swing.JLabel jLabelType;
    private javax.swing.JTextField jTextFieldDepth;
    private javax.swing.JTextField jTextFieldNodes;
    // End of variables declaration//GEN-END:variables
    private Start startForm;
    public String searchType = "Depth-First Search";
    public boolean useMaxDepth = false;
    public int maxDepth = 6;
    public boolean useMaxNodes = false;
    public int maxNodes = 200000;
}
