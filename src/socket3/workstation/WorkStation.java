package socket3.workstation;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/*
 * ������������������  �ļ���Ŀ��IP�Ͷ˿�
 */
public class WorkStation {
	
	private static String host = "127.0.0.1";
	private static int port = 20000;//��������������˿�
	private static Socket socket;
	private static int filesPort = 10000;//����վ�����˿ڣ��ļ��������������Ӷ˿�
	private static String workIP;//����վip
	private static ServerSocket serverSocket;//�ȴ��ļ���������������
	private static OutputStream out;
	private static InputStream in;
	private static Path pathIn = Paths.get("D:\\", "JAR\\","TXTsocket\\");
	public static void main(String[] args) {
		try {
			//��ʼ������
			
			socket = new Socket(host, port);
			in = socket.getInputStream();
			out = socket.getOutputStream();
			workIP = socket.getInetAddress().getHostAddress();
			
			int FilesLen = pathIn.toFile().listFiles().length;//�ļ�����
			//������������������ ���ļ�����--�˿�--IP��
			String join = String.join("--", String.valueOf(FilesLen),String.valueOf(filesPort),workIP,"\nend\n");
			PrintWriter writer = new PrintWriter(out);
			writer.write(join);
			writer.flush();
			System.out.println("����վ֪ͨ���ݣ�"+join);
			//������������������������
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
			String readLine= null;
			//in.read(b);
			while (!"end".equals(readLine=bufferedReader.readLine())) {
				System.out.println("����վ�������ݣ�"+readLine);
			}	
			
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
