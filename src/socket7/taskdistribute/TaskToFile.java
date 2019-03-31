package socket7.taskdistribute;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import socket7.workstation.WorkManager;
/*
 * ��[����վ]��������Ϣת����[�ļ�����������]
 */
public class TaskToFile {


	private int fileAnaPort;//�ļ������������˿�
	private String filesNum;//�ļ���Ŀ
	private String WorkIP;//����վIP
	private String WorkPort;//����վ�˿�
	
	private Socket fileSocket;//�����ļ�����������
	private OutputStream out;
	private InputStream in;
	

	private WorkManager workManager;

	public TaskToFile(Integer fileAnaPort, String filesNum, String workIP, String workPort, WorkManager workManager) {
		this.fileAnaPort = fileAnaPort;
		this.filesNum = filesNum;
		this.WorkIP = workIP;
		this.WorkPort = workPort;
		
		this.workManager = workManager;
		
		
		try {
			fileSocket = new Socket("127.0.0.1", fileAnaPort);
			System.out.println("fileAnaPort"+fileSocket);
			in = fileSocket.getInputStream();
			out = fileSocket.getOutputStream();
			if (fileSocket!=null) {
				workManager.sendState("�ѷ�����ļ�����������"+fileAnaPort);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void taskToFile() {
		try {
			String sp = "--";
			byte[] spb = sp.getBytes();
			String mess = filesNum+"--"+WorkPort+"--"+WorkIP;
//			out.write(filesNum.getBytes());
//			out.write(spb);
//			out.write(WorkIP.getBytes());
//			out.write(spb);
//			out.write(WorkPort.getBytes());
			out.write(mess.getBytes());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				fileSocket.close();
				
				//workManager.closeSocket();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
