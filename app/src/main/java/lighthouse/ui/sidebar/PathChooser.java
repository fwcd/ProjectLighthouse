package lighthouse.ui.sidebar;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * A lightweight abstraction over {@link JFileChooser}
 * that uses {@link Path} instead of {@link File}.
 * Allows the user to select a file from the file system.
 */
public class PathChooser {
	private final JComponent parent;
	private final JFileChooser fc;
	private final String suffix;
	
	public PathChooser(JComponent parent, String suffix) {
		this.parent = parent;
		this.suffix = suffix;
		
		boolean isLinux = System.getProperty("os.name").toLowerCase().contains("linux");
		
		if (isLinux) {
			try {
				LookAndFeel laf = UIManager.getLookAndFeel();
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				fc = new JFileChooser();
				UIManager.setLookAndFeel(laf);
			} catch (UnsupportedLookAndFeelException | ReflectiveOperationException e) {
				throw new RuntimeException("Error while swapping look and feel in PathChooser", e);
			}
		} else {
			fc = new JFileChooser();
		}
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
			return Optional.ofNullable(fc.getSelectedFile()).map(File::toPath).map(this::ensureSuffix);
		} else {
			return Optional.empty();
		}
	}
	
	private Path ensureSuffix(Path path) {
		String fileName = path.getFileName().toString();
		if (fileName.endsWith(suffix)) {
			return path;
		} else {
			return path.resolveSibling(fileName + suffix);
		}
	}
}
