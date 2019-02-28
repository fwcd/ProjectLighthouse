package lighthouse.ui.debug;

import java.awt.BorderLayout;
import java.lang.reflect.Field;
import java.util.stream.Stream;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.AppModel;
import lighthouse.ui.GameViewController;
import lighthouse.ui.ViewController;
import lighthouse.ui.util.LayoutUtils;
import lighthouse.util.ListenerList;

/**
 * Manages a visual representation of the listener graph.
 */
public class ListenerGraphViewController implements ViewController {
	private static final Logger LOG = LoggerFactory.getLogger(ListenerGraphViewController.class);
	private final AppModel appModel;
	private final GameViewController gameVC;
	
	private final JComponent component;
	private final ListenerGraphView view;
	private ListenerGraph model;
	
	public ListenerGraphViewController(AppModel appModel, GameViewController gameVC) {
		this.appModel = appModel;
		this.gameVC = gameVC;
		
		component = new JPanel(new BorderLayout());
		component.add(LayoutUtils.buttonOf("Update", this::update), BorderLayout.NORTH);
		
		view = new ListenerGraphView();
		component.add(view.getComponent(), BorderLayout.CENTER);
	}
	
	private void update() {
		model = buildGraph();
		view.draw(model);
	}
	
	public ListenerGraph buildGraph() {
		Stream<ListenerList<?>> stream = Stream.concat(
			findListenerListsIn(appModel),
			findListenerListsIn(gameVC)
		);
		return new ListenerGraph(stream.toArray(ListenerList[]::new));
	}
	
	private Stream<ListenerList<?>> findListenerListsIn(Object obj) {
		return findFieldsIn(obj.getClass())
			.filter(field -> ListenerList.class.isAssignableFrom(field.getType()))
			.flatMap(field -> {
				if (!field.isAccessible()) {
					field.setAccessible(true);
				}
				try {
					return Stream.of((ListenerList<?>) field.get(obj));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					LOG.error("Error while accessing listener list field:", e);
					return Stream.empty();
				}
			});
	}
	
	private Stream<Field> findFieldsIn(Class<?> objClass) {
		Stream.Builder<Field> stream = Stream.builder();
		findFieldsIn(objClass, stream);
		return stream.build();
	}
	
	private void findFieldsIn(Class<?> objClass, Stream.Builder<Field> stream) {
		for (Field field : objClass.getDeclaredFields()) {
			stream.accept(field);
		}
		Class<?> superClass = objClass.getSuperclass();
		if (superClass != null) {
			findFieldsIn(superClass);
		}
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
