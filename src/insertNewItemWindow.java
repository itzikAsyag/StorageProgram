
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Itzik
 */
public class insertNewItemWindow extends javax.swing.JFrame {

    private DataBase db;
    private String table_name = "";
    private String user_name = "";
    private storageTabs st;
    private String path = "";

    /**
     * Creates new form insertNewItemWindow
     */
    public insertNewItemWindow() {
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }

    public void setST(storageTabs s) {
        this.st = s;
    }

    public void setDB(DataBase d) {
        this.db = d;
    }

    private boolean isImage(File f) {
        try {
            return ImageIO.read(f) != null;
        } catch (Exception e) {
            return false;
        }
    }

    private File lastFileModified(String dir) {
        File fl = new File(dir);
        File[] files = fl.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isFile();
            }
        });
        long lastMod = Long.MIN_VALUE;
        File choice = null;
        for (File file : files) {
            if (file.lastModified() > lastMod) {
                choice = file;
                lastMod = file.lastModified();
            }
        }
        return choice;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }

    public void isBaz() {
        this.pn_jLabel5.setText("IAF number :");
        this.sb_jLabel9.setText("Loan from IAF :");
        this.qasys_jLabel6.setText("Aircraft type :");
    }

    public void setTableName(String name) {
        this.table_name = name;
    }

    public void setUserName(String name) {
        this.user_name = name;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        file_jPanel1 = new javax.swing.JPanel();
        jFileChooser1 = new javax.swing.JFileChooser();
        name_jLabel = new javax.swing.JLabel();
        name_jTextField = new javax.swing.JTextField();
        pn_jTextField1 = new javax.swing.JTextField();
        pn_jLabel1 = new javax.swing.JLabel();
        sn_jTextField2 = new javax.swing.JTextField();
        sn_jLabel2 = new javax.swing.JLabel();
        prn_jTextField3 = new javax.swing.JTextField();
        qas_jTextField4 = new javax.swing.JTextField();
        sb_jTextField5 = new javax.swing.JTextField();
        pn_jLabel5 = new javax.swing.JLabel();
        qasys_jTextField6 = new javax.swing.JTextField();
        qasys_jLabel6 = new javax.swing.JLabel();
        las_jLabel7 = new javax.swing.JLabel();
        las_jTextField7 = new javax.swing.JTextField();
        comments_jLabel8 = new javax.swing.JLabel();
        comments_jTextField8 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        qas_jLabel10 = new javax.swing.JLabel();
        sb_jLabel9 = new javax.swing.JLabel();
        comments_jLabel9 = new javax.swing.JLabel();
        browse_jButton1 = new javax.swing.JButton();
        image_jPanel2 = new javax.swing.JPanel();
        image_jLabel2 = new javax.swing.JLabel();
        life_cam_jButton3 = new javax.swing.JButton();

        javax.swing.GroupLayout file_jPanel1Layout = new javax.swing.GroupLayout(file_jPanel1);
        file_jPanel1.setLayout(file_jPanel1Layout);
        file_jPanel1Layout.setHorizontalGroup(
            file_jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        file_jPanel1Layout.setVerticalGroup(
            file_jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Insert new item");
        setResizable(false);

        name_jLabel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        name_jLabel.setText("Name :");

        name_jTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                name_jTextFieldActionPerformed(evt);
            }
        });

        pn_jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pn_jTextField1ActionPerformed(evt);
            }
        });

        pn_jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        pn_jLabel1.setText("Part number :");

        sn_jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sn_jTextField2ActionPerformed(evt);
            }
        });

        sn_jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        sn_jLabel2.setText("Serial number :");

        prn_jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prn_jTextField3ActionPerformed(evt);
            }
        });

        qas_jTextField4.setText("1");
        qas_jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qas_jTextField4ActionPerformed(evt);
            }
        });

        sb_jTextField5.setText("0");
        sb_jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sb_jTextField5ActionPerformed(evt);
            }
        });

        pn_jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        pn_jLabel5.setText("Producer name :");

        qasys_jTextField6.setText("1");
        qasys_jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qasys_jTextField6ActionPerformed(evt);
            }
        });

        qasys_jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        qasys_jLabel6.setText("Quntity at system :");

        las_jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        las_jLabel7.setText("location at storage :");

        las_jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                las_jTextField7ActionPerformed(evt);
            }
        });

        comments_jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comments_jLabel8.setText("Comments :");

        comments_jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comments_jTextField8ActionPerformed(evt);
            }
        });

        jButton1.setText("cancel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("insert");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        qas_jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        qas_jLabel10.setText("Quntity at storage :");

        sb_jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        sb_jLabel9.setText("Supply by :");

        comments_jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comments_jLabel9.setText("Picture :");

        browse_jButton1.setText("Browse");
        browse_jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browse_jButton1ActionPerformed(evt);
            }
        });

        image_jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout image_jPanel2Layout = new javax.swing.GroupLayout(image_jPanel2);
        image_jPanel2.setLayout(image_jPanel2Layout);
        image_jPanel2Layout.setHorizontalGroup(
            image_jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, image_jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image_jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addContainerGap())
        );
        image_jPanel2Layout.setVerticalGroup(
            image_jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, image_jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image_jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addContainerGap())
        );

        life_cam_jButton3.setText("LifeCam");
        life_cam_jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                life_cam_jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(name_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(88, 88, 88)
                            .addComponent(name_jTextField))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(pn_jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(34, 34, 34)
                            .addComponent(pn_jTextField1))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(sn_jLabel2)
                            .addGap(39, 39, 39)
                            .addComponent(sn_jTextField2))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(pn_jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(25, 25, 25)
                            .addComponent(prn_jTextField3))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(qas_jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(9, 9, 9)
                            .addComponent(qas_jTextField4))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(sb_jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(25, 25, 25)
                            .addComponent(sb_jTextField5))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(qasys_jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(9, 9, 9)
                            .addComponent(qasys_jTextField6))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(las_jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(las_jTextField7))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(comments_jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(24, 24, 24)
                            .addComponent(comments_jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comments_jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(browse_jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(life_cam_jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(image_jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(name_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(name_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pn_jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pn_jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sn_jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sn_jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pn_jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(prn_jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(qas_jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(qas_jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sb_jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sb_jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(qasys_jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(qasys_jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(las_jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(las_jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comments_jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comments_jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(comments_jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(browse_jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(life_cam_jButton3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(image_jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton1))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void name_jTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_name_jTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_name_jTextFieldActionPerformed

    private void pn_jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pn_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pn_jTextField1ActionPerformed

    private void sn_jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sn_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sn_jTextField2ActionPerformed

    private void prn_jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prn_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prn_jTextField3ActionPerformed

    private void qas_jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qas_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qas_jTextField4ActionPerformed

    private void sb_jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sb_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sb_jTextField5ActionPerformed

    private void qasys_jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qasys_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qasys_jTextField6ActionPerformed

    private void las_jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_las_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_las_jTextField7ActionPerformed

    private void comments_jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comments_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comments_jTextField8ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (!this.path.equals("")) {
            File file = new File(this.path);
            if (file.delete()) {
                System.out.println("File deleted successfully");
            } else {
                System.out.println("Failed to delete the file");
            }
        }
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (this.name_jTextField.getText().equals("")
                || this.pn_jTextField1.getText().equals("") || this.sn_jTextField2.getText().equals("")
                || this.las_jTextField7.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "You must to insert : name ,part number , serial number , location at storage.", "Alert", JOptionPane.ERROR_MESSAGE);
        } else {
            Object temp = db.insertItem(this.table_name, this.name_jTextField.getText(),
                    this.pn_jTextField1.getText(),
                    this.sn_jTextField2.getText(), this.prn_jTextField3.getText(),
                    this.qas_jTextField4.getText(), this.sb_jTextField5.getText(),
                    this.qasys_jTextField6.getText(), this.las_jTextField7.getText(),
                    this.comments_jTextField8.getText(), this.user_name);
            if (temp instanceof Boolean) {
                JOptionPane.showMessageDialog(null, "Success!", "information", JOptionPane.INFORMATION_MESSAGE);
                st.refresh();
                this.setVisible(false);
            } else if (temp instanceof Exception) {
                JOptionPane.showMessageDialog(null, "ERROR! \n " + ((Exception) temp).getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
            }
            //image save
            boolean is_update = false;
            String db_path = db.getImagePath(this.name_jTextField.getText(), this.pn_jTextField1.getText(), this.sn_jTextField2.getText(), this.table_name);
            if (db_path != null && !db_path.equals(this.path)) {
                is_update = true;
                File file = new File(db_path);
                if (file.delete()) {
                    System.out.println("File deleted successfully");
                } else {
                    System.out.println("Failed to delete the file");
                }
            }
            temp = db.setImage(is_update, this.user_name, this.name_jTextField.getText(), this.pn_jTextField1.getText(), this.sn_jTextField2.getText(), this.table_name, this.path.replace("\\", "\\\\"));
            if (temp instanceof Exception) {
                JOptionPane.showMessageDialog(null, "Failed to save image \n " + ((Exception) temp).getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Image upload successful!", "information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void browse_jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browse_jButton1ActionPerformed
        // TODO add your handling code here:
        if (!this.path.equals("")) {
            File file = new File(this.path);
            if (file.delete()) {
                System.out.println("File deleted successfully");
            } else {
                System.out.println("Failed to delete the file");
            }
            this.image_jLabel2.setIcon(null);
        }
        this.jFileChooser1.addChoosableFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
        int returnVal = this.jFileChooser1.showOpenDialog(this.file_jPanel1);
        File file = this.jFileChooser1.getSelectedFile();
        if (returnVal == jFileChooser1.APPROVE_OPTION && file != null) {
            if (!isImage(file)) {
                JOptionPane.showMessageDialog(null, "File must to be Image", "Alert", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                BufferedImage originalImage = ImageIO.read(file);//change path to where file is located
                int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

                BufferedImage resizeImageJpg = resizeImage(originalImage, type, this.image_jLabel2.getWidth(), this.image_jLabel2.getHeight());
                File jarDir = new File(System.getProperty("user.dir"));
                File directory = new File(jarDir.getAbsolutePath() + "\\images");
                if (!directory.exists()) {
                    directory.mkdir();
                    // If you require it to make the entire directory path including parents,
                    // use directory.mkdirs(); here instead.
                }
                this.path = jarDir.getAbsolutePath() + "\\images\\" + this.pn_jTextField1.getText() + "_resized" + String.valueOf((Math.random() * (1000000 - 0)) + 0) + ".jpg";
                ImageIO.write(resizeImageJpg, "jpg", new File(path));
                this.image_jLabel2.setIcon(new ImageIcon(path));
            } catch (IOException ex) {
                Logger.getLogger(ItemDescription.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.browse_jButton1.setVisible(true);
        }
    }//GEN-LAST:event_browse_jButton1ActionPerformed

    private void life_cam_jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_life_cam_jButton3ActionPerformed
        // TODO add your handling code here:
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Microsoft LifeCam\\LifeCam.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (process.isAlive()) { // TODO: wait until the user close the program

        }
        if (!this.path.equals("")) {
            File file = new File(this.path);
            if (file.delete()) {
                System.out.println("File deleted successfully");
            } else {
                System.out.println("Failed to delete the file");
            }
            this.image_jLabel2.setIcon(null);
        }
        File last_modified_file = this.lastFileModified("C:\\Users\\Elbit Storage\\Pictures\\LifeCam Files");
        if (last_modified_file != null) {
            if (!isImage(last_modified_file)) {
                JOptionPane.showMessageDialog(null, "File must to be Image", "Alert", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                BufferedImage originalImage = ImageIO.read(last_modified_file);//change path to where file is located
                int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

                BufferedImage resizeImageJpg = resizeImage(originalImage, type, this.image_jLabel2.getWidth(), this.image_jLabel2.getHeight());
                File jarDir = new File(System.getProperty("user.dir"));
                File directory = new File(jarDir.getAbsolutePath() + "\\images");
                if (!directory.exists()) {
                    directory.mkdir();
                    // If you require it to make the entire directory path including parents,
                    // use directory.mkdirs(); here instead.
                }
                this.path = jarDir.getAbsolutePath() + "\\images\\" + this.pn_jTextField1.getText() + "_resized" + String.valueOf((Math.random() * (1000000 - 0)) + 0) + ".jpg";
                ImageIO.write(resizeImageJpg, "jpg", new File(path));
                this.image_jLabel2.setIcon(new ImageIcon(path));
                if (last_modified_file.delete()) {
                    System.out.println("File deleted successfully");
                } else {
                    System.out.println("Failed to delete the file");
                }
            } catch (IOException ex) {
                Logger.getLogger(insertNewItemWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_life_cam_jButton3ActionPerformed

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
//            java.util.logging.Logger.getLogger(insertNewItemWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(insertNewItemWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(insertNewItemWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(insertNewItemWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new insertNewItemWindow().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browse_jButton1;
    private javax.swing.JLabel comments_jLabel8;
    private javax.swing.JLabel comments_jLabel9;
    private javax.swing.JTextField comments_jTextField8;
    private javax.swing.JPanel file_jPanel1;
    private javax.swing.JLabel image_jLabel2;
    private javax.swing.JPanel image_jPanel2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel las_jLabel7;
    private javax.swing.JTextField las_jTextField7;
    private javax.swing.JButton life_cam_jButton3;
    private javax.swing.JLabel name_jLabel;
    private javax.swing.JTextField name_jTextField;
    private javax.swing.JLabel pn_jLabel1;
    private javax.swing.JLabel pn_jLabel5;
    private javax.swing.JTextField pn_jTextField1;
    private javax.swing.JTextField prn_jTextField3;
    private javax.swing.JLabel qas_jLabel10;
    private javax.swing.JTextField qas_jTextField4;
    private javax.swing.JLabel qasys_jLabel6;
    private javax.swing.JTextField qasys_jTextField6;
    private javax.swing.JLabel sb_jLabel9;
    private javax.swing.JTextField sb_jTextField5;
    private javax.swing.JLabel sn_jLabel2;
    private javax.swing.JTextField sn_jTextField2;
    // End of variables declaration//GEN-END:variables
}
