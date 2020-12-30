import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.TransferHandler;

public class DropAndListFileName {

	private JFrame frame;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DropAndListFileName window = new DropAndListFileName();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DropAndListFileName() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		// �h���b�v�����L���ɂ���
		textArea.setTransferHandler(new DropFileHandler());
	}

	/**
	 * �h���b�v����̏������s���N���X
	 */
	private class DropFileHandler extends TransferHandler {

		/**
		 * �h���b�v���ꂽ���̂��󂯎�邩���f (�t�@�C���̂Ƃ������󂯎��)
		 */
		@Override
		public boolean canImport(TransferSupport support) {
			if (!support.isDrop()) {
				// �h���b�v����łȂ��ꍇ�͎󂯎��Ȃ�
		        return false;
		    }

			if (!support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				// �h���b�v���ꂽ�̂��t�@�C���łȂ��ꍇ�͎󂯎��Ȃ�
		        return false;
		    }

			return true;
		}

		/**
		 * �h���b�v���ꂽ�t�@�C�����󂯎��
		 */
		@Override
		public boolean importData(TransferSupport support) {
			// �󂯎���Ă������̂��m�F����
			if (!canImport(support)) {
		        return false;
		    }

			// �h���b�v����
			Transferable t = support.getTransferable();
			try {
				// �t�@�C�����󂯎��
				List files = (List) t.getTransferData(DataFlavor.javaFileListFlavor);

				// �e�L�X�g�G���A�ɕ\������t�@�C�������X�g���쐬����
				StringBuffer fileList = new StringBuffer();
				for (File file : files){
					fileList.append(file.getName());
					fileList.append("\n");
				}

				// �e�L�X�g�G���A�Ƀt�@�C�����̃��X�g��\������
				textArea.setText(fileList.toString());
			} catch (UnsupportedFlavorException | IOException e) {
				e.printStackTrace();
			}
			return true;
		}
	}
}