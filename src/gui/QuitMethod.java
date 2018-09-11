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
 * ���˳��ķ������з�װ���������
 * 
 * @author Nancy
 * @version 1.0
 * @since jdk1.8
 */
public class QuitMethod extends JDialog {
	/**
	 * �˳�����,�˳�ʱ�����ж��Ƿ���Ҫ����
	 * 
	 * @param editArea �ı��༭��
	 * @param oldValue ��ű༭��ԭ��������
	 * @param currentFile ��ǰ�ļ���
	 * @param isNewFile �Ƿ����ļ�
	 * @param statusLabel ״̬����ǩ
	 */
	public void exitWindowChoose(JTextArea editArea, String oldValue, File currentFile, boolean isNewFile,
			JLabel statusLabel) {
		editArea.requestFocus();
		String currentValue = editArea.getText();
		if (currentValue.equals(oldValue) == true) {
			System.exit(0);
		} else {
			int exitChoose = JOptionPane.showConfirmDialog(this, "�����ļ���δ���棬�Ƿ񱣴棿", "�˳���ʾ",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (exitChoose == JOptionPane.YES_OPTION) { // boolean isSave=false;
				if (isNewFile) {
					String str = null;
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					fileChooser.setApproveButtonText("ȷ��");
					fileChooser.setDialogTitle("���Ϊ");
					int result = fileChooser.showSaveDialog(this);
					if (result == JFileChooser.CANCEL_OPTION) {
						statusLabel.setText("����û�б����ļ�");
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
							isNewFile = false;
							currentFile = saveFileName;
							oldValue = editArea.getText();
							this.setTitle(saveFileName.getName() + "  - ���±�");
							statusLabel.setText("����ǰ���ļ�:" + saveFileName.getAbsoluteFile());
							// isSave=true;
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
						// isSave=true;
					} catch (IOException ioException) {
					}
				}
				System.exit(0);
				// if(isSave)System.exit(0);
				// else return;
			} else if (exitChoose == JOptionPane.NO_OPTION) {
				System.exit(0);
			} else {
				return;
			}
		}
	}
}
