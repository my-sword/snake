package xzb.game;

import java.awt.*;
import javax.swing.*;

public class Help extends JDialog {
    private static final long serialVersionUID = 4693799019369193520L;
    private JPanel contentPane;
    private Font f = new Font("微软雅黑",Font.PLAIN,15);
    private JScrollPane scroll;
	
    public Help() {
        setTitle("游戏规则说明");//设置窗体标题
        Image img=Toolkit.getDefaultToolkit().getImage("image//mi64.png");//窗口图标
        setIconImage(img);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModal(true);//设置为模态窗口
        setSize(420,400);
        setResizable(false);
        setLocationRelativeTo(null);
        contentPane = new JPanel();// 创建内容面板
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        ShadePanel shadePanel = new ShadePanel();           // 创建渐变背景面板（help可以调用本包内的所有非private的class）
        contentPane.add(shadePanel, BorderLayout.CENTER);   // 添加面板到窗体内容面板
        shadePanel.setLayout(null);
        
        JTextArea J1 = new JTextArea("游戏说明如下：\n方向键：↑ ↓ ← →   W A S D\n"
        		+ "长按可以加速\n重新开始游戏：ESC键\n暂停和开始:  空格键space\n发射子弹： shitf键\n"
        		+ "    菜单栏的设置菜单可以更改蛇头，蛇身，选择速度，更换背景，以及设置网格是否可见。游戏界面右边会显示你的当前长度，"
        		+ "得分，当前所含有的子弹数。游戏中有多种食物，它们对应的"
        		+ "分值不同，出现的概率也不同，而且食物会在相应时间内自动移动或者消失。所以，请随时加速。"
        		+ "此版本加入了障碍物，障碍物随机产生，每隔一段时间自动随机移动，障碍物的长度也随机，排列也随机。"
        		+ "同时，为了配合障碍物的出现，游戏加入了蛇射出子弹击毁前进道路上的障碍物的技能，子弹数目初始化为20。"
        		+ "注意，待击毁的目标障碍物是你发射子弹时，你前进方向前距离你最近的砖块。如果没有，子弹会沿着发射方向"
        		+ "一直移动到屏幕外。子弹通过吃特定的食物获得，食物样子为一把枪。吃得枪每次增加一颗子弹，"
        		+ "不增加得分，增加长度。子弹产生的概率在所有食物中最低，所以请节约使用。另外，按Shift键发射子弹。                   "
        		+ "\n                   Copyright @XZB2019.\n                   All rights reserved.");
        J1.setFocusable(false);
    	J1.setFont(f);
    	J1.setEditable(false);
    	J1.setOpaque(false);    //背景透明
    	J1.setLineWrap(true);   //自动换行
    	//创建滚动条并添加文本域
    	scroll = new JScrollPane(J1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    	scroll.setBorder(BorderFactory.createTitledBorder("How to play"));//滚动边框标题
    	scroll.setOpaque(false);
    	scroll.getViewport().setOpaque(false);//JScrollPane设置成透明需加上这一行
    	shadePanel.add(scroll);
    	scroll.setBounds(10, 10, 385, 330);
    	
    	setVisible(true);
    }
    
    public static void main(String[] args) {
		new Help();
	}
}
