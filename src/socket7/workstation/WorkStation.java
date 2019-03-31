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
 * �����������վ(3)
 * 
 * [����վ(3)]��[������������]������Ϣ--[������������]����Ϣת����[�ļ�����������(3)]
 * [�ļ�����������]��[����վ]��������
 */
public class WorkStation {
	private static int[] filesPort = {10000,10001,10002};//����վ�����˿ڣ��ļ��������������Ӷ˿�
	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			new WorkStationThread(filesPort[i],i);
		}
	}
}
