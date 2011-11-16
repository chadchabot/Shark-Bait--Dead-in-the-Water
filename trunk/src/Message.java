/**
 * @author      Bonne Justin jbonne@uoguelph.ca
 * @version     0.1                 
 * @since       2011-11-13
 */
import java.util.EventObject;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Message extends EventObject
{
	
	private String message;
    private String message_name = null;
	private ArrayList<String> arguments = null;

    /**
     * Class constructor automattically parses message 
     * into message name and arguments upon creation.
     *
     * @param source reference to the source object
     * @param message a message
     */
    public Message( Object source, String message)
	{
        super( source );
        this.message = message;
		this.parseMessage();
    }
    /**
     * Accessor for the orignal message
     *
     * @return a copy of the message string
     */
  	public String getMessage()
	{
		if(message != null)
		{
			return new String(message);
		}
		else{
			return null;
		}
	}
    /**
     * Accessor for the message name
     *
     * @return a copy of the message name string
     */
	public String getMessageName()
	{
		if(message_name != null)
		{
			return new String(message_name);
		}
		else{
			return null;
		}
	}
    /**
     * Accessor for the number of arguments in the message
     *
     * @return the length of the arguments ArrayList object
     */
	public int getArgumentsNum()
	{
		if(this.arguments != null)
		{
			return this.arguments.size();
		}
		else{
			return 0;
		}
	}
    /**
     * Accessor for individual arguments
     *
     * @param i the index of the argument element
     *
     * @return the element corresponding with the passed index
     */
	public String getArgument(int i)
	{
		if(this.arguments != null)
		{
			return this.arguments.get(i);
		}
		else{
			return null;
		}
	}
	/*public ArrayList getAllArguments()
	{
		if(this.arguments != null)
		{
			return new ArrayList(this.arguments);
		}
		else{
			return null;
		}
	}*/
    /**
     * Tokenizes string into message_name and arguments
     *
     */
	private void parseMessage()
	{
		StringTokenizer tokenizer;
		if(this.message != null)
		{
			tokenizer = new StringTokenizer(this.message,";");
			if(tokenizer.countTokens() >= 1)
			{
				this.message_name = tokenizer.nextToken();
				this.arguments = new ArrayList<String>();
				while(tokenizer.hasMoreTokens())
				{
					this.arguments.add(tokenizer.nextToken());
				}
			}
		}
	}
    /**
     * Prints a description of the message to System.out
     *
     */
	public void printMessage()
	{
		System.out.println("Name: "+this.message_name);
		System.out.println("Arguments:");
		for(int i = 0; i < arguments.size(); i++)
		{
			System.out.println(arguments.get(i));
		}
	}
}