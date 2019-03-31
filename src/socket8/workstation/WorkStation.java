package socket8.workstation;


/*
 * 创建多个工作站(3)
 * 
 * [工作站(3)]向[任务分配服务器]发送信息--[任务分配服务器]将信息转发给[文件分析服务器(3)]
 * [文件分析服务器]与[工作站]主动连接
 */
public class WorkStation {
	private static int[] filesPort = {10000,10001,10002};//工作站监听端口：文件分析服务器连接端口
	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			new Thread(new WorkStationThread(filesPort[i],i)).start();

		}
	}
}
