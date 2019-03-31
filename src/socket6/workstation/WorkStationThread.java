package socket6.workstation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WorkStationThread extends Thread {

	//�������������
	private static Socket socket;
	private static String host = "127.0.0.1";
	private static int port = 20000;
	private OutputStream out;
	private InputStream in;
	//����վip�ͼ����˿�
	private static String workIP;
	private int filesPort;
	//��ȡ�ļ�Ŀ¼
	private static Path pathIn = Paths.get("D:\\", "JAR\\","socket\\","TXTsocket\\");
	
	public WorkStationThread(int filsPort) {

		this.filesPort = filsPort;
		System.out.println("����վ"+filesPort+"��������");
		start();
	}
	
	public void run() {
		try {
			//��ʼ������
			socket = new Socket(host, port);
			in = socket.getInputStream();
			out = socket.getOutputStream();
			workIP = socket.getInetAddress().getHostAddress();
			//�����ļ�����
			int FilesLen = pathIn.toFile().listFiles().length;//�ļ�����
			//������������������ ���ļ�����--�˿�--IP��
			String join = String.join("--", String.valueOf(FilesLen),String.valueOf(filesPort),workIP,"\nend\n");
			PrintWriter writer = new PrintWriter(out);
			writer.write(join);
			writer.flush();
			System.out.println("����վ"+filesPort+"֪ͨ���ݣ�"+join);
			//������������������������
//			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
//			String readLine= null;
//			while (!"end".equals(readLine=bufferedReader.readLine())) {
//				System.out.println("����վ�����������������������ݣ�"+readLine);
//			}
			
			//�ȴ��ļ���������������
			WorkToFile workToFile = new WorkToFile(filesPort);
			workToFile.transFiles();//�����ļ�
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//�ر���������
			try {
				in.close();
				out.close();
				socket.close();
				//serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
