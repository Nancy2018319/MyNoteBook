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
 * 将打开文件的两种方法分装成类
 * 
 * @author Nancy
 * @version 1.0
 * @since jdk1.8
 */
public class OpenMethod extends JDialog {
	/**
	 * 新建文件
	 * 
	 * @param editArea 文本编辑区
	 * @param oldValue 存放编辑区原来的内容
	 * @param currentFile 当前文件名
	 * @param isNewFile 是否新文件
	 * @param statusLabel 状态栏标签
	 * @param undo 撤销操作管理器的一个对象
	 * @param editMenu_Undo 撤销菜单
	 */
	public void openNewFile(JTextArea editArea, String oldValue, File currentFile, boolean isNewFile,
			JLabel statusLabel, UndoManager undo, JMenuItem editMenu_Undo) {
		editArea.requestFocus();
		String currentValue = editArea.getText();
		boolean isTextChange = (currentValue.equals(oldValue)) ? false : true;
		if (isTextChange) {
			int saveChoose = JOptionPane.showConfirmDialog(this, "您的文件尚未保存，是否保存？", "提示",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (saveChoose == JOptionPane.YES_OPTION) {
				String str = null;
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setDialogTitle("另存为");
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
			} else if (saveChoose == JOptionPane.NO_OPTION) {
				editArea.replaceRange("", 0, editArea.getText().length());
				statusLabel.setText(" 新建文件");
				this.setTitle("无标题 - 记事本");
				isNewFile = true;
				undo.discardAllEdits(); // 撤消所有的"撤消"操作
				editMenu_Undo.setEnabled(false);
				oldValue = editArea.getText();
			} else if (saveChoose == JOptionPane.CANCEL_OPTION) {
				return;
			}
		} else {
			editArea.replaceRange("", 0, editArea.getText().length());
			statusLabel.setText(" 新建文件");
			this.setTitle("无标题 - 记事本");
			isNewFile = true;
			undo.discardAllEdits();// 撤消所有的"撤消"操作
			editMenu_Undo.setEnabled(false);
			oldValue = editArea.getText();
		}
	}

	/**
	 * 打开文件
	 * 
	 * @param editArea 文本编辑区
	 * @param oldValue 存放编辑区原来的内容
	 * @param currentFile 当前文件名
	 * @param isNewFile 是否新文件
	 * @param statusLabel 状态栏标签
	 */
	void openFile(JTextArea editArea, String oldValue, File currentFile, boolean isNewFile, JLabel statusLabel) {
		editArea.requestFocus();
		String currentValue = editArea.getText();
		boolean isTextChange = (currentValue.equals(oldValue)) ? false : true;
		if (isTextChange) {
			int saveChoose = JOptionPane.showConfirmDialog(this, "您的文件尚未保存，是否保存？", "提示",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (saveChoose == JOptionPane.YES_OPTION) {
				String str = null;
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				// fileChooser.setApproveButtonText("确定");
				fileChooser.setDialogTitle("另存为");
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
			} else if (saveChoose == JOptionPane.NO_OPTION) {
				String str = null;
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				// fileChooser.setApproveButtonText("确定");
				fileChooser.setDialogTitle("打开文件");
				int result = fileChooser.showOpenDialog(this);
				if (result == JFileChooser.CANCEL_OPTION) {
					statusLabel.setText("您没有选择任何文件");
					return;
				}
				File fileName = fileChooser.getSelectedFile();
				if (fileName == null || fileName.getName().equals("")) {
					JOptionPane.showMessageDialog(this, "不合法的文件名", "不合法的文件名", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						FileReader fr = new FileReader(fileName);
						BufferedReader bfr = new BufferedReader(fr);
						editArea.setText("");
						while ((str = bfr.readLine()) != null) {
							editArea.append(str);
						}
						this.setTitle(fileName.getName() + " - 记事本");
						statusLabel.setText(" 当前打开文件：" + fileName.getAbsoluteFile());
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
			// fileChooser.setApproveButtonText("确定");
			fileChooser.setDialogTitle("打开文件");
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.CANCEL_OPTION) {
				statusLabel.setText(" 您没有选择任何文件 ");
				return;
			}
			File fileName = fileChooser.getSelectedFile();
			if (fileName == null || fileName.getName().equals("")) {
				JOptionPane.showMessageDialog(this, "不合法的文件名", "不合法的文件名", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					FileReader fr = new FileReader(fileName);
					BufferedReader bfr = new BufferedReader(fr);
					editArea.setText("");
					while ((str = bfr.readLine()) != null) {
						editArea.append(str);
					}
					this.setTitle(fileName.getName() + " - 记事本");
					statusLabel.setText(" 当前打开文件：" + fileName.getAbsoluteFile());
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
