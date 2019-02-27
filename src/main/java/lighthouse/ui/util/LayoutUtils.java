package lighthouse.ui.util;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * Static methods for building declarative Swing layouts.
 */
public class LayoutUtils {
	private LayoutUtils() {}
	
	public static JPanel vboxOf(JComponent... components) {
		JPanel vbox = new JPanel();
		vbox.setLayout(new BoxLayout(vbox, BoxLayout.Y_AXIS));
		for (JComponent child : components) {
			vbox.add(child);
		}
		return vbox;
	}
	
	public static JPanel panelOf(JComponent... components) {
		JPanel bar = new JPanel();
		for (JComponent child : components) {
			bar.add(child);
		}
		return bar;
	}
	
	public static JButton buttonOf(String label, Runnable action) {
		JButton button = new JButton(label);
		button.addActionListener(l -> action.run());
		return button;
	}
}
