package socket1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.Socket;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WorkStation {
	
	private static String host = "127.0.0.1";
	private static int port = 20000;
	private static Socket socket;
	private static OutputStream out;
	private static InputStream in;
	private static Path pathIn = Paths.get("D:\\", "JAR\\","TXTsocket\\");
	public static void main(String[] args) {
		try {
			//初始化参数
			socket = new Socket(host, port);
			in = socket.getInputStream();
			out = socket.getOutputStream();
			//读取pathIn目录下文件并分别建立连接进行通信
			DirectoryStream<Path> entries = Files.newDirectoryStream(pathIn,"*.txt");
			for (Path path : entries) {
				
				//读取并传输txt文件
				byte[] b = new byte[1024];
				//发送数据
				byte[] btxt = Files.readAllBytes(path);
				out.write(btxt);
				out.flush();
				System.out.println("客户端发送内容："+new String(btxt));
				//接收数据
				in.read(b);
				System.out.println("客户端接受内容："+new String(b));
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//关闭流和连接
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
