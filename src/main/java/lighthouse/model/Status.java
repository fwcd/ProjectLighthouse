package lighthouse.model;

import java.awt.Color;

/**
 * A colored status message.
 */
public class Status {
	private final String message;
	private final Color color;
	
	public Status(String message, Color color) {
		this.message = message;
		this.color = color;
	}
	
	public String getMessage() { return message; }
	
	public Color getColor() { return color; }
}
