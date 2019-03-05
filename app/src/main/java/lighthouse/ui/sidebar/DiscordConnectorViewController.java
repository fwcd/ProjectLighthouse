package lighthouse.ui.sidebar;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.ui.SwingViewController;
import lighthouse.puzzle.ui.board.BoardViewController;
import lighthouse.puzzle.ui.board.input.BoardKeyInput;
import lighthouse.ui.scene.SceneViewController;
import lighthouse.ui.scene.view.discord.DiscordLighthouseView;
import lighthouse.ui.util.LayoutUtils;
import lighthouse.util.ConfigFile;
import lighthouse.util.ResourceConfigFile;

public class DiscordConnectorViewController implements SwingViewController {
	private static final Logger LOG = LoggerFactory.getLogger(DiscordConnectorViewController.class);
	private final JPanel component;
	private final SceneViewController scene;
	private boolean connected = false;
	
	public DiscordConnectorViewController(SceneViewController scene) {
		this.scene = scene;
		
		JTextField tokenField = new JTextField();
		tokenField.setColumns(15);
		
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
			ConfigFile config = new ResourceConfigFile("/discordConfig.txt");
			SceneKeyInput input = new SceneKeyInput();
			DiscordLighthouseView discordView = new DiscordLighthouseView(config, input);
			
			scene.addLighthouseView(discordView);
			input.addResponder(board.getResponder());
			
			discordView.connect(token);
			discordView.addReadyListener(v -> board.render());
			
			connected = true;
		}
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
