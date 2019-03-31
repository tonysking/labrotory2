package socket6.workstation;

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

	private static Path pathIn = Paths.get("D:\\", "JAR\\", "TXTsocket\\");

	public WorkToFile(int filesPort) {
		try {
			wserverSocket = new ServerSocket(filesPort);
			System.out.println(wserverSocket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void transFiles() {
		
		try {
			//�ȴ��ļ���������������
			while (true) {
				waccept = wserverSocket.accept();
				in = waccept.getInputStream();
				out = waccept.getOutputStream();
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
					in.read(b);
					System.out.println("����վ�����ļ��������������ݣ�"+new String(b));
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				waccept.close();
				wserverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
