package Component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class InputImage extends JPanel implements ActionListener {

    private JButton btnChooseImg;
    private JLabel img;
    private String url_img; // Will store relative path (e.g., "images/P001.jpg")
    private static final String IMAGE_DIR = "images"; // Relative path to images folder

    public InputImage() {
        // Ensure the images directory exists
        File imageDir = new File(IMAGE_DIR);
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
    }

    public InputImage(String title) {
        this();
        this.setBackground(Color.white);
        btnChooseImg = new JButton(title);
        img = new JLabel();
        img.setPreferredSize(new Dimension(250, 280));
        btnChooseImg.addActionListener(this);
        this.add(btnChooseImg);
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        if (url_img == null || url_img.trim().isEmpty()) {
            btnChooseImg.setIcon(null);
            btnChooseImg.setText("Hình minh họa");
            this.url_img = null;
            return;
        }

        // url_img is a relative path (e.g., "images/P001.jpg")
        this.url_img = url_img;

        // Load the image from the relative path
        File imageFile = new File(url_img);
        if (imageFile.exists()) {
            ImageIcon imgIcon = new ImageIcon(url_img);
            imgIcon = new ImageIcon(scale(imgIcon));
            btnChooseImg.setIcon(imgIcon);
            btnChooseImg.setText("");
        } else {
            btnChooseImg.setIcon(null);
            btnChooseImg.setText("Hình không tồn tại");
        }
    }

    public void setUnable() {
        this.btnChooseImg.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG and GIF images", "png", "gif", "jpg", "jpeg");
        jfc.addChoosableFileFilter(filter);
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                // Get the selected image's absolute path
                String sourcePath = jfc.getSelectedFile().getPath();
                System.out.println("Selected image: " + sourcePath);

                // For now, we'll set a placeholder filename (e.g., "temp.jpg")
                // The actual filename (e.g., "P001.jpg") will be set in PhongDialog
                String tempDestPath = Paths.get(IMAGE_DIR, "temp.jpg").toString();
                Files.copy(Paths.get(sourcePath), Paths.get(tempDestPath), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                // Store the relative path
                this.url_img = tempDestPath;

                // Display the image
                ImageIcon imgIcon = new ImageIcon(tempDestPath);
                imgIcon = new ImageIcon(scale(imgIcon));
                btnChooseImg.setText("");
                btnChooseImg.setIcon(imgIcon);
            } catch (IOException ex) {
                Logger.getLogger(InputImage.class.getName()).log(Level.SEVERE, null, ex);
                btnChooseImg.setText("Lỗi tải hình");
            }
        }
    }

    public Image scale(ImageIcon x) {
        int WIDTH = 250;
        int HEIGHT = 280;
        Image scaledImage = x.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        return scaledImage;
    }

    public static ImageIcon resizeImage(ImageIcon imageIcon, int newWidth) {
        int newHeight = (int) (imageIcon.getIconHeight() * ((double) newWidth / imageIcon.getIconWidth()));
        Image scaledImage = imageIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}