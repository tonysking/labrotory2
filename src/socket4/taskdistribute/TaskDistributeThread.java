package socket4.taskdistribute;

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
	
	static {
		fileAnaNumToPort = new HashMap<>();
		fileAnaNumToPort.put(1, 30000);
	}
	
	
	public TaskDistributeThread(Socket socket) {

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
		byte[] b = new byte[1024];//接受数组
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			PrintWriter writer = new PrintWriter(out);
			while (!"end".equals(readLine=bufferedReader.readLine())) {
				System.out.println("任务分配服务器读取内容："+readLine);
				writer.write(readLine);
				//获取工作站[文件数目,IP,端口号]
				String[] split = readLine.split("--");
				filesNum = split[0];
				WorkIP = split[1];
				WorkPort = split[2];
				//将信息转发给文件分析服务器
				TaskToFile forward = new TaskToFile(fileAnaNumToPort.get(1),filesNum,WorkIP,WorkPort);
				forward.taskToFile();
			}
			
			writer.write("end\n");
			writer.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
