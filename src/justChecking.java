
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Elbit Storage
 */
public class justChecking {

    
    /**
     * save the image at the project root directory
     * create directory (if not exist) for each simulator
     * in case of photo from webcam , copy the original photo to the relevant simulator
              directory and delete the original photo
     * in case of photo from local storage the same procedure but the photo will not deleted 
     * @param taken_image_path the path to the original image
     * @param simulator simulator name
     * @param item_id item id (from DB)
     * @param item_pn item part number
     * @param from_cam photo from webcam or local storage
     * @return false in case that the rename or delete photo file methods does not succeed
     *         true in case that the rename or delete photo file methods succeed
     * @throws IOException 
     */
    public boolean saveImage(Path taken_image_path , String simulator , String item_id , String item_pn , Boolean from_cam) throws IOException{
        Path dir_path = Paths.get(System.getProperty("user.dir") + File.separator + simulator);
        File directory = new File(dir_path.toString());
        if (! directory.exists()){
            directory.mkdir();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }
        Files.copy(taken_image_path, dir_path, StandardCopyOption.REPLACE_EXISTING);
        File copy_of_image_file = new File(dir_path.toString() + File.separator + taken_image_path.getFileName());
        String file_new_name = ("__"+item_id + "-" + item_pn+"__").replaceAll("[^a-zA-Z0-9\\.\\-]", "_"); 
        boolean rename = copy_of_image_file.renameTo(new File(file_new_name));
        if(!from_cam){
            return rename;
        }
        else if(rename && removeImage(taken_image_path.toString())){
            return true;
        }
        return false;
    }
    
    
    public boolean removeImage(String path){
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
     * @param simulator simulator name 
     * @param item_pn item part number
     * @param item_id item id (from DB)
     * @param icon photo icon
     */
    public void loadImage(String simulator , String item_pn , String item_id , JLabel icon) throws IOException{
        Path dir_path = Paths.get(System.getProperty("user.dir") + File.separator + simulator);
        String file_name = ("__"+item_id + "-" + item_pn+"__").replaceAll("[^a-zA-Z0-9\\.\\-]", "_"); 
        File file_path = new File(dir_path.toString() + File.separator + file_name);
        BufferedImage resize_image = resizeImage(file_path, icon.getWidth(), icon.getHeight());
        icon.setIcon(new ImageIcon((Image)resize_image));
    }
    
    /**
     * load TEMP image to icon
     * @param path
     * @param icon
     * @throws IOException 
     */
    public void loadImage(File file , JLabel icon) throws IOException{
        BufferedImage resize_image = resizeImage(file, icon.getWidth(), icon.getHeight());
        icon.setIcon(new ImageIcon((Image)resize_image));
    }
                                                             
    /**
     * resize the original image to the icon size
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
    
    private String photo_path = "C:\\storage program\\images";

    public static void main(String[] args) {
        
    }
}
