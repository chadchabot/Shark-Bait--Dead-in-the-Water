/**
 * @author      Bonne Justin jbonne@uoguelph.ca
 * @version     0.1                 
 * @since       2011-11-13
 */
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

class Communication implements Runnable
{
	Socket connection;
	String host = null;
	int port = 0;
	
	MessageListener listener;
	BufferedReader server_input;
	DataOutputStream server_output;
    
    /**
     * Class constructor sets up a MessageListener
     *
     * @param listener listener that will receive all messages
     */
	public Communication(MessageListener listener)
	{
		this.listener = listener;
	}
    /**
     * Class constructor sets up a MessageListener and opens connection
     *
     * @param listener listener that will receive all messages
     * @param host the host to connect to
     * @param port the port to connect on
     */
	public Communication(MessageListener listener, String host, int port)
	{
		this.listener = listener;
		connect(host, port);
	}
    /**
     * Sets the objects host and port attributes as well as opening
     * a connection to the host on the specified port.
     *
     * Use {@link #connect()} to make connection.
     *
     * @param host a string containing the address of the host
     * @param port the port that will be used for the connection
     * 
     * @return true if the connection is made without error, otherwise false
     */
	public boolean connect(String host, int port){
		this.host = host;
		this.port = port;
		return connect();
	}
    /**
     * Uses already specified host and port attribute to connect to 
     *
     * 
     * @return true if the connection is made without error, otherwise false
     */
	public boolean connect(){
		if(this.host != null && this.port != 0){
			try{
				this.connection = new Socket(this.host, this.port);
				
				this.server_input = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
				
				this.server_output = new DataOutputStream(
					connection.getOutputStream());
			}
			catch(IOException e){
				//HOW ARE WE HANDLING THIS
				System.out.println("Derp");
				this.connection = null;
				this.server_input = null;
				this.server_output = null;
				return false;
			}
			return true;
		}
		else{
			return false;
		}
	}
    /**
     * Disconnects all objects associated with the server
     *
     */
	public void disconnect(){
		try
		{
			this.server_input.close();
			this.server_output.close();
			this.connection.close();
		}
		catch(IOException e)
		{
			System.out.println("Derp");
			//HANDLE THISSSSS
		}
	}
    /**
     * Uses an open connection to listen indefintely for 
     * incoming messages
     *
     */
	public void listen(){
		String incoming;
		try
		{
			while(true)
			{
				incoming = server_input.readLine();
				if(incoming != null)
				{
					fireMessage(incoming);
				}
			}
		}
		catch (IOException e)
		{	
			System.out.println("Derp");
		}
	}
	/**
     * Overrides the run method of the runnable class (used for threading)
     * Uses @link{#listen()}.
     *
     */
	public void run()
	{
		this.listen();
	}
    /**
     * Uses an open connection to send messages.
     *
     * @param message message to be sent
     */
	public boolean sendMessage(String message)
	{
		try
		{
			this.server_output.writeBytes(message);
			return true;
		}
		catch(IOException e)
		{
			return false;
		}
	}
    /**
     * Sends messages to MessageListener class.
     *
     * @param incoming string received from the server (should have \n dropped)
     */
	private void fireMessage(String incoming){
		Message message = new Message(this, incoming);
		listener.handleMessage(message);
	}
}