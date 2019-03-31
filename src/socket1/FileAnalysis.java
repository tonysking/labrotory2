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
 * 一次连接文件分析服务器
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
			//初始化参数
			serverSocket = new ServerSocket(port);
			System.out.println("服务器已启动：");
			//获得连接
			accept = serverSocket.accept();
			in = accept.getInputStream();
			out = accept.getOutputStream();
			int i = 3;
			while (i>0) {
				
				i--;
				//处理txt文件
				byte[] b = new byte[1024];//接受数组
				byte[] bres = new byte[1024];//反馈数组
				
				//接收请求数据
				int length = in.read(b);
				System.out.println("服务器读取内容："+new String(b));
				//发送反馈数据
				System.arraycopy(b, 0, bres, 0, 8);//前8个字节(src表示源数组 desc表示目标数组 length表示要复制的长度)
				byte[] lenb = String.valueOf(length).getBytes();
				System.arraycopy(lenb, 0, bres, 8, 4);//文件长度(int为4个字节)
				out.write(bres);
				out.flush();
				System.out.println("服务器反馈内容："+new String(bres)+"长度："+length);
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//关闭流和连接
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
