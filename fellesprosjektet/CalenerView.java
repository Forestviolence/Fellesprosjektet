package fellesprosjektet;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.Calendar;

import com.sun.xml.internal.ws.api.server.Container;

public class CalenerView {
	public JFrame myFrame = new JFrame("Calener");
	public JLayeredPane lpane = new JLayeredPane();
	public JPanel myPanel;
	public JPanel calpanel;
	public JPanel cal2panel;
	public java.awt.Container pane;
	public GridBagConstraints layout;
	public model.Calendar caldata;
	public MonthCalender cal;
	public MonthCalender cal2;
	public JButton butt;
	
	public CalenerView() {
		//Look and feel
//		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
//		catch (ClassNotFoundException e) {}
//		catch (InstantiationException e) {}
//		catch (IllegalAccessException e) {}
//		catch (UnsupportedLookAndFeelException e) {}
		
//		myFrame = new JFrame();
		myPanel = new JPanel();
		
		//Prepare frame
		Dimension mysize = new Dimension(800, 600);
		myFrame.setSize(mysize); //Set size to 400x400 pixels
		myFrame.setLayout(new BorderLayout());
//		lpane.setLayout(new BorderLayout());
		myFrame.setContentPane(lpane);
//		myFrame.add(lpane, BorderLayout.CENTER);
//		pane = myFrame.getContentPane(); //Get content pane
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close when X is clicked
		myFrame.setVisible(true);

		//Create controls
		butt = new JButton("hei");
		myPanel = new JPanel();
		calpanel = new JPanel();
		cal2panel = new JPanel();
		layout = new GridBagConstraints();
		myPanel.setLayout(new GridBagLayout());
		caldata = new model.Calendar();
		cal = new MonthCalender(lpane, caldata, 300, 0, 1, BorderLayout.WEST);
		cal2 = new MonthCalender(lpane, caldata, 0, 0, 0, BorderLayout.EAST);
		
		
		//Add controls to pane
		myPanel.add(butt);
		layout.gridx = 0;
		layout.gridy = 0;
//		lpane.add(myPanel);
//		lpane.add(cal);//, BorderLayout.WEST);
//		lpane.add(cal2, BorderLayout.EAST);
		lpane.repaint();

		myPanel.setBounds(0, 0, 800, 600);
		
		
//		myPanel.add(cal);
	}
}
