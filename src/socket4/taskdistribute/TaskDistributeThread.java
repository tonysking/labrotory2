package socket4.taskdistribute;

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
import java.util.HashMap;
import java.util.Map;

public class TaskDistributeThread extends Thread {

	//���չ���վ����
	private static String filesNum;//�ļ���Ŀ
	private static String WorkIP;//����վIP
	private static String WorkPort;//����վ�˿�
	private Socket socket;
	private OutputStream out;
	private InputStream in;
	//ת�����ļ�����������
	private static Map<Integer, Integer> fileAnaNumToPort;//�ļ�����������<���,�˿�>
	
	static {
		fileAnaNumToPort = new HashMap<>();
		fileAnaNumToPort.put(1, 30000);
	}
	
	
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
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			PrintWriter writer = new PrintWriter(out);
			while (!"end".equals(readLine=bufferedReader.readLine())) {
				System.out.println("��������������ȡ���ݣ�"+readLine);
				writer.write(readLine);
				//��ȡ����վ[�ļ���Ŀ,IP,�˿ں�]
				String[] split = readLine.split("--");
				filesNum = split[0];
				WorkIP = split[1];
				WorkPort = split[2];
				//����Ϣת�����ļ�����������
				TaskToFile forward = new TaskToFile(fileAnaNumToPort.get(1),filesNum,WorkIP,WorkPort);
				forward.taskToFile();
			}
			
			writer.write("end\n");
			writer.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
