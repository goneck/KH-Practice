import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.StringTokenizer;

public class WordleServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ServerSocket serverSocket = null;
		int port = 8888;
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;

		String inputMessage = "";

		ArrayList<String> wordList = new ArrayList<>();

		String outputMessage = "";

		wordList.add("apple");
		wordList.add("array");
		wordList.add("color");
		wordList.add("loyal");
		wordList.add("power");
		wordList.add("trash");
		wordList.add("basic");
		wordList.add("beach");
		wordList.add("crazy");
		wordList.add("phone");
		wordList.add("solid");
		wordList.add("glass");
		wordList.add("tower");
		wordList.add("money");
		wordList.add("eager");
		wordList.add("alert");
		wordList.add("death");
		wordList.add("diner");
		wordList.add("depth");
		wordList.add("fifth");
		wordList.add("flood");
		wordList.add("hight");
		wordList.add("hobby");
		wordList.add("input");
		wordList.add("avoid");
		wordList.add("green");
		wordList.add("china");
		wordList.add("layer");
		wordList.add("hello");
		wordList.add("world");

		try {
			serverSocket = new ServerSocket(port);
			System.out.println("--------- Wordle ----------");
			Thread.sleep(500);
			socket = serverSocket.accept();
			System.out.println("게임을 시작합니다.");

			is = socket.getInputStream();
			os = socket.getOutputStream();
			dis = new DataInputStream(is);
			dos = new DataOutputStream(os);

			Random rand = new Random();
			int index = rand.nextInt(wordList.size());

			String word = wordList.get(index);
			System.out.println("서버의 단어 : " + word);
			while (true) {
				inputMessage = dis.readUTF();
				System.out.println("플레이어 입력 단어 : " + inputMessage);
				if (inputMessage.endsWith("exit")) {
					break;
				}

				if (inputMessage.equals(word)) {
					outputMessage = "맞혔습니다.";
					dos.writeUTF(outputMessage);
					break;
				}

				else {
					char[] resultChar = new char[word.length()];
					HashSet<Character> hashChar = new HashSet<>();
					for (int i = 0; i < resultChar.length; i++) {
						resultChar[i] = '_';
					}

					for (int i = 0; i < inputMessage.length(); i++) {
						for (int j = 0; j < word.length(); j++) {
							if (inputMessage.charAt(i) == word.charAt(j)) {
								if (i == j) {
									resultChar[i] = word.charAt(j);
								} else {
									hashChar.add(word.charAt(j));
								}
							}
//							System.out.println("word index: " + i + " input index: " + j + " hashChar: "
//									+ hashChar.toString());
						}
					}
					String resultStr = "";
					for (int i = 0; i < resultChar.length; i++) {
						if (hashChar.contains(resultChar[i])) {
							hashChar.remove(resultChar[i]);
						}
						resultStr += (resultChar[i] + " ");
					}

					if (hashChar.size() == 0) {
						outputMessage = resultStr;
					} else {
						outputMessage = resultStr + " \n 위치 틀림 : " + hashChar.toString();
					}
					dos.writeUTF(outputMessage);
				}
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dos.close();
				dis.close();
				is.close();
				os.close();
				socket.close();
				serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
