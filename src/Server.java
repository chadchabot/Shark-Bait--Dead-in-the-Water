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
            client_out.writeBytes("registered:1;");
            client_out.writeBytes("shore:3:100:200:300:200:200:300;");	
            client_out.writeBytes("shore:x;");
            client_out.writeBytes("start;");
            client_out.writeBytes("ship:1:0;");
            try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            client_out.writeBytes("shipState:1:300:0:5:180:0;");
            try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            client_out.writeBytes("shipState:1:0:300:0:0:0;");
    //		client_out.writeBytes("gameover;");
            client_out.close();
            connection.close();
		}
		catch(IOException e)
		{

		}
	}
}