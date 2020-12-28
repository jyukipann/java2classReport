/*
import javax.swing.*;
import java.awt.event.*;

public class Sample extends JFrame
{
	public static void main( String[ ] args )
	{
		Sample s = new Sample( );
		s.setSize( 200, 100 );
		s.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		s.setVisible( true );
	}

	private JLabel lb;
	private String lb_text = "";
	private JButton exe_button;
	public Sample()
	{
		//setLayout( new FlowLayout( ) );
		setLayout( new GridLayout( 1, 3 ) );
		lb = new JLabel("");
		exe_button = new JButton("execute");
		add(lb);
		add(exe_button);
		addKeyListener( new SampleKeyListener( ) );
	}

	class SampleKeyListener extends KeyAdapter
	{
		public void keyPressed( KeyEvent e )
		{
			char keyChar = e.getKeyChar( );
			String key = String.valueOf( keyChar );
			String upperKey = key.toUpperCase( );
			lb_text += upperKey;
			lb.setText(lb_text);
		}
	}
}
*/
import javax.swing.*;
import java.awt.*;
public class Sample extends JFrame
{
	public static void main( String[ ] args )
	{
		Sample s = new Sample( );
		s.setSize( 400, 300 );
		s.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		s.setVisible( true );
	}
	public Sample( )
	{
		setLayout( new BorderLayout( ) );

		JMenuBar mb = new JMenuBar( );
		JMenu m1 = new JMenu( "File" );
		m1.add( new JMenuItem( "Open" ) ); m1.add( new JMenuItem( "Save" ) ); m1.add( new JMenuItem( "Close" ) );
		JMenu m2 = new JMenu( "Edit" ); m2.add( new JMenuItem( "Copy" ) ); m2.add( new JMenuItem( "Paste" ) );
		JMenu m3 = new JMenu( "Help" );
		m3.add( new JMenuItem( "About" ) );
		mb.add( m1 ); mb.add( m2 ); mb.add( m3 );
		add( mb, BorderLayout.NORTH );

		JPanel pb = new JPanel( new GridLayout( ) );
		pb.add( new JLabel( "Not saved" ) ); pb.add( new JLabel( "Shift-JIS" ) ); pb.add( new JLabel( "CR-LF" ) );
		add( pb, BorderLayout.SOUTH );

		JPanel pc = new JPanel( new GridLayout( ) );
		pc.add( new JTextArea( ) );
		add( pc, BorderLayout.CENTER );
	}
}
