package fellesprosjektet;

/*Contents of CalendarProgran.class */

//Import packages
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class MonthCalender extends JComponent implements PropertyChangeListener{
	private JLabel lblMonth, lblYear;
	private JButton btnPrev, btnNext;
	private JTable tblCalendar;
	private JComboBox cmbYear;
	private Container pane;
	private DefaultTableModel mtblCalendar; //Table model
	private JScrollPane stblCalendar; //The scrollpane
	private JPanel pnlCalendar;
	private int realYear, realMonth, realWeek, realDay, currentYear, currentMonth, currentWeek;
	private model.Calendar data;
	private GridBagConstraints myCon;
	
	public MonthCalender(JLayeredPane pane, model.Calendar data, int xpos, int ypos, int nr, String blayout) {
		//Look and feel
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}

		//Prepare frame
//		frmMain = new JFrame ("Gestionnaire de clients"); //Create frame
//		frmMain.setSize(400, 400); //Set size to 400x400 pixels
//		pane = frmMain.getContentPane(); //Get content pane
//		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close when X is clicked

		//Create controls
		myCon = new GridBagConstraints();
		lblMonth = new JLabel ("January");
		lblYear = new JLabel ("Change year:");
		cmbYear = new JComboBox();
		btnPrev = new JButton ("<<");
		btnNext = new JButton (">>");
		mtblCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return true;}};
		tblCalendar = new JTable(mtblCalendar);
		stblCalendar = new JScrollPane(tblCalendar);
		pnlCalendar = new JPanel(null);
		pnlCalendar.setOpaque(true);	
		this.data = data;
		

		//Set border
		pnlCalendar.setBorder(BorderFactory.createTitledBorder("Calendar"));
		pnlCalendar.setLayout(new GridBagLayout());
		
		
		//Register action listeners
		btnPrev.addActionListener(new btnPrev_Action());
		btnNext.addActionListener(new btnNext_Action());
		cmbYear.addActionListener(new cmbYear_Action());
		
		//Add controls to pane
		
		pane.add(pnlCalendar, new Integer(nr), nr);
//		pane.add(pnlCalendar);
		
		myCon.weightx = 0;
		myCon.anchor = GridBagConstraints.NORTHWEST;
		myCon.gridx = 0;
		myCon.gridy = 0;
		pnlCalendar.add(btnPrev, myCon);
		
		myCon.weightx = 5;
		myCon.anchor = GridBagConstraints.CENTER;
		myCon.gridx = 1;
		myCon.gridy = 0;
		pnlCalendar.add(lblMonth, myCon);
		
		myCon.weightx = 1;
		myCon.anchor = GridBagConstraints.NORTHEAST;
		myCon.gridx = 3;
		myCon.gridy = 0;
		pnlCalendar.add(btnNext, myCon);
		
		myCon.weightx = 50;
		myCon.weighty = 5;
		myCon.fill = GridBagConstraints.BOTH;
		myCon.gridwidth = 4;
		myCon.gridx = 0;
		myCon.gridy = 1;
		pnlCalendar.add(stblCalendar, myCon);
		
		myCon.weightx = 0;
		myCon.weighty = 0;
		myCon.anchor = GridBagConstraints.SOUTHWEST;
		myCon.gridx = 0;
		myCon.gridy = 2;
		pnlCalendar.add(lblYear, myCon);
		
		myCon.weightx = 0;
		myCon.weighty = 0;
//		myCon.anchor = GridBagConstraints.SOUTHEAST;
		myCon.gridx = 2;
		myCon.gridy = 2;
		pnlCalendar.add(cmbYear, myCon);
		
		//Set bounds
//		pnlCalendar.setBounds(frmMain.getBounds());
		pnlCalendar.setBounds(xpos, ypos, 262, 214);
//		pnlCalendar.setSize(300, 300);
		
		//Make frame visible
//		frmMain.setResizable(true);
//		frmMain.setVisible(true);
		
		//Get real month/year
		GregorianCalendar cal = new GregorianCalendar(); //Create calendar
		realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
		data.setRealDay(realDay);
		realMonth = cal.get(GregorianCalendar.MONTH); //Get month
		data.setRealMonth(realMonth);
		realYear = cal.get(GregorianCalendar.YEAR); //Get year
		data.setRealYear(realYear);
		realWeek = cal.get(GregorianCalendar.WEEK_OF_YEAR);
		data.setRealWeek(realWeek);
		currentMonth = realMonth; //Match month and year
		data.setCurrentMonth(currentMonth);
		currentYear = realYear;
		data.setCurrentYear(currentYear);
		currentWeek = realWeek;
		data.setCurrentWeek(currentWeek);
		
		this.data.addPropertyChangeListener(this);
		
		//Add headers
		String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
		for (int i=0; i<7; i++){
			mtblCalendar.addColumn(headers[i]);
		}
		
		tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //Set background

		//No resize/reorder
		tblCalendar.getTableHeader().setResizingAllowed(false);
		tblCalendar.getTableHeader().setReorderingAllowed(false);

		//Single cell selection
		tblCalendar.setColumnSelectionAllowed(true);
		tblCalendar.setRowSelectionAllowed(true);
		tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//Set row/column count
//		tblCalendar.setRowHeight(28);
		mtblCalendar.setColumnCount(7);
		mtblCalendar.setRowCount(6);
		
		//Populate table
		for (int i=data.getRealYear()-100; i<=data.getRealYear()+100; i++){
			cmbYear.addItem(String.valueOf(i));
		}
		
		//Refresh calendar
		refreshCalendar (realMonth, realYear); //Refresh calendar
	}

//	public private void main (String args[]){
//		JFrame myFrame = new JFrame();
//		MonthCalendar cal = new MonthCalendar();
//	}
	

	private void refreshCalendar(int month, int year){
		//Variables
		String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int nod, som; //Number Of Days, Start Of Month
			
		//Allow/disallow buttons
		btnPrev.setEnabled(true);
		btnNext.setEnabled(true);
		if (month == 0 && year <= data.getRealYear()-10){btnPrev.setEnabled(false);} //Too early
		if (month == 11 && year >= data.getRealYear()+100){btnNext.setEnabled(false);} //Too late
		lblMonth.setText(months[month]); //Refresh the month label (at the top)
		cmbYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
		
		//Clear table
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){
				mtblCalendar.setValueAt(null, i, j);
			}
		}
		
		//Get first day of month and number of days
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);
		
		//Draw calendar
		for (int i=1; i<=nod; i++){
			int row = new Integer((i+som-2)/7);
			int column  =  (i+som-2)%7;
			mtblCalendar.setValueAt(i, row, column);
		}

		//Apply renderers
		tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
//		tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new DefaultTableCellRenderer());
//		tblCalendar.setDefaultRenderer(int, new DefaultTableCellRenderer());
	}

	private class tblCalendarRenderer extends DefaultTableCellRenderer{
		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			if (column == 0 || column == 6){ //Week-end
				setBackground(new Color(255, 220, 220));
			}
			else{ //Week
				setBackground(new Color(255, 255, 255));
			}
			if (value != null){
				if (Integer.parseInt(value.toString()) == data.getRealDay() &&
								data.getCurrentMonth() == data.getRealMonth() &&
								data.getCurrentYear() == data.getRealYear()) { //Today
					setBackground(new Color(220, 220, 255));
				}
			}
			setBorder(null);
			setForeground(Color.black);
			return this;  
		}
	}

	private class btnPrev_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			int currentYear = data.getCurrentYear();
			int currentMounth = data.getCurrentMonth();
			if (currentMounth == 0){ //Foward one year
				data.setCurrentMonth(11);
				currentYear -= 1;
				data.setCurrentYear(currentYear);
			}
			else{ //Foward one month
				currentMounth -= 1;
				data.setCurrentMonth(currentMounth);
			}
			refreshCalendar(currentMonth, currentYear);
//			pnlCalendar.repaint();
		}
	}
	private class btnNext_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			int currentYear = data.getCurrentYear();
			int currentMounth = data.getCurrentMonth();
			if (currentMounth == 11){ //Foward one year
				data.setCurrentMonth(0);
				currentYear += 1;
				data.setCurrentYear(currentYear);
			}
			else{ //Foward one month
				currentMounth += 1;
				data.setCurrentMonth(currentMounth);
			}
			refreshCalendar(data.getCurrentMonth(), data.getCurrentYear());
			System.out.println();
//			pnlCalendar.repaint();
		}
	}
	private class cmbYear_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (cmbYear.getSelectedItem() != null){
				String b = cmbYear.getSelectedItem().toString();
				data.setCurrentYear(Integer.parseInt(b));
				refreshCalendar(data.getCurrentMonth(), data.getCurrentYear());
			}
		}
	}
	
//	public void setModel(model.Calendar data) {
//		this.data = data;
//		this.data.addPropertyChangeListener(this);
//	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		refreshCalendar(data.getCurrentMonth(), data.getCurrentYear());
		pnlCalendar.repaint();
		System.out.println("det skjer ting!");
	}
}
