import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
 
import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class Test{
	public static void main(String[] args) {
		GameWindow gw = new GameWindow("テストウィンドウ",400,300);
		DrawCanvas dc = new DrawCanvas();
		gw.add(dc);
		gw.setVisible(true);
	}
}
class GameWindow extends JFrame{
	public GameWindow(String title, int width, int height) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(width,height);
		setLocationRelativeTo(null);
		//setResizable(false);
	}
}

class DrawCanvas extends JPanel{
	Image kyomu = Toolkit.getDefaultToolkit().getImage("kyomu (1).jpg");
	int angle = 10;
	public void paintComponent(Graphics g){
		super.paintComponent(g);
 
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform at = g2.getTransform();

		//50度回転させます。
		at.setToScale(0.25,0.25);
		at.setToRotation(Math.toRadians(angle), kyomu.getWidth(this)/2*0.25, kyomu.getHeight(this)/2*0.25);
		g2.setTransform(at);
 
		g2.drawImage(kyomu, 0,0, this);
	}
}