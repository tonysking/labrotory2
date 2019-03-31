package socket8.fileanalysis;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import socket8.workstation.WorkManager;

public class FileAnalysisThread extends Thread {

	
	private Socket socket;
	private OutputStream out;
	private InputStream in;
	
	private int fileAnaPort;//文件分析服务器端口
	
	private WorkManager workManager;
	
	public FileAnalysisThread(Socket socket, int fileAnaPort, WorkManager workManager) {

		this.socket = socket;
		
		this.fileAnaPort = fileAnaPort;
		
		this.workManager = workManager;
		
		//start();
	}
	public void run() {
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();
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
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
