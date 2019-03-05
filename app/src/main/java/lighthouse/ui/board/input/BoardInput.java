package lighthouse.ui.board.input;

import lighthouse.ui.board.controller.BoardResponder;

/**
 * A general input-facility that can
 * notify responders about user inputs.
 * 
 * <p>Usually, the implementor will be
 * responsible for transforming the raw
 * events into the higher-level representation
 * that is required by a responder.</p>
 */
public interface BoardInput {
	void addResponder(BoardResponder responder);
}
