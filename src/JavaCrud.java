import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JavaCrud {

	private JFrame frame;
	private JTextField txtbname;
	private JTable table;
	private JTextField txtbid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaCrud window = new JavaCrud();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JavaCrud() {
		initialize();
		Connect();
		table_load();
		
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTextField txtedition;
	private JTextField txtprice;
	
	
	public void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");//register the mysql driver
			con = DriverManager.getConnection("jdbc:mysql://localhost/javacrud","root",""); // telling the path of the database ->the database(javacrud-database name) will reside on the localhost
			/*default credentials of mysql:
			 * username:root
			 * password:""
			 * */
		}
		catch (ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	public void table_load()
	{
	    try 
	    {
	    pst = con.prepareStatement("select * from book");
	    rs = pst.executeQuery();
	    table.setModel(DbUtils.resultSetToTableModel(rs));
	} 
	    catch (SQLException e) 
	     {
	        e.printStackTrace();
	  } 
	}
	
	
	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 981, 595);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("BOOK SHOP");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(357, 22, 257, 40);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "REGISTRATION", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(39, 88, 413, 234);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(39, 37, 115, 28);
		panel.add(lblNewLabel_1);
		
		JLabel txtfield1 = new JLabel("Edition");
		txtfield1.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtfield1.setBounds(39, 88, 87, 28);
		panel.add(txtfield1);
		
		JLabel textPrice = new JLabel("Price");
		textPrice.setFont(new Font("Tahoma", Font.BOLD, 16));
		textPrice.setBounds(39, 139, 87, 28);
		panel.add(textPrice);
		
		txtbname = new JTextField();
		txtbname.setBounds(154, 41, 181, 26);
		panel.add(txtbname);
		txtbname.setColumns(10);
		
		txtedition = new JTextField();
		txtedition.setColumns(10);
		txtedition.setBounds(154, 92, 181, 26);
		panel.add(txtedition);
		
		txtprice = new JTextField();
		txtprice.setColumns(10);
		txtprice.setBounds(154, 143, 181, 26);
		panel.add(txtprice);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				
				String bname,edition,price;
				
				bname=txtbname.getText();
				edition=txtedition.getText();
				price=txtprice.getText();
				
				try {
					pst=con.prepareStatement("insert into book(name,edition,price)values(?,?,?)");
					pst.setString(1,bname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Added..!!!");
					table_load();
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();//used to focus the first box again	
				}
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnNewButton.setBounds(39, 346, 116, 46);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
				
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnExit.setBounds(180, 346, 116, 46);
		frame.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtbname.setText("");
				txtedition.setText("");
				txtprice.setText("");
				txtbname.requestFocus();
				
				
				
				
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnClear.setBounds(330, 346, 116, 46);
		frame.getContentPane().add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(492, 91, 448, 301);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "SEARCH", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(39, 420, 413, 93);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Book ID");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1_1_1.setBounds(32, 29, 87, 28);
		panel_1.add(lblNewLabel_1_1_1);
		
		txtbid = new JTextField();
		txtbid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
                    
                    String id = txtbid.getText();
                        pst = con.prepareStatement("select name,edition,price from book where id = ?");
                        pst.setString(1, id);
                        ResultSet rs = pst.executeQuery();
                    if(rs.next()==true)
                    {
                      
                        String name = rs.getString(1);
                        String edition = rs.getString(2);
                        String price = rs.getString(3);
                        
                        txtbname.setText(name);
                        txtedition.setText(edition);
                        txtprice.setText(price);

                    }   
                    else
                    {
                        txtbname.setText("");
                        txtedition.setText("");
                        txtprice.setText("");
                         
                    }
                } 
            
             catch (SQLException ex) {
                   
                }
        }
		});
		txtbid.setColumns(10);
		txtbid.setBounds(114, 31, 236, 26);
		panel_1.add(txtbid);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bname,edition,price,bid;
				
				bname=txtbname.getText();
				edition=txtedition.getText();
				price=txtprice.getText();
				bid=txtbid.getText();
				
				try {
					pst = con.prepareStatement("update book set name= ?,edition=?,price=? where id =?");
					pst.setString(1,bname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.setString(4, bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Updated..!!!");
					table_load();
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();//used to focus the first box again	
				}
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnUpdate.setBounds(570, 436, 116, 46);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bid;
				
				bid=txtbid.getText();
				
				try {
					pst = con.prepareStatement("delete from book where id =?");
					pst.setString(1, bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Deleted..!!!");
					table_load();
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();//used to focus the first box again	
				}
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnDelete.setBounds(750, 436, 116, 46);
		frame.getContentPane().add(btnDelete);
	}
}
