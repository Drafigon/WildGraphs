package graph.drawing;
/**
 * Basic round without text.
 * 
 * @author J. Wilde
 * @version 1.0
 * @since 02.04.2014
 */
public class VDO_StandardBlank extends VertexDrawOption {

	public VDO_StandardBlank() {
		super();
		setShape(new VS_Round());
		setSize(40);
		setShowText(false);
	}
}
