
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Elbit Storage
 */
public class SendItemOverseas extends javax.swing.JFrame {

    DataBase db;
    private String _user = "";
    private WindowListener exitListener;
    private Boolean for_view = false;
    private String _id;
    private File file;
    private ItemsTracking _it;

    /**
     * Creates new form SendItemOverseas
     */
    public SendItemOverseas() {
        initComponents();
        this.file_jLabel2.setVisible(false);
        this.delete_file_jLabel2.setVisible(false);
        this.update_jButton1.setVisible(false);
        this.exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                if (!for_view) {
                    int confirm = JOptionPane.showOptionDialog(
                            null, "Abort insert new item?",
                            "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (confirm == 0) {
                        cancelOperation();
                    }
                } else {
                    cancelOperation();
                }
            }
        };
        choose_jLabel1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int count = evt.getClickCount();
                if (count == 2) {
                    int returnVal = jFileChooser1.showSaveDialog(null);
                    if (returnVal == jFileChooser1.APPROVE_OPTION) {
                        file = jFileChooser1.getSelectedFile();
                        choose_jLabel1.setText("Replace File");
                        delete_file_jLabel2.setVisible(true);
                        file_jLabel2.setText(file.getName());
                        file_jLabel2.setVisible(true);
                        //This is where a real application would open the file.
                        System.out.println("File: " + file.getName() + ".");
                    } else {
                        System.out.println("Open command cancelled by user.");
                    }
                }
            }
        });
        file_jLabel2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int count = evt.getClickCount();
                if (count == 2) {
                    if (Desktop.isDesktopSupported() && file != null) {
                        try {
                            Desktop.getDesktop().open(file);
                        } catch (IOException ex) {
                            // no application registered for PDFs
                        }
                    }
                }

            }
        });
        delete_file_jLabel2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int count = evt.getClickCount();
                if (count == 2) {
                    if (file != null) {
                        file.delete();
                        file_jLabel2.setText("");
                        file_jLabel2.setVisible(false);
                        choose_jLabel1.setText("Choose File");
                        file = null;
                        delete_file_jLabel2.setVisible(false);
                    }
                }

            }
        });
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        this.sd_jTextField3.setText(formattedDate);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.addWindowListener(exitListener);

    }

    public void insertData(String[] data) {
        this.for_view = true;
        this.send_jButton1.setText("Update");
        this.name_jTextField1.setText(data[0]);
        this.sn_jTextField2.setText(data[1]);
        this.location_jTextField4.setText(data[2]);
        this.sd_jTextField3.setText(data[3]);
        this.dr_jTextField8.setText(data[4]);
        this.simulator_jTextField7.setText(data[5]);
        this.wn_jTextField6.setText(data[6]);
        this.comments_jTextField5.setText(data[7]);
        this.file_jLabel2.setText(data[8]);
        if (!data[8].equals("")) {
            this.file = new File(System.getProperty("user.dir") + "/attachments", data[8]);
            this.file_jLabel2.setText(data[8]);
            this.file_jLabel2.setVisible(true);
            this.delete_file_jLabel2.setVisible(true);
            this.choose_jLabel1.setText("Replace File");
        }
    }

    public void setDB(DataBase d) {
        this.db = d;
    }

    public void setUser(String user) {
        this._user = user;
        this.wn_jTextField6.setText(user);
    }

    private void cancelOperation() {
        this.dispose();
        _it.closeItemDescFrame(_id);
    }

    public void setID(String id) {
        this._id = id;
    }

    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    public void intialMain(ItemsTracking it) {
        this._it = it;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        sn_jLabel1 = new javax.swing.JLabel();
        name_jLabel2 = new javax.swing.JLabel();
        location_jLabel5 = new javax.swing.JLabel();
        sd_jLabel6 = new javax.swing.JLabel();
        sim_jLabel7 = new javax.swing.JLabel();
        attach_jLabel8 = new javax.swing.JLabel();
        dr_jLabel9 = new javax.swing.JLabel();
        comments_jLabel10 = new javax.swing.JLabel();
        wn_jLabel11 = new javax.swing.JLabel();
        name_jTextField1 = new javax.swing.JTextField();
        sn_jTextField2 = new javax.swing.JTextField();
        sd_jTextField3 = new javax.swing.JTextField();
        location_jTextField4 = new javax.swing.JTextField();
        comments_jTextField5 = new javax.swing.JTextField();
        wn_jTextField6 = new javax.swing.JTextField();
        simulator_jTextField7 = new javax.swing.JTextField();
        dr_jTextField8 = new javax.swing.JTextField();
        send_jButton1 = new javax.swing.JButton();
        cancel_jButton2 = new javax.swing.JButton();
        choose_jLabel1 = new javax.swing.JLabel();
        update_jButton1 = new javax.swing.JButton();
        file_jLabel2 = new javax.swing.JLabel();
        delete_file_jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Send Item Oversea");
        setResizable(false);

        sn_jLabel1.setText("Serial Number:");

        name_jLabel2.setText("Name:");

        location_jLabel5.setText("Location:");

        sd_jLabel6.setText("Shipping Date:");

        sim_jLabel7.setText("Simulator:");

        attach_jLabel8.setText("Attachments:");

        dr_jLabel9.setText("Date Received:");

        comments_jLabel10.setText("Comments:");

        wn_jLabel11.setText("Worker Name:");

        send_jButton1.setText("Send");
        send_jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                send_jButton1ActionPerformed(evt);
            }
        });

        cancel_jButton2.setText("Cancel");
        cancel_jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_jButton2ActionPerformed(evt);
            }
        });

        choose_jLabel1.setForeground(new java.awt.Color(0, 51, 204));
        choose_jLabel1.setText("Choose File");
        choose_jLabel1.setToolTipText("");

        update_jButton1.setText("update");
        update_jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_jButton1ActionPerformed(evt);
            }
        });

        file_jLabel2.setForeground(new java.awt.Color(0, 51, 204));
        file_jLabel2.setText("Choosen File");
        file_jLabel2.setToolTipText("");

        delete_file_jLabel2.setForeground(new java.awt.Color(0, 51, 204));
        delete_file_jLabel2.setText("Delete File");
        delete_file_jLabel2.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(name_jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sn_jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(location_jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sd_jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dr_jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                        .addComponent(sim_jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(wn_jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(attach_jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comments_jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(update_jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(name_jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sn_jTextField2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(location_jTextField4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(sd_jTextField3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(dr_jTextField8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(simulator_jTextField7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(wn_jTextField6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(comments_jTextField5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(choose_jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(delete_file_jLabel2)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(file_jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                            .addComponent(send_jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                        .addComponent(cancel_jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(name_jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(name_jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sn_jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sn_jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(location_jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(location_jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sd_jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sd_jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dr_jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dr_jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sim_jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(simulator_jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wn_jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(wn_jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comments_jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comments_jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(attach_jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choose_jLabel1)
                    .addComponent(delete_file_jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(file_jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(send_jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancel_jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(update_jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(45, 45, 45))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void send_jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_send_jButton1ActionPerformed
        // TODO add your handling code here:
        if (!this.for_view) {
            if (this.name_jTextField1.getText().equals("") || this.sn_jTextField2
                    .getText().equals("") || this.location_jTextField4.getText().equals("") || this.sd_jTextField3
                    .getText().equals("") || this.simulator_jTextField7.getText().equals("")
                    || this.wn_jTextField6.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "You must to insert : name , serial number , location  , shipping date , simulator and worker name", "Alert", 0);
            } else {
                //TODO: in the day its gonna be online i need to add the path to the local dir from other computer;
                String attach = "";
                if (file != null) {
                    attach = file.getName();
                    File jarDir = new File(System.getProperty("user.dir"));
                    File directory = new File(jarDir.getAbsolutePath() + "\\attachments");
                    if (!directory.exists()) {
                        directory.mkdir();
                        // If you require it to make the entire directory path including parents,
                        // use directory.mkdirs(); here instead.
                    }
                    File dest = new File(directory.getAbsolutePath() +"\\"+ file.getName());
                    try {
                        copyFileUsingStream(file, dest);
                    } catch (IOException ex) {
                        Logger.getLogger(SendItemOverseas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                Object temp = this.db.insertItemTracking(this.name_jTextField1.getText(), this.sn_jTextField2.getText(),
                        this.location_jTextField4.getText(), this.sd_jTextField3.getText(), this.dr_jTextField8.getText(),
                        this.simulator_jTextField7.getText(), this.wn_jTextField6.getText(), this.comments_jTextField5.getText(),
                        attach);
                if (temp instanceof Boolean) {
                    JOptionPane.showMessageDialog(null, "Success!", "information", 1);
                    this.dispose();
                    _it.closeItemDescFrame(_id);
                } else if (temp instanceof Exception) {
                    JOptionPane.showMessageDialog(null, "ERROR! \n " + ((Exception) temp).getMessage(), "Alert", 0);
                }
            }
        } else {
            this.update_jButton1ActionPerformed(evt);
        }

    }//GEN-LAST:event_send_jButton1ActionPerformed

    private void cancel_jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_jButton2ActionPerformed
        // TODO add your handling code here:
        cancelOperation();
    }//GEN-LAST:event_cancel_jButton2ActionPerformed

    private void update_jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_jButton1ActionPerformed
        // TODO add your handling code here:
        String attach = "";
        if (file != null) {
            attach = file.getName();
            File jarDir = new File(System.getProperty("user.dir"));
            File directory = new File(jarDir.getAbsolutePath() + "\\attachments");
            if (!directory.exists()) {
                directory.mkdir();
                // If you require it to make the entire directory path including parents,
                // use directory.mkdirs(); here instead.
            }
            File dest = new File(directory.getAbsolutePath() +"\\"+ file.getName());
            try {
                copyFileUsingStream(file, dest);
            } catch (IOException ex) {
                Logger.getLogger(SendItemOverseas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Object temp = this.db.updateItem(_id, "items_tracking", this.name_jTextField1.getText(),
                attach, this.sn_jTextField2.getText(), this.location_jTextField4.getText(),
                this.sd_jTextField3.getText(), this.dr_jTextField8.getText(),
                this.simulator_jTextField7.getText(), this.wn_jTextField6.getText(),
                this.comments_jTextField5.getText(), _user);
        if (temp instanceof Boolean) {
            JOptionPane.showMessageDialog(null, "Success!", "information", 1);
            this.dispose();
            _it.closeItemDescFrame(_id);
        } else if (temp instanceof Exception) {
            JOptionPane.showMessageDialog(null, "ERROR! \n " + ((Exception) temp).getMessage(), "Alert", 0);
        }
    }//GEN-LAST:event_update_jButton1ActionPerformed

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
//            java.util.logging.Logger.getLogger(SendItemOverseas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(SendItemOverseas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(SendItemOverseas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(SendItemOverseas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new SendItemOverseas().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel attach_jLabel8;
    private javax.swing.JButton cancel_jButton2;
    private javax.swing.JLabel choose_jLabel1;
    private javax.swing.JLabel comments_jLabel10;
    private javax.swing.JTextField comments_jTextField5;
    private javax.swing.JLabel delete_file_jLabel2;
    private javax.swing.JLabel dr_jLabel9;
    private javax.swing.JTextField dr_jTextField8;
    private javax.swing.JLabel file_jLabel2;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel location_jLabel5;
    private javax.swing.JTextField location_jTextField4;
    private javax.swing.JLabel name_jLabel2;
    private javax.swing.JTextField name_jTextField1;
    private javax.swing.JLabel sd_jLabel6;
    private javax.swing.JTextField sd_jTextField3;
    private javax.swing.JButton send_jButton1;
    private javax.swing.JLabel sim_jLabel7;
    private javax.swing.JTextField simulator_jTextField7;
    private javax.swing.JLabel sn_jLabel1;
    private javax.swing.JTextField sn_jTextField2;
    private javax.swing.JButton update_jButton1;
    private javax.swing.JLabel wn_jLabel11;
    private javax.swing.JTextField wn_jTextField6;
    // End of variables declaration//GEN-END:variables

}
