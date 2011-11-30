/**
 * @author      Bonne Justin jbonne@uoguelph.ca
 * @author      Cardinal, Blake bcardina@uoguelph.ca
 * @author      Chabot, Chad chabot@uoguelph.ca
 * @version     0.9                 
 * @since       2011-11-29
 */


/**
 * An abstract class.
 * Ensures that any class implementing MessageListener has
 * the method handleMessage().
 */

public interface MessageListener 
{
    public void handleMessage(Message message);
}