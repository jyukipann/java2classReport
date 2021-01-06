import javax.swing.*;
import java.util.TimerTask;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.applet.*;

public class Sample extends JFrame
{
	public static void main( String[ ] args ){
		Sample s = new Sample( );
		s.setSize( 300, 300 );
		s.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		s.setVisible( true );
	}

	private JButton ss_button;
	private boolean ss_bool = false;
	private JLabel time_label;
	private Timer timer;
	private JButton reset_button;
	private AudioClip button_push;
	private int sec = 30*60;
	public Sample(){
		setLayout( new FlowLayout());
		time_label = new JLabel("time");
		ss_button = new JButton( "start" );
		reset_button = new JButton("reset");
		timer = new Timer(1000, new timer_ss_countdown());
		button_push = Applet.newAudioClip(getClass( ).getResource("se_maoudamashii_onepoint09.wav"));
		ss_button.addActionListener( new ss_button_Listener());
		reset_button.addActionListener(new reset_button_Listener());
		add(time_label);
		add( ss_button );
		add(reset_button);
	}

	String switch_ss_label(){
		ss_bool = !ss_bool;
		if(ss_bool){
			timer.start();
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
			String hms = JOptionPane.showInputDialog( null, "00:00‚ÌŒ`Ž®‚Å“ü—Í" );
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
			}else{
				sec--;
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