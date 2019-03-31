package socket8;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import socket8.fileanalysis.FileAnalysis;
import socket8.taskdistribute.TaskDistribute;
import socket8.workstation.WorkStationThread;
/*
 * 有问题 ：启动顺序固定 不能开线程
 */
public class ManagerMain {

	public static void main(String[] args) {
		
//		//����ƽ̨
//		Runnable rM =()->{
//			ClassLoader classLoaderM = Manager.class.getClassLoader();
//			
//			try {
//				Class<?> loadClassM = classLoaderM.loadClass("socket8.Manager");
//				Method methodM = loadClassM.getMethod("main", String[].class);
//				methodM.invoke(null, new Object[] { new String[] {} });
//			} catch (ClassNotFoundException e1) {
//				e1.printStackTrace();
//			} catch (NoSuchMethodException e) {
//				e.printStackTrace();
//			} catch (SecurityException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				e.printStackTrace();
//			}
//		};
//		new Thread(rM).start();
		
		
		
		
		//������������
		Runnable r =()->{
			
			ClassLoader classLoaderT = TaskDistribute.class.getClassLoader();
			try {
				
				Class<?> loadClassT = classLoaderT.loadClass("socket8.taskdistribute.TaskDistribute");
				Method methodT = loadClassT.getMethod("main", String[].class);
				methodT.invoke(null, new Object[] { new String[] {} });
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		};
		new Thread(r).start();
		
		//�ļ�����������
		Runnable r1 =()->{
			
			int[] fileAnaPort = {30000,30001,30002};
			for (int i = 0; i < 3; i++) {
				new FileAnalysis(fileAnaPort[i]);
			}
			
		};
		new Thread(r1).start();
		
		//����վ
		Runnable r2 =()->{
			
			int[] filesPort = {10000,10001,10002};//����վ�����˿ڣ��ļ��������������Ӷ˿�
			for (int i = 0; i < 3; i++) {
				new WorkStationThread(filesPort[i],i);
			}
			
		};
		new Thread(r2).start();
	}
}
