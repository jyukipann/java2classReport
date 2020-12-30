
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
 
		// ポイント３．ドロップすると1.で実装した「drop」が実行されます。
		// 「DropTargetDropEvent.acceptDrop」でドロップを受け取る準備をします。
		dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
		boolean flg = false;
		String str = "<html><pre>";
		try {
			// 「DropTargetDropEvent.getTransferable」で転送クラスを取得します。
			Transferable tr = dtde.getTransferable();
 
			// 「DropTargetDropEvent.isDataFlavorSupported」で、受け取り可能なフレーバーを調べます。
			// 標準では文字列用の「stringFlavor」、ファイル用の「javaFileListFlavor」、画像イメージ用の「imageFlavor」があります。
			if (dtde.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				// 文字列をドロップされた場合
				// ドロップされた文字列をラベルに表示します。
				str += "文字列をドロップされました。\n";
				// 「Transferable.getTransferData」でドロップされたオブジェクトを受け取ります。
				str += tr.getTransferData(DataFlavor.stringFlavor).toString();
				flg = true;
			} else if (dtde
					.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				// ファイルをドロップされた場合
				// ドロップされたファイルを文字列に入れてラベルに表示します。
				str += "ファイルをドロップされました。\n";
				// 「Transferable.getTransferData」でドロップされたオブジェクトを受け取ります。
				// ファイルは「List<File>」にキャストして操作するとよいです。
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
			// 「DropTargetDropEvent.dropComplete」で転送完了を通知して終了です。
			dtde.dropComplete(flg);
 
			if (flg) {
				// ドロップされたオブジェクトをJLabelに設定します。
				lb.setText(str);
			} else {
				// ドロップを受け取れなかった場合はこちらで。
				lb.setText("ドロップを受け取りできませんでした。");
			}
		}
	}
}