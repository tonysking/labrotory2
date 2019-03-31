package socket3.taskdistribute;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/*
 * �����������������ļ���Ŀ������վ��IP�Ͷ˿� 
 */
public class TaskDistribute {
	private static int port = 20000;
	private static Socket accept;
	private static ServerSocket serverSocket;
	
	public static void main(String[] args) {
		try {
			//��ʼ������
			serverSocket = new ServerSocket(port);
			System.out.println("��������������������");
			
			
			while (true) {
				//�������
				accept = serverSocket.accept();
				//�������̴߳�������
				new TaskDistributeThread(accept);
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
