package test;
//Tuần 12

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JList;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import javafx.scene.control.PasswordField;

import javax.swing.SwingConstants;

public class Form2 extends JFrame {

	private JPanel contentPane;
	private  JTextField txtUser;
	private  JTextField txtUrl;
	private static Connection.Response response;
	private static String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36";
	private static int count = 0;
	private  JPasswordField passwordField;
	private static JComboBox comboBox = new JComboBox<>();
	static String divName = "box basic";
	static Document pageUrl;
	static ArrayList<String> listHref = new ArrayList<String>();
	static ArrayList<String> listName = new ArrayList<String>();
	static TreeMap<String, String> hrefName = new TreeMap<String, String>();
	static int false_num = 0;
	static Random rd = new Random();
	static int thematic_type = 0;
	static int numRanInt = 0;
	static int ketqua = 0;
	static int point = 0;
	static JComboBox comboMonhoc = new JComboBox();
	static JLabel lblMonHoc = DefaultComponentFactory.getInstance().createTitle("Môn học");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				Form2 frame = new Form2();
				frame.setVisible(true);
				Connection.Response loginForm;

			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Form2() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 852, 398);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(10, 55, 71, 14);
		contentPane.add(lblUsername);

		txtUser = new JTextField();
		txtUser.setBounds(91, 52, 207, 20);
		contentPane.add(txtUser);
		txtUser.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(359, 55, 61, 14);
		contentPane.add(lblPassword);

		JLabel lblClass = new JLabel("Class");
		lblClass.setBounds(519, 14, 34, 14);
		contentPane.add(lblClass);

		comboBox.setBounds(563, 11, 107, 20);
		contentPane.add(comboBox);
		comboBox.addItem("Lớp 1");
		comboBox.addItem("Lớp 2");
		comboBox.addItem("Lớp 3");
		comboBox.addItem("Lớp 4");
		comboBox.addItem("Lớp 5");

		// lblTenMonHoc.setText("aaaaaa");

		JLabel lblUrl = new JLabel("Link Website");
		lblUrl.setBounds(10, 14, 92, 14);
		contentPane.add(lblUrl);

		txtUrl = new JTextField();
		txtUrl.setBounds(93, 11, 374, 20);
		contentPane.add(txtUrl);
		txtUrl.setColumns(10);
		JButton btnAllCB = new JButton("Search All");
		btnAllCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (String href : listHref) {

					// System.out.println(href);
					try {
						getQuestionAllWeek(href, userAgent);
					} catch (IOException | JSONException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});
		btnAllCB.setBounds(511, 107, 107, 20);
		contentPane.add(btnAllCB);
		JLabel label1 = new JLabel("Tên bài học");
		label1.setBounds(158, 194, 71, 14);

		contentPane.add(label1);

		JLabel soLuongCu = new JLabel("0");
		soLuongCu.setBounds(359, 239, 61, 14);
		contentPane.add(soLuongCu);

		JLabel soLuongMoi = new JLabel("0");
		soLuongMoi.setBounds(482, 239, 71, 14);
		contentPane.add(soLuongMoi);
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(680, 10, 146, 62);

		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					String responseUrl = txtUrl.getText();
					response = Jsoup.connect(responseUrl).userAgent(userAgent).method(Connection.Method.GET).execute();
					response = Jsoup.connect(responseUrl).cookies(response.cookies()).userAgent(userAgent)
							.data("LoginForm[username]", txtUser.getText(), "LoginForm[password]", passwordField.getText())
							.data("save_login", "1").followRedirects(false).method(Connection.Method.POST)
							.followRedirects(true).timeout(30 * 1000).execute();
					pageUrl = Jsoup.connect("https://hocsinh.lika.edu.vn/classroom/detail").cookies(response.cookies())
							.userAgent(userAgent).timeout(30 * 1000).get();
					Element pageElement = pageUrl
							.selectFirst("div[class=\"" + divName + "\"] ul[class=\"row align-items-center\"]");
					// System.out.println(pageElement.html());
					Elements hrefs = pageElement.select("a");
					// System.out.println(hrefs.html());
					listHref = new ArrayList<String>();
					listName = new ArrayList<String>();
					hrefName = new TreeMap<String, String>();
					for (Element href : hrefs) {
						String absHref = href.attr("abs:href");
						listHref.add(absHref);
					}

					Elements subjects = pageElement.select("span");
					for (Element subject : subjects) {
						String nameSubject = subject.text();
						listName.add(nameSubject);
					}
					comboMonhoc.removeAllItems();
					for (int i = 0; i < listName.size(); i++) {
						comboMonhoc.addItem(listName.get(i));
						hrefName.put(listName.get(i), listHref.get(i));
					}
					System.err.println(response.statusCode());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		contentPane.add(btnSubmit);

		passwordField = new JPasswordField();
		passwordField.setBounds(433, 52, 241, 20);
		contentPane.add(passwordField);

		JLabel lblSoluongCu = new JLabel("Số lượng cũ");
		lblSoluongCu.setBounds(339, 194, 81, 14);
		contentPane.add(lblSoluongCu);

		JLabel lblSoLuongMoi = new JLabel("Số lượng mới");
		lblSoLuongMoi.setBounds(453, 194, 87, 14);
		contentPane.add(lblSoLuongMoi);

		comboMonhoc.setBounds(127, 138, 226, 20);
		contentPane.add(comboMonhoc);

		JComboBox comboChuongtrinh = new JComboBox();
		comboChuongtrinh.setBounds(127, 107, 226, 20);
		contentPane.add(comboChuongtrinh);
		comboChuongtrinh.addItem("Chương trình cơ bản");
		comboChuongtrinh.addItem("Chương trình nâng cao");

		comboChuongtrinh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == comboChuongtrinh) {
					if (comboChuongtrinh.getSelectedItem().equals("Chương trình cơ bản")) {
						divName = "box basic";
						thematic_type = 1;
					} else {
						thematic_type = 2;
						divName = "box advanced";
					}

					Element pageElement = pageUrl
							.selectFirst("div[class=\"" + divName + "\"] ul[class=\"row align-items-center\"]");
					// System.out.println(pageElement.html());
					Elements hrefs = pageElement.select("a");
					// System.out.println(hrefs.html());
					listHref = new ArrayList<String>();
					listName = new ArrayList<String>();
					hrefName = new TreeMap<String, String>();
					for (Element href : hrefs) {
						String absHref = href.attr("abs:href");
						listHref.add(absHref);
					}

					Elements subjects = pageElement.select("span");
					for (Element subject : subjects) {
						String nameSubject = subject.text();
						listName.add(nameSubject);
					}
					comboMonhoc.removeAllItems();
					for (int i = 0; i < listName.size(); i++) {
						comboMonhoc.addItem(listName.get(i));
						hrefName.put(listName.get(i), listHref.get(i));
					}

				}
			}
		});

		JLabel lblChonChuongTrinh = new JLabel("Chọn chương trình");
		lblChonChuongTrinh.setBounds(10, 110, 122, 14);
		contentPane.add(lblChonChuongTrinh);

		JLabel lblChonMonHoc = new JLabel("Chọn môn học");
		lblChonMonHoc.setBounds(10, 141, 122, 14);
		contentPane.add(lblChonMonHoc);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.out.println(hrefName.get(comboMonhoc.getSelectedItem()));

				// System.out.println(hrefName.get(comboMonhoc.getSelectedItem()));
				// System.out.println(Integer.toString(thematic_type));

				try {
					// System.out.println("ComboBox: " + comboBox.getSelectedItem().toString());
					// System.out.println(hrefName.get(comboMonhoc.getSelectedItem()));
					getQuestionAllWeek(hrefName.get(comboMonhoc.getSelectedItem()), userAgent);

				} catch (IOException | JSONException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnSearch.setBounds(409, 106, 89, 23);
		contentPane.add(btnSearch);

		lblMonHoc.setBounds(168, 239, 88, 14);
		contentPane.add(lblMonHoc);
		setResizable(false);

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
			if (!done.text().contains("Hoàn thành")) {
				// System.out.println(link.html());
				Element absHrefElement = link.selectFirst("a");
				String absHref = absHrefElement.attr("abs:href");
				System.out.println(absHref);
				for (int i = 0; i < 50; i++) {
					// TimeUnit.SECONDS.sleep(rd.nextInt(2));
					String result = getQuestion(subjectWeek, absHref, userAgent);
					if (result.equals("true"))
						System.out.println(++count);
					if (result.equals("Lỗi rồi") || result.equals("Chuyển hướng") || result.equals("Hoàn thành"))
						break;
					System.out.println("-----------------------------------------------------------------------");
				}
			}
		}

	}

	private static void getQuestionAllWeek(String url, String userAgent)
			throws IOException, JSONException, InterruptedException {
		// // Tap cac link lam bai
		// https://hocsinh.lika.edu.vn/classroom/subject?subject_id=1&alias=toan&week=35

		Document weeksDoc = Jsoup.connect(url).cookies(response.cookies()).userAgent(userAgent).timeout(300 * 1000)
				.get();
		Elements weeks = weeksDoc.select("div[class=list] a");
		for (Element week : weeks) {
			String absHrefWeek = week.attr("abs:href").toLowerCase();
			getQuestionWeek(absHrefWeek, userAgent);
		}
	}

	private static String getQuestion(String subjectWeek, String url, String userAgent)
			throws IOException, JSONException, InterruptedException {
		String[] thematic = subjectWeek.split(" - ", 2);
		try {
			Document page = Jsoup.connect(url).cookies(response.cookies()).userAgent(userAgent).timeout(300 * 1000)
					.get();

			// System.out.println(page.text());
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

			if (type == 3) // Dang trac nghiem
			{
				answerTemp = "C";
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
						.userAgent(userAgent).timeout(300 * 1000).post();
				answerTemp = (answerTemp.equals("C") == true) ? "D" : "C";
				String answerString = correctAnswerDoc.body().text();
				System.out.println(answerString);

				JSONObject answerJson = new JSONObject(answerString);
				JSONArray correctAnswer = (JSONArray) answerJson.get("dapan_quiz");
				tempCorrectAnswerString = correctAnswer.get(0).toString();
				// System.out.println(tempCorrectAnswerString);

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
					answerTemp = "";
					for (i = 0; i < answerCorrectArray.size(); i++) {
						if (i > 0) {
							listAnswerCorrectToString += "|;|";
							answerTemp += "|;|";
						}
						listAnswerCorrectToString += (i + 1) + "|_|" + answerCorrectArray.get(i);
						answerTemp += (i + 1) + "|_|" + (answerCorrectArray.get(i) + 1 + rd.nextInt(10));

					}
					// System.out.println(listAnswerCorrectToString);
					correctAnswerDoc = Jsoup.connect("https://hocsinh.lika.edu.vn/ajax/answer-quiz")
							.cookies(response.cookies()).data("id_quiz", id_string)
							.data("answer", listAnswerCorrectToString).userAgent(userAgent).timeout(30 * 1000).post();
					answerString = correctAnswerDoc.body().text();
					answerJson = new JSONObject(answerString);
					giaithich = answerJson.getString("giaithich_quiz");
					// System.out.println(giaithich);

					Element question = page.selectFirst("div[class=info]");
					content = question.html();
					// System.out.println(answerCorrectJson);
				} else { // Xử lý các type khác

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

			// System.out.println("Content: " + content);
			// System.out.println("thematic_type: " + thematic_type);
			// System.out.println("thematic_title: " + thematic_title);
			// System.out.println("explain: " + giaithich);
			// System.out.println("hint : " + hint);
			// System.out.println("answer: " + answerString);
			// System.out.println("answer_correct: " + correctAnswerString);
			// System.out.println("question_type: " + typeString);
			// System.out.println("class_title: " + comboBox.getSelectedItem().toString());
			// System.out.println("subject_title: " + thematic[0]);
			// System.out.println("week_title: " + thematic[1]);
			// System.out.println("TempAnswerCorrect: " + tempCorrectAnswerString);

			Document postData = Jsoup.connect("http://schoolkid.tigerweb.vn/admin/ajax-question/clone")
					.data("id", id_ques_string).data("content", content)
					.data("thematic_type", Integer.toString(thematic_type)).data("thematic_title", thematic_title)
					.data("explain", giaithich).data("hint", hint).data("answer", answerString)
					.data("answer_correct", correctAnswerString).data("image", "").data("question_type", typeString)
					.data("classroom_title", comboBox.getSelectedItem().toString()).data("subject_title", thematic[0])
					.data("week_title", thematic[1]).data("note", tempCorrectAnswerString).ignoreContentType(true)
					.post();
			if (postData.text().equals("true")) {

				System.out.println("Content: " + content);
				System.out.println("thematic_type: " + thematic_type);
				System.out.println("thematic_title: " + thematic_title);
				System.out.println("explain: " + giaithich);
				System.out.println("hint : " + hint);
				System.out.println("answer: " + answerString);
				System.out.println("answer_correct: " + correctAnswerString);
				System.out.println("question_type: " + typeString);
				System.out.println("class_title: " + comboBox.getSelectedItem().toString());
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
					numRanInt = rd.nextInt(8) + 2;
					String numRan = Integer.toString(numRanInt);
					TimeUnit.SECONDS.sleep(numRanInt);

					if (thematic[0].equals("Môn Toán") || thematic[0].equals("Môn Toán Apmops")) {
						if (point < 80) {
							// System.out.println("Thematic[0]: " +thematic[0]);
							if (point < 30) {
								ketqua = 1;
							} else {
								ketqua = rd.nextInt(2);
								if (ketqua == 0) {
									tempCorrectAnswerString = answerTemp;
								}
							}
						} else {
							ketqua = 0;
							tempCorrectAnswerString = answerTemp;
						}
					} else {
						if (point < 20) {
							if (point < 10) {
								ketqua = 1;
							} else {
								ketqua = rd.nextInt(2);
								if (ketqua == 0) {
									tempCorrectAnswerString = answerTemp;
								}
							}
						} else {
							ketqua = 0;
							tempCorrectAnswerString = answerTemp;
						}
					}

					Document getPointDoc = Jsoup.connect("https://hocsinh.lika.edu.vn/ajax/save-report-quiz-coolpoint")
							.cookies(response.cookies()).data("id_question", id_quiz)
							.data("answer", tempCorrectAnswerString).data("time", numRan)
							.data("ketqua", Integer.toString(ketqua)).data("is_quiz_level", "0")
							.data("skill_id", Integer.toString(id_skill)).userAgent(userAgent).timeout(30 * 1000)
							.post();
					System.out.println("?????????????????????????????????");
					System.out.println("InforHack");
					System.out.println("id_question: " + id_quiz);
					System.out.println("Ketqua: " + ketqua);
					System.out.println("answer: " + tempCorrectAnswerString);
					System.out.println("time: " + numRan);
					System.out.println("skill_id: " + Integer.toString(id_skill));
					System.out.println("Hack: " + getPointDoc.html());
					String pointString = getPointDoc.body().text();
					JSONObject pointJson = new JSONObject(pointString);
					point = pointJson.getInt("coolpoint");
					System.out.println("point: " + point);
					System.out.println("?????????????????????????????????");

					//
				}

			}
			return postData.text();
		} catch (java.io.IOException e) {
			System.out.println("Web lỗi rồi");
			return "Lỗi rồi";
		} catch (java.lang.NullPointerException e) {
			System.out.println("nullPointer: " + point);
			if (point == 100) {
				return "Hoàn thành";
			} else {
				Document pages = Jsoup.connect(url).cookies(response.cookies()).userAgent(userAgent).timeout(300 * 1000)
						.get();
				if (!thematic[0].equals("Môn Toán") && !thematic[0].equals("Môn Toán Apmops")) {
					// System.out.println(page.html());
					Element checkElement = pages.selectFirst("div[class=header]");
					if (checkElement != null) {
						Element checkImg = checkElement.selectFirst("img");
						// System.out.println(checkImg.html());
						String imgCheck = checkImg.attr("src");
						String absHref = "";
						// System.out.println("img: "+imgCheck);
						if (imgCheck.equals("/images/female_finish_challenge_200x188.jpg")) {
							// System.out.println(imgCheck);
							// TimeUnit.SECONDS.sleep(5);
							Element pageChange = checkElement.selectFirst("a");
							absHref = pageChange.attr("abs:href");
							// System.out.println("Link: "+absHref);
							TimeUnit.SECONDS.sleep(8);
							getQuestion(subjectWeek, absHref, userAgent);
							TimeUnit.SECONDS.sleep(8);
							getQuestion(subjectWeek, absHref, userAgent);
							return "Chuyển hướng+ " + absHref;
						}
					}

				}
			}
		} catch (org.json.JSONException e) {
			System.out.println("Giải thích quá dài!");
		}
		return "";

	}

}
