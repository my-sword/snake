package xzb.game;
//地图类（写入读取地图数据判断碰撞事件）
import java.io.*;

public class Write2file {
	//字符串写出到文本
    public static void  Write2Txt(String str,String filepath) throws Exception{
        FileWriter fw = null;
        String path = filepath;
        File f = new File(path);
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            else f.delete();
            fw = new FileWriter(f);
            BufferedWriter out = new BufferedWriter(fw);
            // FileOutputStream fos = new FileOutputStream(f); 
            // OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
            out.write(str.toString());
            out.close();
            //System.out.println("===========写入文本成功========");
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    public static int readPoint(String filepath) {
        FileReader fr = null;
        String path = filepath;
        File f = new File(path);
        String sorce;

        try {
            if (!f.exists()) {
               return 0;
            }
            fr = new FileReader(f);
            BufferedReader out = new BufferedReader(fr);
            sorce= out.readLine();;//Integer.valueOf(out.read());读取成16进制了
            //System.out.println("读取分数："+Integer.parseInt(String.valueOf(sorce),16));//Integer.parseInt(hex, 16);
            //System.out.println(sorce);
            int a=Integer.parseInt(sorce.trim());
            //System.out.println(sorce);
            out.close();
            return a;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static void writePoint(int a, String filepath) {
        FileWriter fw = null;
        String path = filepath;
        File f = new File(path);
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            else f.delete();
            fw = new FileWriter(f);
            //BufferedWriter out = new BufferedWriter(fw);

            fw.write(String.valueOf(a));//不能用字符写入，否则转化为16进制
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void PrintMap(int[][] map,String filepath){
    	String temp = "";
    	temp += "\t";
    	for(int i = 0;i < map[0].length;i++)
    		temp += i + "\t";
    	temp += "\n";
    	
    	for(int i = 0;i < map.length;i++)
    	{
    		temp += i + "\t";
    		for(int j = 0;j <map[0].length;j++)
    			temp += map[i][j] + "\t";
			temp += "\n";
    	}
    	try {
			Write2Txt(temp,filepath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
