import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;

public class jta3 extends JPanel{

	private static final long serialVersionUID = 1L;

	public jta3(SentimentAnalysis sa,Integer rowcnt)
	{
		JTextArea jta = new JTextArea(rowcnt.toString());
		jta.setSize(175, 40);
		jta.setLocation(660, 208);
		jta.setEditable(false);
		Font f=new Font("Ariel",Font.PLAIN,12);
		jta.setFont(f);
		Dimension ds=new Dimension(175, 40);
		jta.setPreferredSize(ds);
		//jta.setBounds(, , , );
		jta.setText(rowcnt.toString());
		JScrollPane js=new JScrollPane(jta);
		js.setVisible(true);
		add(js);
	}
	
}

