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
            client_out.writeBytes("registered:1;\n");
            //client_out.writeBytes("shore:3:100:200:300:200:200:300;");	
            client_out.writeBytes("shore:3:360:430:380:410:380:430;\n");
            client_out.writeBytes("shore:5:420:410:430:390:440:410:440:430:420:430;\n");
            client_out.writeBytes("shore:x;\n");
            client_out.writeBytes("start;\n");
            client_out.writeBytes("ship:1:0;\n");
            client_out.writeBytes("ship:2:2;\n");
            client_out.writeBytes("ship:3:2;\n");
            client_out.writeBytes("ship:4:2;\n");
            client_out.writeBytes("ship:5:2;\n");
            client_out.writeBytes("shipState:5:600:250:0:0:0;\n");
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            client_out.writeBytes("shipState:1:400:450:1:45:0;\n");
            client_out.writeBytes("shipState:2:400:450:2:33:0;\n");
            client_out.writeBytes("shipState:3:400:450:3:206:0;\n");
            client_out.writeBytes("shipState:4:400:450:4:349:0;\n");
            try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            client_out.writeBytes("wind:0:225;\n");
    //		client_out.writeBytes("gameover;");*/
            client_out.close();
            connection.close();
		}
		catch(IOException e)
		{

		}
	}
}