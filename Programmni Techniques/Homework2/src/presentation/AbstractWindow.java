package presentation;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import business.EntityDTO;
import business.UserManager;

/**
 * 
 * @author Robert This class is extended by the User Window and Admin Window classes
 *
 */
public abstract class AbstractWindow extends JFrame implements WindowListener {

	// rivate static Window window = new Window();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 480;
	public static final int HEIGHT = 360;
	private boolean logged = false;
	protected EntityDTO entity;
	private int mode=0;

	public AbstractWindow(int x, int y, int mode) {
		this.mode = mode;
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		int width = x + Toolkit.getDefaultToolkit().getScreenSize().width / 2 - WIDTH / 2;
		int height = y + Toolkit.getDefaultToolkit().getScreenSize().height / 2 - HEIGHT / 2;
		setLocation(width, height);
		// this.logIn();
		// this.addExitListener();
		// setVisible(true);
		this.logIn();
		if (logged) {
			execute();
			// setVisible(true);
		}
		// execute();
	}
	
	public void setMode(int value) {
		this.mode = value;
	}

	/**
	 * Controls the way the frame will exit
	 */
	// protected abstract void addExitListener();
	@Override
	public void dispose() {
		super.dispose();
		Window.unblock();
	}

	/**
	 * 
	 */
	protected void logIn() {
		FlowLayout fl = new FlowLayout(FlowLayout.TRAILING);
		JPanel panel = new JPanel(new GridLayout(0, 1));
		JPanel userName = new JPanel(fl);
		JPanel pass = new JPanel(fl);
		JPanel buttons = new JPanel();
		JButton enter = new JButton("Log In");
		JButton createAccount = new JButton("Create");
		JPanel cancel = new JPanel();
		JButton cancelButton = new JButton("Cancel");
		JTextField userNameText = new JTextField();
		JPasswordField passText = new JPasswordField();
		JLabel userNameLabel = new JLabel("User: \t");
		JLabel passLabel = new JLabel("Password: \t");
		JDialog jd = new JDialog(this, true);

		// boolean returnValue = false;

		userNameText.setPreferredSize(new Dimension(140, 20));
		passText.setPreferredSize(new Dimension(140, 20));

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				AbstractWindow.this.dispose();
				logged = false;
			}
		});

		enter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!UserManager.getInstance().authenticate(userNameText.getText(), passText.getPassword(), mode)) {
					JOptionPane.showMessageDialog(AbstractWindow.this, "Bad Input", "ERROR", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(AbstractWindow.this, "Logged In", "Logged In", JOptionPane.INFORMATION_MESSAGE);
					jd.dispose();
					logged = true;
				}

			}
		});

		createAccount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!UserManager.getInstance().create(userNameText.getText(), passText.getPassword())) {
					JOptionPane.showMessageDialog(AbstractWindow.this, "Bad Input", "ERROR", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(AbstractWindow.this, " New Account Created", "Created", JOptionPane.INFORMATION_MESSAGE);
					jd.dispose();
				}
			}
		});

		userName.add(userNameLabel);
		userName.add(userNameText);
		pass.add(passLabel);
		pass.add(passText);
		cancel.add(cancelButton);

		panel.add(userName);
		panel.add(pass);
		buttons.add(enter);
		buttons.add(createAccount);
		panel.add(buttons);
		panel.add(cancel);

		jd.add(panel);
		jd.setSize(250, 200);
		jd.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		jd.setLocationRelativeTo(this);

		jd.setVisible(true);
		// execute();
		// setVisible(true);

	}
	
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}
	public abstract void execute();
	public void setEntityDTO(EntityDTO entity) {
		this.entity = entity;
	}
	public EntityDTO getEntityDTO() {
		return entity;
	}
	// public abstract String[] getLogInForm();
}
