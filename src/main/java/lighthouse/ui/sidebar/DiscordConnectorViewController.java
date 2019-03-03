package lighthouse.ui.sidebar;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.ui.ViewController;
import lighthouse.ui.board.BoardViewController;
import lighthouse.ui.board.view.DiscordLighthouseView;
import lighthouse.ui.util.LayoutUtils;
import lighthouse.util.ConfigFile;
import lighthouse.util.ResourceConfigFile;

public class DiscordConnectorViewController implements ViewController {
	private static final Logger LOG = LoggerFactory.getLogger(DiscordConnectorViewController.class);
	private final JPanel component;
	private final BoardViewController board;
	private boolean connected = false;
	
	public DiscordConnectorViewController(BoardViewController board) {
		this.board = board;
		JTextField tokenField = new JTextField();
		
		component = LayoutUtils.vboxOf(
			LayoutUtils.compoundOf(
				new JLabel("Token: "),
				tokenField
			),
			LayoutUtils.panelOf(
				LayoutUtils.buttonOf("Connect", () -> connect(tokenField.getText()))
			)
		);
		
		ConfigFile auth = new ResourceConfigFile("/discordToken.txt");
		if (auth.has("token")) {
			tokenField.setText(auth.get("token"));
		} else {
			LOG.warn("Discord token file did not contain token");
		}
	}
	
	private void connect(String token) {
		if (connected) {
			JOptionPane.showMessageDialog(component, "Already connected!");
		} else {
			DiscordLighthouseView discordView = new DiscordLighthouseView();
			
			board.addLighthouseView(discordView);
			
			discordView.connect(token);
			discordView.addReadyListener(v -> board.render());
			
			connected = true;
		}
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
