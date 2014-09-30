package graph.drawing;
/**
 * Basic round with text.
 * 
 * @author J. Wilde
 * @version 1.0
 * @since 02.04.2014
 */
public class VDO_StandardText extends VertexDrawOption {

	public VDO_StandardText() {
		super();
		setShape(new VS_Round());
		setSize(40);
		setShowText(true);
	}
}
