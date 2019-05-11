
/* Darsh Patel - Final Project - Spring 2019 */
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout; //useful for layouts
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//controls-label text fields, button
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Dao conn = new Dao();

	public Login() {

		super("WELCOME TO IIT HELP DESK LOGIN");

		// set look and feel for GUI
		try {
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		conn = new Dao();
		setSize(500, 310);
		setLayout(new GridLayout(4, 2));
		setLocationRelativeTo(null); // centers window

		// SET UP CONTROLS
		JLabel lblUsername = new JLabel("Username", JLabel.LEFT);
		JLabel lblPassword = new JLabel("Password", JLabel.LEFT);
		JLabel lblStatus = new JLabel(" ", JLabel.CENTER);

		JTextField txtUname = new JTextField(10);

		JPasswordField txtPassword = new JPasswordField();

		JButton btn = new JButton("Submit");
		JButton btnExit = new JButton("Exit");
		JButton reset = new JButton("Reset Password");
		reset.setVisible(false);

		// constraints

		lblStatus.setToolTipText("Contact help desk to unlock password");
		lblUsername.setHorizontalAlignment(JLabel.CENTER);
		lblPassword.setHorizontalAlignment(JLabel.CENTER);

		// create Font
		Font f = new Font("Century", Font.BOLD, 18);
		Font status = new Font("Century", Font.BOLD, 15);

		// set font style to the button's text
		lblStatus.setFont(status);
		lblUsername.setFont(f);
		lblPassword.setFont(f);
		btn.setFont(f);
		btnExit.setFont(f);
		reset.setFont(f);

		// set colors
		btn.setBackground(Color.GRAY);
		btnExit.setBackground(Color.GRAY);
		btn.setForeground(Color.WHITE);
		btnExit.setForeground(Color.WHITE);
		reset.setForeground(Color.WHITE);
		reset.setBackground(Color.GRAY);

		// ADD OBJECTS TO FRAME
		add(lblUsername);// 1st row filler
		add(txtUname);
		add(lblPassword); // 2nd row
		add(txtPassword);
		add(btn); // 3rd row
		add(btnExit);
		add(lblStatus); // 4th row
		add(reset);

		btn.addActionListener(new ActionListener() {
			int count = 0; // count agent

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean admin = false;
				count = count + 1;

				// verify credentials of user

				String query = "SELECT * FROM dpatel_users WHERE uname = ? and upass = ?;";
				try (PreparedStatement stmt = conn.connect().prepareStatement(query)) {
					stmt.setString(1, txtUname.getText());
					stmt.setString(2, txtPassword.getText());
					ResultSet rs = stmt.executeQuery();
					if (rs.next()) {
						admin = rs.getBoolean("admin"); // get table column value
						new Tickets(admin);
						setVisible(false); // HIDE THE FRAME
						dispose(); // CLOSE OUT THE WINDOW
					} else {

						lblStatus.setText("Try again! " + (3 - count) + " / 3 attempts left");
					}

					// hiding options after 3 attempts
					if (count == 3) {
						txtUname.disable();
						txtPassword.disable();
						txtUname.setBackground(Color.RED);
						txtPassword.setBackground(Color.RED);
						JOptionPane.showMessageDialog(null, "Reset the Password and Try Again!");
						lblStatus.setText("Click on Reset Password");
						btn.setVisible(false);
						reset.setVisible(true);

					}

				} catch (SQLException ex) {
					ex.printStackTrace();
				}

			}
		});
		btnExit.addActionListener(e -> System.exit(0));

		// reset password for either user or admin

		reset.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {

				// get info from users

				String username = JOptionPane.showInputDialog(null, "Enter Username");

				// error trapping
				while (username.length() == 0) {
					username = JOptionPane.showInputDialog(null, "Please re-enter New Password");
				}

				String password = JOptionPane.showInputDialog(null, "Enter New Password");

				// error trapping
				while (password.length() == 0) {
					password = JOptionPane.showInputDialog(null, "Please re-enter New Password");
				}

				String confirmPass = JOptionPane.showInputDialog(null, "Confirm New Password");

				// error trapping
				while (confirmPass.length() == 0) {
					confirmPass = JOptionPane.showInputDialog(null, "Please confirm New Password");
				}

				int id = conn.updatePassword(username, confirmPass);

				if (id != 0) {
					System.out.println("New Password : " + confirmPass);
					JOptionPane.showMessageDialog(null, "New Password : " + confirmPass);
				} else {
					System.out.println("New Password cannot be updated");
				}

				/*
				 * showing options again for user to login after resetting the new password
				 */
				txtUname.enable();
				txtPassword.enable();
				txtUname.setBackground(Color.white);
				txtPassword.setBackground(Color.white);
				lblStatus.setText("Login Again");
				btn.setVisible(true);
				reset.setVisible(false);

			}

		});

		setVisible(true); // SHOW THE FRAME

	}

	public static void main(String[] args) {

		new Login();
	}
}