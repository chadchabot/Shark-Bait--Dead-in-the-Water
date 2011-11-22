import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import java.awt.Color;
import java.awt.Dimension;

public class GUI extends JFrame
{
    private int xSize;
    private int ySize;
    private JLayeredPane main;
    public GUI()
    {
        this.xSize = 1024;
        this.ySize = 768;
        
        main = new JLayeredPane(); 
        main.setPreferredSize(new Dimension(this.xSize, this.ySize));
		main.setVisible(true);
		main.setLayout(null);
		this.getContentPane().add(main);
        
        setTitle("Shark Bait: Dead in the Water");
        setSize(this.xSize, this.ySize);
        setResizable(false);
		setBackground(Color.BLACK);
        Sprite water = new Sprite("water");
		main.add(water, new Integer(2));
    }
    public void addToLayer(Sprite something, int layer)
    {
        main.add(something, new Integer(layer));
        this.pack();
    }
}