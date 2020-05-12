package infocarbon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.fasterxml.jackson.core.JsonProcessingException;

import user.Users;


public class CarbonMenu extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel p = new JPanel();
	private JPanel cg = new JPanel();
	String[] tab_string = {"1", "2", "3", "4", "5", "6"};
	JButton[] tab_button = new JButton[tab_string.length];
	private JButton b1,b2,b3,b4,b5; 
	private JRadioButton rb1, rb2 ;
	private CarbonOrder co;
	private UtilDateModel model = new UtilDateModel();
	

	
	public CarbonMenu(){
		this.setBounds(0,0,700,700);// X,Y,Largeur, Longueur
		this.setTitle("Carbon info DELTA-City");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new FlowLayout());
		JPanel pannel = new JPanel();
		pannel.setBackground(Color.yellow);
		pannel.setBorder(new TitledBorder("Choisissez une option"));
		getContentPane().add(pannel);
		ButtonGroup groupe = new ButtonGroup();
		rb1 = new JRadioButton("Calculer l'empreinte carbonne de la ville");
		groupe.add(rb1);
		rb1.addActionListener(this);
		pannel.add(rb1);
		rb2 = new JRadioButton("Estimer l'empreinte carbonne de la ville");
		groupe.add(rb2);
		rb2.addActionListener(this);
		pannel.add(rb2);
		//DATE PICKER
		Properties prop = new Properties();
		prop.put("text.today", "Today");
		prop.put("text.month", "Month");
		prop.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, prop);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		pannel.add(datePicker);
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		this.getContentPane().add(BorderLayout.EAST,p);
		b1 = new JButton("Empreinte carbonne globale pour la date d'hier");
		b2 = new JButton("Empreite carbonne globale de la ville pour une date souhaitée");
		b3 = new JButton("Empreinte carbonne pour les transports publics pour une date souhaitée");
		b4 = new JButton("Empreinte carbonne pour les transports privées pour une date souhaitée");
		b5 = new JButton("Estimer l'empreinte carbonne");
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  }
	
	public void actionPerformed(ActionEvent event) {
		Object o = event.getSource();
		if (o == rb1) {
			getContentPane().remove(p);
			cg.removeAll();
			p.removeAll();
			p.add(b1);
			p.add(b2);
			p.add(b3);
			p.add(b4);
			getContentPane().add(p);
			b1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cg.removeAll();
					JTextField jt = new JTextField("L'empreinte carbonne globale de la ville est 345");
					cg.add(jt);
					getContentPane().add(cg);
					Users u = new Users();
					co = new CarbonOrder(1,u);
					String res;
					try {
						res = co.generateJson();
						System.out.println(res);
					} catch (JsonProcessingException e1) {
						e1.printStackTrace();
					}
					
					setVisible(true);
					
				}
				
			});
			b2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					cg.removeAll();
					JTextField jt = new JTextField("Veuillez entrer la date souhaitée avec le format JJ/MM/AAAA");
					cg.add(jt);
					
					JTextField te = new JTextField(16);
					JButton but = new JButton("submit"); 
					cg.add(te);
					cg.add(but);
					getContentPane().add(cg);
					but.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String d = te.getText();
							System.out.println(d);
							boolean b = validateJavaDate(d);
							if(b) {
								boolean bb = compareDate(d);
								if(bb) {
									JTextField jtt = new JTextField("Calcul de l'empreinte carbonne pour la date du "+ d);
									cg.add(jtt);
									getContentPane().add(cg);
									Users u = new Users();
									CarbonOrder co2 = new CarbonOrder(2,u);
									co2.setDate(d);
									String res;
									try {
										res = co2.generateJson();
										System.out.println(res);
									} catch (JsonProcessingException e1) {
										e1.printStackTrace();
									}
									setVisible(true);
								}else {
									JTextField jtt = new JTextField("Veuillez entrez une date valide et antérieure à celle d'aujourd'hui");
									getContentPane().add(cg);
									setVisible(true);
									
								}
							}else {
								JTextField jtt = new JTextField("Veuillez entrez une date valide sous le format JJ/MM/AAAA");
								cg.add(jtt);
								getContentPane().add(cg);
								setVisible(true);
							}
							System.out.println(d);
						}
					});

					
					setVisible(true);
					
				}
				
			});
			
			
			
			setVisible(true);
		}
		if (o == rb2) {
			getContentPane().remove(p);
			cg.removeAll();
			p.removeAll();
			p.add(b5);
			getContentPane().add(p);
			setVisible(true);
		}
		
	}
	
	//Function which validate the date format
		public static boolean validateJavaDate(String date)
		   {
			/* Check if date is 'null' */
			if (date.trim().equals(""))
			{
				System.out.println("L'entrée est vide\n");
			    return false;
			}
			/* Date is not 'null' */
			else
			{
			    /*
			     * Set preferred date format,
			     * dd/MM/yyyy */
			    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			    formatter.setLenient(false);
			    try
			    {
			        Date javaDate = formatter.parse(date); 
			        
			    }
			    /* Date format is invalid */
			    catch (ParseException e)
			    {
			    	System.out.println("L'entrée est invalide\n");
			        return false;
			    }
			    /* Return true if date format is valid */
			    System.out.println("L'entrée est valide\n");
			    return true;
			}
		   }
		
	//Function which allow to compare two dates 
	public static boolean compareDate(String date) {
		  SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		  Date dateToday = new Date();  
	      Date d1;
	      boolean res = false ;
	      try {
	    	  d1 = formatter.parse(date);
		      if(dateToday.compareTo(d1) > 0) {
		         
		         res = true;
		      } else if(dateToday.compareTo(d1) < 0) {
		    	  res =  false;
		      } else if(dateToday.compareTo(d1) == 0) {
		         res = false;
		      }
	      } catch (ParseException e) {
			e.printStackTrace();
	      }
		return res;
	      
	       
	   }
	
	public static void main(String[] args) {
		CarbonMenu f = new CarbonMenu();
		f.setVisible(true);
		
	}

}
