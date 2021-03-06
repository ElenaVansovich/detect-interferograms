/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detect.interferogramm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_highgui.cvWaitKey;
import static org.bytedeco.javacpp.opencv_imgproc.*;

/**
 *
 * @author user
 */
public class JFrame extends javax.swing.JFrame {

    private JFileChooser chooser;
    private File folder;
    private List<File> files = new ArrayList<>();
    private File file;
    private ImagePanel imagePanel = null;
    
    /**
     * Creates new form JFrame
     */
    public JFrame() {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        //imagePanel = new ImagePanel();
        initComponents();        
        //this.getContentPane().add(imagePanel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("File");

        jMenuItem1.setText("Open image");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Open directory");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Detect");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 605, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 402, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void listFilesForFolder(List<File> list, final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(list, fileEntry);
            } else {
                String mymetype = null;
                try {
                    mymetype = Files.probeContentType(fileEntry.toPath());
                    if (mymetype != null && mymetype.split("/")[0].equals("image"))
                        list.add(fileEntry);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = chooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    folder = chooser.getSelectedFile();
                    JOptionPane.showMessageDialog(null, "Directory \"" + folder.getName() + "\" is choosed");
                    listFilesForFolder(files, folder);
                }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    Image image;
    
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        int result = chooser.showOpenDialog(null);
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                if (result == JFileChooser.APPROVE_OPTION) {
                    file = chooser.getSelectedFile();
                    JOptionPane.showMessageDialog(null, "Image was selected");
                    String path = file.getPath();
                    if(imagePanel != null){
                        this.remove(imagePanel);
                    }
                    imagePanel = new ImagePanel(
                    new ImageIcon(path).getImage());
                    this.getContentPane().add(imagePanel);
                }
    }//GEN-LAST:event_jMenuItem1ActionPerformed
int k = 0;
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        
        if(imagePanel != null){
            this.remove(imagePanel);
        }
        k++;
        imagePanel = new ImagePanel(
        new ImageIcon(detect(file, files, String.valueOf(k))).getImage());
        this.getContentPane().add(imagePanel);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame().setVisible(true);
            }
        });
    }
    
    private String detect(File image, List<File> fragments, String newNameFile ){
        //загрузка исходного изображения
        IplImage src = cvLoadImage(image.getAbsolutePath(),0);
        IplImage tmp = null;
        IplImage result = null;
        String newName = "results\\" + newNameFile + ".jpeg";
        IplImage imageNew = cvLoadImage(image.getAbsolutePath(),1);
        
        DoublePointer min_val = new DoublePointer();
        DoublePointer max_val = new DoublePointer();

        CvPoint minLoc = new CvPoint();
        CvPoint maxLoc = new CvPoint();
        
        for(int i = 0; i < fragments.size(); i++){
            //загрузка шаблонов
            tmp = cvLoadImage(fragments.get(i).getAbsolutePath(),0);	

            //изображение для хранения результата сравнения
            result = cvCreateImage(
                    cvSize(src.width() - tmp.width() + 1,
                            src.height() - tmp.height() + 1), IPL_DEPTH_32F, 1);

            // сравнение изображения с шаблоном
            //CV_TM_CCOEFF - метод корреляции
            cvMatchTemplate(src, tmp, result, CV_TM_CCOEFF);
            //определение лучшего положения для сравнения
            // (поиск минимумов и максимумов на изображении)
            cvMinMaxLoc(result, min_val, max_val, minLoc, maxLoc, null);
            //System.out.println(max_val.get(0));
            cvNormalize( result, result, 1, 0, CV_MINMAX, null );
            // Get the Max or Min Correlation Value
             //System.out.println(result.);
            //System.out.println(max_val);

            //выделим область прямоугольником
            CvPoint point = new CvPoint();
            point.x(maxLoc.x() + tmp.width());
            point.y(maxLoc.y() + tmp.height());

            cvRectangle(imageNew, maxLoc, point, CvScalar.BLACK, 1, 8, 0);
            CvFont font = new CvFont();
            cvInitFont(font, FONT_HERSHEY_PLAIN, 1.0f, 1.0f, 0, 1, 4 );
            CvPoint pointText = new CvPoint();
            pointText.x(maxLoc.x());
            pointText.y(maxLoc.y() - 10);
            cvPutText(imageNew, fragments.get(i).getName(), pointText,
                    font, CvScalar.BLACK);
        }
        //сохранение результата
        cvSaveImage(newName, imageNew);
        
        // освобождение ресурсов
        cvWaitKey(0);
        cvReleaseImage(src);
        cvReleaseImage(tmp);
        cvReleaseImage(result);
        return newName;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    // End of variables declaration//GEN-END:variables
}
