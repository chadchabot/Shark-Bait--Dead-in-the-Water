public class TestGame implements MessageListener
{
	public Communication comm;
	public TestGame()
	{
		
	}
	public void handleMessage(Message message)
	{
		message.printMessage();
	}
	public static void main(String[] args)
	{
		TestGame game = new TestGame();
		System.out.println("sdfsf");
		Thread theThread = new Thread(game.comm = new Communication(game, "localhost", 7430));
		theThread.start();
	}
}