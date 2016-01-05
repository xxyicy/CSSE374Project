package sample;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;

/**
 * {@link IComponent} represents the GUI component hierarchy. It provides facilities
 * to create new GUI widgets. The client of this framework, however, are advised to subclass
 * from the {@link AbstractComponent} class rather than implementing this interface. 
 * {@link AbstractComponent} provides some default implementation and supplies core 
 * component hierarchy specific drawing logic.
 * 
 * @author Chandan R. Rupakheti (chandan.rupakheti@rose-hulman.edu)
 */
public interface IComponent {
	/**
	 * Default bound to be used by the GUI component framework when one is not available.
	 */
	public static final Rectangle DEFAULT_BOUNDS = new Rectangle(0,0,50,50);
	
	/**
	 * Gets the parent of this component.
	 */
	public IComponent getParent();
	
	/**
	 * Add the supplied child component to the list of children 
	 * and sets this component to be the part of the child.
	 * Calling this method results in the call to {@link #fireUpdate()}, 
	 * which informs the component hierarchy to re-draw itself.
	 * 
	 * @param c {@link IComponent}, a child component.
	 * @return true for success, otherwise, false.
	 */
	boolean addChild(IComponent c);
	
	/**
	 * Return an immutable list of children components.
	 */
	public List<IComponent> getChildren();
	
	/**
	 * Gets the {@link Rectangle} area that bounds this component.
	 */
	public Rectangle getBounds();

	/**
	 * Sets the new bound {@link Rectangle} of this component.
	 * Calling this method results in the call to {@link #fireUpdate()}, 
	 * which informs the component hierarchy to re-draw itself.
	 * 
	 * @param r {@link Rectangle} The supplied new bound.
	 */
	public void setBounds(Rectangle r);
	
	/**
	 * This method calls the drawing logic of this component and 
	 * all other child components attached to this component hierarchy. 
	 * A concrete component <strong>should not</strong> override this method, instead,
	 * should implement the component specific drawing logic in the 
	 * {@link #drawComponent(Graphics2D)} method.
	 * 
	 * @param g The {@link Graphics2D} object to be used for rendering the component herarchy.
	 */
	public void draw(Graphics2D g);
	
	/**
	 * This method should be overriden by a concrete component to specify its drawing
	 * logic. This method will be called internally by the {@link #draw(Graphics2D)}
	 * method to render the whole component hierarchy.
	 * 
	 * @param g The {@link Graphics2D} object to be used for rendering this component.
	 */
	public void drawComponent(Graphics2D g);
	
	/**
	 * This will cascade the state change information all the way up to the outermost component.
	 * The outermost component then starts redrawing all of the components in the component
	 * hierarchy.
	 */
	public void fireUpdate();
}

