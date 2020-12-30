
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JFrame;
//import java.awt.GridLayout;

public class Sample extends JFrame implements DropTargetListener
{
	public static void main( String[ ] args )
	{
		Sample s = new Sample( );
		s.setSize( 300, 300 );
		s.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		s.setVisible( true );
	}

	private JLabel lb;
	private String lb_text = "aaa";
	private JButton exe_button;
	private Image img;
	private JTextField get_path_text;
	//private List<File> dropppedFiles;
	public Sample()
	{
		new DropTarget(this, this);
		setLayout( new BorderLayout( ) );
		//setLayout( new GridLayout( 3, 3 ) );
		JPanel rightPanel = new JPanel( new GridLayout(0,1,10,10) );
		exe_button = new JButton("execute");
		rightPanel.add(exe_button);

		JPanel centerPanel = new JPanel( new GridLayout(0,1,10,10) );
		lb = new JLabel(lb_text);
		get_path_text = new JTextField(20);
		centerPanel.add(lb);
		centerPanel.add(get_path_text);

		//image_repaint();
		//dropppedFiles = (List<File>)transferable.getTransferData(DataFlavor.javaFileListFlavor);

		addKeyListener( new SampleKeyListener( ) );
		add(rightPanel,BorderLayout.EAST);
		add(centerPanel,BorderLayout.CENTER);
	}

	public void image_repaint(){
		try {
			img = ImageIO.read( new File( "kyomu.jpg" ));
			repaint();
		}
		catch( Exception ex ) { }
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

	public void paint( Graphics g ) { super.paint( g ); g.drawImage( img, 15, 35, null ); }
	
	@Override
	public void drop(DropTargetDropEvent dtde) {
 
		// �|�C���g�R�D�h���b�v�����1.�Ŏ��������udrop�v�����s����܂��B
		// �uDropTargetDropEvent.acceptDrop�v�Ńh���b�v���󂯎�鏀�������܂��B
		dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
		boolean flg = false;
		String str = "<html><pre>";
		try {
			// �uDropTargetDropEvent.getTransferable�v�œ]���N���X���擾���܂��B
			Transferable tr = dtde.getTransferable();
 
			// �uDropTargetDropEvent.isDataFlavorSupported�v�ŁA�󂯎��\�ȃt���[�o�[�𒲂ׂ܂��B
			// �W���ł͕�����p�́ustringFlavor�v�A�t�@�C���p�́ujavaFileListFlavor�v�A�摜�C���[�W�p�́uimageFlavor�v������܂��B
			if (dtde.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				// ��������h���b�v���ꂽ�ꍇ
				// �h���b�v���ꂽ����������x���ɕ\�����܂��B
				str += "��������h���b�v����܂����B\n";
				// �uTransferable.getTransferData�v�Ńh���b�v���ꂽ�I�u�W�F�N�g���󂯎��܂��B
				str += tr.getTransferData(DataFlavor.stringFlavor).toString();
				flg = true;
			} else if (dtde
					.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				// �t�@�C�����h���b�v���ꂽ�ꍇ
				// �h���b�v���ꂽ�t�@�C���𕶎���ɓ���ă��x���ɕ\�����܂��B
				str += "�t�@�C�����h���b�v����܂����B\n";
				// �uTransferable.getTransferData�v�Ńh���b�v���ꂽ�I�u�W�F�N�g���󂯎��܂��B
				// �t�@�C���́uList<File>�v�ɃL���X�g���đ��삷��Ƃ悢�ł��B
				List<File> list = (List<File>) tr.getTransferData(DataFlavor.javaFileListFlavor);
				for (File file : list) {
					str += file.getPath() + "\n";
				}
				flg = true;
			}
			str += "</pre></html>";
 
		} catch (UnsupportedFlavorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// �uDropTargetDropEvent.dropComplete�v�œ]��������ʒm���ďI���ł��B
			dtde.dropComplete(flg);
 
			if (flg) {
				// �h���b�v���ꂽ�I�u�W�F�N�g��JLabel�ɐݒ肵�܂��B
				lb.setText(str);
			} else {
				// �h���b�v���󂯎��Ȃ������ꍇ�͂�����ŁB
				lb.setText("�h���b�v���󂯎��ł��܂���ł����B");
			}
		}
	}
}