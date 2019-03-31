package socket8.workstation;

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

	private Path pathIn;

	public WorkToFile(int fileDiri, int filesPort,  WorkManager workManager) {
		try {
			
			wserverSocket = new ServerSocket(filesPort);
			//System.out.println(wserverSocket);
			
			this.workManager = workManager;
			
			 pathIn = Paths.get("D:\\", "JAR\\", "TXTsocket\\","txt"+fileDiri+"\\");
			System.out.println("workTOFile路径："+pathIn);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void transFiles() {
		
		try {
			//等待文件分析服务器连接
			//while (true) {
				waccept = wserverSocket.accept();
				in = waccept.getInputStream();
				out = waccept.getOutputStream();
				//----------向管理平台发送状态
				if (waccept!=null) {
					
					workManager.sendState("已与文件分析服务器建立连接");
				}
				
				//读取pathIn目录下文件并分别建立连接进行通信
				DirectoryStream<Path> entries = Files.newDirectoryStream(pathIn,"*.txt");
				int FilesLen = pathIn.toFile().listFiles().length;//文件数量
				for (Path path : entries) {
					//读取文件名
					Path fileName = path.getFileName();
					String fileMess = null;
					//读取并传输txt文件
					byte[] b = new byte[1024];
					//发送数据
					byte[] btxt;
					btxt = Files.readAllBytes(path);
					out.write(btxt);//----------只写入文件内容
					
//					fileMess = fileName.toString()+"#"+new String(btxt);
//					out.write(fileMess.getBytes());//---------写入文件名#文件内容
					
					System.out.println("工作站发送文件内容："+new String(btxt));
					//接收数据
					int readlen = in.read(b);
					System.out.println("工作站接收文件分析服务器内容："+new String(b));
					
					//----------向管理平台发送状态
					if (readlen!=-1) {
						workManager.sendState("成功向文件分析服务器发送文件！！！！！！");
					}else {
						workManager.sendState("向文件分析服务器发送文件失败！");
						
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
