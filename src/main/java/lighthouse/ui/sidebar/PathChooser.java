package lighthouse.ui.sidebar;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

import javax.swing.JComponent;
import javax.swing.JFileChooser;

/**
 * A lightweight abstraction over {@link JFileChooser}
 * that uses {@link Path} instead of {@link File}.
 * Allows the user to select a file from the file system.
 */
public class PathChooser {
	private final JComponent parent;
	private final JFileChooser fc;
	
	public PathChooser(JComponent parent) {
		this.parent = parent;
		fc = new JFileChooser();
	}
	
	public Optional<Path> showOpenDialog() {
		if (fc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			return Optional.ofNullable(fc.getSelectedFile()).map(File::toPath);
		} else {
			return Optional.empty();
		}
	}
	
	public Optional<Path> showSaveDialog() {
		if (fc.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
			return Optional.ofNullable(fc.getSelectedFile()).map(File::toPath);
		} else {
			return Optional.empty();
		}
	}
}
