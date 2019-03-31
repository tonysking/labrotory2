package socket7.fileanalysis;

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

import socket7.workstation.WorkManager;

public class FileAnalysis extends Thread{
	private int fileAnaPort;
	public FileAnalysis(int fileAnaPort) {
		this.fileAnaPort = fileAnaPort;
		start();
	}
	private static Socket accept;
	private static ServerSocket serverSocket;
	public void run() {
		try {
			//��ʼ������
			serverSocket = new ServerSocket(fileAnaPort);
			System.out.println("�ļ�����������"+fileAnaPort+"��������");
			
			//---------�����ƽ̨���ͳ�ʼ״̬
			String workName = "������"+fileAnaPort;
			String workState = "������"+fileAnaPort+"������";
			WorkManager workManager = new WorkManager();
			workManager.createState(workName, "127.0.0.1", String.valueOf(fileAnaPort), workState);
			
			while (true) {
				//�������
				accept = serverSocket.accept();
				System.out.println(accept);
				//-----
				if (accept!=null) {
					
					workManager.sendState("�Ѻ�����������������");
				}
				
				//�������̴߳�������
				new FileAnalysisThread(accept,fileAnaPort,workManager);
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

