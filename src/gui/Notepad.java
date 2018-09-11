package gui;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import java.io.*;
import java.net.URISyntaxException;
import javax.swing.undo.*;
import javax.swing.border.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import java.awt.datatransfer.*;

/**
 * 需求：模仿Window自带的记事本，实现90%以上功能的类似记事本，记事本界面从总体布局上 分为菜单栏、工具栏、文本编辑区和状态栏。 
 * 分析：
 * 1、Notepad类继承了JFrame，ActionListener, DocumentListener类，用于创建图形化界面。
 * 2、由最小的组件开始创建，逐渐添加到他的上一级组件中，最终拼成一个完整的界面。 
 * 步骤： 
 * 1、引入需要用到的类。
 * 2、在main()中创建Notepad的对象，调用其构造函数，对其进行初始化。
 * 3、JMenuBar创建菜单栏，JMenu和JMenuItem创建其下拉菜单项，并将菜单项添加到JMenuBar中。 
 * 4、JToolBar创建工具栏。
 * 5、JTextArea创建文本编辑区，实现监听功能，并实现右键弹出菜单功能。
 * 6、JLabel创建状态栏，实现编辑状态的可视化。
 * 7、将这些组件放入容器Container中，组成完整的界面。 
 * 8、定义监听器，实现菜单栏、工具栏及文本编辑区执行相应操作时触发的事件。
 * 9、定义方法，实现监听器事件的处理方法。
 * 
 * @author Nancy
 * @version 1.0
 * @since jdk1.8
 */
public class Notepad extends JFrame implements ActionListener, DocumentListener {
	// 菜单
	JMenu fileMenu, editMenu, formatMenu, viewMenu, helpMenu;
	// 右键弹出菜单项
	JPopupMenu popupMenu;
	JMenuItem popupMenu_Undo, popupMenu_Cut, popupMenu_Copy, popupMenu_Paste, popupMenu_Delete, popupMenu_SelectAll;
	// “文件”的菜单项
	JMenuItem fileMenu_New, fileMenu_Open, fileMenu_Save, fileMenu_SaveAs, fileMenu_Exit;
	// “编辑”的菜单项
	JMenuItem editMenu_Undo, editMenu_Cut, editMenu_Copy, editMenu_Paste, editMenu_Delete, editMenu_Find,
			editMenu_Replace, editMenu_GoTo, editMenu_SelectAll, editMenu_TimeDate;
	// “格式”的菜单项
	JCheckBoxMenuItem formatMenu_LineWrap;
	JMenuItem formatMenu_Font;
	JMenuItem formatMenu_Color;
	// “查看”的菜单项
	JCheckBoxMenuItem viewMenu_Status, viewMenu_ToolBar;
	// "工具栏"按钮
	JButton newButton, openButton, saveButton, copyButton, cutButton, pasteButton;
	JPanel toolBar;
	// “帮助”的菜单项
	JMenuItem helpMenu_HelpTopics, helpMenu_AboutNotepad;
	// “文本”编辑区域
	JTextArea editArea;
	// 状态栏标签
	JLabel statusLabel;
	// JToolBar statusBar;
	// 系统剪贴板
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Clipboard clipBoard = toolkit.getSystemClipboard();
	// 创建撤销操作管理器(与撤销操作有关)
	protected UndoManager undo = new UndoManager();
	protected UndoableEditListener undoHandler = new UndoHandler();
	// 其他变量
	String oldValue;// 存放编辑区原来的内容，用于比较文本是否有改动
	boolean isNewFile = true;// 是否新文件(未保存过的)
	File currentFile;// 当前文件名

	// 无参构造函数开始
	public Notepad() {
		super("无标题 - 记事本");
		// 改变系统默认字体
		Font font = new Font("Dialog", Font.PLAIN, 12);
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, font);
			}
		}
		// ----------------------------设置图标
		Icon newIcon = new ImageIcon("src/gui/resource/new.gif");
		Icon openIcon = new ImageIcon("src/gui/resource/open.gif");
		Icon saveIcon = new ImageIcon("src/gui/resource/save.gif");
		Icon copyIcon = new ImageIcon("src/gui/resource/copy.gif");
		Icon cutIcon = new ImageIcon("src/gui/resource/cut.gif");
		Icon pasteIcon = new ImageIcon("src/gui/resource/paste.gif");
		// ----------------------------创建菜单条
		JMenuBar menuBar = new JMenuBar();
		// ----------------------------创建文件菜单及菜单项并注册事件监听
		fileMenu = new JMenu("文件(F)");
		fileMenu.setMnemonic('F');// 设置快捷键ALT+F

		fileMenu_New = new JMenuItem("新建(N)", newIcon);
		fileMenu_New.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		fileMenu_New.addActionListener(this);

		fileMenu_Open = new JMenuItem("打开(O)...", openIcon);
		fileMenu_Open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		fileMenu_Open.addActionListener(this);

		fileMenu_Save = new JMenuItem("保存(S)", saveIcon);
		fileMenu_Save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		fileMenu_Save.addActionListener(this);

		fileMenu_SaveAs = new JMenuItem("另存为(A)...");
		fileMenu_SaveAs.addActionListener(this);

		fileMenu_Exit = new JMenuItem("退出(X)");
		fileMenu_Exit.addActionListener(this);

		// ----------------------------创建编辑菜单及菜单项并注册事件监听
		editMenu = new JMenu("编辑(E)");
		editMenu.setMnemonic('E');// 设置快捷键ALT+E
		// 当选择编辑菜单时，设置剪切、复制、粘贴、删除等功能的可用性
		editMenu.addMenuListener(new MenuListener() {
			public void menuCanceled(MenuEvent e)// 取消菜单时调用
			{
				checkMenuItemEnabled();// 设置剪切、复制、粘贴、删除等功能的可用性
			}

			public void menuDeselected(MenuEvent e)// 取消选择某个菜单时调用
			{
				checkMenuItemEnabled();// 设置剪切、复制、粘贴、删除等功能的可用性
			}

			public void menuSelected(MenuEvent e)// 选择某个菜单时调用
			{
				checkMenuItemEnabled();// 设置剪切、复制、粘贴、删除等功能的可用性
			}
		});

		editMenu_Undo = new JMenuItem("撤销(U)");
		editMenu_Undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		editMenu_Undo.addActionListener(this);
		editMenu_Undo.setEnabled(false);

		editMenu_Cut = new JMenuItem("剪切(T)", cutIcon);
		editMenu_Cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		editMenu_Cut.addActionListener(this);

		editMenu_Copy = new JMenuItem("复制(C)", copyIcon);
		editMenu_Copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		editMenu_Copy.addActionListener(this);

		editMenu_Paste = new JMenuItem("粘贴(P)", pasteIcon);
		editMenu_Paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		editMenu_Paste.addActionListener(this);

		editMenu_Delete = new JMenuItem("删除(D)");
		editMenu_Delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		editMenu_Delete.addActionListener(this);

		editMenu_Find = new JMenuItem("查找(F)...");
		editMenu_Find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		editMenu_Find.addActionListener(this);

		editMenu_Replace = new JMenuItem("替换(R)...", 'R');
		editMenu_Replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
		editMenu_Replace.addActionListener(this);

		editMenu_GoTo = new JMenuItem("转到(G)...", 'G');
		editMenu_GoTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
		editMenu_GoTo.addActionListener(this);

		editMenu_SelectAll = new JMenuItem("全选", 'A');
		editMenu_SelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		editMenu_SelectAll.addActionListener(this);

		editMenu_TimeDate = new JMenuItem("时间/日期(D)", 'D');
		editMenu_TimeDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		editMenu_TimeDate.addActionListener(this);

		// ----------------------------创建格式菜单及菜单项并注册事件监听
		formatMenu = new JMenu("格式(O)");
		formatMenu.setMnemonic('O');// 设置快捷键ALT+O

		formatMenu_LineWrap = new JCheckBoxMenuItem("自动换行(W)");
		formatMenu_LineWrap.setMnemonic('W');// 设置快捷键ALT+W
		formatMenu_LineWrap.setState(true);
		formatMenu_LineWrap.addActionListener(this);

		formatMenu_Font = new JMenuItem("字体(F)...");
		formatMenu_Font.addActionListener(this);

		formatMenu_Color = new JMenuItem("颜色(C)...");
		formatMenu_Color.addActionListener(this);
		// ----------------------------创建查看菜单及菜单项并注册事件监听
		viewMenu = new JMenu("查看(V)");
		viewMenu.setMnemonic('V');// 设置快捷键ALT+V

		viewMenu_Status = new JCheckBoxMenuItem("状态栏(S)");
		viewMenu_Status.setMnemonic('S');// 设置快捷键ALT+S
		viewMenu_Status.setState(true);
		viewMenu_Status.addActionListener(this);

		viewMenu_ToolBar = new JCheckBoxMenuItem("工具栏(T)");
		viewMenu_ToolBar.setMnemonic('T');// 设置快捷键ALT+S
		viewMenu_ToolBar.setState(true);
		viewMenu_ToolBar.addActionListener(this);

		// ----------------------------创建帮助菜单及菜单项并注册事件监听
		helpMenu = new JMenu("帮助(H)");
		helpMenu.setMnemonic('H');// 设置快捷键ALT+H

		helpMenu_HelpTopics = new JMenuItem("帮助主题(H)");
		helpMenu_HelpTopics.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		helpMenu_HelpTopics.addActionListener(this);

		helpMenu_AboutNotepad = new JMenuItem("关于记事本(A)");
		helpMenu_AboutNotepad.addActionListener(this);

		// 向菜单条添加"文件"菜单及菜单项
		menuBar.add(fileMenu);
		fileMenu.add(fileMenu_New);
		fileMenu.add(fileMenu_Open);
		fileMenu.add(fileMenu_Save);
		fileMenu.add(fileMenu_SaveAs);
		fileMenu.addSeparator(); // 分隔线
		fileMenu.add(fileMenu_Exit);

		// 向菜单条添加"编辑"菜单及菜单项
		menuBar.add(editMenu);
		editMenu.add(editMenu_Undo);
		editMenu.addSeparator(); // 分隔线
		editMenu.add(editMenu_Cut);
		editMenu.add(editMenu_Copy);
		editMenu.add(editMenu_Paste);
		editMenu.add(editMenu_Delete);
		editMenu.addSeparator(); // 分隔线
		editMenu.add(editMenu_Find);
		editMenu.add(editMenu_Replace);
		editMenu.add(editMenu_GoTo);
		editMenu.addSeparator(); // 分隔线
		editMenu.add(editMenu_SelectAll);
		editMenu.add(editMenu_TimeDate);

		// 向菜单条添加"格式"菜单及菜单项
		menuBar.add(formatMenu);
		formatMenu.add(formatMenu_LineWrap);
		formatMenu.add(formatMenu_Font);
		formatMenu.add(formatMenu_Color);

		// 向菜单条添加"查看"菜单及菜单项
		menuBar.add(viewMenu);
		viewMenu.add(viewMenu_Status);
		viewMenu.add(viewMenu_ToolBar);

		// 向菜单条添加"帮助"菜单及菜单项
		menuBar.add(helpMenu);
		helpMenu.add(helpMenu_HelpTopics);
		helpMenu.addSeparator();
		helpMenu.add(helpMenu_AboutNotepad);

		// 向窗口添加菜单条
		this.setJMenuBar(menuBar);

		// ----------------------------创建文本编辑区并添加滚动条
		editArea = new JTextArea(20, 50);
		JScrollPane scroller = new JScrollPane(editArea);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.add(scroller, BorderLayout.CENTER);// 向窗口添加文本编辑区
		editArea.setWrapStyleWord(true);// 设置单词在一行不足容纳时换行
		editArea.setLineWrap(true);// 设置文本编辑区自动换行默认为true,即会"自动换行"
		oldValue = editArea.getText();// 获取原文本编辑区的内容

		// 编辑区注册事件监听(与撤销操作有关)
		editArea.getDocument().addUndoableEditListener(undoHandler);
		editArea.getDocument().addDocumentListener(this);

		// 创建右键弹出菜单
		popupMenu = new JPopupMenu();
		popupMenu_Undo = new JMenuItem("撤销(U)");
		popupMenu_Cut = new JMenuItem("剪切(T)");
		popupMenu_Copy = new JMenuItem("复制(C)");
		popupMenu_Paste = new JMenuItem("粘帖(P)");
		popupMenu_Delete = new JMenuItem("删除(D)");
		popupMenu_SelectAll = new JMenuItem("全选(A)");

		popupMenu_Undo.setEnabled(false);

		// 向右键菜单添加菜单项和分隔符
		popupMenu.add(popupMenu_Undo);
		popupMenu.addSeparator();
		popupMenu.add(popupMenu_Cut);
		popupMenu.add(popupMenu_Copy);
		popupMenu.add(popupMenu_Paste);
		popupMenu.add(popupMenu_Delete);
		popupMenu.addSeparator();
		popupMenu.add(popupMenu_SelectAll);

		// 文本编辑区注册右键菜单事件
		popupMenu_Undo.addActionListener(this);
		popupMenu_Cut.addActionListener(this);
		popupMenu_Copy.addActionListener(this);
		popupMenu_Paste.addActionListener(this);
		popupMenu_Delete.addActionListener(this);
		popupMenu_SelectAll.addActionListener(this);

		// 文本编辑区注册右键菜单事件
		editArea.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger())// 返回此鼠标事件是否为该平台的弹出菜单触发事件
				{
					popupMenu.show(e.getComponent(), e.getX(), e.getY());// 在组件调用者的坐标空间中的位置X、Y
				}
				checkMenuItemEnabled();// 设置剪切，复制，粘帖，删除等功能的可用性
				editArea.requestFocus();// 编辑区获取焦点
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger())// 返回此鼠标事件是否为该平台的弹出菜单触发事件
				{
					popupMenu.show(e.getComponent(), e.getX(), e.getY());// 在组件调用者的坐标空间中的位置X、Y
				}
				checkMenuItemEnabled();// 设置剪切，复制，粘帖，删除等功能的可用性
				editArea.requestFocus();// 编辑区获取焦点
			}
		});// 文本编辑区注册右键菜单事件结束

		// ----------------------------创建工具栏
		toolBar = new JPanel();
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		newButton = new JButton(newIcon);
		openButton = new JButton(openIcon);
		saveButton = new JButton(saveIcon);
		copyButton = new JButton(copyIcon);
		copyButton.setEnabled(false);
		cutButton = new JButton(cutIcon);
		cutButton.setEnabled(false);
		pasteButton = new JButton(pasteIcon);
		pasteButton.setEnabled(false);
		newButton.setPreferredSize(new Dimension(26, 22));
		openButton.setPreferredSize(new Dimension(26, 22));
		saveButton.setPreferredSize(new Dimension(26, 22));
		copyButton.setPreferredSize(new Dimension(26, 22));
		cutButton.setPreferredSize(new Dimension(26, 22));
		pasteButton.setPreferredSize(new Dimension(26, 22));
		// 注册工具栏按钮事件
		newButton.addActionListener(this);
		openButton.addActionListener(this);
		saveButton.addActionListener(this);
		copyButton.addActionListener(this);
		cutButton.addActionListener(this);
		pasteButton.addActionListener(this);
		// 向工具栏添加按钮
		toolBar.add(newButton);
		toolBar.add(openButton);
		toolBar.add(saveButton);
		toolBar.add(copyButton);
		toolBar.add(cutButton);
		toolBar.add(pasteButton);
		// 向容器添加工具栏
		this.add(toolBar, BorderLayout.NORTH);
		// -----------------------------------创建和添加状态栏
		statusLabel = new JLabel("状态栏");
		this.add(statusLabel, BorderLayout.SOUTH);// 向窗口添加状态栏标签
		// 改变标题栏窗口左侧默认图标
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.createImage("src/gui/resource/logo.png");
		this.setIconImage(image);
		// 设置窗口在屏幕上的位置、大小和可见性
		this.setLocation(100, 100);
		this.setSize(650, 550);
		this.setVisible(true);
		// 添加窗口监听器
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				QuitMethod quitmethod = new QuitMethod();
				quitmethod.exitWindowChoose(editArea, oldValue, currentFile, isNewFile, statusLabel);
			}
		});
		checkMenuItemEnabled();
		editArea.requestFocus();
	}// 构造方法结束

	/**
	 * 设置菜单项的可用性：剪切，复制，粘帖，删除功能
	 */
	public void checkMenuItemEnabled() {
		String selectText = editArea.getSelectedText();
		if (selectText == null) {
			editMenu_Cut.setEnabled(false);
			popupMenu_Cut.setEnabled(false);
			editMenu_Copy.setEnabled(false);
			popupMenu_Copy.setEnabled(false);
			editMenu_Delete.setEnabled(false);
			popupMenu_Delete.setEnabled(false);
			cutButton.setEnabled(false);
			copyButton.setEnabled(false);
		} else {
			editMenu_Cut.setEnabled(true);
			popupMenu_Cut.setEnabled(true);
			editMenu_Copy.setEnabled(true);
			popupMenu_Copy.setEnabled(true);
			editMenu_Delete.setEnabled(true);
			popupMenu_Delete.setEnabled(true);
			cutButton.setEnabled(true);
			copyButton.setEnabled(true);
		}
		// 粘帖功能可用性判断
		Transferable contents = clipBoard.getContents(this);
		if (contents == null) {
			editMenu_Paste.setEnabled(false);
			popupMenu_Paste.setEnabled(false);
			pasteButton.setEnabled(false);
		} else {
			editMenu_Paste.setEnabled(true);
			popupMenu_Paste.setEnabled(true);
			pasteButton.setEnabled(true);
		}
	}

	/**
	 * 所有按钮的事件处理
	 */
	public void actionPerformed(ActionEvent e) {
		// 新建
		if (e.getSource() == fileMenu_New || e.getSource() == newButton) {
			OpenMethod openmethod = new OpenMethod();
			openmethod.openNewFile(editArea, oldValue, currentFile, isNewFile, statusLabel, undo, editMenu_Undo);
		} // 新建结束
			// 打开
		else if (e.getSource() == fileMenu_Open || e.getSource() == openButton) {
			OpenMethod openmethod = new OpenMethod();
			openmethod.openFile(editArea, oldValue, currentFile, isNewFile, statusLabel);
		} // 打开结束
			// 保存
		else if (e.getSource() == fileMenu_Save || e.getSource() == saveButton) {
			editArea.requestFocus();
			SaveMethod savemethod = new SaveMethod();
			savemethod.Save(editArea, oldValue, currentFile, isNewFile, statusLabel);
		} // 保存结束
			// 另存为
		else if (e.getSource() == fileMenu_SaveAs) {
			SaveMethod savemethod = new SaveMethod();
			savemethod.SaveAs(editArea, oldValue, currentFile, statusLabel);
		} // 另存为结束
			// 退出
		else if (e.getSource() == fileMenu_Exit) {
			int exitChoose = JOptionPane.showConfirmDialog(this, "确定要退出吗?", "退出提示", JOptionPane.OK_CANCEL_OPTION);
			if (exitChoose == JOptionPane.OK_OPTION) {
				System.exit(0);
			} else {
				return;
			}
		} // 退出结束
			// 撤销
		else if (e.getSource() == editMenu_Undo || e.getSource() == popupMenu_Undo) {
			editArea.requestFocus();
			if (undo.canUndo()) {
				try {
					undo.undo();
				} catch (CannotUndoException ex) {
					System.out.println("Unable to undo:" + ex);
				}
			}
			if (!undo.canUndo()) {
				editMenu_Undo.setEnabled(false);
			}
		} // 撤销结束
			// 剪切
		else if (e.getSource() == editMenu_Cut || e.getSource() == popupMenu_Cut || e.getSource() == cutButton) {
			editArea.requestFocus();
			String text = editArea.getSelectedText();
			StringSelection selection = new StringSelection(text);
			clipBoard.setContents(selection, null);
			editArea.replaceRange("", editArea.getSelectionStart(), editArea.getSelectionEnd());
			checkMenuItemEnabled();// 设置剪切，复制，粘帖，删除功能的可用性
		} // 剪切结束
			// 复制
		else if (e.getSource() == editMenu_Copy || e.getSource() == popupMenu_Copy || e.getSource() == copyButton) {
			editArea.requestFocus();
			String text = editArea.getSelectedText();
			StringSelection selection = new StringSelection(text);
			clipBoard.setContents(selection, null);
			checkMenuItemEnabled();// 设置剪切，复制，粘帖，删除功能的可用性
		} // 复制结束
			// 粘帖
		else if (e.getSource() == editMenu_Paste || e.getSource() == popupMenu_Paste || e.getSource() == pasteButton) {
			editArea.requestFocus();
			Transferable contents = clipBoard.getContents(this);
			if (contents == null)
				return;
			String text = "";
			try {
				text = (String) contents.getTransferData(DataFlavor.stringFlavor);
			} catch (Exception exception) {
			}
			editArea.replaceRange(text, editArea.getSelectionStart(), editArea.getSelectionEnd());
			checkMenuItemEnabled();
		} // 粘帖结束
			// 删除
		else if (e.getSource() == editMenu_Delete || e.getSource() == popupMenu_Delete) {
			editArea.requestFocus();
			editArea.replaceRange("", editArea.getSelectionStart(), editArea.getSelectionEnd());
			checkMenuItemEnabled(); // 设置剪切、复制、粘贴、删除等功能的可用性
		} // 删除结束
			// 查找
		else if (e.getSource() == editMenu_Find) {
			editArea.requestFocus();
			findDialog f = new findDialog();
			f.find(editArea);
		} // 查找结束
			// 替换
		else if (e.getSource() == editMenu_Replace) {
			editArea.requestFocus();
			replaceDialog r = new replaceDialog();
			r.replace(editArea);
		} // 替换结束
			// 转到
		else if (e.getSource() == editMenu_GoTo) {
			Go_toDialog go_to = new Go_toDialog();
			go_to.Go_toMethod(editArea);
		} // 转到结束
			// 时间日期
		else if (e.getSource() == editMenu_TimeDate) {
			editArea.requestFocus();
			Calendar rightNow = Calendar.getInstance();
			Date date = rightNow.getTime();
			editArea.insert(date.toString(), editArea.getCaretPosition());
		} // 时间日期结束
			// 全选
		else if (e.getSource() == editMenu_SelectAll || e.getSource() == popupMenu_SelectAll) {
			editArea.selectAll();
		} // 全选结束
			// 自动换行(已在前面设置)
		else if (e.getSource() == formatMenu_LineWrap) {
			if (formatMenu_LineWrap.getState()) {
				editArea.setLineWrap(true);
				viewMenu_Status.setState(false);
				statusLabel.setVisible(false);
			} else
				editArea.setLineWrap(false);
		} // 自动换行结束
			// 字体设置
		else if (e.getSource() == formatMenu_Font) {
			editArea.requestFocus();
			fontDialog fon = new fontDialog();
			fon.font(editArea);
		} // 字体设置结束
			// 颜色设置
		else if (e.getSource() == formatMenu_Color) {
			editArea.requestFocus();
			Color color = JColorChooser.showDialog(this, "更改字体颜色", Color.black);
			if (color != null) {
				editArea.setForeground(color);
			} else
				return;
		} // 颜色设置结束
			// 设置状态栏可见性
		else if (e.getSource() == viewMenu_Status) {
			if (viewMenu_Status.getState())
				statusLabel.setVisible(true);
			else
				statusLabel.setVisible(false);
		} // 设置状态栏可见性结束
			// 设置工具栏可见性
		else if (e.getSource() == viewMenu_ToolBar) {
			if (viewMenu_ToolBar.getState())
				toolBar.setVisible(true);
			else
				toolBar.setVisible(false);
		} // 设置工具栏可见性结束
			// 帮助主题
		else if (e.getSource() == helpMenu_HelpTopics) {
			editArea.requestFocus();
			JOptionPane.showMessageDialog(this, "路漫漫其修远兮，吾将上下而求索。", "帮助主题", JOptionPane.INFORMATION_MESSAGE);
		} // 帮助主题结束
			// 关于
		else if (e.getSource() == helpMenu_AboutNotepad) {
			editArea.requestFocus();
			JOptionPane.showMessageDialog(this, "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n" + " 编写者：Nancy \n"
					+ " 编写时间：2018-06-23                          \n" + " e-mail：Nancy2018319@163.com                \n"
					+ " 一些地方借鉴他人，不足之处希望大家能提出意见，谢谢！  \n" + "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n", "记事本",
					JOptionPane.INFORMATION_MESSAGE);
		} // 关于结束
	}// 方法actionPerformed()结束

	// 实现DocumentListener接口中的方法(与撤销操作有关)
	public void removeUpdate(DocumentEvent e) {
		editMenu_Undo.setEnabled(true);
	}

	public void insertUpdate(DocumentEvent e) {
		editMenu_Undo.setEnabled(true);
	}

	public void changedUpdate(DocumentEvent e) {
		editMenu_Undo.setEnabled(true);
	}// DocumentListener结束

	// 实现接口UndoableEditListener的类UndoHandler(与撤销操作有关)
	class UndoHandler implements UndoableEditListener {
		public void undoableEditHappened(UndoableEditEvent uee) {
			undo.addEdit(uee.getEdit());
		}
	}

	/**
	 * main 方法
	 * 
	 * @param args-String
	 */
	public static void main(String args[]) {
		Notepad notepad = new Notepad();
		// 使用 System exit 方法退出应用程序
		notepad.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
