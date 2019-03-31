package socket6.fileanalysis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FileToWork {

	private String filesNum;//�ļ���Ŀ
	private String WorkIP;//����վIP
	private String WorkPort;//����վ�˿�
	
	private Socket workSocket;//���ӹ���վ
	private OutputStream out;
	private InputStream in;
	
	public FileToWork(String filesNum, String workPort, String workIP) {

		this.filesNum = filesNum;
		this.WorkIP = workIP;
		this.WorkPort = workPort;
		try {
			workSocket = new Socket("127.0.0.1", Integer.parseInt(workPort));
			in = workSocket.getInputStream();
			out = workSocket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void connectWork(int fileAnaPort) {
		int filesCount = Integer.parseInt(filesNum);
			//������������
			int length;
			try {
				while (filesCount>0) {
					filesCount--;
					//����txt�ļ�
					byte[] b = new byte[1024];//��������
					byte[] bres = new byte[1024];//��������
					length = in.read(b);
					System.out.println("������"+fileAnaPort+"��ȡ���ݣ�"+new String(b));
					
					//���ͷ�������
					System.arraycopy(b, 0, bres, 0, 8);//ǰ8���ֽ�(src��ʾԴ���� desc��ʾĿ������ length��ʾҪ���Ƶĳ���)
					byte[] lenb = String.valueOf(length).getBytes();
					//byte[] lenb = new byte[10];
					System.arraycopy(lenb, 0, bres, 8, 4);//�ļ�����(intΪ4���ֽ�)
					out.write(bres);
					out.flush();
					System.out.println("������"+fileAnaPort+"�������ݣ�"+new String(bres)+"���ȣ�"+length);
					
					//д����־
					String log = new String(bres);
					FileWriteToLog fileWriteToLog = new FileWriteToLog(WorkIP,WorkPort,log,length);
					fileWriteToLog.writeToLog();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
					out.close();
					workSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}

}
