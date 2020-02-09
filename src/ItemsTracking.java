
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Elbit Storage
 */
public class ItemsTracking extends javax.swing.JFrame {
    DataBase db;
    private String _user = "";
    private HashMap<String, SendItemOverseas> send_item_track_map = new HashMap<String, SendItemOverseas>();
    private WindowListener exitListener;

    /**
     * Creates new form ItemsTracking
     */
    public ItemsTracking() {
        initComponents();
        this.exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                cancelOperation();
            }
        };
        this.jTable1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    doubleClickItem();
                }
            }
        });
        this.jTable1.setSelectionModel(new ForcedListSelectionModel());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.addWindowListener(exitListener);
    }
    
    
    public void doubleClickItem() {
        JTable table = this.jTable1;
        if (!send_item_track_map.containsKey(table.getValueAt(table.getSelectedRow(), 0).toString())) {
            String[] data = new String[table.getColumnCount() - 1];
            int row = table.getSelectedRow();
            for (int i = 0; i < data.length; i++) {
                if (table.getValueAt(table.getSelectedRow(), i + 1) != null) {
                    data[i] = table.getValueAt(table.getSelectedRow(), i + 1).toString();
                } else {
                    data[i] = "";
                }
            }
//            ItemDescription id = new ItemDescription();
//            id.setID(table.getValueAt(table.getSelectedRow(), 0).toString());
//            id.intialMain(this);
//            id.setAdmin(this.isAdmin);
//            id.setDB(db);
//            id.setTableName(this.tabbs.getTitleAt(this.tabbs.getSelectedIndex()).toLowerCase() + "_new");
//            id.inserData(data);
//            id.setVisible(true);
            SendItemOverseas sio = new SendItemOverseas();
            sio.setDB(db);
            sio.intialMain(this);
            sio.setID(table.getValueAt(table.getSelectedRow(), 0).toString());
            sio.insertData(data);
            sio.setVisible(true);
            send_item_track_map.put(table.getValueAt(table.getSelectedRow(), 0).toString(), sio);
            //id.setAlwaysOnTop(true);
        } else {
            send_item_track_map.get(table.getValueAt(table.getSelectedRow(), 0).toString()).setState(java.awt.Frame.NORMAL);
            send_item_track_map.get(table.getValueAt(table.getSelectedRow(), 0).toString()).toFront();
        }
    }
    
    public void closeItemDescFrame(String id) {
        if (this.send_item_track_map.containsKey(id)) {
            this.send_item_track_map.remove(id).dispose();
        }
        this.refresh();
    }
    
    public void loadSearchResultToJTable(ArrayList<String[]> table, JTable gui_table) {
        if (table == null || table.size() == 0) {
            JOptionPane.showMessageDialog(null, "Not found", "Alert", JOptionPane.ERROR_MESSAGE);
            return;
        }
        DefaultTableModel model = (DefaultTableModel) gui_table.getModel();
        model.setRowCount(0);
        String[] row = new String[10];
        for (int i = 0; i < table.size(); i++) {
            for (int j = 0; j < table.get(i).length; j++) {
                row[j] = table.get(i)[j];
            }
            //DefaultTableModel model = (DefaultTableModel) gui_table.getModel();
            model.addRow(row);
        }
    }
    
    private void searchTable(JTable table, String id) {
        this.loadSearchResultToJTable(db.search("items_tracking", id), table);
    }
    
    
    public void loadDBtoJTable(String table_name, JTable gui_table) {
        ArrayList<String[]> table = db.getTable(table_name);
        String[] row = new String[10];
        for (int i = 0; i < table.size(); i++) {
            for (int j = 0; j < table.get(i).length; j++) {
                row[j] = table.get(i)[j];
            }
            DefaultTableModel model = (DefaultTableModel) gui_table.getModel();
            model.addRow(row);
        }
        //this.paintZeroToRed(gui_table);
    }
    
    public void refresh() {
        JTable gui_table = this.jTable1;
        DefaultTableModel model = (DefaultTableModel) gui_table.getModel();
        model.setRowCount(0);
        loadDBtoJTable("items_tracking", gui_table);
    }
    
    
    public void setDB(DataBase d) {
        this.db = d;
        loadDBtoJTable("items_tracking", this.jTable1);
    }

    public void setUser(String user) {
        this._user = user;
    }
    
    private void cancelOperation(){
        this.dispose();
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
        search_jTextField1 = new javax.swing.JTextField();
        search_jButton1 = new javax.swing.JButton();
        list_jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        refresh_jButton1 = new javax.swing.JButton();
        delete_row_jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Overseas Item Tracking");
        setResizable(false);

        search_jButton1.setText("Search");
        search_jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Serial Number", "Location", "Shipping Date ", "Date Received ", "Simulator", "Worker Name", "Comments", "Attachments"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout list_jPanel2Layout = new javax.swing.GroupLayout(list_jPanel2);
        list_jPanel2.setLayout(list_jPanel2Layout);
        list_jPanel2Layout.setHorizontalGroup(
            list_jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(list_jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE)
                .addContainerGap())
        );
        list_jPanel2Layout.setVerticalGroup(
            list_jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(list_jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                .addContainerGap())
        );

        refresh_jButton1.setText("Refresh");
        refresh_jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refresh_jButton1ActionPerformed(evt);
            }
        });

        delete_row_jButton2.setText("Delete row");
        delete_row_jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_row_jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(list_jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(search_jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search_jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71)
                        .addComponent(refresh_jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(delete_row_jButton2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(search_jTextField1)
                    .addComponent(delete_row_jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(search_jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(refresh_jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(list_jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void search_jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_jButton1ActionPerformed
        // TODO add your handling code here:
        this.searchTable(this.jTable1, this.search_jTextField1.getText());
    }//GEN-LAST:event_search_jButton1ActionPerformed

    private void delete_row_jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_row_jButton2ActionPerformed
        // TODO add your handling code here:
        JTable table = this.jTable1;
        String table_name = "items_tracking";
        String id = table.getValueAt(table.getSelectedRow(), 0).toString();
        String item = table.getValueAt(table.getSelectedRow(), 1).toString();
        String user_name = this._user;
        Object temp = this.db.deleteRow(table_name, id, item, user_name);
        if (temp instanceof Exception) {
            JOptionPane.showMessageDialog(null, "ERROR! \n " + ((Exception) temp).getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        } else {
            this.refresh_jButton1ActionPerformed(evt);
        }
    }//GEN-LAST:event_delete_row_jButton2ActionPerformed

    private void refresh_jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refresh_jButton1ActionPerformed
        // TODO add your handling code here:
        this.refresh();
    }//GEN-LAST:event_refresh_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(ItemsTracking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ItemsTracking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ItemsTracking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ItemsTracking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new ItemsTracking().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton delete_row_jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel list_jPanel2;
    private javax.swing.JButton refresh_jButton1;
    private javax.swing.JButton search_jButton1;
    private javax.swing.JTextField search_jTextField1;
    // End of variables declaration//GEN-END:variables
}
