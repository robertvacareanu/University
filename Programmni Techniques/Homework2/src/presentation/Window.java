package presentation;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import datalayer.Constants;

public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 400;
	private static final int HEIGHT = 80;
	private static final String NAME = "Homework2";
	private static JButton userButton;
	private static JButton adminButton;
	// private static AdminWindow adminWindow;
	// private static UserWindow userWindow;

	public Window() {
		setTitle(NAME);
		setSize(WIDTH, HEIGHT);
		setLayout(new FlowLayout(FlowLayout.CENTER, 25, 10));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		// Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		int widthLeft = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - WIDTH / 2;
		int widthTop = Toolkit.getDefaultToolkit().getScreenSize().height / 8 - HEIGHT / 2;
		setLocation(widthLeft, widthTop);

		userButton = new JButton("User");
		userButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				block();
				new UserWindow();

			}
		});
		adminButton = new JButton("Admin");
		adminButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				block();
				new AdminWindow();

			}
		});
		JPanel gap = new JPanel();
		gap.setMinimumSize(new Dimension(200, 80));

		add(userButton);
		add(gap);
		add(adminButton);

		setVisible(true);
	}

	public void block() {
		userButton.setEnabled(false);
		adminButton.setEnabled(false);
	}

	public static void unblock() {
		userButton.setEnabled(true);
		adminButton.setEnabled(true);
	}

	public static String[] getFields(Object obj) {
		List<Field> fields = new ArrayList<>();
		List<Field> fieldsAux = new ArrayList<>();
		for (Class<?> c = obj.getClass(); c != null; c = c.getSuperclass()) {
			fieldsAux.addAll(Arrays.asList(c.getDeclaredFields()));
		}
		for (Field f : fieldsAux) {
			if (!Modifier.isStatic(f.getModifiers())) {
				fields.add(f);
			}
		}
		String result[] = new String[fields.size()];
		for (int i = 0; i < fields.size(); i++) {
			result[i] = Constants.Linker.tableEntityLinker.get(fields.get(i).getName());
		}

		return result;
	}

}
