//Dap an dung
package test;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
import org.jsoup.select.Elements;

import net.miginfocom.swing.MigLayout;

public class Form extends JFrame {

	private JPanel contentPane;
	private static JTextField tFUrl;
	private static JTextField tFUsername;
	private static JTextField tFPassword;
	public static Connection.Response response;
	private static int count = 0;
	private static Random rd = new Random();
	private static int false_num;

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
						
						response = Jsoup.connect("https://hocsinh.lika.edu.vn/site/login?student_id=3174")
								.cookies(response.cookies()).userAgent(userAgent)
								.data("LoginForm[username]", "nguyenvantai", "LoginForm[password]", "0967048347")
								.data("save_login", "1").followRedirects(false).method(Connection.Method.POST)
								.followRedirects(true).timeout(30 * 1000).execute();
						System.err.println(response.statusCode());

					// getQuestionWeek("https://hocsinh.lika.edu.vn/classroom/subject?alias=toan&subject_id=1&week=9",
					// userAgent);

					// Document doc = Jsoup.connect("https://hocsinh.lika.edu.vn/classroom/detail")
					// .cookies(response.cookies()).userAgent(userAgent).timeout(30 * 1000).get();
					// String divName = "box advanced";
					// Element pageElement = doc.selectFirst("div[class=\"" + divName + "\"]
					// ul[class=\"row align-items-center\"]");
					//// System.out.println(pageElement.html());
					// Elements hrefs = pageElement.select("a");
					// for (Element href: hrefs) {
					// String absHref = href.attr("abs:href");
					// System.out.println( absHref);
					// }
					// Elements subjects = pageElement.select("span");
					// for (Element subject : subjects) {
					// String nameSubject = subject.text();
					// System.out.println( nameSubject);
					// }

					// for (int i = 0; i < 100; i++) {
					// getQuestion("Môn Toán - Tuần 9",
					// "https://hocsinh.lika.edu.vn/practice/index?skill_id=9676",
					// userAgent);
					try {
						getQuestionWeek("https://hocsinh.lika.edu.vn/classroom/subject?alias=toan&subject_id=1&week=1",
								userAgent);
					} catch (JSONException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// }

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 

				// Lấy thông tin của câu hỏi trong trang web

			}
		});

	}

	private static void getQuestionWeek(String url, String userAgent)
			throws IOException, JSONException, InterruptedException {
		Document weeksDoc = Jsoup.connect(url).cookies(response.cookies()).userAgent(userAgent).timeout(30 * 1000)
				.get();
		Element header = weeksDoc.selectFirst("h1[class=\"subject-name\"]");
		String subjectWeek = header.text(); // Ten mon hoc va tuan
		// System.out.println(subjectWeek);
		Elements links = weeksDoc.select("div[class=\"skill-item col-lg-4 col-sm-6\"]");
		for (Element link : links) {
			Element done = link.selectFirst("div[class=\"skill-status\"] p");
			if (!done.text().contains("Hoàn thành"))
			{
//				System.out.println(link.html());
				Element absHrefElement = link.selectFirst("a");
				System.out.println(absHrefElement.attr("abs:href"));
			}
			
			// String absHref = link.attr("abs:href");
			// for (int i = 0; i <= 20; i++) {
			// // TimeUnit.SECONDS.sleep(rd.nextInt(2));
			// String result = getQuestion(subjectWeek, absHref, userAgent);
			// if (result.equals("true"))
			// System.out.println(++count);
			// System.out.println("-----------------------------------------------------------------------");
			// }
		}

	}

	private static void getQuestionAllWeek(String url, String userAgent)
			throws IOException, JSONException, InterruptedException {
		// // Tap cac link lam bai
		// https://hocsinh.lika.edu.vn/classroom/subject?subject_id=1&alias=toan&week=35

		Document weeksDoc = Jsoup.connect(url).cookies(response.cookies()).userAgent(userAgent).timeout(30 * 1000)
				.get();
		Elements weeks = weeksDoc.select("div[class=list] a");
		for (Element week : weeks) {
			String absHrefWeek = week.attr("abs:href").toLowerCase();
			getQuestionWeek(absHrefWeek, userAgent);
		}
	}

	private static String getQuestion(String subjectWeek, String url, String userAgent)
			throws IOException, JSONException, InterruptedException {
		Document page = Jsoup.connect(url).cookies(response.cookies()).userAgent(userAgent).timeout(30 * 1000).get();
		Element quizElement = page.selectFirst("input[type=hidden]");
		String quiz = quizElement.attr("data-quiz");
		JSONObject quiz_json = new JSONObject(quiz);

		// id_ques,type,id_skill,skill_name

		int type, id_ques, id_skill;

		String skill_name, id_string;
		type = quiz_json.getInt("type");
		// type = 1;
		String typeString = Integer.toString(type);
		String id_quiz = Integer.toString(quiz_json.getInt("id_ques"));
		id_ques = quiz_json.getInt("id");
		String id_ques_string = Integer.toString(id_ques);
		// id_ques = 75927;
		// System.out.println(id_ques);
		id_string = Integer.toString(id_ques);
		id_skill = quiz_json.getInt("id_skill");
		skill_name = quiz_json.getString("skill_name");
		String giaithich = "";

		String correctAnswerString = "";
		String hint = "";
		String tempCorrectAnswerString = "";
		String answerTemp = "";
		String content = "";
		// Dap an dung va giai thich
		System.out.println(type + " | " + id_ques_string + " | " + id_skill + " | " + skill_name);
		String listAnswerCorrectToString = "";
		String status = "";
		if (type == 3) // Dang trac nghiem
		{
			answerTemp = "";
			// Lấy đáp án đúng
			Document getAnswerDoc = Jsoup.connect("http://schoolkid.tigerweb.vn/admin/ajax-question/view")
					.data("id", id_string).ignoreContentType(true).timeout(30 * 1000).post();
			if (!getAnswerDoc.body().text().equals("false")) {
				String answerString = getAnswerDoc.body().text();
				JSONObject answerJson = new JSONObject(answerString);
				// System.out.println(answerJson);
				answerTemp = answerJson.getString("note");
			}

			// Gửi đáp án

			Document correctAnswerDoc = Jsoup.connect("https://hocsinh.lika.edu.vn/ajax/answer-quiz")
					.cookies(response.cookies()).data("id_quiz", id_string).data("answer", answerTemp)
					.userAgent(userAgent).timeout(30 * 1000).post();
			String answerString = correctAnswerDoc.body().text();
			System.out.println(answerString);
			JSONObject answerJson = new JSONObject(answerString);
			JSONArray correctAnswer = (JSONArray) answerJson.get("dapan_quiz");
			tempCorrectAnswerString = correctAnswer.get(0).toString();
			// System.out.println(tempCorrectAnswerString);
			JSONObject statusJson = answerJson.getJSONObject("status_answer");
			status = statusJson.toString();

			giaithich = answerJson.getString("giaithich_quiz");

			// System.out.println(correctAnswerString);
			Element contentCorrectAnswerElement = page.selectFirst(
					"label[id=\"label_toggle" + tempCorrectAnswerString + "\"] div[class=\"label_content\"]");
			// JSONObject jsonObj = new JSONObject("{\"1\":\"" +
			// contentCorrectAnswerElement.text() + "\"}");
			correctAnswerString = "{\"1\":\"" + contentCorrectAnswerElement.text() + "\"}";
			// System.out.println(correctAnswerString);
			if (page.selectFirst("div[class=\"suggest-wrap\"]") != null) {
				Element hintElement = page.selectFirst("div[class=\"suggest-wrap\"]");
				hint = hintElement.html();
			}

			// System.out.println(contentCorrectAnswerElement);
			// System.out.println(correctAnswer.get(0));
			// System.out.println(giaithich);
			// Xử lý lấy câu hỏi
			Element question = page.selectFirst("div[class=info]");
			String[] words = question.html().split("<div", 2);
			content = words[0];
		} else {
			if (type == 1) { //
				answerTemp = "1|_|6|;|2|_|9|;|3|_|9|;|4|_|9|;|5|_|9|;|6|_|9";
				Document correctAnswerDoc = Jsoup.connect("https://hocsinh.lika.edu.vn/ajax/answer-quiz")
						.cookies(response.cookies()).data("id_quiz", id_string).data("answer", answerTemp)
						.userAgent(userAgent).timeout(30 * 1000).post();
				String answerString = correctAnswerDoc.body().text();
				JSONObject answerJson = new JSONObject(answerString);
				System.out.println(answerJson);
				giaithich = answerJson.getString("giaithich_quiz");
				// System.out.println(giaithich);
				JSONObject statusJson = answerJson.getJSONObject("status_answer");
				status = statusJson.toString();
				JSONObject answerCorrectJson = answerJson.getJSONObject("dapan_quiz");
				correctAnswerString = answerCorrectJson.toString();
				ArrayList<String> answerCorrectArray = new ArrayList<String>();
				int i = 1;
				while (!answerCorrectJson.isNull(Integer.toString(i))) {
					// System.out.println(answerCorrectJson.getString(Integer.toString(i)));
					answerCorrectArray.add(answerCorrectJson.getString(Integer.toString(i)));
					i++;
				}
				// System.out.println(answerCorrectArray.toString());

				for (i = 0; i < answerCorrectArray.size(); i++) {
					if (i > 0) {
						listAnswerCorrectToString += "|;|";
					}
					listAnswerCorrectToString += (i + 1) + "|_|" + answerCorrectArray.get(i);
				}
				// System.out.println(listAnswerCorrectToString);

				Element question = page.selectFirst("div[class=info]");
				content = question.html();
				// System.out.println(answerCorrectJson);
			} else {

			}
		}
		// System.out.println(status);
		// System.out.println(words[0]);
		Elements answers = page.select("div[class=label_content]");
		ArrayList<String> answerArray = new ArrayList<String>();
		for (Element answer : answers) {
			if (!answerArray.contains(answer.html()))
				answerArray.add(answer.html());
		}

		Element thematic_title_element = page.selectFirst("h3[class=title]");
		String thematic_title = thematic_title_element.text();
		String answerString = "";
		for (int i = 0; i < answerArray.size(); i++) {
			if (i == 0)
				answerString += "{";
			String end = "\",";
			if (i == answerArray.size() - 1)
				end = "\"}";
			answerString += "\"" + (i + 1) + "\":\"" + answerArray.get(i) + end;
		}
		String[] thematic = subjectWeek.split(" - ", 2);

		Document postData = Jsoup.connect("http://schoolkid.tigerweb.vn/admin/ajax-question/clone")
				.data("id", id_ques_string).data("content", content).data("thematic_type", "1")
				.data("thematic_title", thematic_title).data("explain", giaithich).data("hint", hint)
				.data("answer", answerString).data("answer_correct", correctAnswerString).data("image", "")
				.data("question_type", typeString).data("classroom_title", "Lớp 2").data("subject_title", thematic[0])
				.data("week_title", thematic[1]).data("note", tempCorrectAnswerString).ignoreContentType(true).post();
		if (postData.text().equals("true")) {

			System.out.println("Content: " + content);
			System.out.println("thematic_title: " + thematic_title);
			System.out.println("explain: " + giaithich);
			System.out.println("hint : " + hint);
			System.out.println("answer: " + answerString);
			System.out.println("answer_correct: " + correctAnswerString);
			System.out.println("question_type: " + typeString);
			System.out.println("subject_title: " + thematic[0]);
			System.out.println("week_title: " + thematic[1]);
			System.out.println("TempAnswerCorrect: " + tempCorrectAnswerString);
			false_num = 0;
		} else {
			false_num++;
			if (false_num >= 5) {
				if (type == 1) {
					tempCorrectAnswerString = listAnswerCorrectToString;
				}
				String numRan = Integer.toString(rd.nextInt(10));
				Document correctAnswerDoc = Jsoup.connect("https://hocsinh.lika.edu.vn/ajax/save-report-quiz-coolpoint")
						.cookies(response.cookies()).data("id_question", id_quiz)
						.data("answer", tempCorrectAnswerString).data("time", numRan).data("ketqua", status)
						.data("is_quiz_level", "0").data("skill_id", Integer.toString(id_skill)).userAgent(userAgent)
						.timeout(30 * 1000).post();
				System.out.println("?????????????????????????????????");
				System.out.println("InforHack");
				System.out.println("id_question: " + id_quiz);
				System.out.println("Ketqua: " + status);
				System.out.println("answer: " + tempCorrectAnswerString);
				System.out.println("time: " + numRan);
				System.out.println("skill_id: " + Integer.toString(id_skill));
				System.out.println("Hack: " + correctAnswerDoc.html());
				System.out.println("?????????????????????????????????");
				TimeUnit.SECONDS.sleep(rd.nextInt(10));
				//
			}

		}
		return postData.text();
		// return "a";

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
