package socket8.taskdistribute;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import socket8.workstation.WorkManager;
/*
 * 将[工作站]传来的信息转发给[文件分析服务器]
 */
public class TaskToFile {


	private int fileAnaPort;//文件分析服务器端口
	private String filesNum;//文件数目
	private String WorkIP;//工作站IP
	private String WorkPort;//工作站端口
	
	private Socket fileSocket;//连接文件分析服务器
	private OutputStream out;
	private InputStream in;
	

	private WorkManager workManager;

	public TaskToFile(Integer fileAnaPort, String filesNum, String workIP, String workPort, WorkManager workManager) {
		this.fileAnaPort = fileAnaPort;
		this.filesNum = filesNum;
		this.WorkIP = workIP;
		this.WorkPort = workPort;
		
		this.workManager = workManager;
		
		
		try {
			fileSocket = new Socket("127.0.0.1", fileAnaPort);
			System.out.println("fileAnaPort"+fileSocket);
			in = fileSocket.getInputStream();
			out = fileSocket.getOutputStream();
			if (fileSocket!=null) {
				workManager.sendState("已分配给文件分析服务器"+fileAnaPort);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void taskToFile() {
		try {
			String sp = "--";
			byte[] spb = sp.getBytes();
			String mess = filesNum+"--"+WorkPort+"--"+WorkIP;
			out.write(mess.getBytes());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				fileSocket.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
