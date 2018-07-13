package test;

import java.io.File;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		// ghiFile();
		// docFile();
		String temp = "https://hocsinh.lika.edu.vn/classroom/subject?subject_id=1&alias=toan&week=35";
		String temp1="https://hocsinh.lika.edu.vn/classroom/subject?alias=phuong-phap-toan&subject_id=19&week=18";
		String[] thematic = temp1.split("week=", 2);
		System.out.println(thematic[0]);
		System.out.println(thematic[1]);
//		System.out.println(thematic[2]);
	}

	public static void makeFile(String path) {
		File files = new File(path);
		if (!files.exists()) {
			if (files.mkdirs()) {
				System.out.println(path + " are created!");
			} else {
				System.out.println("Failedreate multiple directories!");
			}
		}
	}

	public static void ghiFile() {
		File file = new File("output.txt");
		try (PrintWriter pw = new PrintWriter(file)) {
			pw.println("hello");
			pw.println("haha");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void docFile() {
		File file = new File("output.txt");
		try (Scanner sc = new Scanner(file)) {
			String str;
			while (sc.hasNext()) {
				str = sc.next();
				System.out.println(str + " ");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
