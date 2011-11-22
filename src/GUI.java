import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JComponent;

public class GUI extends JFrame
{
    private int xSize;
    private int ySize;
    public JLayeredPane main;
    public GUI()
    {
        this.xSize = 1024;
        this.ySize = 768;
        
        this.main = new JLayeredPane(); 
        this.main.setPreferredSize(new Dimension(this.xSize, this.ySize));
		this.main.setVisible(true);
		this.main.setLayout(null);
		//this.getContentPane().add(main);
        
        setTitle("Shark Bait: Dead in the Water");
        setSize(this.xSize, this.ySize);
        setResizable(false);
		setBackground(Color.GRAY);
        
    }
    public void addToLayer(JComponent something, int layer)
    {
        this.main.add(something, new Integer(layer));
    }
}