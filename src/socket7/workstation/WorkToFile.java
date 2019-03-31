package socket7.workstation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WorkToFile {

	private ServerSocket wserverSocket;
	private Socket waccept;
	private OutputStream out;
	private InputStream in;
	
	private WorkManager workManager;

	private static Path pathIn = Paths.get("D:\\", "JAR\\", "TXTsocket\\");

	public WorkToFile(int filesPort, WorkManager workManager) {
		try {
			
			wserverSocket = new ServerSocket(filesPort);
			//System.out.println(wserverSocket);
			
			this.workManager = workManager;
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void transFiles() {
		
		try {
			//�ȴ��ļ���������������
			//while (true) {
				waccept = wserverSocket.accept();
				in = waccept.getInputStream();
				out = waccept.getOutputStream();
				//----------�����ƽ̨����״̬
				if (waccept!=null) {
					
					workManager.sendState("�����ļ�������������������");
				}
				
				//��ȡpathInĿ¼���ļ����ֱ������ӽ���ͨ��
				DirectoryStream<Path> entries = Files.newDirectoryStream(pathIn,"*.txt");
				int FilesLen = pathIn.toFile().listFiles().length;//�ļ�����
				for (Path path : entries) {
					//��ȡ������txt�ļ�
					byte[] b = new byte[1024];
					//��������
					byte[] btxt;
					btxt = Files.readAllBytes(path);
					out.write(btxt);
					
					System.out.println("����վ�����ļ����ݣ�"+new String(btxt));
					//��������
					int readlen = in.read(b);
					System.out.println("����վ�����ļ��������������ݣ�"+new String(b));
					
					//----------�����ƽ̨����״̬
					if (readlen!=-1) {
						workManager.sendState("�ɹ����ļ����������������ļ���");
					}else {
						workManager.sendState("���ļ����������������ļ�ʧ�ܣ�");
						
					}
					
				}
			//}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				waccept.close();
				wserverSocket.close();
				
				workManager.closeSocket();
	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
