import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.IOException;

public class Server
{
	public static void main(String[] args)
	{
		try
		{
		ServerSocket server = new ServerSocket(7430);
		System.out.println("Waiting");
		Socket connection = server.accept();
		System.out.println("Accepted");
		DataOutputStream client_out = new DataOutputStream(connection.getOutputStream());
		client_out.writeBytes("fire:1:3;");
		client_out.writeBytes("gameover;");
		client_out.close();
		connection.close();
		}
		catch(IOException e)
		{

		}
	}
}