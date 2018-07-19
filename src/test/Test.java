package test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Test {

	public static Connection.Response response;

	public static void main(String[] args) throws InterruptedException {
		// ghiFile();
		// docFile();
		// String temp =
		// "https://hocsinh.lika.edu.vn/classroom/subject?subject_id=1&alias=toan&week=35";
		// String
		// temp1="https://hocsinh.lika.edu.vn/classroom/subject?alias=phuong-phap-toan&subject_id=19&week=18";
		// String[] thematic = temp1.split("week=", 2);
		// System.out.println(thematic[0]);
		// System.out.println(thematic[1]);
		Connection.Response loginForm;

		String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36";

		try {
			response = Jsoup.connect("https://hocsinh.lika.edu.vn/site/login?student_id=3919").userAgent(userAgent)
					.method(Connection.Method.GET).execute();

			response = Jsoup.connect("https://hocsinh.lika.edu.vn/site/login?student_id=3919")
					.cookies(response.cookies()).userAgent(userAgent)
					.data("LoginForm[username]", "ngothuylinh1", "LoginForm[password]", "0969167445")
					.data("save_login", "1").followRedirects(false).method(Connection.Method.POST).followRedirects(true)
					.timeout(30 * 1000).execute();
			System.err.println(response.statusCode());
			// Document page =
			// Jsoup.connect("https://hocsinh.lika.edu.vn/practice/finish?skill_id=23").cookies(response.cookies()).userAgent(userAgent).timeout(30
			// * 1000).get();
			// TimeUnit.SECONDS.sleep(3);
			// TimeUnit.SECONDS.sleep(3);
			// page =
			// Jsoup.connect("https://hocsinh.lika.edu.vn/practice/finish?skill_id=23").cookies(response.cookies()).userAgent(userAgent).timeout(30
			// * 1000).get();
			Document page = Jsoup.connect(
					"https://hocsinh.lika.edu.vn/practice/index?skill_id=228&alias=o-c-bo-co")
					.cookies(response.cookies()).userAgent(userAgent).timeout(300 * 1000).get();
			
			
			
			System.out.println(page.html());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
