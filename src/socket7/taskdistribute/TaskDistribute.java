package socket7.taskdistribute;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

import socket7.workstation.WorkManager;


/*
 * ������������
 * �ļ���Ŀ������վ��IP�Ͷ˿�  ����ѡ���ġ��ļ�������������
 */
public class TaskDistribute {
	private static int port = 20000;
	private static Socket accept;
	private static ServerSocket serverSocket;
	
	//private static AtomicInteger count = new AtomicInteger(-1);//�����ļ������������ı��
	private static int count = -1;
	public static void main(String[] args) {
		try {
			//��ʼ������
			serverSocket = new ServerSocket(port);
			System.out.println("��������������������");
			
			//---------�����ƽ̨���ͳ�ʼ״̬
			String workName = "������������"+port;
			String workState = "������������������";
			WorkManager workManager = new WorkManager();
			workManager.createState(workName, "127.0.0.1", String.valueOf(port), workState);
			
			
			while (true) {
				//if (count<3) {
					count = (count+1)%3;
					System.out.println("�����ţ�"+count);
				//}
				
				
				
				//��������ǰ�ȴ�1��
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				//�������
				accept = serverSocket.accept();
				//------
				workManager.sendState("�ѵõ�����վ����");
				
				
				
				//��������ǰ�ȴ�1��
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//�������̴߳�������
				new TaskDistributeThread(accept,count,workManager);;
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//�ر�����
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
