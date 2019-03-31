package socket6.workstation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WorkStationThread extends Thread {

	//连接任务分配器
	private static Socket socket;
	private static String host = "127.0.0.1";
	private static int port = 20000;
	private OutputStream out;
	private InputStream in;
	//工作站ip和监听端口
	private static String workIP;
	private int filesPort;
	//读取文件目录
	private static Path pathIn = Paths.get("D:\\", "JAR\\","socket\\","TXTsocket\\");
	
	public WorkStationThread(int filsPort) {

		this.filesPort = filsPort;
		System.out.println("工作站"+filesPort+"已启动：");
		start();
	}
	
	public void run() {
		try {
			//初始化参数
			socket = new Socket(host, port);
			in = socket.getInputStream();
			out = socket.getOutputStream();
			workIP = socket.getInetAddress().getHostAddress();
			//发送文件数量
			int FilesLen = pathIn.toFile().listFiles().length;//文件数量
			//向任务分配服务器传送 【文件数量--端口--IP】
			String join = String.join("--", String.valueOf(FilesLen),String.valueOf(filesPort),workIP,"\nend\n");
			PrintWriter writer = new PrintWriter(out);
			writer.write(join);
			writer.flush();
			System.out.println("工作站"+filesPort+"通知内容："+join);
			//接收任务分配服务器反馈内容
//			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
//			String readLine= null;
//			while (!"end".equals(readLine=bufferedReader.readLine())) {
//				System.out.println("工作站接受任务分配服务器反馈内容："+readLine);
//			}
			
			//等待文件分析服务器连接
			WorkToFile workToFile = new WorkToFile(filesPort);
			workToFile.transFiles();//传送文件
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//关闭流和连接
			try {
				in.close();
				out.close();
				socket.close();
				//serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
