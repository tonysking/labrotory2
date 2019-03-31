package socket9.workstation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class WorkStationThread extends Thread {

	//连接任务分配器
	private Socket socket;
	private String host = "127.0.0.1";
	private int port = 20000;
	private OutputStream out;
	private InputStream in;
	//工作站ip和监听端口
	private String workIP;
	private int filesPort;
	//读取文件目录
	private File fileIn = null;
	private int fileDiri;
	
	public WorkStationThread(int filsPort, int fileDiri) {

		this.filesPort = filsPort;
		
		this.fileDiri =fileDiri;
		System.out.println("工作站"+filesPort+"已启动：");
		
		fileIn = new File("D:\\JAR\\TXTsocket\\txt"+fileDiri);//读取文件目录
		System.out.println(fileIn);
	}
	
	public void run() {
		try {
			//初始化参数
			socket = new Socket(host, port);
			in = socket.getInputStream();
			out = socket.getOutputStream();
			workIP = socket.getInetAddress().getHostAddress();
			
			//---------向管理平台发送初始状态
			String workName = "工作站"+filesPort;
			String workState = "工作站已启动";
			WorkManager workManager = new WorkManager();
			workManager.createState(workName, workIP, String.valueOf(filesPort), workState);
			
			
			
			//向任务分配服务器传送 【文件数量--端口--IP】
			Thread.sleep((long) (Math.random()*1000));
			int FilesLen = fileIn.listFiles().length;//文件数量
			
			String join = String.join("--", String.valueOf(FilesLen),String.valueOf(filesPort),workIP);
			out.write(join.getBytes());
			out.flush();
			System.out.println("工作站"+filesPort+"通知内容："+join);
			//----------向管理平台发送状态
			workManager.sendState("已经向任务分配服务器传输请求");
			
			//等待文件分析服务器连接
			workManager.sendState("等待文件分析服务器连接...");
			
			WorkToFile workToFile = new WorkToFile(fileDiri,filesPort,workManager);
			workToFile.transFiles();//传送文件
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
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
