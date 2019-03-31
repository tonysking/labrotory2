package socket8.fileanalysis;

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

import socket8.workstation.WorkManager;

public class FileAnalysis extends Thread{
	private int fileAnaPort;
	private Socket accept;
	private ServerSocket serverSocket;
	public FileAnalysis(int fileAnaPort) {
		this.fileAnaPort = fileAnaPort;
		//start();
	}
	public void run() {
		try {
			//初始化参数
			serverSocket = new ServerSocket(fileAnaPort);
			System.out.println("文件分析服务器"+fileAnaPort+"已启动：");
			
			//---------向管理平台发送初始状态
			String workName = "服务器"+fileAnaPort;
			String workState = "服务器"+fileAnaPort+"已启动";
			WorkManager workManager = new WorkManager();
			workManager.createState(workName, "127.0.0.1", String.valueOf(fileAnaPort), workState);
			
			while (true) {
				//获得连接
				accept = serverSocket.accept();
				System.out.println(accept);
				//-----
				if (accept!=null) {
					
					workManager.sendState("已和任务分配服务器连接");
				}
				
				//创建新线程处理连接
				//new FileAnalysisThread(accept,fileAnaPort,workManager);
				InputStream in = accept.getInputStream();
				OutputStream out = accept.getOutputStream();
				try {
					//读取全部内容
					byte[] all = new byte[1024];
					int len = in.read(all);
					String splitAll = new String(all,0,len);
					String[] split = splitAll.split("--");
					String filesNum = split[0];
					System.out.println("文件数量:"+filesNum);
					
					String WorkPort = split[1];
					System.out.println("工作站端口:"+WorkPort);
					
					String WorkIP = split[2];
					System.out.println("工作站IP:"+WorkIP);
					
					//--------
					workManager.sendState("已接收文件分配服务器内容");
					
					//主动与工作站建立连接
					if (filesNum!=null) {
						FileToWork fileToWork = new FileToWork(filesNum,WorkPort,WorkIP,workManager);
						fileToWork.connectWork(fileAnaPort);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					//关闭流和连接
					try {
						in.close();
						out.close();
						accept.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
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

