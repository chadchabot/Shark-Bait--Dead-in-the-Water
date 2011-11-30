import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.IOException;

public class ServerBoom
{
	public static void main(String[] args)
	{
		try
		{
            ServerSocket server = new ServerSocket(5283);
            System.out.println("Waiting");
            Socket connection = server.accept();
            System.out.println("Accepted");
            DataOutputStream client_out = new DataOutputStream(connection.getOutputStream());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
            client_out.writeBytes("registered:1;\n");
            //client_out.writeBytes("shore:3:100:200:300:200:200:300;");	
            client_out.writeBytes("shore:3:360:430:380:410:380:430;\n");
            client_out.writeBytes("shore:5:420:410:430:390:440:410:440:430:420:430;\n");
            client_out.writeBytes("shore:x;\n");
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
            client_out.writeBytes("start;\n");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            client_out.writeBytes("ship:1:0;\n");
            client_out.writeBytes("ship:2:1;\n");
            client_out.writeBytes("ship:3:2;\n");
            client_out.writeBytes("shipState:1:400:450:0:0:3.0;\n");
            client_out.writeBytes("shipState:2:450:450:0:0:4.0;\n");
            client_out.writeBytes("shipState:3:500:450:0:0:5.0;\n");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			client_out.writeBytes("shipState:1:400:450:0:0:2.5;\n");
            client_out.writeBytes("shipState:2:450:450:0:0:3.5;\n");
            client_out.writeBytes("shipState:3:500:450:0:0:4.5;\n");
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			client_out.writeBytes("shipState:1:400:450:0:0:2.0;\n");
            client_out.writeBytes("shipState:2:450:450:0:0:3.0;\n");
            client_out.writeBytes("shipState:3:500:450:0:0:4.0;\n");
            try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			client_out.writeBytes("shipState:1:400:450:0:0:1.5;\n");
            client_out.writeBytes("shipState:2:450:450:0:0:2.5;\n");
            client_out.writeBytes("shipState:3:500:450:0:0:3.5;\n");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			client_out.writeBytes("gameover;");
            client_out.close();
            connection.close();
		}
		catch(IOException e)
		{

		}
	}
}