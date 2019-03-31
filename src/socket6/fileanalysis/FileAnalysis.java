package socket6.fileanalysis;

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
			//初始化参数
			serverSocket = new ServerSocket(fileAnaPort);
			System.out.println("文件分析服务器"+fileAnaPort+"已启动：");
			
			
			while (true) {
				//获得连接
				accept = serverSocket.accept();
				//创建新线程处理连接
				new FileAnalysisThread(accept,fileAnaPort);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//关闭连接
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}

