package test;

import java.io.File;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class Test {
	static Random rd = new Random();
	public static void main(String [] args) {
//		ghiFile();
//		docFile();
		
		System.out.println(Integer.toString(rd.nextInt(10)));
		
	}
	
	public static void makeFile(String path) {
		File files = new File(path);
        if (!files.exists()) {
            if (files.mkdirs()) {
                System.out.println(path+" are created!");
            } else {
                System.out.println("Failedreate multiple directories!");
            }
        }
	}
	
	public static void ghiFile() {
		File file = new File("output.txt");
		try (PrintWriter pw = new PrintWriter(file)){
			pw.println("hello");
			pw.println("haha");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void docFile() {
		File file = new File("output.txt");
		try (Scanner sc  = new Scanner(file)){
			String str;
			while(sc.hasNext()) {
				str=sc.next();
				System.out.println(str+" ");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
