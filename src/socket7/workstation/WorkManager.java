package socket7.workstation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class WorkManager {
	private Socket managerSocket;//���ӹ���ƽ̨
	private OutputStream out;
	private InputStream in;
	public WorkManager() {
		try {
			managerSocket = new Socket("127.0.0.1", 40000);
			in = managerSocket.getInputStream();
			out = managerSocket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createState(String workName,String workIP,String workPort,String workState) {
		String message = null;//����״̬����
		List<String> mesList = new ArrayList<>();
		mesList.add("create:");
		mesList.add(workName);
		mesList.add(workIP);
		mesList.add(workPort);
		mesList.add(workState);
		message = String.join("#", mesList);
		System.out.println("����վ�����ƽ̨����:"+message);
		try {
			out.write(message.getBytes());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void sendState(String workState) {
		System.out.println("����վ�����ƽ̨����:"+workState);
		try {
			out.write(workState.getBytes());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void closeSocket() {
		try {
			in.close();
			out.close();
			managerSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
