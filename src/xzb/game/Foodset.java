package xzb.game;
//食物类121w 149g 177g
import java.awt.Image;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Foodset {
	
	private MainWindow GameUI;				//主窗体,即游戏主界面
	private List<Food> food = new LinkedList<Food>();//用于描述食物的数组(存放一定数量的食物对象)（Food本类方法）
	private static final int MAXSIZE = 8;	//食物的上下限
	private static final int MINSIZE = 3;
	
	private static final int FOODKIND = 6;	//食物种类
	private int[] point = new int[FOODKIND];//6种食物各自对应的得分
	private ImageIcon[] foodIcon = new ImageIcon[FOODKIND];//6种食物各自对应的图标
	
	private Thread run;
	private int time = 10000;//10秒移动刷新一次
	private boolean quit = false;
	
	public Foodset(MainWindow GameUI){
		this.GameUI = GameUI;
		
		//初始化各食物对应的得分
	    point[0] = 50;
	    point[1] = 40;
	    point[2] = 30;
	    point[3] = 20;
	    point[4] = 10;
	    point[5] = 0;
	    
	    //加载6张食物的图片
	    for(int i = 0;i < FOODKIND;i++)
	    {
	    	String s1="food//food" + i + ".png";
	    	//System.out.println(s1);
	    	foodIcon[i] = new ImageIcon(s1);
			foodIcon[i].setImage(foodIcon[i].getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));//保持图片的清晰
	    }
	    
	    produceFood();		//产生食物方法z
	    show();				//
	    AutoMoveThread();	//
		run.start();
	}
	//食物的数据结构
	public class Food {
		int kind;//食物种类，0-5对应5种不同的食物，见文档说明
		JLabel label;
		Coordinate coor;//坐标
		public Food(int kind,int x,int y,ImageIcon icon){
			this.kind = kind;
			label = new JLabel(icon);
			coor = new Coordinate(x,y);
		}

		public Food(int kind,Coordinate coor,ImageIcon icon){
			this.kind = kind;
			label = new JLabel(icon);
			this.coor = coor;
		}
	}
	//产生具体坐标
	public synchronized void produceFood(){
		Random rand = new Random();
		int amount = rand.nextInt(MINSIZE);//[0,MINSIZE-1]//设置随机食物产生数量
		double prob;	//各种食物出现的概率
		int foodtag = 2;//图标出现的标号: foodIcon[foodtag]
		Food newfood;	//产生的新食物

		//P();
		if(food.size() == 0)//当食物的数量被吃光
		{
			amount = MINSIZE;
		}
		else
		{	//当存在随机值(0,2)＋食物个数小于最小值，或随机值(0,2)＋食物>最大值
			while(amount + food.size() < MINSIZE || amount + food.size() > MAXSIZE)
			{
				amount = rand.nextInt(MINSIZE);//[0,MINSIZE-1];//重置随机食物产生数量
			}
		}
		//V();

		for(int i = 0;i < amount;i++)
		{	//10列5行外产生空白坐标(getMap()[x][y] == 0)
			Coordinate coor = GameUI.produceRandomCoordinate();//注意，coor.x是数组的行，coor.y是数组的列，和界面上的行序号和列序号正好相反
			Coordinate _coor = new Coordinate(coor.y,coor.x);//置换行和列（5行10列之外产生食物）每个对象的coor.xy不一样
			prob = rand.nextDouble();
			if(prob >= 0 && prob <0.1) 		    foodtag = 0;//10%
			else if(prob >= 0.1  && prob <0.25) foodtag = 4;//15%
			else if(prob >= 0.25 && prob <0.5)  foodtag = 3;//25%
			else if(prob >= 0.5  && prob <0.8)  foodtag = 2;//30%
			else if(prob >= 0.8 && prob <0.95)  foodtag = 1;//15%
			else if(prob >= 0.95 && prob <1) 	foodtag = 5;//5%

			//GameUI.P();
			//GameUI.map[coor.x][coor.y] = 2;//x行，y列
			GameUI.setMap(coor.x, coor.y, 2);//map[i][j] = e  数字2表示食物
			//GameUI.V();

			newfood = new Food(foodtag,_coor,foodIcon[foodtag]);//产生食物图标
			food.add(newfood);			//添加List数组的食物对象
			GameUI.add(newfood.label);	//主界面添加食物的图标（坐标由show()）
//			newfood.label.setBounds(IndexToPixel.getXPixel(newfood.coor.y),
//					IndexToPixel.getYPixel(newfood.coor.x), 20, 20);//     x指的是是第几行，对应的是纵坐标
//																	//同理，y指的是是第几列，对应的是横坐标
		}

		GameUI.getAmountLabel().setText("" + food.size());//刷新GameUI界面上显示食物数量的Label
		//show();此处不能显示食物
		System.out.print("产生" + amount + "个食物\t");
		String Time = SysTime.getSysTime();
		System.out.println(Time);
	}
	//显示图标具体坐标
	public synchronized void show(){//线程锁(synchronized(对象或变量)，方法锁只能同时调用一个方法)
		for (Iterator<Food> iter = food.iterator(); iter.hasNext();) {//对象迭代
			Food node = iter.next();
			node.label.setBounds(IndexToPixel.getXPixel(node.coor.x),//操作对象时所有.coor.x都是独立的
					IndexToPixel.getYPixel(node.coor.y), 20, 20);//具体坐标由IndexToPixel自类产生(根据行列设置位置)
			node.label.setVisible(true);
			//System.out.println(node.coor.x+" "+node.coor.y+",,");
		}
	}
	//自动产出线程
	public void AutoMoveThread(){
		run = new Thread() {
			public void run() {
				while (!quit)//不退出程序
				{
					try {
						Thread.sleep(time);//10秒移动刷新一次地图
					} catch (InterruptedException e) {
						e.printStackTrace();
						break;
					}

					if(!GameUI.getPause() && GameUI.getIsrun())//如果不是暂停或者继续运行
					{
						removeAll();	//调用自方法 //移除界面上的所有食物图片
						produceFood();	//产生食物
						Write2file.PrintMap(GameUI.getMap(),"map.txt");//写入地图数据
						if(quit)		//如果退出为真

							break;
						show();		//显示图片
					}
				}
				System.out.println("Food thread exit...");
			}
		};
		//run.start();
	}

	public void quit(){
		quit = true;
	}
	//获取对应食物分数和吃掉食物
	public synchronized int getFoodPoint(Coordinate coor){
		/*给定界面上的一个点，判断该点是否有食物存在，若有，则返回对应食物的得分，否则返回-1
		 * 注意coor.x代表横向的序号，从左到右依次为[0,WIDTH-1]
		 * coor.y代表纵向的序号，从上到下依次为[0,HEIGHT-1]
		 */
		for (Iterator<Food> iter = food.iterator(); iter.hasNext();) {
			Food node = iter.next();
			//System.out.println(node.coor.x +" "+coor.x +" "+node.coor.y +" "+coor.y);
			if(node.coor.x == coor.x && node.coor.y == coor.y)//当前坐标（蛇头）等于食物坐标，则移除食物
			{
				node.label.setVisible(false);//从界面上移除食物
				GameUI.remove(node.label);
				iter.remove();//从food数组中移除被吃掉的食物
				
				produceFood();//注意每次吃完食物后增加随机数量的食物，保证界面上食物的数量维持在[0,MINSIZE-1]之间
				show();

				GameUI.setMap(node.coor.y, node.coor.x, 0);//所有地图上的食物的点重新标记为0 先y后x（初始设置）
				GameUI.getAmountLabel().setText("" + food.size());
				return point[node.kind];	//返回该食物对应的分数
			}
		}
		return -1;
	}

	public synchronized void removeAll(){	//移除界面上的所有食物图片
		for (Iterator<Food> iter = food.iterator(); iter.hasNext();) {
			Food node = iter.next();
			
			GameUI.setMap(node.coor.y, node.coor.x, 0);//所有地图上的食物的点重新标记为0 先y后x（初始设置）
			
			node.label.setVisible(false);
			GameUI.remove(node.label);
		}
		food.clear();//调用List类clear方法
	}

}
