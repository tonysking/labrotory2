package socket7.taskdistribute;

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

import socket7.workstation.WorkManager;

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
	
	private WorkManager workManager;
	
	static {
		fileAnaNumToPort = new HashMap<>();
		fileAnaNumToPort.put(0, 30000);
		fileAnaNumToPort.put(1, 30001);
		fileAnaNumToPort.put(2, 30002);
	}
	
	
	public TaskDistributeThread(Socket socket, int count, WorkManager workManager) {

		this.socket = socket;
		
		fileAnaNum = count;
		
		this.workManager = workManager;
		
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
				WorkPort = split[1];
				WorkIP = split[2];
				//---------
				workManager.sendState("�Ѿ���ȡ����վ"+WorkPort+"��������");
				
				//����Ϣת�����ļ�����������fileAnaNum
//				for (Integer filePort : fileAnaNumToPort.values()) {
//					TaskToFile forward = new TaskToFile(filePort,filesNum,WorkIP,WorkPort);
//					forward.taskToFile();
//				}
				String mes = fileAnaNumToPort.get(fileAnaNum)+"   "+filesNum+"   ����վIP:"+WorkIP+"   ����վ�˿ڣ�"+WorkPort+"   "+workManager;
				System.out.println("�������ݣ�"+mes);
					
					TaskToFile forward = new TaskToFile(fileAnaNumToPort.get(fileAnaNum),filesNum,WorkIP,WorkPort,workManager);
					forward.taskToFile();
				
				
			}
			
//			writer.write("end\n");//------��������
//			writer.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
}
