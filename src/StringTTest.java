
public class StringTTest {

	public static void main(String[] args) {
		
		String join = String.join("--", String.valueOf(3),String.valueOf(10000),"127.0.0.1");
		System.out.println(join);
		byte[] b = join.getBytes();
		String readLine = new String(b,0,b.length);
		String[] split = readLine.split("--");
		String filesNum = split[0];
		String WorkPort = split[1];
		String WorkIP = split[2];
		
		String mess = filesNum+"--"+WorkPort+"--"+WorkIP;
		System.out.println(mess);
		
		System.out.println("文件数量:"+filesNum);
		
		System.out.println("工作站端口:"+WorkPort);
		
		System.out.println("工作站IP:"+WorkIP);
	}
}
