import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;
public class jta2 extends JPanel{

	private static final long serialVersionUID = 1L;

	public jta2(SentimentAnalysis sa,noterecognition nr)
	{
		JTextArea jTextArea1 = new JTextArea();
		jTextArea1.setSize(335, 185);
		jTextArea1.setLocation(186, 208);
		jTextArea1.setEditable(false);
		Font f=new Font("Ariel",Font.PLAIN,12);
		jTextArea1.setFont(f);
		Dimension ds=new Dimension(335, 185);
		jTextArea1.setPreferredSize(ds);
		//jta.setBounds(, , , );
		jTextArea1.setText(" ");
		for (int rowno = 0; rowno < sa.row; rowno++) {
			for (int colno = 0; colno < nr.notecnt[rowno]; colno++) {
				int nv = nr.notevalue[rowno][colno] % 12;
				// System.out.println(nv);
				switch (nv) {
				case 1:
					jTextArea1.append("C ");
					break;
				case 2:
					jTextArea1.append("C# ");
					break;
				case 3:
					jTextArea1.append("D ");
					break;
				case 4:
					jTextArea1.append("D# ");
					break;
				case 5:
					jTextArea1.append("E ");
					break;
				case 6:
					jTextArea1.append("F ");
					break;
				case 7:
					jTextArea1.append("F# ");
					break;
				case 8:
					jTextArea1.append("G ");
					break;
				case 9:
					jTextArea1.append("G# ");
					break;
				case 10:
					jTextArea1.append("A ");
					break;
				case 11:
					jTextArea1.append("Bb ");
					break;
				case 0:
					jTextArea1.append("B ");
					break;

				}

			}
			if (rowno != sa.row - 1)
				jTextArea1.append("\n");
		}
		JScrollPane js=new JScrollPane(jTextArea1);
		js.setVisible(true);
		add(js);
	}
	
}