package socket5.workstation;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/*
 * [工作站]向[任务分配服务器]发送信息--[任务分配服务器]将信息转发给[文件分析服务器]
 */
public class WorkStation {
	
	private static String host = "127.0.0.1";
	private static int port = 20000;//连接任务分配器端口
	private static Socket socket;
	private static int filesPort = 10000;//工作站监听端口：文件分析服务器连接端口
	private static String workIP;//工作站ip
	private static ServerSocket serverSocket;//等待文件分析服务器连接
	private static OutputStream out;
	private static InputStream in;
	private static Path pathIn = Paths.get("D:\\", "JAR\\","TXTsocket\\");
	public static void main(String[] args) {
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
			System.out.println("工作站通知内容："+join);
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
