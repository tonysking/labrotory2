package socket6.fileanalysis;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FileWriteToLog {

	private String filesName;//�ļ���
	private String WorkIP;//����վIP
	private String WorkPort;//����վ�˿�
	private LocalDate localDate;//��������
	private LocalTime localTime;//����ʱ��
	private String log8;//ǰ��8���ֽ�
	private String fileLength;//�ļ�����
	
	private static Path pathLog = Paths.get("D:\\", "JAR\\","socket\\","socketLog\\","FileResult.log");
	
	public FileWriteToLog(String WorkIP, String WorkPort, String log, int fileLength) {

		this.WorkIP = WorkIP;
		this.WorkPort = WorkPort;
		localDate = LocalDate.now();
		localTime = LocalTime.now();
		log8 = log.substring(0, 8);
		this.fileLength = String.valueOf(fileLength);
	}

	public void writeToLog() {
		String logContent = null; //��־����
		List<String> logList = new ArrayList<>();
		logList.add(WorkIP.substring(0, 15));
		//System.out.println("WorkIP�ĳ���"+WorkIP.length());
		logList.add(WorkPort);
		logList.add(localDate.toString());
		logList.add(localTime.toString());
		logList.add(log8);
		logList.add(fileLength);
		logList.add("\n");
		logContent = String.join("        ", logList);
		System.out.println("��־����:"+logContent);
		Writer writerLog = null;
		try {
			synchronized (FileWriteToLog.class) {
				
				writerLog = new OutputStreamWriter(new FileOutputStream(pathLog.toFile(),true));
				writerLog.write(logContent);
				System.out.println("��־����"+logContent);
				writerLog.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writerLog.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
