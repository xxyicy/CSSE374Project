package sample;

import java.awt.Rectangle;

public abstract class Button extends AbstractComponent {
	String text;
	
	public Button(String text, Rectangle bound){
		this(null,text,bound);
	}
	
	public Button(IComponent parent, String text, Rectangle bound) {
		super(parent, bound);
		
		this.text = text;
		
		if(this.text == null)
			this.text = "";
	}
	
	/**
	 * Gets the text in the label.
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Sets the text in the label. 
	 * Calling this method results in the call to {@link #fireUpdate()}, 
	 * which informs the component hierarchy to re-draw itself.
	 */
	public void setText(String text) throws Exception{
		this.text = text;
		this.fireUpdate();
	}
}
