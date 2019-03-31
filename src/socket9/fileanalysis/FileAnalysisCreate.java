package socket9.fileanalysis;

/*
 * 创建多个文件分析服务器(3)
 */

public class FileAnalysisCreate {

	private static int[] fileAnaPort = {30000,30001,30002};
	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			new Thread(new FileAnalysis(fileAnaPort[i])).start();

		}
	}
	
}
