package socket8.fileanalysis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import socket8.workstation.WorkManager;

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
			System.out.println("工作站端口转int:"+Integer.parseInt(workPort));
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
	
	public void connectWork(int fileAnaPort) {
		int filesCount = Integer.parseInt(filesNum);
		System.out.println("文件数量："+filesCount);
			//接收请求数据
			int length;
			try {
				while (filesCount>0) {
					filesCount--;
					//处理txt文件
					byte[] b = new byte[1024];//接受数组
					byte[] bres = new byte[1024];//反馈数组
					length = in.read(b);
					System.out.println("服务器"+fileAnaPort+"读取内容："+new String(b));
					
					//发送反馈数据
					System.arraycopy(b, 0, bres, 0, 8);//前8个字节(src表示源数组 desc表示目标数组 length表示要复制的长度)
					byte[] lenb = String.valueOf(length).getBytes();
					//byte[] lenb = new byte[10];
					System.arraycopy(lenb, 0, bres, 8, 4);//文件长度(int为4个字节)
					out.write(bres);
					out.flush();
					System.out.println("服务器"+fileAnaPort+"反馈内容："+new String(bres)+"长度："+length);
					//---
					workManager.sendState("已向工作站"+WorkPort+"发送文件处理结果");
					
					
					//写入日志
					String log = new String(bres);
					FileWriteToLog fileWriteToLog = new FileWriteToLog(WorkIP,WorkPort,log,length);
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
					
					workManager.closeSocket();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}

}
