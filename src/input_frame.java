import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;


public class input_frame extends javax.swing.JFrame {
private static final long serialVersionUID = -444342297629260997L;
	JButton button;
	JLabel label;
	int ts,tempo,c;
	 Image dimg;
	 File file;
	 BufferedImage bi;
    
   
     public input_frame() {
       super("Sheet Music Analyser");
    	    //button = new JButton("Browse");
    	    //button.setBounds(300,300,100,40);
    	    label = new JLabel();
    	    label.setBounds(10,10,500,540);
    	    //add(button);
    	    add(label);
    	        initComponents();
     }
        
    private void initComponents() {

        b1 = new javax.swing.JButton();
        l1 = new javax.swing.JLabel();
        t1 = new javax.swing.JTextField();
        l2 = new javax.swing.JLabel();
        t2 = new javax.swing.JTextField();
        b3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setFocusable(false);
        setFont(new java.awt.Font("Agency FB", 0, 8)); // NOI18N
        setForeground(java.awt.Color.white);
        setLocation(new java.awt.Point(0, 0));
        setPreferredSize(new java.awt.Dimension(900, 600));
        getContentPane().setLayout(null);

        b1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        b1.setText("Upload Image");
        b1.setToolTipText("Upload the image of the sheet music to be analysed");
        b1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b1MouseEntered(evt);
            }
        });
        b1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b1ActionPerformed(evt);
            }
        });
        getContentPane().add(b1);
        b1.setBounds(680, 290, 150, 50);

        l1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        l1.setText("Tempo");
        l1.setToolTipText("Tempo is measured in beats per minute (bpm)");
        getContentPane().add(l1);
        l1.setBounds(680, 80, 70, 40);

        t1.setMinimumSize(new java.awt.Dimension(10, 10));
        t1.setPreferredSize(new java.awt.Dimension(10, 10));
        t1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t1ActionPerformed(evt);
            }
        });
        getContentPane().add(t1);
        t1.setBounds(770, 170, 60, 40);

        l2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        l2.setText("Time");
        l2.setToolTipText("Time Signature refers to the idea number of beats per bar in a piece of music");
        getContentPane().add(l2);
        l2.setBounds(680, 170, 90, 20);

        t2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t2ActionPerformed(evt);
            }
        });
        getContentPane().add(t2);
        t2.setBounds(770, 80, 60, 40);

        b3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        b3.setText("Next");
        b3.setToolTipText("Next Step");
        b3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b3ActionPerformed(evt);
            }
        });
        getContentPane().add(b3);
        b3.setBounds(680, 430, 150, 50);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setText("Signature");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(680, 190, 80, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b1ActionPerformed
			           
 JFileChooser filechooser= new JFileChooser();
        filechooser.setDialogTitle("Choose Your File");
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
     
        int returnval=filechooser.showOpenDialog(this);
        if(returnval==JFileChooser.APPROVE_OPTION)
        {
            file = filechooser.getSelectedFile();
            
            try
            {   //display the image in jlabel
                bi=ImageIO.read(file);
                //System.out.println("0"+bi);
               dimg = bi.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
              //System.out.println("1"+file);
              //System.out.println("2"+dimg);
               ImageIcon i = new ImageIcon(dimg);
                //JLabel imageLabel=new JLabel(i); 
                label.setIcon(i);
                 
                //jPanel1.getGraphics().drawImage(i, 0, 0, jPanel1.getWidth(), label1.getHeight(), null);
            }
            catch(IOException e)
            {

            }
            this.pack();
           
        }

    }//GEN-LAST:event_b1ActionPerformed
  /*  public ImageIcon ResizeImage(String ImagePath)
    {
        ImageIcon MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }*/

    private void t1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t1ActionPerformed

    }//GEN-LAST:event_t1ActionPerformed

    private void b1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b1MouseEntered

    }//GEN-LAST:event_b1MouseEntered

    private void b1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b1MouseClicked

    }//GEN-LAST:event_b1MouseClicked

    private void b3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b3ActionPerformed
        
        
         ts=Integer.parseInt(t1.getText());
        tempo=Integer.parseInt(t2.getText());
        noterecognition nr = null;
    try {
        nr = new noterecognition(bi,ts,tempo);
    } catch (IOException ex) {
        Logger.getLogger(input_frame.class.getName()).log(Level.SEVERE, null, ex);
    }
        output_frame p2 = null;
    try {
        p2 = new output_frame(file,ts,tempo,nr);
    } catch (IOException ex) {
        Logger.getLogger(input_frame.class.getName()).log(Level.SEVERE, null, ex);
    }
        //page2 p2=new page2(file,ts,tempo,nr);
        p2.setVisible(true);
        this.setVisible(false);
        //this.setVisible(false);
    }//GEN-LAST:event_b3ActionPerformed

    private void t2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t2ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_t2ActionPerformed

    
    public static void main(String args[]) {
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Project().setVisible(true);
                }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b1;
    private javax.swing.JButton b3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel l1;
    private javax.swing.JLabel l2;
    private javax.swing.JTextField t1;
    private javax.swing.JTextField t2;
    // End of variables declaration//GEN-END:variables
}
