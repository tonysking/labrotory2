package socket7.workstation;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/*
 * 创建多个工作站(3)
 * 
 * [工作站(3)]向[任务分配服务器]发送信息--[任务分配服务器]将信息转发给[文件分析服务器(3)]
 * [文件分析服务器]与[工作站]主动连接
 */
public class WorkStation {
	private static int[] filesPort = {10000,10001,10002};//工作站监听端口：文件分析服务器连接端口
	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			new WorkStationThread(filesPort[i],i);
		}
	}
}
