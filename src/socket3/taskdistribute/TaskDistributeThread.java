package socket3.taskdistribute;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.nio.CharBuffer;

public class TaskDistributeThread extends Thread {

	private static String filesNum;//�ļ���Ŀ
	private static String WorkIP;//����վIP
	private static int WorkPort;//����վ�˿�
	private Socket socket;
	private OutputStream out;
	private InputStream in;
	public TaskDistributeThread(Socket socket) {

		this.socket = socket;
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		start();
	}
	public void run() {
		byte[] b = new byte[1024];//��������
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			PrintWriter writer = new PrintWriter(out);
			while (!"end".equals(readLine=bufferedReader.readLine())) {
				System.out.println("��������������ȡ���ݣ�"+readLine);
				writer.write(readLine);
			}
			writer.write("end\n");
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
