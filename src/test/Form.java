//Dap an dung
package test;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import net.miginfocom.swing.MigLayout;

public class Form extends JFrame {

	private JPanel contentPane;
	private static JTextField tFUrl;
	private static JTextField tFUsername;
	private static JTextField tFPassword;
	private static Connection.Response response;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				Connection.Response loginForm;

				String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36";

				try {
					response = Jsoup.connect("https://hocsinh.lika.edu.vn/site/login?student_id=3174")
							.userAgent(userAgent).method(Connection.Method.GET).execute();
					loginForm = Jsoup.connect("https://hocsinh.lika.edu.vn/site/login?student_id=3174")
							.method(Connection.Method.GET).execute();

					response = Jsoup.connect("https://hocsinh.lika.edu.vn/site/login?student_id=3174")
							.cookies(response.cookies()).userAgent(userAgent)
							.data("LoginForm[username]", "nguyenvantai", "LoginForm[password]", "0967048347")
							.data("save_login", "1").followRedirects(false).method(Connection.Method.POST)
							.followRedirects(true).timeout(30 * 1000).execute();
					System.err.println(response.statusCode());

//					getQuestionWeek("https://hocsinh.lika.edu.vn/classroom/subject?subject_id=1&alias=toan&week=35",
//							response, userAgent);
//					 for (int i = 0; i < 100; i++) {
					 getQuestion("Môn Toán - Tuần 35",
					 "https://hocsinh.lika.edu.vn/practice/index?skill_id=36&alias=on-tap-cac-so-den-100-tuan-1",
					 response, userAgent);
//					 }

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Lấy thông tin của câu hỏi trong trang web

			}
		});
	}

	private static void getQuestionWeek(String url, Connection.Response response, String userAgent)
			throws IOException, JSONException {
		// // Tap cac link lam bai
		// https://hocsinh.lika.edu.vn/classroom/subject?subject_id=1&alias=toan&week=35
		int count = 0;
		Document weeksDoc = Jsoup.connect(url).cookies(response.cookies()).userAgent(userAgent).timeout(30 * 1000)
				.get();
		Elements weeks = weeksDoc.select("div[class=list] a");
		for (Element week : weeks) {
			String absHrefWeek = week.attr("abs:href").toLowerCase();

			Document docWeek = Jsoup.connect(absHrefWeek).cookies(response.cookies()).userAgent(userAgent)
					.timeout(30 * 1000).get();
			Element header = docWeek.selectFirst("h1[class=\"subject-name\"]");
			String subjectWeek = header.text(); // Ten mon hoc va tuan
			// System.out.println(subjectWeek);
			Elements links = docWeek.select("div[class=subject-content] a");
			for (Element link : links) {
				String absHref = link.attr("abs:href");
				for (int i = 0; i <= 20; i++) {
					getQuestion(subjectWeek, absHref, response, userAgent);
					System.out.println(++count);
				}

			}

			// Document docWeek =
			// Jsoup.connect(absHrefWeek).cookies(response.cookies()).userAgent(userAgent)
			// .timeout(30 * 1000).get();
			// Element header = docWeek.selectFirst("h1[class=\"subject-name\"]");
			// String subjectWeek = header.text(); // Ten mon hoc va tuan
			// // System.out.println(subjectWeek);
			// Elements links = docWeek.select("div[class=subject-content] a");
			// for (Element link : links) {
			// String absHref = link.attr("abs:href");
			// // if (absHref ==
			// //
			// "https://hocsinh.lika.edu.vn/classroom/subject?subject_id=1&alias=toan&week=5")
			// // System.out.println(absHref);
			//
			// // for (int i = 0; i <= 20; i++) {
			// // getQuestion(subjectWeek, absHref, response, userAgent);
			// // System.out.println(++count);
			// // }
			//
			// }
		}
	}

	private static void getQuestion(String subjectWeek, String url, Connection.Response response, String userAgent)
			throws IOException, JSONException {
		Document page = Jsoup.connect(url).cookies(response.cookies()).userAgent(userAgent).timeout(30 * 1000).get();
		Element quizElement = page.selectFirst("input[type=hidden]");
		String quiz = quizElement.attr("data-quiz");
		JSONObject quiz_json = new JSONObject(quiz);

		// id_ques,type,id_skill,skill_name

		int type, id_ques, id_skill;

		String skill_name, id_string;
		type = quiz_json.getInt("type");
//		 type=3;
		String typeString = Integer.toString(type);
		id_ques = quiz_json.getInt("id_ques");
		String id_ques_string = Integer.toString(id_ques);
//		 id_ques=349;
		// System.out.println(id_ques);
		id_string = Integer.toString(id_ques);
		id_skill = quiz_json.getInt("id_skill");
		skill_name = quiz_json.getString("skill_name");
		String giaithich = "";

		String correctAnswerString = "";
		String hint = "";
		// Dap an dung va giai thich
		if (type == 3) // Dang trac nghiem
		{
			String answerTemp = "C";
			Document correctAnswerDoc = Jsoup.connect("https://hocsinh.lika.edu.vn/ajax/answer-quiz")
					.cookies(response.cookies()).data("id_quiz", id_string).data("answer", answerTemp)
					.userAgent(userAgent).timeout(30 * 1000).post();
			String answerString = correctAnswerDoc.body().text();

			JSONObject answerJson = new JSONObject(answerString);
			// System.out.println(answerJson);
			giaithich = answerJson.getString("giaithich_quiz");

			JSONArray correctAnswer = (JSONArray) answerJson.get("dapan_quiz");
			correctAnswerString = correctAnswer.get(0).toString();
			System.out.println(correctAnswerString);
//			Element contentCorrectAnswerElement = page.selectFirst("label[id=\"label_toggleB\"] div[class=\"label_content\"]");
//			System.out.println(contentCorrectAnswerElement.html());
			Element hintElement = page.selectFirst("div[class=\"item-wrap\"] p");
			hint = hintElement.text();
//			System.out.println(contentCorrectAnswerElement);
			// System.out.println(correctAnswer.get(0));
			// System.out.println(giaithich);
		} else {
			if (type == 1) { //
				String answerTemp = "1|_|6|;|2|_|9|;|3|_|9";
				Document correctAnswerDoc = Jsoup.connect("https://hocsinh.lika.edu.vn/ajax/answer-quiz")
						.cookies(response.cookies()).data("id_quiz", id_string).data("answer", answerTemp)
						.userAgent(userAgent).timeout(30 * 1000).post();
				String answerString = correctAnswerDoc.body().text();
				JSONObject answerJson = new JSONObject(answerString);
				// System.out.println(answerJson);
				giaithich = answerJson.getString("giaithich_quiz");
				// System.out.println(giaithich);
				JSONObject answerCorrectJson = answerJson.getJSONObject("dapan_quiz");
				correctAnswerString = answerCorrectJson.toString();
				// System.out.println(answerCorrectJson);
			} else {

			}
		}
		Element question = page.selectFirst("div[class=info]");
		String[] words = question.html().split("<div", 2);
		// System.out.println(words[0]);
		Elements answers = page.select("div[class=label_content]");
		ArrayList<String> answerArray = new ArrayList<String>();
		for (Element answer : answers) {
			if (!answerArray.contains(answer.text()))
				answerArray.add(answer.text());
		}

		Element thematic_title_element = page.selectFirst("h3[class=title]");
		String thematic_title = thematic_title_element.text();
		String answerString = answerArray.toString();
		String[] thematic = subjectWeek.split(" - ", 2);

//		System.out.println(type + " | " + id_ques + " | " + id_skill + " | " + skill_name);
//		System.out.println("Cau hoi: " + words[0]);
//		System.out.println("Goi y : " + hint);
//		System.out.println("Giai thich: " + giaithich);
//		System.out.println("Dap an: " + answerString);
//		System.out.println("Dap an dung: " + correctAnswerString);
//		System.out.println("Dang cau hoi: " + typeString);
//
//		System.out.println("Mon hoc: " + thematic[0]);
//		System.out.println("Tuan: " + thematic[1]);
//		System.out.println(thematic_title);

//		Document postData = Jsoup.connect("http://schoolkid.tigerweb.vn/admin/ajax-question/clone")
//				.data("id", id_ques_string).data("content", words[0]).data("thematic_id", "1")
//				.data("thematic_title", thematic_title).data("explain", giaithich).data("hint", hint)
//				.data("answer", answerString).data("answer_correct", correctAnswerString).data("image", "")
//				.data("question_type", typeString).data("classroom_title", "Lớp 2").data("subject_title", thematic[0])
//				.data("week_title", thematic[1]).ignoreContentType(true).post();
//		System.out.println(postData.text());
	}

	/**
	 * Create the frame.
	 */
	public Form() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow][][grow]", "[][][][][][]"));

		JLabel lblUrl = new JLabel("Url");
		contentPane.add(lblUrl, "cell 0 1,alignx trailing");

		tFUrl = new JTextField();
		contentPane.add(tFUrl, "cell 1 1 3 1,growx");
		tFUrl.setColumns(10);

		JLabel lblUserName = new JLabel("UserName");
		contentPane.add(lblUserName, "cell 0 2,alignx trailing");

		tFUsername = new JTextField();
		contentPane.add(tFUsername, "cell 1 2 3 1,growx");
		tFUsername.setColumns(10);

		JLabel lblPassword = new JLabel("PassWord");
		contentPane.add(lblPassword, "cell 0 3,alignx trailing");

		tFPassword = new JTextField();
		contentPane.add(tFPassword, "cell 1 3 3 1,growx");
		tFPassword.setColumns(10);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.out.println(tFUrl.getText());
				// System.out.println(tFUsername.getText());
				// System.out.println(tFPassword.getText());
				// Connection.Response loginForm;
				// try {
				// String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36
				// (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36";
				//
				// Connection.Response response = Jsoup
				// .connect("https://hocsinh.lika.edu.vn/site/login?student_id=3174").userAgent(userAgent)
				// .method(Connection.Method.GET).execute();
				// loginForm =
				// Jsoup.connect("https://hocsinh.lika.edu.vn/site/login?student_id=3174")
				// .method(Connection.Method.GET).execute();
				//
				// // response = Jsoup.connect(
				// // "https://hocsinh.lika.edu.vn/site/login?student_id=3174")
				// // .userAgent(userAgent).data("cookieexists", "false")
				// // .data("LoginForm[password]", "0967048347").data("LoginForm[username]",
				// // "nguyenvantai")
				// // .cookies(loginForm.cookies()).post();
				// response =
				// Jsoup.connect("https://hocsinh.lika.edu.vn/site/login?student_id=3174")
				// .cookies(response.cookies()).userAgent(userAgent)
				// .data("LoginForm[username]", "nguyenvantai", "LoginForm[password]",
				// "0967048347")
				// .data("save_login",
				// "1").followRedirects(false).method(Connection.Method.POST)
				// .followRedirects(true).timeout(30 * 1000).execute();
				// System.err.println(response.statusCode());
				// Document doc = Jsoup
				// .connect("https://hocsinh.lika.edu.vn/classroom/subject?alias=toan&subject_id=1&week=1")
				// .cookies(response.cookies()).userAgent(userAgent).timeout(30 * 1000).get();
				//

				//
				//
				// Document page = Jsoup
				// .connect("https://hocsinh.lika.edu.vn/practice/index?skill_id=2502&alias=on-tap-ve-cac-so-trong-pham-vi-1000-tuan-35")
				// .cookies(response.cookies())
				// .userAgent(userAgent)
				// .timeout(30 * 1000).get();
				// System.out.println(page.getElementsByAttribute("quiz"));

				//
				// // push div chua bai tap vao divName
				// // ArrayList<String> divNames = new ArrayList<String>();
				// // Elements divs = doc.select("div[id=main] div");
				// // for(Element div:divs) {
				// // String divClass = div.attr("class");
				// // if(divClass.contains("box")) {
				// // divNames.add(divClass);
				// // }
				// // }
				// //
				// // for (String divName : divNames) {
				// // Elements subjects = doc.select("div[class=\""+divName+"\"] a");
				// // System.out.println(divName);
				// // for (Element subject:subjects) {
				// // String absHref = subject.attr("abs:href");
				// // System.out.println(" "+absHref);
				// // }
				// //
				// // }
				//
				// } catch (IOException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }

			}
		});
		contentPane.add(btnSubmit, "cell 2 4");
	}

}
