import javax.imageio.*;
import java.awt.*; 
import javax.swing.*;
import java.util.TimerTask;
import java.awt.event.*;
import java.applet.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sample extends JFrame
{
	public static void main( String[ ] args ){
		Sample s = new Sample("修行タイマー");
		s.setSize( 400, 300 );
		s.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		s.setVisible( true );
		s.setFocusTraversalKeysEnabled(false);
		s.setResizable(false);
	}

	private JButton ss_button;
	private boolean ss_bool = false;
	private JLabel time_label;
	private Timer timer;
	private JButton reset_button;
	private AudioClip button_push, time_up;
	private int sec = 30*60;
	private float angle = 0;
	private int start_time = 0;
	private DrawCanvas background;
	private JPanel buttons;
	private JPanel timerPanel;
	private JPanel bgms;
	private JRadioButton[] bgm_buttons;
	private ButtonGroup bgmgroup;
	private Clip bgm;
	private AudioInputStream audioIn;
	private JPanel target;
	private JButton target_set;
	private JLabel target_label;
	public Sample(String title){
		setFocusTraversalKeysEnabled(false);
		requestFocus(); 
		setTitle(title);
		setLayout(new BorderLayout());

		target = new JPanel(new FlowLayout());
		target_set = new JButton("set ");
		target_label = new JLabel();
		target_set.addActionListener(new set_target());
		target.add(target_label);
		target.add(target_set);

		timerPanel = new JPanel(new GridLayout(2,1));
		time_label = new JLabel("time");
		time_label.setPreferredSize(new Dimension(50, 50));
		time_label.setHorizontalAlignment(JLabel.CENTER);
		timerPanel.add(time_label);

		buttons = new JPanel(new FlowLayout());
		buttons.setFocusTraversalKeysEnabled(false);
		ss_button = new JButton( "start" );
		ss_button.addActionListener( new ss_button_Listener());
		ss_button.setMnemonic(KeyEvent.VK_S);
		reset_button = new JButton("reset");
		reset_button.addActionListener(new reset_button_Listener());
		reset_button.setMnemonic(KeyEvent.VK_R);
		buttons.add(ss_button);
		buttons.add(reset_button);
		timerPanel.add(buttons);

		background = new DrawCanvas();
		background.setPreferredSize(new Dimension(200, 200));
		timer = new Timer(1000, new timer_ss_countdown());

		bgm_buttons = new JRadioButton[3];
		bgms = new JPanel(new GridLayout(1,bgm_buttons.length+1));
		bgm_buttons[0] = new JRadioButton("None");
		bgm_buttons[1] = new JRadioButton("BGM 1");
		bgm_buttons[2] = new JRadioButton("BGM 2");
		bgmgroup = new ButtonGroup();
		for(int i = 0; i < bgm_buttons.length; i++){
			bgm_buttons[i].addActionListener(new bgm_buttons_Listener());
			bgm_buttons[i].setActionCommand(String.format("%d",i));
			bgms.add(bgm_buttons[i]);
			bgmgroup.add(bgm_buttons[i]);
			
		}

		button_push = Applet.newAudioClip(getClass( ).getResource("se_maoudamashii_onepoint09.wav"));
		time_up = Applet.newAudioClip(getClass( ).getResource("se_maoudamashii_jingle02.wav"));
		
		addKeyListener(new keyboardShortcuts());
		add(timerPanel,BorderLayout.CENTER);
		add(background,BorderLayout.WEST);
		add(bgms, BorderLayout.SOUTH);
		add(target,BorderLayout.NORTH);
	}

	class keyboardShortcuts extends KeyAdapter
	{
		public void keyPressed( KeyEvent e )
		{
			char key = e.getKeyChar();
			System.out.println(key);
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_UP:
					
					break;
				case KeyEvent.VK_DOWN:
					
					break;
				case KeyEvent.VK_LEFT:
					
					break;
				case KeyEvent.VK_RIGHT:
					
					break;
				case KeyEvent.VK_R:
					System.out.println("R");
					_reset();
					break;
				case KeyEvent.VK_SPACE:
					switch_ss_label();
					break;
			}
		}
	}

	//https://nompor.com/2017/12/08/post-1695/
	class DrawCanvas extends JPanel{
		Image kyomu = Toolkit.getDefaultToolkit().getImage("kyomu (1).jpg");
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			AffineTransform at = g2.getTransform();
			//at.setToScale(0.25,0.25);
			at.setToRotation(Math.toRadians(angle), kyomu.getWidth(this)/2, kyomu.getHeight(this)/2);
			g2.setTransform(at);
			g2.drawImage(kyomu, 0,0, this);
		}
	}

	void switch_ss_label(){
		button_push.play();
		ss_bool = !ss_bool;
		if(ss_bool){
			timer.start();
			start_time = sec;
			ss_button.setText("stop");
		}else{
			timer.stop();
			ss_button.setText("start");
		}
	}

	class ss_button_Listener implements ActionListener{
		public void actionPerformed( ActionEvent e )
		{
			switch_ss_label();
		}
	}

	class set_target implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String target = JOptionPane.showInputDialog(null, "修行の内容をセット");
			target_label.setText(target);
		}
	}

	//http://akameco.hatenablog.com/entry/2014/12/26/070721
	class bgm_buttons_Listener implements ActionListener{
		private String[] bgmsClip = {"","maou_11_soreha_shinkiro_datta.wav","bgm_maoudamashii_healing13.wav"};
		public void actionPerformed( ActionEvent e )
		{
			int bgmN = Integer.parseInt(bgmgroup.getSelection().getActionCommand());
			if(bgmN == 0){
				try{
					bgm.stop();
				}catch(Exception ex){}
			}else{
				try {
					audioIn = AudioSystem.getAudioInputStream(new File(bgmsClip[bgmN]));
					bgm = AudioSystem.getClip();
					bgm.open(audioIn);
					bgm.setFramePosition(0);
					bgm.loop(-1);
				}catch (Exception ex){}
				//System.out.println(bgmsClip[bgmN]);
			}
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

	void _reset(){
		button_push.play();
		ss_bool = true;
		switch_ss_label();
		timer.stop();
		String hms = JOptionPane.showInputDialog(null, "00:00の形式で入力");
		button_push.play();
		if(hms != null){
			sec = hm2sec_andSetLabel(hms);
		}else{
			sec = 30*60;
			set_time_label(0,30,0);
		}
		angle = 0;
		repaint();
		try{
			ss_button.requestFocus();
		}catch(Exception ex){}
		
	}

	class reset_button_Listener implements ActionListener{
		public void actionPerformed( ActionEvent e){
			_reset();
		}
	}

	class timer_ss_countdown implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (sec <= 0){
				ss_bool = true;
				switch_ss_label();
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