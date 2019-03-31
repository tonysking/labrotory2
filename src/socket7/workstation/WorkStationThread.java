package socket7.workstation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
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
	//private static Path pathIn = Paths.get("D:\\", "JAR\\","socket\\","TXTsocket\\");
	private static File fileIn = null;
	
	public WorkStationThread(int filsPort, int fileDiri) {

		this.filesPort = filsPort;
		System.out.println("����վ"+filesPort+"��������");
		
		fileIn = new File("D:\\JAR\\TXTsocket\\txt"+fileDiri);//��ȡ�ļ�Ŀ¼
		System.out.println(fileIn);
		start();
	}
	
	public void run() {
		try {
			//��ʼ������
			socket = new Socket(host, port);
			in = socket.getInputStream();
			out = socket.getOutputStream();
			workIP = socket.getInetAddress().getHostAddress();
			
			//---------�����ƽ̨���ͳ�ʼ״̬
			String workName = "����վ"+filesPort;
			String workState = "����վ������";
			WorkManager workManager = new WorkManager();
			workManager.createState(workName, workIP, String.valueOf(filesPort), workState);
			
			
			//�����ļ�����
//			int filesLen = 0;
//			DirectoryStream<Path> entries = Files.newDirectoryStream(pathIn,"*.txt");
//			for (Path path : entries) {
//				filesLen++;
//			}
			int FilesLen = fileIn.listFiles().length;//�ļ�����
			System.out.println(FilesLen);
			//������������������ ���ļ�����--�˿�--IP��
			String join = String.join("--", String.valueOf(FilesLen),String.valueOf(filesPort),workIP,"\nend\n");
			PrintWriter writer = new PrintWriter(out);
			writer.write(join);
			writer.flush();
			System.out.println("����վ"+filesPort+"֪ͨ���ݣ�"+join);
			//----------�����ƽ̨����״̬
			workManager.sendState("�Ѿ�����������������������");
			//������������������������
//			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
//			String readLine= null;
//			while (!"end".equals(readLine=bufferedReader.readLine())) {
//				System.out.println("����վ�����������������������ݣ�"+readLine);
//			}
			
			//�ȴ��ļ���������������
			workManager.sendState("�ȴ��ļ���������������...");
			
			WorkToFile workToFile = new WorkToFile(filesPort,workManager);
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
