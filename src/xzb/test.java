package xzb;


//


import java.io.*;

public class test {

    public static void writePoint(int str, String filepath) {
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
            out.write(str);
            out.close();
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
            sorce= out.readLine();
            System.out.println(sorce);
            out.close();
            return 0;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        readPoint("sorce.txt");
    }

}
