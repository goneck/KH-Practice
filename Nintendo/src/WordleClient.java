import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class WordleClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Socket socket = null;
		int port = 8888;
		String address = "127.0.0.1";
		InputStream is = null;
		OutputStream os = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;

		String message = "";
		Scanner sc = new Scanner(System.in);
		StringTokenizer st;

		try {
			socket = new Socket(address, port);
			System.out.println("------- Wordle Loading --------");
			System.out.println("");
			Thread.sleep(1000);
			System.out.println(">>>>>>> Start Wordle! <<<<<<<<");
			System.out.println("");
			is = socket.getInputStream();
			os = socket.getOutputStream();
			dis = new DataInputStream(is);
			dos = new DataOutputStream(os);

			while (true) {
				System.out.print("5글자 영단어 입력 >> ");
				message = sc.nextLine();
				dos.writeUTF(message);
				if (message.equals("exit")) {
					break;
				}

				String result = dis.readUTF();
				if (result.equals("맞혔습니다.")) {
					System.out.println("맞혔습니다!");
					break;
				} else {
					System.out.println(result);
				}
				System.out.println("");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dos.close();
				dis.close();
				is.close();
				os.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
