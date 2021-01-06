import javax.imageio.*;
import java.awt.*; 
import javax.swing.*;
import java.util.TimerTask;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.applet.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.*;


public class Sample extends JFrame
{
	public static void main( String[ ] args ){
		Sample s = new Sample("修行タイマー");
		s.setSize( 600, 600 );
		s.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		s.setVisible( true );
		s.setResizable(false);
	}

	private JButton ss_button;
	private boolean ss_bool = false;
	private JLabel time_label;
	private Timer timer;
	private JButton reset_button;
	private AudioClip button_push, time_up;
	private int sec = 30*60;
	private Image kyomu;
	private float angle = 0;
	private int start_time = 0;
	private DrawCanvas background;
	private JPanel buttons;
	public Sample(String title){
		setTitle(title);
		setLayout(new BorderLayout());
		buttons = new JPanel(new FlowLayout());
		time_label = new JLabel("time");
		ss_button = new JButton( "start" );
		reset_button = new JButton("reset");
		background = new DrawCanvas();
		background.setPreferredSize(new Dimension(300, 300));
		timer = new Timer(1000, new timer_ss_countdown());
		button_push = Applet.newAudioClip(getClass( ).getResource("se_maoudamashii_onepoint09.wav"));
		time_up = Applet.newAudioClip(getClass( ).getResource("se_maoudamashii_jingle02.wav"));
		try{
			kyomu = ImageIO.read(new File("kyomu.jpg"));
			repaint();
		} catch(Exception ex){}
		ss_button.addActionListener( new ss_button_Listener());
		reset_button.addActionListener(new reset_button_Listener());
		buttons.add(ss_button);
		buttons.add(reset_button);
		add(time_label,BorderLayout.CENTER);
		add(background,BorderLayout.WEST);
		add(buttons,BorderLayout.SOUTH);
	}

	//https://nompor.com/2017/12/08/post-1695/
	class DrawCanvas extends JPanel{
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D)g;
			AffineTransform af = g2d.getTransform();
			af.setToRotation(Math.toRadians(angle), kyomu.getWidth(this)/2, kyomu.getHeight(this)/2);
			System.out.println(angle);
			af.setToScale(0.3,0.3);
			g2d.setTransform(af);
			g2d.drawImage(kyomu, af, this);
		}
	}

	String switch_ss_label(){
		ss_bool = !ss_bool;
		if(ss_bool){
			timer.start();
			start_time = sec;
			return "stop";
		}else{
			timer.stop();
			return "start";
		}
	}

	class ss_button_Listener implements ActionListener{
		public void actionPerformed( ActionEvent e )
		{
			button_push.play();
			ss_button.setText(switch_ss_label());
		}
	}

	void set_time_label(int h,int m,int s){
		if(h != 0){
			time_label.setText(String.format("%02d:%02d:%02d",h,m,s));
		}else{
			time_label.setText(String.format("%02d:%02d",m,s));
		}
	}

	int hm2sec_andSetLabel(String ms){
		String[] hour_min_sec = ms.split(":", -1);
		int hour = 0, min = 0, sec = 0;
		if(hour_min_sec.length == 3){
			try{
				hour = Integer.parseInt(hour_min_sec[0]);
			} catch( Exception ex ){}
			try{
				min = Integer.parseInt(hour_min_sec[1]);
			} catch( Exception ex ){}
			try{
				sec = Integer.parseInt(hour_min_sec[2]);
			} catch( Exception ex ){}
		}else if(hour_min_sec.length == 2){
			try{
				min = Integer.parseInt(hour_min_sec[0]);
			} catch( Exception ex ){}
			try{
				sec = Integer.parseInt(hour_min_sec[1]);
			} catch( Exception ex ){}
		}
		set_time_label(hour,min,sec);
		return hour*3600+min*60+sec;
	}

	class reset_button_Listener implements ActionListener{
		public void actionPerformed( ActionEvent e){
			button_push.play();
			ss_bool = true;
			ss_button.setText(switch_ss_label());
			timer.stop();
			String hms = JOptionPane.showInputDialog(null, "00:00の形式で入力");
			button_push.play();
			if(hms != null){
				sec = hm2sec_andSetLabel(hms);
			}else{
				sec = 30*60;
				set_time_label(0,30,0);
			}
		}
	}

	class timer_ss_countdown implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (sec <= 0){
				ss_bool = true;
				ss_button.setText(switch_ss_label());
				timer.stop();
				time_up.play();
			}else{
				sec--;
				angle = (float)(start_time-sec)/start_time*360;
				repaint();
			}
			int s = sec;
			int h = s/3600;
			s -= h*3600;
			int m = s/60;
			s -= m*60;
			set_time_label(h,m,s);
		}
	}
}