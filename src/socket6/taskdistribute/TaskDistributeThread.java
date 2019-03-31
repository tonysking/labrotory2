package socket6.taskdistribute;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;

public class TaskDistributeThread extends Thread {

	//接收工作站请求
	private static String filesNum;//文件数目
	private static String WorkIP;//工作站IP
	private static String WorkPort;//工作站端口
	private Socket socket;
	private OutputStream out;
	private InputStream in;
	
	
	//转发给文件分析服务器
	private static Map<Integer, Integer> fileAnaNumToPort;//文件分析服务器<编号,端口>
	private int fileAnaNum;
	
	static {
		fileAnaNumToPort = new HashMap<>();
		fileAnaNumToPort.put(0, 30000);
		fileAnaNumToPort.put(1, 30001);
		fileAnaNumToPort.put(2, 30002);
	}
	
	
	public TaskDistributeThread(Socket socket, int count) {

		this.socket = socket;
		
		fileAnaNum = count;
		
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		start();
	}
	public void run() {
		byte[] b = new byte[1024];//接受数组
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			PrintWriter writer = new PrintWriter(out);
			while (!"end".equals(readLine=bufferedReader.readLine())) {
				System.out.println("任务分配服务器读取内容："+readLine);
				//writer.write(readLine);//----反馈测试
				//获取工作站[文件数目,IP,端口号]
				String[] split = readLine.split("--");
				filesNum = split[0];
				WorkIP = split[1];
				WorkPort = split[2];
				//将信息转发给文件分析服务器fileAnaNum
//				for (Integer filePort : fileAnaNumToPort.values()) {
//					TaskToFile forward = new TaskToFile(filePort,filesNum,WorkIP,WorkPort);
//					forward.taskToFile();
//				}
				
				TaskToFile forward = new TaskToFile(fileAnaNumToPort.get(fileAnaNum),filesNum,WorkIP,WorkPort);
				forward.taskToFile();
				
				
			}
			
//			writer.write("end\n");//------反馈测试
//			writer.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
