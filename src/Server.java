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
            ServerSocket server = new ServerSocket(5283);
            System.out.println("Waiting");
            Socket connection = server.accept();
            System.out.println("Accepted");
            DataOutputStream client_out = new DataOutputStream(connection.getOutputStream());
			try {
				Thread.sleep(3000);
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
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
            client_out.writeBytes("start;\n");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            client_out.writeBytes("ship:1:0;\n");
            client_out.writeBytes("ship:2:2;\n");
            client_out.writeBytes("ship:3:2;\n");
            client_out.writeBytes("ship:4:2;\n");
            client_out.writeBytes("ship:5:2;\n");
            client_out.writeBytes("shipState:5:600:250:0:0:4.0;\n");
            try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			client_out.writeBytes("wind:270;\n");
            client_out.writeBytes("shipState:1:400:450:1:45:3.0;\n");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            client_out.writeBytes("shipState:1:400:450:1:45:1.5;\n");
            client_out.writeBytes("shipState:2:400:450:2:33:2.0;\n");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			client_out.writeBytes("shipState:1:400:450:1:45:2.0;\n");
            client_out.writeBytes("shipState:3:400:450:3:206:1.0;\n");
            client_out.writeBytes("shipState:4:400:450:4:349:0.3;\n");
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			client_out.writeBytes("shipState:1:400:450:1:45:0.5;\n");
            client_out.writeBytes("wind:0;\n");
			System.out.println( "wind:0;" ); 
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
            client_out.writeBytes("wind:270;\n");
			System.out.println( "wind:270;" ); 

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
            client_out.writeBytes("wind:193;\n");
			System.out.println( "wind:193;" ); 
			
			
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