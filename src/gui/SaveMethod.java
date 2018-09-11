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
 * 将保存的两种方法保存和另存分装成类，方便调用
 * 
 * @author Nancy
 * @version 1.0
 * @since jdk1.8
 */
public class SaveMethod extends JDialog {
	/**
	 * 保存文件
	 * 
	 * @param editArea 文本编辑区
	 * @param oldValue 存放编辑区原来的内容
	 * @param currentFile 当前文件名
	 * @param isNewFile 是否新文件
	 * @param statusLabel 状态栏标签
	 */
	public void Save(JTextArea editArea, String oldValue, File currentFile, boolean isNewFile, JLabel statusLabel) {
		editArea.requestFocus();
		if (isNewFile) {
			String str = null;
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setDialogTitle("保存");
			int result = fileChooser.showSaveDialog(this);
			if (result == JFileChooser.CANCEL_OPTION) {
				statusLabel.setText("您没有选择任何文件");
				return;
			}
			File saveFileName = fileChooser.getSelectedFile();
			if (saveFileName == null || saveFileName.getName().equals("")) {
				JOptionPane.showMessageDialog(this, "不合法的文件名", "不合法的文件名", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					FileWriter fw = new FileWriter(saveFileName);
					BufferedWriter bfw = new BufferedWriter(fw);
					bfw.write(editArea.getText(), 0, editArea.getText().length());
					bfw.flush();// 刷新该流的缓冲
					bfw.close();
					isNewFile = false;
					currentFile = saveFileName;
					oldValue = editArea.getText();
					this.setTitle(saveFileName.getName() + " - 记事本");
					statusLabel.setText("当前打开文件：" + saveFileName.getAbsoluteFile());
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
	 * 另存文件
	 * 
	 * @param editArea 文本编辑区
	 * @param oldValue 存放编辑区原来的内容
	 * @param currentFile 当前文件名
	 * @param statusLabel 状态栏标签
	 */
	public void SaveAs(JTextArea editArea, String oldValue, File currentFile, JLabel statusLabel) {
		editArea.requestFocus();
		String str = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setDialogTitle("另存为");
		int result = fileChooser.showSaveDialog(this);
		if (result == JFileChooser.CANCEL_OPTION) {
			statusLabel.setText("　您没有选择任何文件");
			return;
		}
		File saveFileName = fileChooser.getSelectedFile();
		if (saveFileName == null || saveFileName.getName().equals("")) {
			JOptionPane.showMessageDialog(this, "不合法的文件名", "不合法的文件名", JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				FileWriter fw = new FileWriter(saveFileName);
				BufferedWriter bfw = new BufferedWriter(fw);
				bfw.write(editArea.getText(), 0, editArea.getText().length());
				bfw.flush();
				fw.close();
				oldValue = editArea.getText();
				this.setTitle(saveFileName.getName() + "  - 记事本");
				statusLabel.setText("　当前打开文件:" + saveFileName.getAbsoluteFile());
			} catch (IOException ioException) {
			}
		}
	}
}
