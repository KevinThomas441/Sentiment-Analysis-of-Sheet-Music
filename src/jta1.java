import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;

public class jta1 extends JPanel{

	private static final long serialVersionUID = 1L;

	public jta1(SentimentAnalysis sa)
	{
		String emo=sa.calcSentiment(sa.tav, sa.tae);
		JTextArea jta = new JTextArea(emo);
		jta.setSize(649, 122);
		jta.setLocation(186, 55);
		jta.setEditable(false);
		Font f=new Font("Ariel",Font.BOLD,14);
		jta.setFont(f);
		Dimension ds=new Dimension(649, 122);
		jta.setPreferredSize(ds);
		//jta.setBounds(, , , );
		jta.setText(emo);
		JScrollPane js=new JScrollPane(jta);
		js.setVisible(true);
		add(js);
	}
	
}
