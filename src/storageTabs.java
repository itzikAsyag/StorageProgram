
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultEditorKit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Itzik
 */
public class storageTabs extends javax.swing.JFrame {

    private boolean isAdmin;
    private boolean dismiss = false;
    DataBase db;
    private String _user = "";
    private HashMap<String, ItemDescription> item_desc_map = new HashMap<String, ItemDescription>();
    private HashMap<String, String[]> low_qunt_map = new HashMap<String, String[]>();
    private KeyListener kl = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent ke) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyPressed(KeyEvent ke) {
            if ((ke.getKeyCode() == KeyEvent.VK_F) && ((ke.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
                while (true) {
                    String shelf = JOptionPane.showInputDialog(null, "Enter shelf number");
                    if (shelf != null && !shelf.equals("") && shelf.matches("[0-9]+")) {
                        searchByShelf(shelf);
                        break;
                    } else if (shelf != null) {
                        JOptionPane.showMessageDialog(null, "You must insert only numbers", "Alert", JOptionPane.ERROR_MESSAGE);
                    } else {
                        break;
                    }
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };

    /**
     * Creates new form storageTabs
     */
    public storageTabs() {
        initComponents();
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.isAdmin = false;
        this.search_textfield.addKeyListener(kl);
        this.jMenuBar1.addKeyListener(kl);
        this.tabbs.addKeyListener(kl);
        this.barak_jTable.addKeyListener(kl);
        this.sufa_jTable.addKeyListener(kl);
        this.baz_raam_jTable.addKeyListener(kl);
        JPopupMenu menu = new JPopupMenu();

        Action cut = new DefaultEditorKit.CutAction();
        cut.putValue(Action.NAME, "Cut");
        cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
        menu.add(cut);

        Action copy = new DefaultEditorKit.CopyAction();
        copy.putValue(Action.NAME, "Copy");
        copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
        menu.add(copy);

        Action paste = new DefaultEditorKit.PasteAction();
        paste.putValue(Action.NAME, "Paste");
        paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
        menu.add(paste);

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

        this.search_textfield.setComponentPopupMenu(menu);
        this.barak_jTable.setSelectionModel(new ForcedListSelectionModel());
        this.sufa_jTable.setSelectionModel(new ForcedListSelectionModel());
        this.baz_raam_jTable.setSelectionModel(new ForcedListSelectionModel());
        this.startLisner(5);

        //////////////////////////
        this.barak_jTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        this.sufa_jTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        this.baz_raam_jTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        //////////////////////////
        
    }

    public void closeItemDescFrame(String id) {
        if (this.item_desc_map.containsKey(id)) {
            this.item_desc_map.remove(id).dispose();
        }
    }
    
    public void setDissmissed(){
        this.dismiss = true;
    }

    public void doubleClickBarak() {
        JTable table = this.barak_jTable;
        if (!item_desc_map.containsKey(table.getValueAt(table.getSelectedRow(), 0).toString())) {
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
            id.intialMain(this);
            id.setID(table.getValueAt(table.getSelectedRow(), 0).toString());
            id.setAdmin(this.isAdmin);
            id.setUserName(this._user);
            id.setDB(db);
            id.setTableName(this.tabbs.getTitleAt(this.tabbs.getSelectedIndex()).toLowerCase() + "_new");
            id.inserData(data);
            id.setVisible(true);
            item_desc_map.put(table.getValueAt(table.getSelectedRow(), 0).toString(), id);
            //id.setAlwaysOnTop(true);
        } else {
            item_desc_map.get(table.getValueAt(table.getSelectedRow(), 0).toString()).setState(java.awt.Frame.NORMAL);
            item_desc_map.get(table.getValueAt(table.getSelectedRow(), 0).toString()).toFront();
        }

    }

    public void refreshFromOtherFrame() {
        ActionEvent evt = null;
        this.search_buttonActionPerformed(evt);
    }

    public void doubleClickBaz() {
        JTable table = this.baz_raam_jTable;
        if (!item_desc_map.containsKey(table.getValueAt(table.getSelectedRow(), 0).toString())) {
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
            id.intialMain(this);
            id.setAdmin(this.isAdmin);
            id.setDB(db);
            id.isBaz();
            id.setTableName(this.tabbs.getTitleAt(this.tabbs.getSelectedIndex()).toLowerCase() + "_new");
            id.inserData(data);
            id.setVisible(true);
            item_desc_map.put(table.getValueAt(table.getSelectedRow(), 0).toString(), id);
            //id.setAlwaysOnTop(true);
        } else {
            item_desc_map.get(table.getValueAt(table.getSelectedRow(), 0).toString()).setState(java.awt.Frame.NORMAL);
            item_desc_map.get(table.getValueAt(table.getSelectedRow(), 0).toString()).toFront();
        }
    }

    public void doubleClickSufa() {
        JTable table = this.sufa_jTable;
        if (!item_desc_map.containsKey(table.getValueAt(table.getSelectedRow(), 0).toString())) {
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
            id.intialMain(this);
            id.setAdmin(this.isAdmin);
            id.setDB(db);
            id.setTableName(this.tabbs.getTitleAt(this.tabbs.getSelectedIndex()).toLowerCase() + "_new");
            id.inserData(data);
            id.setVisible(true);
            item_desc_map.put(table.getValueAt(table.getSelectedRow(), 0).toString(), id);
            //id.setAlwaysOnTop(true);
        } else {
            item_desc_map.get(table.getValueAt(table.getSelectedRow(), 0).toString()).setState(java.awt.Frame.NORMAL);
            item_desc_map.get(table.getValueAt(table.getSelectedRow(), 0).toString()).toFront();
        }
    }

    public void firstLoad() {
        loadDBtoJTable("barak_new", this.barak_jTable);
        loadDBtoJTable("sufa_new", this.sufa_jTable);
        loadDBtoJTable("baz_new", this.baz_raam_jTable);
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
    }

    public void setDB(DataBase d) {
        this.db = d;
    }
    
    public void showLowQuntity(){
        if(isAdmin){
            LowQuntityItems lqi = new LowQuntityItems(db);
            lqi.intialMain(this);
            lqi.setVisible(true);
            lqi.toFront();
        }
    }

    public void setUser(String user) {
        this._user = user;
    }

    public void startLisner(int minutes) {
        InactivityListener lisner;
        lisner = new InactivityListener(this, this.logout(this), minutes);
        lisner.start();
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
            if(row[1] != null && !row[1].equals("")){
                model.addRow(row);
            }
            if(qunt <= min_qunt){
                this.low_qunt_map.put(table_name, row);
            }
        }
        //this.paintZeroToRed(gui_table);
    }
    

    /*public void paintZeroToRed(JTable gui_table){
        for (int i = 0; i < gui_table.getRowCount(); i++) {
            System.out.println("gui_table.getModel().getValueAt(i = "+i+", 5) = "+gui_table.getModel().getValueAt(i, 5));
            if(gui_table.getModel().getValueAt(i, 5) == null || gui_table.getModel().getValueAt(i, 5).equals("0")){
                gui_table.getCellRenderer(5, 5).getTableCellRendererComponent(gui_table, 0, false, false, 5, 5`).setBackground(Color.RED);
            }
        }
    }*/
    public void searchByShelf(String shelf) {
        JTable[] my_tables = {this.barak_jTable, this.sufa_jTable, this.baz_raam_jTable};
        JTable table = my_tables[this.tabbs.getSelectedIndex()];
        String table_name = this.tabbs.getTitleAt(this.tabbs.getSelectedIndex()).toLowerCase() + "_new";
        this.loadSearchResultToJTable(this.db.searchByShelf(table_name, shelf), table);
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
        /*for (int i = 0; i < table.size(); i++) { //fill the rows with search result
            for (int j = 0; j < table.get(i).length; j++) {
                gui_table.setValueAt(table.get(i)[j], i, j);
            }
        }
        for (int i = table.size(); i < gui_table.getRowCount(); i++) { //clear the other rows
            for (int j = 0; j < table.get(0).length; j++) {
                gui_table.setValueAt("", i , j);
            }
        }*/
    }

    public void setAdmin(boolean state) {
        this.isAdmin = state;
    }

    public Action logout(JFrame window) {
        return new Action() {
            @Override
            public Object getValue(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void putValue(String string, Object o) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setEnabled(boolean bln) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isEnabled() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener pl) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener pl) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }

    public void setAdminButtonsVisible() {
        this.insert_item_button.setVisible(isAdmin);
        //this.add_item_button.setVisible(isAdmin);
        this.admin_menu.setVisible(isAdmin);
        this.delete_row_jButton.setVisible(isAdmin);
        this.admin_menu.setVisible(isAdmin);
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public boolean getIsAdmin() {
        return this.isAdmin;
    }

    public void refresh() {
        JTable[] my_tables = {this.barak_jTable, this.sufa_jTable, this.baz_raam_jTable};
        //searchTable(my_tables[this.tabbs.getSelectedIndex()] , "");
        JTable gui_table = my_tables[this.tabbs.getSelectedIndex()];
        DefaultTableModel model = (DefaultTableModel) gui_table.getModel();
        model.setRowCount(0);
        loadDBtoJTable(this.tabbs.getTitleAt(this.tabbs.getSelectedIndex()) + "_new", my_tables[this.tabbs.getSelectedIndex()]);
    }

    private void searchTable(JTable table, String id) {
        this.loadSearchResultToJTable(db.search(this.tabbs.getTitleAt(this.tabbs.getSelectedIndex()).toLowerCase() + "_new", id), table);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbs = new javax.swing.JTabbedPane();
        barak_jpanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        barak_jTable = new javax.swing.JTable();
        sufa_jpanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        sufa_jTable = new javax.swing.JTable();
        baz_raam_jpanel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        baz_raam_jTable = new javax.swing.JTable();
        search_textfield = new javax.swing.JTextField();
        search_button = new javax.swing.JButton();
        get_item_button = new javax.swing.JButton();
        add_item_button = new javax.swing.JButton();
        insert_item_button = new javax.swing.JButton();
        delete_row_jButton = new javax.swing.JButton();
        refresh_jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        file_menu = new javax.swing.JMenu();
        about_jMenuItem6 = new javax.swing.JMenuItem();
        log_out_jMenuItem7 = new javax.swing.JMenuItem();
        exit_jMenuItem1 = new javax.swing.JMenuItem();
        edit_menu = new javax.swing.JMenu();
        copy_jMenuItem4 = new javax.swing.JMenuItem();
        paste_jMenuItem5 = new javax.swing.JMenuItem();
        admin_menu = new javax.swing.JMenu();
        create_new_user_jMenuItem1 = new javax.swing.JMenuItem();
        edit_user_jMenuItem2 = new javax.swing.JMenuItem();
        delete_user_jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        items_without_image_jMenuItem2 = new javax.swing.JMenuItem();
        delete_items_without_image_jMenuItem3 = new javax.swing.JMenuItem();
        items_without_serial_number_jMenuItem4 = new javax.swing.JMenuItem();
        items_overseas_jMenu2 = new javax.swing.JMenu();
        send_new_itam_jMenuItem2 = new javax.swing.JMenuItem();
        view_items_jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hzor Storage System");

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

        javax.swing.GroupLayout barak_jpanelLayout = new javax.swing.GroupLayout(barak_jpanel);
        barak_jpanel.setLayout(barak_jpanelLayout);
        barak_jpanelLayout.setHorizontalGroup(
            barak_jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1126, Short.MAX_VALUE)
        );
        barak_jpanelLayout.setVerticalGroup(
            barak_jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );

        tabbs.addTab("Barak", barak_jpanel);

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

        javax.swing.GroupLayout sufa_jpanelLayout = new javax.swing.GroupLayout(sufa_jpanel);
        sufa_jpanel.setLayout(sufa_jpanelLayout);
        sufa_jpanelLayout.setHorizontalGroup(
            sufa_jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1126, Short.MAX_VALUE)
        );
        sufa_jpanelLayout.setVerticalGroup(
            sufa_jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );

        tabbs.addTab("Sufa", sufa_jpanel);

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

        javax.swing.GroupLayout baz_raam_jpanelLayout = new javax.swing.GroupLayout(baz_raam_jpanel);
        baz_raam_jpanel.setLayout(baz_raam_jpanelLayout);
        baz_raam_jpanelLayout.setHorizontalGroup(
            baz_raam_jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1126, Short.MAX_VALUE)
        );
        baz_raam_jpanelLayout.setVerticalGroup(
            baz_raam_jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );

        tabbs.addTab("Baz", baz_raam_jpanel);

        search_textfield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                search_textfieldMouseClicked(evt);
            }
        });
        search_textfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_textfieldActionPerformed(evt);
            }
        });
        search_textfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                search_textfieldKeyPressed(evt);
            }
        });

        search_button.setText("Search");
        search_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_buttonActionPerformed(evt);
            }
        });

        get_item_button.setText("Reduce quntity");
        get_item_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                get_item_buttonActionPerformed(evt);
            }
        });

        add_item_button.setText("Add quntity");
        add_item_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_item_buttonActionPerformed(evt);
            }
        });

        insert_item_button.setText("Insert new item");
        insert_item_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insert_item_buttonActionPerformed(evt);
            }
        });

        delete_row_jButton.setText("Delete row");
        delete_row_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_row_jButtonActionPerformed(evt);
            }
        });

        refresh_jButton1.setText("Refresh");
        refresh_jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refresh_jButton1ActionPerformed(evt);
            }
        });

        file_menu.setText("File");

        about_jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        about_jMenuItem6.setText("About");
        about_jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                about_jMenuItem6ActionPerformed(evt);
            }
        });
        file_menu.add(about_jMenuItem6);

        log_out_jMenuItem7.setText("Logout");
        log_out_jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                log_out_jMenuItem7ActionPerformed(evt);
            }
        });
        file_menu.add(log_out_jMenuItem7);

        exit_jMenuItem1.setText("Exit");
        exit_jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exit_jMenuItem1ActionPerformed(evt);
            }
        });
        file_menu.add(exit_jMenuItem1);

        jMenuBar1.add(file_menu);

        edit_menu.setText("Edit");

        copy_jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        copy_jMenuItem4.setText("Copy");
        copy_jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copy_jMenuItem4ActionPerformed(evt);
            }
        });
        edit_menu.add(copy_jMenuItem4);

        paste_jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        paste_jMenuItem5.setText("Paste");
        paste_jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paste_jMenuItem5ActionPerformed(evt);
            }
        });
        edit_menu.add(paste_jMenuItem5);

        jMenuBar1.add(edit_menu);

        admin_menu.setText("Admin");

        create_new_user_jMenuItem1.setText("Create new user");
        create_new_user_jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                create_new_user_jMenuItem1ActionPerformed(evt);
            }
        });
        admin_menu.add(create_new_user_jMenuItem1);

        edit_user_jMenuItem2.setText("Edit user ");
        edit_user_jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit_user_jMenuItem2ActionPerformed(evt);
            }
        });
        admin_menu.add(edit_user_jMenuItem2);

        delete_user_jMenuItem3.setText("Delete user");
        delete_user_jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_user_jMenuItem3ActionPerformed(evt);
            }
        });
        admin_menu.add(delete_user_jMenuItem3);

        jMenuItem1.setText("Log");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        admin_menu.add(jMenuItem1);

        jMenu1.setText("Debug");

        items_without_image_jMenuItem2.setText("items without image");
        items_without_image_jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                items_without_image_jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(items_without_image_jMenuItem2);

        delete_items_without_image_jMenuItem3.setText("delete items without image");
        delete_items_without_image_jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_items_without_image_jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(delete_items_without_image_jMenuItem3);

        items_without_serial_number_jMenuItem4.setText("items without serial number");
        items_without_serial_number_jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                items_without_serial_number_jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(items_without_serial_number_jMenuItem4);

        admin_menu.add(jMenu1);

        items_overseas_jMenu2.setText("Item Overseas");

        send_new_itam_jMenuItem2.setText("Send new item");
        send_new_itam_jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                send_new_itam_jMenuItem2ActionPerformed(evt);
            }
        });
        items_overseas_jMenu2.add(send_new_itam_jMenuItem2);

        view_items_jMenuItem3.setText("View items ");
        view_items_jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view_items_jMenuItem3ActionPerformed(evt);
            }
        });
        items_overseas_jMenu2.add(view_items_jMenuItem3);

        admin_menu.add(items_overseas_jMenu2);

        jMenuItem2.setText("Low quntity items");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        admin_menu.add(jMenuItem2);

        jMenuBar1.add(admin_menu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbs, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(search_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(search_button)
                .addGap(150, 150, 150)
                .addComponent(refresh_jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(insert_item_button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(get_item_button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(add_item_button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(delete_row_jButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(search_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search_button)
                    .addComponent(get_item_button)
                    .addComponent(add_item_button)
                    .addComponent(insert_item_button)
                    .addComponent(delete_row_jButton)
                    .addComponent(refresh_jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbs))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void search_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_buttonActionPerformed
        // search button
        JTable[] my_tables = {this.barak_jTable, this.sufa_jTable, this.baz_raam_jTable};
        searchTable(my_tables[this.tabbs.getSelectedIndex()], search_textfield.getText());
    }//GEN-LAST:event_search_buttonActionPerformed

    private void get_item_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_get_item_buttonActionPerformed
        // TODO add your handling code here:
        JTable[] my_tables = {this.barak_jTable, this.sufa_jTable, this.baz_raam_jTable};
        JTable table = my_tables[this.tabbs.getSelectedIndex()];
        String table_name = this.tabbs.getTitleAt(this.tabbs.getSelectedIndex()).trim().toLowerCase() + "_new";
        if (!table.getSelectionModel().isSelectionEmpty()) {
            String id = table.getValueAt(table.getSelectedRow(), 0).toString();
            String name = table.getValueAt(table.getSelectedRow(), 1).toString();
            String pn = table.getValueAt(table.getSelectedRow(), 2).toString();
            String sn = table.getValueAt(table.getSelectedRow(), 3).toString();
            String[] item_serials = this.db.getItemSerials(table_name, id);
            if (item_serials != null && item_serials.length == 0) {
                JOptionPane.showMessageDialog(null, "Serial number not found , Please contact with your administrator", "Alert", 0);
            } else {

                int index = 5;
                for (int i = 0; i < table.getColumnCount(); i++) {
                    if (table.getColumnName(i).equals("quntity at storage")) {
                        index = i;
                        break;
                    }
                }
                String qunt = table.getModel().getValueAt(table.getSelectedRow(), index).toString();
                if (Integer.parseInt(qunt) == 0) {
                    JOptionPane.showMessageDialog(null, "quntity at storage for item '" + table.getValueAt(table.getSelectedRow(), 1).toString() + "' is 0", "Alert", 0);
                } else {

                    JPanel panel = new JPanel();
                    int option_int = JOptionPane.showOptionDialog(null, panel, "Which serial you take?", 1, -1, null, (Object[]) item_serials, null);

                    if (option_int >= 0) {
                        String taken_serial = item_serials[option_int];
                        List<String> list = new ArrayList<>(Arrays.asList(item_serials));
                        list.remove(taken_serial);
                        item_serials = list.toArray(new String[0]);
                        String update_serials = String.join(",", (CharSequence[]) item_serials);
                        int one = -1;
                        String user_name = this._user;
                        Object temp = this.db.updateItemQuntity(table_name, id, one, user_name, update_serials);
                        String image_path = db.getImagePath(name, pn, sn, table_name);
                        String image_id = db.getImageID(name, pn, sn, table_name);
                        if (image_id != null) {
                            this.db.updateChangesForImageDB(image_id, user_name, name, pn, update_serials, table_name, image_path);
                        }
                        if (temp instanceof Exception) {
                            JOptionPane.showMessageDialog(null, "quntity at storage for item '" + table.getValueAt(table.getSelectedRow(), 1).toString() + "' is 0 \n " + ((Exception) temp).getMessage(), "Alert", 0);
                        } else {

                            table.getModel().setValueAt(Integer.valueOf(Integer.parseInt(qunt) + one), table.getSelectedRow(), index);
                            search_buttonActionPerformed(evt);
                        }

                    }
                }
            }
        } else {

            JOptionPane.showMessageDialog(null, "Row is not selected", "Alert", 0);
        }
    }//GEN-LAST:event_get_item_buttonActionPerformed

    private void add_item_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_item_buttonActionPerformed
        // TODO add your handling code here:
        JTable[] my_tables = {this.barak_jTable, this.sufa_jTable, this.baz_raam_jTable};
        JTable table = my_tables[this.tabbs.getSelectedIndex()];
        String table_name = this.tabbs.getTitleAt(this.tabbs.getSelectedIndex()).trim().toLowerCase() + "_new";
        if (!table.getSelectionModel().isSelectionEmpty()) {
            String id = table.getValueAt(table.getSelectedRow(), 0).toString();
            String name = table.getValueAt(table.getSelectedRow(), 1).toString();
            String pn = table.getValueAt(table.getSelectedRow(), 2).toString();
            String sn = table.getValueAt(table.getSelectedRow(), 3).toString();
            String[] item_serials = this.db.getItemSerials(table_name, id);
            Object[] options = {"insert", "cancel"};

            JPanel panel = new JPanel();
            panel.add(new JLabel("S.N  : "));
            JTextField new_serial_textField = new JTextField(16);
            panel.add(new_serial_textField);
            int option_int = JOptionPane.showOptionDialog(null, panel, "Enter serial number", 1, -1, null, options, null);

            if (option_int == 0) {
                List<String> list = new ArrayList<>(Arrays.asList(item_serials));
                if (list.contains(new_serial_textField.getText())) {
                    JOptionPane.showMessageDialog(null, "Serial number alredy exist", "Alert", 0);
                } else if (new_serial_textField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Serial number cannot be blank", "Alert", 0);
                } else {

                    list.add(new_serial_textField.getText());
                    item_serials = list.toArray(new String[0]);
                    String update_serials = String.join(",", (CharSequence[]) item_serials);

                    int one = 1;
                    String user_name = this._user;
                    Object temp = this.db.updateItemQuntity(table_name, id, one, user_name, update_serials);
                    String image_path = db.getImagePath(name, pn, sn, table_name);
                    String image_id = db.getImageID(name, pn, sn, table_name);
                    if (image_id != null) {
                        this.db.updateChangesForImageDB(image_id, user_name, name, pn, update_serials, table_name, image_path);
                    }
                    if (temp instanceof Exception) {
                        JOptionPane.showMessageDialog(null, "Error! \n " + ((Exception) temp).getMessage(), "Alert", 0);
                    }
                    int index = 5;
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        if (table.getColumnName(i).equals("quntity at storage")) {
                            index = i;
                            break;
                        }
                    }
                    String qunt = table.getModel().getValueAt(table.getSelectedRow(), index).toString();
                    table.getModel().setValueAt(Integer.valueOf(Integer.parseInt(qunt) + one), table.getSelectedRow(), index);

                    search_buttonActionPerformed(evt);
                }
            }
        } else {

            JOptionPane.showMessageDialog(null, "Row is not selected", "Alert", 0);
        }
    }//GEN-LAST:event_add_item_buttonActionPerformed

    private void delete_row_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_row_jButtonActionPerformed
        // TODO add your handling code here:
        JTable[] my_tables = {this.barak_jTable, this.sufa_jTable, this.baz_raam_jTable};
        JTable table = my_tables[this.tabbs.getSelectedIndex()];
        String table_name = this.tabbs.getTitleAt(this.tabbs.getSelectedIndex()).toLowerCase() + "_new";
        String id = table.getValueAt(table.getSelectedRow(), 0).toString();
        String item = table.getValueAt(table.getSelectedRow(), 1).toString();
        String user_name = this._user;
        Object temp = this.db.deleteRow(table_name, id, item, user_name);
        if (temp instanceof Exception) {
            JOptionPane.showMessageDialog(null, "ERROR! \n " + ((Exception) temp).getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        } else {
            this.refresh_jButton1ActionPerformed(evt);
        }
    }//GEN-LAST:event_delete_row_jButtonActionPerformed

    private void insert_item_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insert_item_buttonActionPerformed
        // TODO add your handling code here:
        JTable[] my_tables = {this.barak_jTable, this.sufa_jTable, this.baz_raam_jTable};
        JTable table = my_tables[this.tabbs.getSelectedIndex()];
        insertNewItemWindow inw = new insertNewItemWindow();
        inw.setST(this);
        inw.setDB(this.db);
        inw.setUserName(this._user);
        if ((this.tabbs.getTitleAt(this.tabbs.getSelectedIndex()).toLowerCase() + "_new").equals("baz_new")) {
            inw.isBaz();
        }
        inw.setTableName(this.tabbs.getTitleAt(this.tabbs.getSelectedIndex()).toLowerCase() + "_new");
        inw.setVisible(true);
    }//GEN-LAST:event_insert_item_buttonActionPerformed

    private void refresh_jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refresh_jButton1ActionPerformed
        // TODO add your handling code here:
        if (search_textfield.getText().equals("")) {
            this.refresh();
        } else {
            search_buttonActionPerformed(evt);
        }
    }//GEN-LAST:event_refresh_jButton1ActionPerformed

    private void create_new_user_jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_create_new_user_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        createUser cu = new createUser();
        cu.setDB(this.db);
        cu.setCreator(this._user);
        cu.setVisible(true);
    }//GEN-LAST:event_create_new_user_jMenuItem1ActionPerformed

    private void copy_jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copy_jMenuItem4ActionPerformed
        // copy
    }//GEN-LAST:event_copy_jMenuItem4ActionPerformed

    private void log_out_jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_log_out_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new main().setVisible(true);
    }//GEN-LAST:event_log_out_jMenuItem7ActionPerformed

    private void paste_jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paste_jMenuItem5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_paste_jMenuItem5ActionPerformed

    private void search_textfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_textfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_textfieldActionPerformed

    private void search_textfieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search_textfieldMouseClicked
        // TODO add your handling code here:
        JTable[] my_tables = {this.barak_jTable, this.sufa_jTable, this.baz_raam_jTable};
        JTable table = my_tables[this.tabbs.getSelectedIndex()];
        table.getSelectionModel().clearSelection();
    }//GEN-LAST:event_search_textfieldMouseClicked

    private void exit_jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exit_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exit_jMenuItem1ActionPerformed

    private void about_jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_about_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Created by Itzik Asayag", "information", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_about_jMenuItem6ActionPerformed

    private void edit_user_jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_user_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        updateUser uu = new updateUser();
        uu.setDB(this.db);
        uu.setCreator(this._user);
        uu.editable(false);
        uu.setVisible(true);
    }//GEN-LAST:event_edit_user_jMenuItem2ActionPerformed

    private void delete_user_jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_user_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        deleteUser du = new deleteUser();
        du.setDB(this.db);
        du.setCreator(this._user);
        du.editable(false);
        du.setVisible(true);
    }//GEN-LAST:event_delete_user_jMenuItem3ActionPerformed

    private void search_textfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_textfieldKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            JTable[] my_tables = {this.barak_jTable, this.sufa_jTable, this.baz_raam_jTable};
            searchTable(my_tables[this.tabbs.getSelectedIndex()], search_textfield.getText());
        }
    }//GEN-LAST:event_search_textfieldKeyPressed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        logFrame lf = new logFrame();
        lf.setDB(this.db);
        lf.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void items_without_image_jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_items_without_image_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        String[] my_tables = {"barak_new", "sufa_new", "baz_new"};
        String text = "";
        for (int i = 0; i < my_tables.length; i++) {
            text = text + "\n" + "============= " + my_tables[i] + "============= ";
            ArrayList<String[]> table = db.getTable(my_tables[i]);
            for (int j = 0; j < table.size(); j++) {
                String row[] = table.get(j);
                String img_pth = this.db.getImagePath(row[1], row[2], row[3], my_tables[i]);
                if (img_pth != null) {
                    File f = new File(img_pth);
                    if (!f.exists()) {
                        text = text + "\n" + row[1] + "\t" + row[2] + "\t" + row[3] + "\t" + my_tables[i] +" (file dosent exist)";
                    }
                }
                else{
                    text = text + "\n" + row[1] + "\t" + row[2] + "\t" + row[3] + "\t" + my_tables[i] +" (no image)";
                }
            }
        }
        File myFoo = new File("items without image.log");
        FileWriter fooWriter;
        try {
            fooWriter = new FileWriter(myFoo, false); // true to append
            fooWriter.write(text);
            fooWriter.close();
            JOptionPane.showMessageDialog(null, "File 'items without image.log' was successfully created", "Alert", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(storageTabs.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_items_without_image_jMenuItem2ActionPerformed

    private void items_without_serial_number_jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_items_without_serial_number_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        String[] my_tables = {"barak_new", "sufa_new", "baz_new"};
        String text = "";
        for (int i = 0; i < my_tables.length; i++) {
            text = text + "\n" + "============= " + my_tables[i] + "============= ";
            ArrayList<String[]> table = db.getTable(my_tables[i]);
            for (int j = 0; j < table.size(); j++) {
                String row[] = table.get(j);
                if (!isInteger(row[5])) {
                    text = text + "\n" + row[0] + "\t" + row[1] + "\t" + row[2] + "\t" + my_tables[i] + "serial number is not valid!!!!";
                } else if ((row[3] == null || row[3].equals("")) && (row[5] != null && Integer.parseInt(row[5]) > 0)) {
                    text = text + "\n" + row[0] + "\t" + row[1] + "\t" + row[2] + "\t" + my_tables[i];
                }
            }
        }
        File myFoo = new File("items without serial number.log");
        FileWriter fooWriter;
        try {
            fooWriter = new FileWriter(myFoo, false); // true to append
            fooWriter.write(text);
            fooWriter.close();
            JOptionPane.showMessageDialog(null, "File 'items without serial number.log' was successfully created", "Alert", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(storageTabs.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_items_without_serial_number_jMenuItem4ActionPerformed

    private void delete_items_without_image_jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_items_without_image_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        String[] my_tables = {"barak_new", "sufa_new", "baz_new"};
        for (int i = 0; i < my_tables.length; i++) {
            ArrayList<String[]> table = db.getTable(my_tables[i]);
            for (int j = 0; j < table.size(); j++) {
                String row[] = table.get(j);
                String img_pth = this.db.getImagePath(row[1], row[2], row[3], my_tables[i]);
                if (img_pth != null && !img_pth.equals("")) {
                    File f = new File(img_pth);
                    if (!f.exists()) {
                           // public synchronized Object deleteRow(String table_name, String id, String name, String user_name) {
                           //this.db.deleteRow(table_name, id, item, user_name);
                           this.db.deleteRow(my_tables[i], row[0], row[1], this._user);
                    }
                }
                else{
                    this.db.deleteRow(my_tables[i], row[0], row[1], this._user);
                }
            }
        }
        //pb.dispose();
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.refresh_jButton1ActionPerformed(evt);
    }//GEN-LAST:event_delete_items_without_image_jMenuItem3ActionPerformed

    private void send_new_itam_jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_send_new_itam_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        SendItemOverseas sio = new SendItemOverseas();
        sio.setDB(this.db);
        sio.setUser(this._user);
        sio.setVisible(true);
    }//GEN-LAST:event_send_new_itam_jMenuItem2ActionPerformed

    private void view_items_jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view_items_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        ItemsTracking it = new ItemsTracking();
        it.setUser(_user);
        it.setDB(db);
        it.setVisible(true);
    }//GEN-LAST:event_view_items_jMenuItem3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        this.showLowQuntity();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

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
//            java.util.logging.Logger.getLogger(storageTabs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(storageTabs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(storageTabs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(storageTabs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                /*storageTabs st =*/ new storageTabs().setVisible(true);
//                /*st.setVisible(true);
//                InactivityListener lisner;
//                lisner = new InactivityListener(st, st.logout(st), 0);
//                lisner.start();*/
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem about_jMenuItem6;
    private javax.swing.JButton add_item_button;
    private javax.swing.JMenu admin_menu;
    private javax.swing.JTable barak_jTable;
    private javax.swing.JPanel barak_jpanel;
    private javax.swing.JTable baz_raam_jTable;
    private javax.swing.JPanel baz_raam_jpanel;
    private javax.swing.JMenuItem copy_jMenuItem4;
    private javax.swing.JMenuItem create_new_user_jMenuItem1;
    private javax.swing.JMenuItem delete_items_without_image_jMenuItem3;
    private javax.swing.JButton delete_row_jButton;
    private javax.swing.JMenuItem delete_user_jMenuItem3;
    private javax.swing.JMenu edit_menu;
    private javax.swing.JMenuItem edit_user_jMenuItem2;
    private javax.swing.JMenuItem exit_jMenuItem1;
    private javax.swing.JMenu file_menu;
    private javax.swing.JButton get_item_button;
    private javax.swing.JButton insert_item_button;
    private javax.swing.JMenu items_overseas_jMenu2;
    private javax.swing.JMenuItem items_without_image_jMenuItem2;
    private javax.swing.JMenuItem items_without_serial_number_jMenuItem4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JMenuItem log_out_jMenuItem7;
    private javax.swing.JMenuItem paste_jMenuItem5;
    private javax.swing.JButton refresh_jButton1;
    private javax.swing.JButton search_button;
    private javax.swing.JTextField search_textfield;
    private javax.swing.JMenuItem send_new_itam_jMenuItem2;
    private javax.swing.JTable sufa_jTable;
    private javax.swing.JPanel sufa_jpanel;
    private javax.swing.JTabbedPane tabbs;
    private javax.swing.JMenuItem view_items_jMenuItem3;
    // End of variables declaration//GEN-END:variables
}
