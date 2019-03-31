package socket5.fileanalysis;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileAnalysisThread extends Thread {

	
	private Socket socket;
	private OutputStream out;
	private InputStream in;
	public FileAnalysisThread(Socket socket) {

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
		try {
			//��ȡȫ������
			byte[] all = new byte[1024];
			in.read(all);
			String splitAll = new String(all);
			String[] split = splitAll.split("-");
			String filesNum = split[0];
			System.out.println("�ļ�����:"+filesNum);
			
			String WorkPort = split[1];
			System.out.println("����վ�˿�:"+WorkPort);
			
			String WorkIP = split[2];
			System.out.println("����վIP:"+WorkIP);
			
			//�����빤��վ��������
			if (filesNum!=null) {
				FileToWork fileToWork = new FileToWork(filesNum,WorkPort,WorkIP);
				fileToWork.connectWork();
			}
			
//			while (i>0) {
//				
//				i--;
//				//����txt�ļ�
//				byte[] b = new byte[1024];//��������
//				byte[] bres = new byte[1024];//��������
//				
//				//������������
//				int length = in.read(b);
//				System.out.println("��������ȡ���ݣ�"+new String(b));
//				//���ͷ�������
//				System.arraycopy(b, 0, bres, 0, 8);//ǰ8���ֽ�(src��ʾԴ���� desc��ʾĿ������ length��ʾҪ���Ƶĳ���)
//				byte[] lenb = String.valueOf(length).getBytes();
//				System.arraycopy(lenb, 0, bres, 8, 4);//�ļ�����(intΪ4���ֽ�)
//				out.write(bres);
//				//out.flush();
//				System.out.println("�������������ݣ�"+new String(bres)+"���ȣ�"+length);
//			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//�ر���������
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
