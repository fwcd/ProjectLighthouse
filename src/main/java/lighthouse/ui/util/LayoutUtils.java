package lighthouse.ui.util;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;

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
	
	public static JPanel hboxOf(JComponent... components) {
		JPanel hbox = new JPanel();
		hbox.setLayout(new BoxLayout(hbox, BoxLayout.X_AXIS));
		for (JComponent child : components) {
			hbox.add(child);
		}
		return hbox;
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
	
	public static WebMenuBar menuBarOf(WebMenu... menus) {
		WebMenuBar menuBar = new WebMenuBar();
		for (WebMenu menu : menus) {
			menuBar.add(menu);
		}
		return menuBar;
	}
	
	public static WebMenu menuOf(String name, WebMenuItem... items) {
		WebMenu menu = new WebMenu(name);
		for (WebMenuItem item : items) {
			menu.add(item);
		}
		return menu;
	}
	
	public static WebMenuItem itemOf(String label, Runnable action) {
		WebMenuItem item = new WebMenuItem(label);
		item.addActionListener(l -> action.run());
		return item;
	}
}
