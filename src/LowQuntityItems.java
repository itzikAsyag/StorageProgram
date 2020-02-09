
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
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
public class LowQuntityItems extends javax.swing.JFrame {

    private HashMap<String, ItemDescription> item_desc_map = new HashMap<String, ItemDescription>();
    private boolean isAdmin = false;
    DataBase db;
    private storageTabs main = null;
    private String _user = "";

    /**
     * Creates new form LowQuntityItems
     */
    public LowQuntityItems(DataBase d) {
        initComponents();
        this.db = d;
        this.barak_jTable.setSelectionModel(new ForcedListSelectionModel());
        this.sufa_jTable.setSelectionModel(new ForcedListSelectionModel());
        this.baz_raam_jTable.setSelectionModel(new ForcedListSelectionModel());
        firstLoad();
        this.barak_jTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    doubleClickBarak();
                }
            }
        });
        this.sufa_jTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    doubleClickSufa();
                }
            }
        });
        this.baz_raam_jTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    doubleClickBaz();
                }
            }
        });
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.barak_jTable.getColumnModel().getColumn(1).setPreferredWidth(800);
        this.sufa_jTable.getColumnModel().getColumn(1).setPreferredWidth(800);
        this.baz_raam_jTable.getColumnModel().getColumn(1).setPreferredWidth(800);
    }

    public void firstLoad() {
        loadDBtoJTable("barak_new", this.barak_jTable);
        loadDBtoJTable("sufa_new", this.sufa_jTable);
        loadDBtoJTable("baz_new", this.baz_raam_jTable);
        /*
        ///// i dont know why the id column was right rendered defult.
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        this.sufa_jTable.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
        this.baz_raam_jTable.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
        this.barak_jTable.setRowHeight(40);
        this.sufa_jTable.setRowHeight(40);
        this.baz_raam_jTable.setRowHeight(40);
        this.barak_jTable.setAutoCreateRowSorter(true);
        this.sufa_jTable.setAutoCreateRowSorter(true);
        this.baz_raam_jTable.setAutoCreateRowSorter(true);
         */
    }

    public void setDB(DataBase d) {
        this.db = d;
    }

    public void intialMain(storageTabs frame) {
        this.main = frame;
    }

    public void loadDBtoJTable(String table_name, JTable gui_table) {
        ArrayList<String[]> table = db.getTable(table_name);
        String[] row = new String[10];
        for (int i = 0; i < table.size(); i++) {
            int min_qunt = Integer.parseInt(table.get(i)[11]);
            for (int j = 0; j < row.length; j++) {
                row[j] = table.get(i)[j];
            }
            DefaultTableModel model = (DefaultTableModel) gui_table.getModel();
            int qunt = Integer.parseInt(row[5]);
            if (row[1] != null && !row[1].equals("") && qunt <= min_qunt) {
                model.addRow(row);
            }
        }
        //this.paintZeroToRed(gui_table);
    }

    public void doubleClickBarak() {
        JTable table = this.barak_jTable;
        String[] data = new String[table.getColumnCount() - 1];
        int row = table.getSelectedRow();
        for (int i = 0; i < data.length; i++) {
            if (table.getValueAt(table.getSelectedRow(), i + 1) != null) {
                data[i] = table.getValueAt(table.getSelectedRow(), i + 1).toString();
            } else {
                data[i] = "";
            }
        }
        ItemDescription id = new ItemDescription();
        id.setID(table.getValueAt(table.getSelectedRow(), 0).toString());
        id.setAdmin(this.isAdmin);
        id.setUserName(this._user);
        id.setDB(db);
        id.intialMain(this.main);
        id.setTableName(this.tabbs.getTitleAt(this.tabbs.getSelectedIndex()).toLowerCase() + "_new");
        id.inserData(data);
        id.setVisible(true);

    }

    public void doubleClickBaz() {
        JTable table = this.baz_raam_jTable;
        String[] data = new String[table.getColumnCount() - 1];
        int row = table.getSelectedRow();
        for (int i = 0; i < data.length; i++) {
            if (table.getValueAt(table.getSelectedRow(), i + 1) != null) {
                data[i] = table.getValueAt(table.getSelectedRow(), i + 1).toString();
            } else {
                data[i] = "";
            }
        }

        ItemDescription id = new ItemDescription();
        id.setID(table.getValueAt(table.getSelectedRow(), 0).toString());
        id.setAdmin(this.isAdmin);
        id.setDB(db);
        id.isBaz();
        id.setTableName(this.tabbs.getTitleAt(this.tabbs.getSelectedIndex()).toLowerCase() + "_new");
        id.inserData(data);
        id.setVisible(true);
    }

    public void doubleClickSufa() {
        JTable table = this.sufa_jTable;
        String[] data = new String[table.getColumnCount() - 1];
        int row = table.getSelectedRow();
        for (int i = 0; i < data.length; i++) {
            if (table.getValueAt(table.getSelectedRow(), i + 1) != null) {
                data[i] = table.getValueAt(table.getSelectedRow(), i + 1).toString();
            } else {
                data[i] = "";
            }
        }
        ItemDescription id = new ItemDescription();
        id.setID(table.getValueAt(table.getSelectedRow(), 0).toString());
        id.setAdmin(this.isAdmin);
        id.setDB(db);
        id.setTableName(this.tabbs.getTitleAt(this.tabbs.getSelectedIndex()).toLowerCase() + "_new");
        id.inserData(data);
        id.setVisible(true);

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
        tabbs = new javax.swing.JTabbedPane();
        barak_jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        barak_jTable = new javax.swing.JTable();
        sufa_jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        sufa_jTable = new javax.swing.JTable();
        baz_jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        baz_raam_jTable = new javax.swing.JTable();
        ok_jButton1 = new javax.swing.JButton();
        dismiss_jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Low Quntity Items");

        barak_jTable.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        barak_jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "name", "part number", "serial number", "producer name", "quntity at storage", "supply by", "quntity at system", "location at storage", "comments"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(barak_jTable);

        javax.swing.GroupLayout barak_jPanel2Layout = new javax.swing.GroupLayout(barak_jPanel2);
        barak_jPanel2.setLayout(barak_jPanel2Layout);
        barak_jPanel2Layout.setHorizontalGroup(
            barak_jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
        );
        barak_jPanel2Layout.setVerticalGroup(
            barak_jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
        );

        tabbs.addTab("barak", barak_jPanel2);

        sufa_jTable.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        sufa_jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "name", "part number", "serial number", "producer name", "quntity at storage", "supply by", "quntity at system", "location at storage", "comments"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(sufa_jTable);

        javax.swing.GroupLayout sufa_jPanel3Layout = new javax.swing.GroupLayout(sufa_jPanel3);
        sufa_jPanel3.setLayout(sufa_jPanel3Layout);
        sufa_jPanel3Layout.setHorizontalGroup(
            sufa_jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
        );
        sufa_jPanel3Layout.setVerticalGroup(
            sufa_jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
        );

        tabbs.addTab("sufa", sufa_jPanel3);

        baz_raam_jTable.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        baz_raam_jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "name", "part number", "serial number", "iaf number", "quntity at storage", "loan from iaf", "aircraft type", "location at storage", "comments"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(baz_raam_jTable);

        javax.swing.GroupLayout baz_jPanel4Layout = new javax.swing.GroupLayout(baz_jPanel4);
        baz_jPanel4.setLayout(baz_jPanel4Layout);
        baz_jPanel4Layout.setHorizontalGroup(
            baz_jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
        );
        baz_jPanel4Layout.setVerticalGroup(
            baz_jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
        );

        tabbs.addTab("baz", baz_jPanel4);

        ok_jButton1.setText("OK");
        ok_jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ok_jButton1ActionPerformed(evt);
            }
        });

        dismiss_jButton2.setText("Dismiss");
        dismiss_jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dismiss_jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("    Low Quntity Items");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ok_jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dismiss_jButton2))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tabbs, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tabbs, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ok_jButton1)
                    .addComponent(dismiss_jButton2))
                .addGap(0, 18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dismiss_jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dismiss_jButton2ActionPerformed
        // TODO add your handling code here:
        this.main.setDissmissed();
        this.dispose();
    }//GEN-LAST:event_dismiss_jButton2ActionPerformed

    private void ok_jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ok_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_ok_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel barak_jPanel2;
    private javax.swing.JTable barak_jTable;
    private javax.swing.JPanel baz_jPanel4;
    private javax.swing.JTable baz_raam_jTable;
    private javax.swing.JButton dismiss_jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JButton ok_jButton1;
    private javax.swing.JPanel sufa_jPanel3;
    private javax.swing.JTable sufa_jTable;
    private javax.swing.JTabbedPane tabbs;
    // End of variables declaration//GEN-END:variables
}
