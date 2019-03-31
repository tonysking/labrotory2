package socket1;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
/*
 * һ�������ļ�����������
 */
public class FileAnalysis {
	private static int port = 20000;
	private static Socket accept;
	private static ServerSocket serverSocket;
	private static OutputStream out;
	private static InputStream in;
	private static Writer writer;
	private static Path pathIn = Paths.get("D:\\", "JAR\\","TXTsocket\\");
	public static void main(String[] args) {
		try {
			//��ʼ������
			serverSocket = new ServerSocket(port);
			System.out.println("��������������");
			//�������
			accept = serverSocket.accept();
			in = accept.getInputStream();
			out = accept.getOutputStream();
			int i = 3;
			while (i>0) {
				
				i--;
				//����txt�ļ�
				byte[] b = new byte[1024];//��������
				byte[] bres = new byte[1024];//��������
				
				//������������
				int length = in.read(b);
				System.out.println("��������ȡ���ݣ�"+new String(b));
				//���ͷ�������
				System.arraycopy(b, 0, bres, 0, 8);//ǰ8���ֽ�(src��ʾԴ���� desc��ʾĿ������ length��ʾҪ���Ƶĳ���)
				byte[] lenb = String.valueOf(length).getBytes();
				System.arraycopy(lenb, 0, bres, 8, 4);//�ļ�����(intΪ4���ֽ�)
				out.write(bres);
				out.flush();
				System.out.println("�������������ݣ�"+new String(bres)+"���ȣ�"+length);
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//�ر���������
			try {
				in.close();
				out.close();
				accept.close();
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

}
