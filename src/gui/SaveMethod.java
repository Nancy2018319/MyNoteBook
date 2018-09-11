package gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * ����������ַ������������װ���࣬�������
 * 
 * @author Nancy
 * @version 1.0
 * @since jdk1.8
 */
public class SaveMethod extends JDialog {
	/**
	 * �����ļ�
	 * 
	 * @param editArea �ı��༭��
	 * @param oldValue ��ű༭��ԭ��������
	 * @param currentFile ��ǰ�ļ���
	 * @param isNewFile �Ƿ����ļ�
	 * @param statusLabel ״̬����ǩ
	 */
	public void Save(JTextArea editArea, String oldValue, File currentFile, boolean isNewFile, JLabel statusLabel) {
		editArea.requestFocus();
		if (isNewFile) {
			String str = null;
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setDialogTitle("����");
			int result = fileChooser.showSaveDialog(this);
			if (result == JFileChooser.CANCEL_OPTION) {
				statusLabel.setText("��û��ѡ���κ��ļ�");
				return;
			}
			File saveFileName = fileChooser.getSelectedFile();
			if (saveFileName == null || saveFileName.getName().equals("")) {
				JOptionPane.showMessageDialog(this, "���Ϸ����ļ���", "���Ϸ����ļ���", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					FileWriter fw = new FileWriter(saveFileName);
					BufferedWriter bfw = new BufferedWriter(fw);
					bfw.write(editArea.getText(), 0, editArea.getText().length());
					bfw.flush();// ˢ�¸����Ļ���
					bfw.close();
					isNewFile = false;
					currentFile = saveFileName;
					oldValue = editArea.getText();
					this.setTitle(saveFileName.getName() + " - ���±�");
					statusLabel.setText("��ǰ���ļ���" + saveFileName.getAbsoluteFile());
				} catch (IOException ioException) {
				}
			}
		} else {
			try {
				FileWriter fw = new FileWriter(currentFile);
				BufferedWriter bfw = new BufferedWriter(fw);
				bfw.write(editArea.getText(), 0, editArea.getText().length());
				bfw.flush();
				fw.close();
			} catch (IOException ioException) {
			}
		}
	}

	/**
	 * ����ļ�
	 * 
	 * @param editArea �ı��༭��
	 * @param oldValue ��ű༭��ԭ��������
	 * @param currentFile ��ǰ�ļ���
	 * @param statusLabel ״̬����ǩ
	 */
	public void SaveAs(JTextArea editArea, String oldValue, File currentFile, JLabel statusLabel) {
		editArea.requestFocus();
		String str = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setDialogTitle("���Ϊ");
		int result = fileChooser.showSaveDialog(this);
		if (result == JFileChooser.CANCEL_OPTION) {
			statusLabel.setText("����û��ѡ���κ��ļ�");
			return;
		}
		File saveFileName = fileChooser.getSelectedFile();
		if (saveFileName == null || saveFileName.getName().equals("")) {
			JOptionPane.showMessageDialog(this, "���Ϸ����ļ���", "���Ϸ����ļ���", JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				FileWriter fw = new FileWriter(saveFileName);
				BufferedWriter bfw = new BufferedWriter(fw);
				bfw.write(editArea.getText(), 0, editArea.getText().length());
				bfw.flush();
				fw.close();
				oldValue = editArea.getText();
				this.setTitle(saveFileName.getName() + "  - ���±�");
				statusLabel.setText("����ǰ���ļ�:" + saveFileName.getAbsoluteFile());
			} catch (IOException ioException) {
			}
		}
	}
}
