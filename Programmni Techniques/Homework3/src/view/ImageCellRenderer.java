package view;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

public class ImageCellRenderer extends DefaultListCellRenderer {

	ImageIcon image;

	public ImageCellRenderer(String path) {
		image = new ImageIcon(path);
	}

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		label.setIcon(image);
		setOpaque(false);
		return label;

	}

	@Override
	public Icon getIcon() {
		return super.getIcon();
	}
}
