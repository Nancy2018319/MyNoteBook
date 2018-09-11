package gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.undo.UndoManager;

/**
 * �����ļ������ַ�����װ����
 * 
 * @author Nancy
 * @version 1.0
 * @since jdk1.8
 */
public class OpenMethod extends JDialog {
	/**
	 * �½��ļ�
	 * 
	 * @param editArea �ı��༭��
	 * @param oldValue ��ű༭��ԭ��������
	 * @param currentFile ��ǰ�ļ���
	 * @param isNewFile �Ƿ����ļ�
	 * @param statusLabel ״̬����ǩ
	 * @param undo ����������������һ������
	 * @param editMenu_Undo �����˵�
	 */
	public void openNewFile(JTextArea editArea, String oldValue, File currentFile, boolean isNewFile,
			JLabel statusLabel, UndoManager undo, JMenuItem editMenu_Undo) {
		editArea.requestFocus();
		String currentValue = editArea.getText();
		boolean isTextChange = (currentValue.equals(oldValue)) ? false : true;
		if (isTextChange) {
			int saveChoose = JOptionPane.showConfirmDialog(this, "�����ļ���δ���棬�Ƿ񱣴棿", "��ʾ",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (saveChoose == JOptionPane.YES_OPTION) {
				String str = null;
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setDialogTitle("���Ϊ");
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
			} else if (saveChoose == JOptionPane.NO_OPTION) {
				editArea.replaceRange("", 0, editArea.getText().length());
				statusLabel.setText(" �½��ļ�");
				this.setTitle("�ޱ��� - ���±�");
				isNewFile = true;
				undo.discardAllEdits(); // �������е�"����"����
				editMenu_Undo.setEnabled(false);
				oldValue = editArea.getText();
			} else if (saveChoose == JOptionPane.CANCEL_OPTION) {
				return;
			}
		} else {
			editArea.replaceRange("", 0, editArea.getText().length());
			statusLabel.setText(" �½��ļ�");
			this.setTitle("�ޱ��� - ���±�");
			isNewFile = true;
			undo.discardAllEdits();// �������е�"����"����
			editMenu_Undo.setEnabled(false);
			oldValue = editArea.getText();
		}
	}

	/**
	 * ���ļ�
	 * 
	 * @param editArea �ı��༭��
	 * @param oldValue ��ű༭��ԭ��������
	 * @param currentFile ��ǰ�ļ���
	 * @param isNewFile �Ƿ����ļ�
	 * @param statusLabel ״̬����ǩ
	 */
	void openFile(JTextArea editArea, String oldValue, File currentFile, boolean isNewFile, JLabel statusLabel) {
		editArea.requestFocus();
		String currentValue = editArea.getText();
		boolean isTextChange = (currentValue.equals(oldValue)) ? false : true;
		if (isTextChange) {
			int saveChoose = JOptionPane.showConfirmDialog(this, "�����ļ���δ���棬�Ƿ񱣴棿", "��ʾ",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (saveChoose == JOptionPane.YES_OPTION) {
				String str = null;
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				// fileChooser.setApproveButtonText("ȷ��");
				fileChooser.setDialogTitle("���Ϊ");
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
			} else if (saveChoose == JOptionPane.NO_OPTION) {
				String str = null;
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				// fileChooser.setApproveButtonText("ȷ��");
				fileChooser.setDialogTitle("���ļ�");
				int result = fileChooser.showOpenDialog(this);
				if (result == JFileChooser.CANCEL_OPTION) {
					statusLabel.setText("��û��ѡ���κ��ļ�");
					return;
				}
				File fileName = fileChooser.getSelectedFile();
				if (fileName == null || fileName.getName().equals("")) {
					JOptionPane.showMessageDialog(this, "���Ϸ����ļ���", "���Ϸ����ļ���", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						FileReader fr = new FileReader(fileName);
						BufferedReader bfr = new BufferedReader(fr);
						editArea.setText("");
						while ((str = bfr.readLine()) != null) {
							editArea.append(str);
						}
						this.setTitle(fileName.getName() + " - ���±�");
						statusLabel.setText(" ��ǰ���ļ���" + fileName.getAbsoluteFile());
						fr.close();
						isNewFile = false;
						currentFile = fileName;
						oldValue = editArea.getText();
					} catch (IOException ioException) {
					}
				}
			} else {
				return;
			}
		} else {
			String str = null;
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			// fileChooser.setApproveButtonText("ȷ��");
			fileChooser.setDialogTitle("���ļ�");
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.CANCEL_OPTION) {
				statusLabel.setText(" ��û��ѡ���κ��ļ� ");
				return;
			}
			File fileName = fileChooser.getSelectedFile();
			if (fileName == null || fileName.getName().equals("")) {
				JOptionPane.showMessageDialog(this, "���Ϸ����ļ���", "���Ϸ����ļ���", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					FileReader fr = new FileReader(fileName);
					BufferedReader bfr = new BufferedReader(fr);
					editArea.setText("");
					while ((str = bfr.readLine()) != null) {
						editArea.append(str);
					}
					this.setTitle(fileName.getName() + " - ���±�");
					statusLabel.setText(" ��ǰ���ļ���" + fileName.getAbsoluteFile());
					fr.close();
					isNewFile = false;
					currentFile = fileName;
					oldValue = editArea.getText();
				} catch (IOException ioException) {
				}
			}
		}
	}
}
