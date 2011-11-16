public class Game implements MessageListener
{
	public Communication comm;
	public Game()
	{
		
	}
	public void handleMessage(Message message)
	{
		message.printMessage();
	}
	public static void main(String[] args)
	{
		Game game = new Game();
		System.out.println("sdfsf");
		Thread theThread = new Thread(game.comm = new Communication(game, "localhost", 7430));
		theThread.start();
	}
}