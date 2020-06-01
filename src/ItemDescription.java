
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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
public class ItemDescription extends javax.swing.JFrame {

    private DataBase db;
    private String table_name = "";
    private String user_name = "";
    //private String path = "";
    //private String image_load = "";
    private String id = "";
    private boolean isAdmin = false;
    private boolean somthing_changed = false;
    private WindowListener exitListener;
    private String[] item_data = null;
    private JTextField[] item_parms = null;
    private storageTabs main = null;
    // private String image_id = "";
    ///////////////////////////
    private String temp_image_path;
    private String origin_image_path;
    private boolean image_changed = false;
    private boolean from_cam_or_local = false; //false == local , true == from cam;
    ///////////////////////////

    /**
     * Creates new form ItemDescription
     */
    public ItemDescription() {
        initComponents();
        this.item_parms = new JTextField[9];
        item_parms[0] = this.name_jTextField;
        item_parms[1] = this.pn_jTextField1;
        item_parms[2] = this.sn_jTextField2;
        item_parms[3] = this.prn_jTextField3;
        item_parms[4] = this.qas_jTextField4;
        item_parms[5] = this.sb_jTextField5;
        item_parms[6] = this.qasys_jTextField6;
        item_parms[7] = this.las_jTextField7;
        item_parms[8] = this.comments_jTextField8;
        this.exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                for (int i = 0; i < item_data.length; i++) {
                    if (!somthing_changed && !item_data[i].equals(item_parms[i].getText())) {
                        somthing_changed = true;
                    }
                }
                if (somthing_changed) {
                    int confirm = JOptionPane.showOptionDialog(
                            null, "Discard item description changes?",
                            "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (confirm == 0) {
                        cancelOperation();
                    }
                } else {
                    if (main != null) {
                        main.closeItemDescFrame(id);
                    }
                    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                }
            }
        };

        ////////////// double click on image icon open image file 
        this.image_jLabel1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
            }

            @Override
            public void mousePressed(MouseEvent me) {
                if (me.getClickCount() >= 2) {
                    try {
                        File f = new File(origin_image_path);
                        if (temp_image_path != null) {
                            f = new File(temp_image_path);
                        }
                        Desktop dt = Desktop.getDesktop();
                        dt.open(f);
                    } catch (IOException ex) {
                        Logger.getLogger(ItemDescription.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        });
        this.fixJtextfieldSize();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.addWindowListener(exitListener);
    }

    public void intialMain(storageTabs frame) {
        this.main = frame;
    }

    public void fixJtextfieldSize() {
        this.name_jTextField.setColumns(10);
        this.pn_jTextField1.setColumns(10);
        this.sn_jTextField2.setColumns(10);
        this.prn_jTextField3.setColumns(10);
        this.qas_jTextField4.setColumns(10);
        this.sb_jTextField5.setColumns(10);
        this.qasys_jTextField6.setColumns(10);
        this.las_jTextField7.setColumns(10);
        this.comments_jTextField8.setColumns(10);
    }

    public void insertData(String[] data) {
        this.item_data = data;
        this.update_jButton.setVisible(isAdmin);
        this.name_jTextField.setEditable(isAdmin);
        this.pn_jTextField1.setEditable(isAdmin);
        this.sn_jTextField2.setEditable(isAdmin);
        this.prn_jTextField3.setEditable(isAdmin);
        this.qas_jTextField4.setEditable(isAdmin);
        this.sb_jTextField5.setEditable(isAdmin);
        this.qasys_jTextField6.setEditable(isAdmin);
        this.las_jTextField7.setEditable(isAdmin);
        this.comments_jTextField8.setEditable(isAdmin);
        this.name_jTextField.setText(data[0]);
        this.pn_jTextField1.setText(data[1]);
        this.sn_jTextField2.setText(data[2]);
        this.prn_jTextField3.setText(data[3]);
        this.qas_jTextField4.setText(data[4]);
        this.sb_jTextField5.setText(data[5]);
        this.qasys_jTextField6.setText(data[6]);
        this.las_jTextField7.setText(data[7]);
        this.comments_jTextField8.setText(data[8]);
        this.minimum_quntity_jTextField9.setText((String) db.getMinimumQuntity(this.table_name, id));
        String loan = (String) this.db.isLoanFromIAF(this.table_name, id);
        if (loan.equals("1")) {
            this.loan_jCheckBox1.setSelected(true);
        }
        this.loan_jCheckBox1.setEnabled(isAdmin);
        minimum_quntity_jLabel12.setVisible(isAdmin);
        minimum_quntity_jTextField9.setVisible(isAdmin);
        //loadImage();
        try {
            this.origin_image_path = loadImage(this.table_name, data[1], this.id, this.image_jLabel1);
        } catch (IOException ex) {
            Logger.getLogger(ItemDescription.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setTitle("Item Description (" + name_jTextField.getText() + ")");
    }

    /*public void loadImage() {
        String path = db.getImagePath(this.name_jTextField.getText(), this.pn_jTextField1.getText(), this.table_name);
        if (path != null) {
            this.image_jLabel1.setIcon(new ImageIcon(path));
        }
        this.image_load = path;
    }*/
    public void setAdmin(boolean state) {
        this.isAdmin = state;
        this.browse_jButton1.setVisible(isAdmin);
        this.life_cam_jButton1.setVisible(isAdmin);
    }

    public void setDB(DataBase d) {
        this.db = d;
    }

    public void isBaz() {
        this.pn_jLabel5.setText("IAF number :");
        this.sb_jLabel9.setText("Loan from IAF :");
        this.qasys_jLabel6.setText("Aircraft type :");
    }

    public void setTableName(String name) {
        this.table_name = name;
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

    public void setUserName(String name) {
        this.user_name = name;
    }

    public void setID(String id) {
        this.id = id;
    }

    private boolean isImage(File f) {
        try {
            return ImageIO.read(f) != null;
        } catch (Exception e) {
            return false;
        }
    }

    private void cancelOperation() {
        this.setVisible(false);
        if (main != null) {
            main.closeItemDescFrame(this.id);
        }
    }

    public static boolean isValidName(String text) {
        Pattern pattern = Pattern.compile(
                "# Match a valid Windows filename (unspecified file system).          \n"
                + "^                                # Anchor to start of string.        \n"
                + "(?!                              # Assert filename is not: CON, PRN, \n"
                + "  (?:                            # AUX, NUL, COM1, COM2, COM3, COM4, \n"
                + "    CON|PRN|AUX|NUL|             # COM5, COM6, COM7, COM8, COM9,     \n"
                + "    COM[1-9]|LPT[1-9]            # LPT1, LPT2, LPT3, LPT4, LPT5,     \n"
                + "  )                              # LPT6, LPT7, LPT8, and LPT9...     \n"
                + "  (?:\\.[^.]*)?                  # followed by optional extension    \n"
                + "  $                              # and end of string                 \n"
                + ")                                # End negative lookahead assertion. \n"
                + "[^<>:\"/\\\\|?*\\x00-\\x1F]*     # Zero or more valid filename chars.\n"
                + "[^<>:\"/\\\\|?*\\x00-\\x1F\\ .]  # Last char is not a space or dot.  \n"
                + "$                                # Anchor to end of string.            ",
                Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.COMMENTS);
        Matcher matcher = pattern.matcher(text);
        boolean isMatch = matcher.matches();
        return isMatch;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }

    //////////////////////////////////////////////image system update START//////////////////////////////
    public static String getFileExtension(String fullName) {
        if (fullName != null) {
            String fileName = new File(fullName).getName();
            int dotIndex = fileName.lastIndexOf('.');
            return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
        }
        return null;
    }

    /**
     * save the image at the project root directory create directory (if not
     * exist) for each simulator in case of photo from webcam , copy the
     * original photo to the relevant simulator directory and delete the
     * original photo in case of photo from local storage the same procedure but
     * the photo will not deleted
     *
     * @param taken_image_path the path to the original image
     * @param simulator simulator name
     * @param item_id item id (from DB)
     * @param item_pn item part number
     * @param from_cam photo from webcam or local storage
     * @return false in case that the rename or delete photo file methods does
     * not succeed true in case that the rename or delete photo file methods
     * succeed
     * @throws IOException
     */
    public boolean saveImage(Path taken_image_path, String simulator, String item_id, String item_pn, Boolean from_cam) throws IOException {
        String dir_path = System.getProperty("user.dir") + File.separator + "photos" + File.separator + simulator;
        File directory = new File(dir_path + File.separator + taken_image_path.getFileName());
        directory.getParentFile().mkdirs();
        Path newFilePath = directory.toPath();
        Files.copy(taken_image_path, newFilePath, StandardCopyOption.REPLACE_EXISTING);
        File copy_of_image_file = new File(dir_path + File.separator + taken_image_path.getFileName());
        String file_new_name = ("__" + item_id + "-" + item_pn + "__").replaceAll("[^a-zA-Z0-9\\.\\-]", "_") + ".jpg";
        File file_exist = new File(dir_path + File.separator + file_new_name);
        if (file_exist.exists()) {
            removeImage(file_exist.getAbsolutePath());
        }
        boolean rename = copy_of_image_file.renameTo(new File(dir_path + File.separator + file_new_name));
        if (!from_cam) {
            return rename;
        } else if (rename && removeImage(taken_image_path.toString())) {
            return true;
        }
        return false;
    }

    public void rename(String simulator, String old_name, String new_name) {
        Path dir_path = Paths.get(System.getProperty("user.dir") + File.separator + "photos" + File.separator + simulator);
        File origin_file = new File(dir_path.toString() + File.separator + old_name);
        if (origin_file.renameTo(new File(dir_path.toString() + File.separator + new_name))) {
            this.origin_image_path = dir_path.toString() + File.separator + new_name;
        }
    }

    public boolean removeImage(String path) {
        File myObj = new File(path);
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
            return true;
        } else {
            System.out.println("Failed to delete the file.");
            return false;
        }
    }

    /**
     * load SAVED image to icon at item description window
     *
     * @param simulator simulator name
     * @param item_pn item part number
     * @param item_id item id (from DB)
     * @param icon photo icon
     * @return path of the loaded image
     */
    public String loadImage(String simulator, String item_pn, String item_id, JLabel icon) throws IOException {
        //need to load and resize the image to icon// 
        String dir_path = System.getProperty("user.dir") + File.separator + "photos" + File.separator + simulator;
        String file_name = ("__" + item_id + "-" + item_pn + "__").replaceAll("[^a-zA-Z0-9\\.\\-]", "_") + ".jpg";
        File file_to_load = new File(dir_path + File.separator + file_name);
        if (file_to_load.exists()) {
            BufferedImage resize_image = resizeImage(file_to_load, icon.getWidth(), icon.getHeight());
            icon.setIcon(new ImageIcon((Image) resize_image));
            return file_to_load.getAbsolutePath();
        }
        return null;
    }

    /**
     * load TEMP image to icon
     *
     * @param path
     * @param icon
     * @throws IOException
     */
    public String loadImage(File file, JLabel icon) throws IOException {
        if (file.exists() && !file.isDirectory()) {
            BufferedImage resize_image = resizeImage(file, icon.getWidth(), icon.getHeight());
            icon.setIcon(new ImageIcon((Image) resize_image));
            return file.getAbsolutePath();
        }
        return null;
    }

    /**
     * resize the original image to the icon size
     *
     * @param image_file image that we want to load
     * @param IMG_WIDTH this.image_jLabel1.getWidth()
     * @param IMG_HEIGHT this.image_jLabel1.getHeight()
     * @return
     * @throws IOException
     */
    private BufferedImage resizeImage(File image_file, int IMG_WIDTH, int IMG_HEIGHT) throws IOException {
        BufferedImage originalImage = ImageIO.read(image_file);//change path to where file is located
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }
    //////////////////////////////////////////////image system update  END//////////////////////////////

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
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        qasys_jTextField6 = new javax.swing.JTextField();
        name_jTextField = new javax.swing.JTextField();
        sb_jTextField5 = new javax.swing.JTextField();
        cancel_operation_jButton1 = new javax.swing.JButton();
        browse_jButton1 = new javax.swing.JButton();
        life_cam_jButton1 = new javax.swing.JButton();
        minimum_quntity_jTextField9 = new javax.swing.JTextField();
        pn_jLabel5 = new javax.swing.JLabel();
        prn_jTextField3 = new javax.swing.JTextField();
        qas_jLabel10 = new javax.swing.JLabel();
        las_jTextField7 = new javax.swing.JTextField();
        sn_jTextField2 = new javax.swing.JTextField();
        image_jPanel2 = new javax.swing.JPanel();
        image_jLabel1 = new javax.swing.JLabel();
        minimum_quntity_jLabel12 = new javax.swing.JLabel();
        update_jButton = new javax.swing.JButton();
        sn_jLabel2 = new javax.swing.JLabel();
        comments_jLabel8 = new javax.swing.JLabel();
        loan_jLabel11 = new javax.swing.JLabel();
        sb_jLabel9 = new javax.swing.JLabel();
        pn_jTextField1 = new javax.swing.JTextField();
        las_jLabel7 = new javax.swing.JLabel();
        name_jLabel = new javax.swing.JLabel();
        pn_jLabel1 = new javax.swing.JLabel();
        comments_jLabel9 = new javax.swing.JLabel();
        loan_jCheckBox1 = new javax.swing.JCheckBox();
        qas_jTextField4 = new javax.swing.JTextField();
        comments_jTextField8 = new javax.swing.JTextField();
        qasys_jLabel6 = new javax.swing.JLabel();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Item Description");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        qasys_jTextField6.setEditable(false);
        qasys_jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qasys_jTextField6ActionPerformed(evt);
            }
        });

        name_jTextField.setEditable(false);
        name_jTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                name_jTextFieldActionPerformed(evt);
            }
        });

        sb_jTextField5.setEditable(false);
        sb_jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sb_jTextField5ActionPerformed(evt);
            }
        });

        cancel_operation_jButton1.setText("cancel");
        cancel_operation_jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_operation_jButton1ActionPerformed(evt);
            }
        });

        browse_jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kisspng-computer-icons-download-android-5ae0ca3c68c6e5.0123531015246812764292.png"))); // NOI18N
        browse_jButton1.setText("Browse");
        browse_jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browse_jButton1ActionPerformed(evt);
            }
        });

        life_cam_jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/download.jpg"))); // NOI18N
        life_cam_jButton1.setText("LifeCam");
        life_cam_jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                life_cam_jButton1ActionPerformed(evt);
            }
        });

        minimum_quntity_jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minimum_quntity_jTextField9ActionPerformed(evt);
            }
        });

        pn_jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        pn_jLabel5.setText("Producer name :");

        prn_jTextField3.setEditable(false);
        prn_jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prn_jTextField3ActionPerformed(evt);
            }
        });

        qas_jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        qas_jLabel10.setText("Quntity at storage :");

        las_jTextField7.setEditable(false);
        las_jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                las_jTextField7ActionPerformed(evt);
            }
        });

        sn_jTextField2.setEditable(false);
        sn_jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sn_jTextField2ActionPerformed(evt);
            }
        });

        image_jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout image_jPanel2Layout = new javax.swing.GroupLayout(image_jPanel2);
        image_jPanel2.setLayout(image_jPanel2Layout);
        image_jPanel2Layout.setHorizontalGroup(
            image_jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, image_jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image_jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addContainerGap())
        );
        image_jPanel2Layout.setVerticalGroup(
            image_jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, image_jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image_jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addContainerGap())
        );

        minimum_quntity_jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        minimum_quntity_jLabel12.setText("Minimum quntity :");

        update_jButton.setText("update");
        update_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_jButtonActionPerformed(evt);
            }
        });

        sn_jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        sn_jLabel2.setText("Serial number :");

        comments_jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comments_jLabel8.setText("Comments :");

        loan_jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        loan_jLabel11.setText("Loan from IAF : ");

        sb_jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        sb_jLabel9.setText("Supply by :");

        pn_jTextField1.setEditable(false);
        pn_jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pn_jTextField1ActionPerformed(evt);
            }
        });

        las_jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        las_jLabel7.setText("location at storage :");

        name_jLabel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        name_jLabel.setText("Name :");

        pn_jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        pn_jLabel1.setText("Part number :");

        comments_jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comments_jLabel9.setText("Picture :");

        qas_jTextField4.setEditable(false);
        qas_jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qas_jTextField4ActionPerformed(evt);
            }
        });

        comments_jTextField8.setEditable(false);
        comments_jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comments_jTextField8ActionPerformed(evt);
            }
        });

        qasys_jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        qasys_jLabel6.setText("Quntity at system :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(browse_jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(life_cam_jButton1)
                            .addComponent(comments_jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(update_jButton)
                                .addGap(203, 203, 203)
                                .addComponent(cancel_operation_jButton1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(image_jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(loan_jCheckBox1)
                                        .addGap(37, 37, 37)
                                        .addComponent(minimum_quntity_jLabel12))))))
                    .addComponent(loan_jLabel11)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(name_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(88, 88, 88)
                            .addComponent(name_jTextField))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(pn_jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(34, 34, 34)
                            .addComponent(pn_jTextField1))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(sn_jLabel2)
                            .addGap(39, 39, 39)
                            .addComponent(sn_jTextField2))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(pn_jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(25, 25, 25)
                            .addComponent(prn_jTextField3))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(qas_jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(9, 9, 9)
                            .addComponent(qas_jTextField4))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(sb_jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(25, 25, 25)
                            .addComponent(sb_jTextField5))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(qasys_jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(9, 9, 9)
                            .addComponent(qasys_jTextField6))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(las_jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(las_jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(minimum_quntity_jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(comments_jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(24, 24, 24)
                            .addComponent(comments_jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(name_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(name_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pn_jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pn_jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sn_jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sn_jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pn_jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(prn_jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(qas_jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(qas_jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sb_jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sb_jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(qasys_jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(qasys_jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(las_jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(las_jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(comments_jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(loan_jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(minimum_quntity_jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(minimum_quntity_jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(comments_jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loan_jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(comments_jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(browse_jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(life_cam_jButton1))
                    .addComponent(image_jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancel_operation_jButton1)
                    .addComponent(update_jButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comments_jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comments_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comments_jTextField8ActionPerformed

    private void qas_jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qas_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qas_jTextField4ActionPerformed

    private void pn_jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pn_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pn_jTextField1ActionPerformed

    private void update_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_jButtonActionPerformed
        // TODO add your handling code here:
        if (!(this.sn_jTextField2.getText().equals("") && this.qas_jTextField4.getText().equals("0")) && this.sn_jTextField2.getText().split(",").length != Integer.parseInt(this.qas_jTextField4.getText())) {
            JOptionPane.showMessageDialog(null, "Quantity at storage not equal to the quntity of serial numbers.", "Alert", 0);
        } else {
            Object temp = this.db.updateItem(this.id, this.table_name, this.name_jTextField.getText(), this.pn_jTextField1
                    .getText(), this.sn_jTextField2.getText(), this.prn_jTextField3
                    .getText(), this.qas_jTextField4.getText(), this.sb_jTextField5
                    .getText(), this.qasys_jTextField6.getText(), this.las_jTextField7
                    .getText(), this.comments_jTextField8.getText(), this.user_name,
                    Integer.parseInt(this.minimum_quntity_jTextField9.getText()), this.loan_jCheckBox1.isSelected() ? 1 : 0);

            if (temp instanceof Exception) {
                JOptionPane.showMessageDialog(null, "Update item: '" + this.name_jTextField.getText() + "' failed \n " + ((Exception) temp).getMessage(), "Alert", 0);
            } else {
                for (int i = 0; i < this.item_data.length; i++) {
                    if (!this.somthing_changed && !this.item_data[i].equals(this.item_parms[i].getText())) {
                        this.somthing_changed = true;
                    }
                }
                if (this.image_changed) {
                    if (this.origin_image_path != null && !this.item_data[1].equals(this.item_parms[1].getText())) { // if part number change , rename image file needed;
                        this.rename(this.table_name, this.origin_image_path, ("__" + this.id + "-" + this.pn_jTextField1.getText() + "__").replaceAll("[^a-zA-Z0-9\\.\\-]", "_") + ".jpg");
                    }
                    try {
                        saveImage(Paths.get(this.temp_image_path), this.table_name, this.id, this.pn_jTextField1.getText(), this.from_cam_or_local);
                    } catch (IOException ex) {
                        Logger.getLogger(ItemDescription.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                JOptionPane.showMessageDialog(null, "Update item: '" + this.name_jTextField.getText() + "' successful", "information", 1);
                setVisible(false);
            }
            //this.path = "";
            if (main != null) {
                this.main.closeItemDescFrame(this.id);
                this.main.refreshFromOtherFrame();
            }
        }
    }//GEN-LAST:event_update_jButtonActionPerformed

    private void sn_jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sn_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sn_jTextField2ActionPerformed

    private void las_jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_las_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_las_jTextField7ActionPerformed

    private void prn_jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prn_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prn_jTextField3ActionPerformed

    private void minimum_quntity_jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minimum_quntity_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_minimum_quntity_jTextField9ActionPerformed

    private void life_cam_jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_life_cam_jButton1ActionPerformed
        // TODO add your handling code here:
        somthing_changed = true;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Microsoft LifeCam\\LifeCam.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (process.isAlive()) { // TODO: wait until the user close the program

        }
        File last_modified_file = this.lastFileModified("C:\\Users\\Elbit Storage\\Pictures\\LifeCam Files");
        if (last_modified_file != null) {
            if (!isImage(last_modified_file)) {
                JOptionPane.showMessageDialog(null, "File must to be Image", "Alert", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                this.temp_image_path = loadImage(last_modified_file, this.image_jLabel1);
                this.image_changed = true;
                this.from_cam_or_local = true;
                return;
            } catch (IOException ex) {
                Logger.getLogger(ItemDescription.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.image_changed = false;
    }//GEN-LAST:event_life_cam_jButton1ActionPerformed

    private void browse_jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browse_jButton1ActionPerformed
        // TODO add your handling code here:
        somthing_changed = true;
        this.jFileChooser1.addChoosableFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
        int returnVal = this.jFileChooser1.showOpenDialog(this.file_jPanel1);
        File file = this.jFileChooser1.getSelectedFile();
        if (returnVal == JFileChooser.APPROVE_OPTION && file != null) {
            if (!isImage(file)) {
                JOptionPane.showMessageDialog(null, "File must to be Image", "Alert", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                this.temp_image_path = loadImage(file, this.image_jLabel1);
                this.image_changed = true;
                this.from_cam_or_local = false;
                return;
            } catch (IOException ex) {
                Logger.getLogger(ItemDescription.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.image_changed = false;
    }//GEN-LAST:event_browse_jButton1ActionPerformed

    private void cancel_operation_jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_operation_jButton1ActionPerformed
        // TODO add your handling code here:
        this.cancelOperation();
    }//GEN-LAST:event_cancel_operation_jButton1ActionPerformed

    private void sb_jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sb_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sb_jTextField5ActionPerformed

    private void name_jTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_name_jTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_name_jTextFieldActionPerformed

    private void qasys_jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qasys_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qasys_jTextField6ActionPerformed

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
//            java.util.logging.Logger.getLogger(ItemDescription.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ItemDescription.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ItemDescription.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ItemDescription.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new ItemDescription().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browse_jButton1;
    private javax.swing.JButton cancel_operation_jButton1;
    private javax.swing.JLabel comments_jLabel8;
    private javax.swing.JLabel comments_jLabel9;
    private javax.swing.JTextField comments_jTextField8;
    private javax.swing.JPanel file_jPanel1;
    private javax.swing.JLabel image_jLabel1;
    private javax.swing.JPanel image_jPanel2;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel las_jLabel7;
    private javax.swing.JTextField las_jTextField7;
    private javax.swing.JButton life_cam_jButton1;
    private javax.swing.JCheckBox loan_jCheckBox1;
    private javax.swing.JLabel loan_jLabel11;
    private javax.swing.JLabel minimum_quntity_jLabel12;
    private javax.swing.JTextField minimum_quntity_jTextField9;
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
    private javax.swing.JButton update_jButton;
    // End of variables declaration//GEN-END:variables
}
