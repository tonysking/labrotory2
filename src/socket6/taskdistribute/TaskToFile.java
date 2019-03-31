package socket6.taskdistribute;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
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
	
	
	

	public TaskToFile(Integer fileAnaPort, String filesNum, String workIP, String workPort) {
		this.fileAnaPort = fileAnaPort;
		this.filesNum = filesNum;
		this.WorkIP = workIP;
		this.WorkPort = workPort;
		
		try {
			fileSocket = new Socket("127.0.0.1", fileAnaPort);
			in = fileSocket.getInputStream();
			out = fileSocket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void taskToFile() {
		try {
			String sp = "-";
			byte[] spb = sp.getBytes();
			out.write(filesNum.getBytes());
			out.write(spb);
			out.write(WorkIP.getBytes());
			out.write(spb);
			out.write(WorkPort.getBytes());
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
