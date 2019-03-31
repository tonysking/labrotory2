package socket9.taskdistribute;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import socket9.workstation.WorkManager;


/*
 * 任务分配服务器
 * 文件数目，工作站的IP和端口  告诉选定的“文件分析服务器”
 */
public class TaskDistribute {
	private static int port = 20000;
	
	private static final int FILEANALY_NUM = 3;//文件分析服务器数量
	
	public static void main(String[] args) {
		Socket accept = null;
		ServerSocket serverSocket = null;
		int count = -1;
		try {
			//初始化参数
			serverSocket = new ServerSocket(port);
			System.out.println("任务分配服务器已启动：");
			
			//---------向管理平台发送初始状态
			String workName = "任务分配服务器"+port;
			String workState = "任务分配服务器已启动";
			WorkManager workManager = new WorkManager();
			workManager.createState(workName, "127.0.0.1", String.valueOf(port), workState);
			
//				//处理连接前等待1秒
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
			
			while (true) {
				
				//获得连接
				accept = serverSocket.accept();
				//------向管理平台发送状态
				workManager.sendState("已得到工作站请求");
				
				//分配文件分析服务器编号
				count = (count+1)%FILEANALY_NUM;
				System.out.println("分配编号："+count);
				
				//创建新线程处理连接
				new Thread(new TaskDistributeThread(accept,count,workManager)).start();
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
