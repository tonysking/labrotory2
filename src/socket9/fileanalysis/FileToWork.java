package socket9.fileanalysis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import socket9.workstation.WorkManager;

public class FileToWork {

	private String filesNum;//文件数目
	private String WorkIP;//工作站IP
	private String WorkPort;//工作站端口
	
	private Socket workSocket;//连接工作站
	private OutputStream out;
	private InputStream in;
	
	private WorkManager workManager;
	
	public FileToWork(String filesNum, String workPort, String workIP, WorkManager workManager) {

		this.filesNum = filesNum;
		this.WorkIP = workIP;
		this.WorkPort = workPort;
		
		this.workManager = workManager;
		
		try {
			//System.out.println("工作站端口转int:"+Integer.parseInt(workPort));//---若端口为split最后一项可能会出错
			workSocket = new Socket("127.0.0.1", Integer.parseInt(workPort));
			if (workSocket!=null) {
				workManager.sendState("已与工作站"+WorkPort+"建立连接");
			}
			in = workSocket.getInputStream();
			out = workSocket.getOutputStream();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	//连接工作站
	public void connectWork(int fileAnaPort) {
		int filesCount = Integer.parseInt(filesNum);
		System.out.println("文件数量："+filesCount);
			//接收请求数据
			int length;
			try {
				while (filesCount>0) {
					filesCount--;
					//处理txt文件
					byte[] b = new byte[2024];//接受数组
					byte[] bres = new byte[2024];//反馈数组
					
					length = in.read(b);
					String recMess = new String(b);//接受字符
					String filesName = recMess.split("#")[0];//文件名
					System.out.println("服务器"+fileAnaPort+"读取内容："+new String(b));
					
					//发送反馈数据
					String wriMess =null;
					wriMess = recMess.split("#")[1].substring(0, 8)+String.valueOf(length);//前8个字节及总长度
					out.write(wriMess.getBytes());
					out.flush();
					
					int fileLength = length - filesName.length() -1;//文件长度=总长度 -文件名长度-#
					System.out.println("服务器"+fileAnaPort+"反馈内容："+wriMess+"长度："+fileLength);
					//----
					workManager.sendState("已向工作站"+WorkPort+"发送文件处理结果");
					
					
					//写入日志
					FileWriteToLog fileWriteToLog = new FileWriteToLog(filesName,WorkIP,WorkPort,wriMess,fileLength);
					fileWriteToLog.writeToLog();
					//----
					workManager.sendState("已写入日志！！！！！！");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
					out.close();
					workSocket.close();
					//文件分析服务器一直等待新的任务 需要不断向管理平台发送消息 故不需要关闭[否则报socket closed异常]
					//workManager.closeSocket();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}

}
