package socket9.fileanalysis;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FileWriteToLog {

	private String filesName;//文件名
	private String WorkIP;//工作站IP
	private String WorkPort;//工作站端口
	private LocalDate localDate;//处理日期
	private LocalTime localTime;//处理时间
	private String log8;//前面8个字节
	private String fileLength;//文件长度
	
	private static Path pathLog = Paths.get("D:\\", "JAR\\","socket\\","socketLog\\","FileResult.log");
	
	public FileWriteToLog(String filesName,String WorkIP, String WorkPort, String log,  int fileLength) {

		this.filesName =filesName;
		this.WorkIP = WorkIP;
		this.WorkPort = WorkPort;
		localDate = LocalDate.now();
		localTime = LocalTime.now();
		log8 = log.substring(0, 8);
		this.fileLength = String.valueOf(fileLength);
	}

	public void writeToLog() {
		String logContent = null; //日志内容
		List<String> logList = new ArrayList<>();
		logList.add(filesName);
		logList.add(WorkIP.substring(0, 9));
		//System.out.println("WorkIP的长度"+WorkIP.length());
		logList.add(WorkPort);
		logList.add(localDate.toString());
		logList.add(localTime.toString());
		logList.add(log8);
		logList.add(fileLength);
		logList.add("\n");
		logContent = String.join("\t\t\t", logList);
		System.out.println("日志内容:"+logContent);
		Writer writerLog = null;
		try {
			synchronized (FileWriteToLog.class) {
				
				writerLog = new OutputStreamWriter(new FileOutputStream(pathLog.toFile(),true));
				writerLog.write(logContent);
				System.out.println("日志内容"+logContent);
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
