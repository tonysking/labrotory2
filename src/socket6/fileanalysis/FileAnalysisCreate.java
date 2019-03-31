package socket6.fileanalysis;

import java.io.IOException;
import java.net.ServerSocket;

import socket6.workstation.WorkStationThread;

public class FileAnalysisCreate {

	private static int[] fileAnaPort = {30000,30001,30002};
	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			new FileAnalysis(fileAnaPort[i]);
		}
	}
	
}
