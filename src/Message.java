/**
 * @author      Bonne Justin jbonne@uoguelph.ca
 * @author      Cardinal, Blake bcardina@uoguelph.ca
 * @author      Chabot, Chad chabot@uoguelph.ca
 * @version     0.9                 
 * @since       2011-11-29
 */

import java.util.EventObject;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Message extends EventObject
{
	
	private String              message;
    private String              message_name	= null;
	private ArrayList<String>   arguments		= null;

    /**
     * Class constructor automatically parses message 
     * into message name and arguments upon creation.
     *
     * @param pSource reference to the source object
     * @param message a message
     */
    public Message( Object pSource, String pMessage )
	{
        super( pSource );
        this.message = pMessage;
		this.parseMessage();
    }
	
    /**
     * Accessor for the orignal message
     *
     * @return a copy of the message string
     */
  	public String getMessage( )
	{
		if( this.message != null )
		{
			return new String( this.message );
		}
		else
        {
			return null;
		}
	}
	
    /**
     * Accessor for the message name
     *
     * @return a copy of the message name string
     */
	public String getMessageName( )
	{
		if( this.message_name != null )
		{
			return new String( this.message_name );
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
	public int getArgumentsNum( )
	{
		if( this.arguments != null )
		{
			return this.arguments.size( );
		}
		else{
			return 0;
		}
	}
	
    /**
     * Accessor for individual arguments
     *
     * @param pI the index of the argument element
     *
     * @return the element at the corresponding index in the message
     */
	public String getArgument( int pI )
	{
		if( this.arguments != null )
		{
			return this.arguments.get( pI );
		}
		else{
			return null;
		}
	}
	
    /**
     * Accessor for all arguments in a message
     *
     * @return An ArrayList with all arguments in the message
     */	
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
	private void parseMessage( )
	{
        String sansSemi = this.message.substring(0, this.message.length() - 1); 
        /*String[] splitsVille = sansSemi.split(":");
        this.arguments = new ArrayList<String>( );
        for( int i = 0; i < splitsVille.length; i++)
        {
            if(i == 0)
            {
                this.message_name = splitsVille[i];
            }
            else
            {
                this.arguments.add(splitsVille[i];
            }
        }*/
		StringTokenizer mTokenizer;
		if( this.message != null )
		{ 
			mTokenizer = new StringTokenizer( sansSemi, ":" );
			if( mTokenizer.countTokens() >= 1 )
			{
				this.message_name = mTokenizer.nextToken( );
				this.arguments = new ArrayList<String>( );
				while( mTokenizer.hasMoreTokens( ) )
				{
					this.arguments.add(mTokenizer.nextToken( ) );
				}
			}
		}
	}
    /**
     * Echoes a description of the message to System.out so the client knows
	 * when messages have been received.
     *
     */
	public void printMessage( )
	{
		System.out.println( "Name: " + this.message_name );
		System.out.println( "Arguments:" );
        
		for(int i = 0; i < arguments.size(); i++)
		{
			System.out.println( this.arguments.get( i ) );
		}
	}
}