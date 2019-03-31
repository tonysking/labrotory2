package socket6.taskdistribute;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/*
 * ������������
 * �ļ���Ŀ������վ��IP�Ͷ˿�  ����ѡ���ġ��ļ�������������
 */
public class TaskDistribute {
	private static int port = 20000;
	private static Socket accept;
	private static ServerSocket serverSocket;
	
	private static int count = -1;//�����ļ������������ı��
	
	public static void main(String[] args) {
		try {
			//��ʼ������
			serverSocket = new ServerSocket(port);
			System.out.println("��������������������");
			
			
			while (true) {
				//�������
				accept = serverSocket.accept();
				
				count = (count+1)%3;
				
				//�������̴߳�������
				new TaskDistributeThread(accept,count);
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
