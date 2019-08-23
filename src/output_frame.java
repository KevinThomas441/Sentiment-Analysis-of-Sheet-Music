
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

public class output_frame extends javax.swing.JFrame {
	private static final long serialVersionUID = -2426644928668413364L;
	JLabel label1;
	Integer[][] notevalue = new Integer[15][50];
	float[][][] outputsa = new float[20][5][2];
	static File image;
	static int timesig, tempo;
	Image dimg;
	String emo = "VOID";
	SentimentAnalysis sa = null;
	Integer[] notecnt = new Integer[100];
	Integer rowcnt;
	Integer totalnotes;
	Integer totalbars;
	int k = 0;
	noterecognition nrhere;
	
	
	public output_frame(File i, int ts, int temp, noterecognition nr) throws IOException {
		image = i;
		timesig = ts;
		tempo = temp;
		label1 = new JLabel();
		label1.setBounds(250, 400, 449, 681);
		// add(button);
		nrhere=nr;
		add(label1);
		initComponents();
		notevalue = nr.ScanCall();
		rowcnt = nr.rowcnt;
		totalnotes = nr.totalnotes;
		totalbars = nr.totalbars;
		sa = new SentimentAnalysis(notevalue, nr.noteperbarcnt, nr.barcnt, nr.rowcnt, nr.notecnt, tempo, ts);
		outputsa = sa.valenceFind();
		emo = sa.calcSentiment(sa.tav, sa.tae);
		
		initComponents();
		this.notecnt = nr.notecnt;

	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sentiment Output");
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(0, 0));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setText("Translated");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel4.setText("Row Count");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setText("Notes");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel7.setText("Bar Count");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel8.setText("Note Count");

        jButton2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButton2.setText("Result");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel3.setText("Sentiment");

        jLabel10.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel10.setText("Report");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel11.setText("Detailed");

        jButton3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButton3.setText("Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5))
                                .addGap(393, 393, 393)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel11)
                                .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING)))))
                .addGap(234, 234, 234)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(452, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 194, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jLabel7)
                                .addGap(42, 42, 42)
                                .addComponent(jLabel8)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addGap(98, 98, 98)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51))))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       new input_frame().setVisible(true);
		this.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		new input_frame().setVisible(true);
		this.setVisible(false);
	}

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
            output();
	}// GEN-LAST:event_jButton2ActionPerformed

        void output()
        {

		BufferedImage bi = null;
		try {
			bi = ImageIO.read(image);
		} catch (IOException ex) {
			Logger.getLogger(output_frame.class.getName()).log(Level.SEVERE, null, ex);
		}

		dimg = bi.getScaledInstance(jLabel9.getWidth(), jLabel9.getHeight(), Image.SCALE_SMOOTH);

		ImageIcon img = new ImageIcon(dimg);
		
		jLabel9.setIcon(img);
		
		PrintTable();
		Printjta1();
		Printjta2();
		Printjta3();
		Printjta4();
		Printjta5();
		
		SwingUtilities.invokeLater(() -> {
			SAGraph example = new SAGraph("Sentiment Analysis of Music Piece", outputsa, sa.row, sa.barsperrow, sa.tav,
					sa.tae);
			example.setSize(1000, 600);
			example.setLocationRelativeTo(null);
			example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			example.setVisible(true);
		});
		TSGraph tsg = new TSGraph("Valence - Time Graph", "Valence - Time Graph", outputsa, sa.row, sa.barsperrow,
				tempo, timesig, sa.totalbarno);
		tsg.pack();
		RefineryUtilities.centerFrameOnScreen(tsg);
		tsg.setVisible(true);
		TSGraph1 tsg1 = new TSGraph1("Energy - Time", "Energy - Time Graph", outputsa, sa.row, sa.barsperrow, tempo,
				timesig, sa.totalbarno);
		tsg1.pack();
		RefineryUtilities.centerFrameOnScreen(tsg1);
		tsg1.setVisible(true);

        }
        
        private void Printjta5()
        {
        	JPanel jf1=new JPanel();
        	jta5 jta=new jta5(sa,totalnotes);
        	jf1.setSize(175,40);
        	jf1.setVisible(true);
    		jf1.setLocation(660, 342);
    		add(jf1);
    		jf1.add(jta);
        }
        
        private void Printjta4()
        {
        	JPanel jf1=new JPanel();
        	jta4 jta=new jta4(sa,totalbars);
        	jf1.setSize(175,40);
        	jf1.setVisible(true);
    		jf1.setLocation(660, 270);
    		add(jf1);
    		jf1.add(jta);
        }
        
        private void Printjta3()
        {
        	JPanel jf1=new JPanel();
        	jta3 jta=new jta3(sa,rowcnt);
        	jf1.setSize(175,40);
        	jf1.setVisible(true);
    		jf1.setLocation(660, 208);
    		add(jf1);
    		jf1.add(jta);
        }
        
        private void Printjta2()
        {
        	JPanel jf1=new JPanel();
        	jta2 jta=new jta2(sa,nrhere);
        	jf1.setSize(335,185);
        	jf1.setVisible(true);
    		jf1.setLocation(186, 208);
    		add(jf1);
    		jf1.add(jta);
        }
        
        private void Printjta1()
        {
        	JPanel jf1=new JPanel();
        	jta1 jta=new jta1(sa);
        	jf1.setSize(649, 122);
        	jf1.setVisible(true);
    		jf1.setLocation(186, 55);
    		add(jf1);
    		jf1.add(jta);
        }
        
        private void PrintTable() {
        	JPanel jf1=new JPanel();
        	Table tab = new Table(sa);
        	jf1.setSize(649, 270);
    		jf1.setVisible(true);
    		jf1.setLocation(186, 424);
    		//jf.add(table);
    		add(jf1);
    		jf1.add(tab);
	}

	public static void main(String args[]) {

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(output_frame.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				// new page2(image,x,y).setVisible(true);

			}

		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
