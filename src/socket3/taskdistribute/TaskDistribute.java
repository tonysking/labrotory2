package socket3.taskdistribute;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/*
 * 任务分配服务器接收文件数目，工作站的IP和端口 
 */
public class TaskDistribute {
	private static int port = 20000;
	private static Socket accept;
	private static ServerSocket serverSocket;
	
	public static void main(String[] args) {
		try {
			//初始化参数
			serverSocket = new ServerSocket(port);
			System.out.println("任务分配服务器已启动：");
			
			
			while (true) {
				//获得连接
				accept = serverSocket.accept();
				//创建新线程处理连接
				new TaskDistributeThread(accept);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//关闭连接
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
