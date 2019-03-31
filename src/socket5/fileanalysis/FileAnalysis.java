package socket5.fileanalysis;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileAnalysis {
	private static int port = 30000;
	private static Socket accept;
	private static ServerSocket serverSocket;
	private static Path pathIn = Paths.get("D:\\", "JAR\\","TXTsocket\\");
	public static void main(String[] args) {
		try {
			//��ʼ������
			serverSocket = new ServerSocket(port);
			System.out.println("�ļ�������������������");
			
			
			while (true) {
				//�������
				accept = serverSocket.accept();
				//�������̴߳�������
				new FileAnalysisThread(accept);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//�ر�����
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}

