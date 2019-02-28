package lighthouse.ui.util;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.util.function.Function;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;

import lighthouse.util.ListenerList;

/**
 * Static methods for building declarative Swing layouts.
 */
public class LayoutUtils {
	private LayoutUtils() {}
	
	public static JPanel vboxOf(Component... components) {
		JPanel vbox = new JPanel();
		vbox.setLayout(new BoxLayout(vbox, BoxLayout.Y_AXIS));
		for (Component child : components) {
			vbox.add(child);
		}
		return vbox;
	}
	
	public static JPanel hboxOf(Component... components) {
		JPanel hbox = new JPanel();
		hbox.setLayout(new BoxLayout(hbox, BoxLayout.X_AXIS));
		for (Component child : components) {
			hbox.add(child);
		}
		return hbox;
	}
	
	public static JPanel panelOf(LayoutManager layout, Component... components) {
		JPanel bar = new JPanel(layout);
		for (Component child : components) {
			bar.add(child);
		}
		return bar;
	}
	
	public static JPanel panelOf(Component... components) {
		JPanel bar = new JPanel();
		for (Component child : components) {
			bar.add(child);
		}
		return bar;
	}
	
	public static JPanel compoundOf(Component... components) {
		JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEADING, 2, 0));
		for (Component child : components) {
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
	
	public static WebMenu menuOf(String name, Icon icon, WebMenuItem... items) {
		WebMenu menu = new WebMenu(name, icon);
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
	
	public static WebMenuItem itemOf(String label, Icon icon, Runnable action) {
		WebMenuItem item = new WebMenuItem(label, icon);
		item.addActionListener(l -> action.run());
		return item;
	}
	
	public static <T> JLabel labelOf(T value, ListenerList<T> listeners) {
		JLabel label = new JLabel(value.toString());
		listeners.add(it -> label.setText(it.toString()));
		return label;
	}
	
	public static <T> JLabel labelOf(T value, ListenerList<T> listeners, Function<T, String> stringifier) {
		JLabel label = new JLabel(stringifier.apply(value));
		listeners.add(it -> label.setText(stringifier.apply(it)));
		return label;
	}
}
