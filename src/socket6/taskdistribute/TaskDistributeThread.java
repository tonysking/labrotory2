package socket6.taskdistribute;

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
	private int fileAnaNum;
	
	static {
		fileAnaNumToPort = new HashMap<>();
		fileAnaNumToPort.put(0, 30000);
		fileAnaNumToPort.put(1, 30001);
		fileAnaNumToPort.put(2, 30002);
	}
	
	
	public TaskDistributeThread(Socket socket, int count) {

		this.socket = socket;
		
		fileAnaNum = count;
		
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
				//writer.write(readLine);//----��������
				//��ȡ����վ[�ļ���Ŀ,IP,�˿ں�]
				String[] split = readLine.split("--");
				filesNum = split[0];
				WorkIP = split[1];
				WorkPort = split[2];
				//����Ϣת�����ļ�����������fileAnaNum
//				for (Integer filePort : fileAnaNumToPort.values()) {
//					TaskToFile forward = new TaskToFile(filePort,filesNum,WorkIP,WorkPort);
//					forward.taskToFile();
//				}
				
				TaskToFile forward = new TaskToFile(fileAnaNumToPort.get(fileAnaNum),filesNum,WorkIP,WorkPort);
				forward.taskToFile();
				
				
			}
			
//			writer.write("end\n");//------��������
//			writer.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
