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
			//等待文件分析服务器连接
			while (true) {
				waccept = wserverSocket.accept();
				in = waccept.getInputStream();
				out = waccept.getOutputStream();
				//读取pathIn目录下文件并分别建立连接进行通信
				DirectoryStream<Path> entries = Files.newDirectoryStream(pathIn,"*.txt");
				int FilesLen = pathIn.toFile().listFiles().length;//文件数量
				for (Path path : entries) {
					//读取并传输txt文件
					byte[] b = new byte[1024];
					//发送数据
					byte[] btxt;
					btxt = Files.readAllBytes(path);
					out.write(btxt);
					
					System.out.println("工作站发送文件内容："+new String(btxt));
					//接收数据
					in.read(b);
					System.out.println("工作站接收文件分析服务器内容："+new String(b));
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
