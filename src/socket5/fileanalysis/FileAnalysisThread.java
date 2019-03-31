package socket5.fileanalysis;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileAnalysisThread extends Thread {

	
	private Socket socket;
	private OutputStream out;
	private InputStream in;
	public FileAnalysisThread(Socket socket) {

		this.socket = socket;
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		start();
	}
	public void run() {
		try {
			//读取全部内容
			byte[] all = new byte[1024];
			in.read(all);
			String splitAll = new String(all);
			String[] split = splitAll.split("-");
			String filesNum = split[0];
			System.out.println("文件数量:"+filesNum);
			
			String WorkPort = split[1];
			System.out.println("工作站端口:"+WorkPort);
			
			String WorkIP = split[2];
			System.out.println("工作站IP:"+WorkIP);
			
			//主动与工作站建立连接
			if (filesNum!=null) {
				FileToWork fileToWork = new FileToWork(filesNum,WorkPort,WorkIP);
				fileToWork.connectWork();
			}
			
//			while (i>0) {
//				
//				i--;
//				//处理txt文件
//				byte[] b = new byte[1024];//接受数组
//				byte[] bres = new byte[1024];//反馈数组
//				
//				//接收请求数据
//				int length = in.read(b);
//				System.out.println("服务器读取内容："+new String(b));
//				//发送反馈数据
//				System.arraycopy(b, 0, bres, 0, 8);//前8个字节(src表示源数组 desc表示目标数组 length表示要复制的长度)
//				byte[] lenb = String.valueOf(length).getBytes();
//				System.arraycopy(lenb, 0, bres, 8, 4);//文件长度(int为4个字节)
//				out.write(bres);
//				//out.flush();
//				System.out.println("服务器反馈内容："+new String(bres)+"长度："+length);
//			}
			
			
			
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
