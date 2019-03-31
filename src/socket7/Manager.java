package socket7;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Manager {
    private static int curRow = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(40000);
        ManagementThread managementThread = new ManagementThread();
        MyFrame myFrame = managementThread.getMyFrame();
        EventQueue.invokeLater(managementThread);
        while(true)
        {
            Socket accept = serverSocket.accept();
         //   System.out.println(accept);
            MessageThread messageThread;
            synchronized (Manager.class){
                messageThread = new MessageThread(accept,myFrame,curRow++);
            }
            new Thread(messageThread).start();
        }

    }
}

class MessageThread implements Runnable{

    private Socket accept;
    private MyFrame myFrame;
    private int curRow;   //�ڱ���е�ǰ����ռ�ڼ���

    public MessageThread(Socket socket, MyFrame myFrame, int curRow) {
        this.accept = socket;
        this.myFrame = myFrame;
        this.curRow = curRow;
    }

    @Override
    public void run() {
        try{
            InputStream inputStream = accept.getInputStream();
            while(true)
            {
                byte[] bytes = new byte[1024];
                
                int len = inputStream.read(bytes);
                if(len==-1)//˵���������Ѿ��ر�
                {
                    break;
                }
                String msg = new String(bytes,0,len);
                //System.out.println("����ƽ̨��"+msg);
                synchronized (Manager.class) {
                	if(msg.startsWith("create:"))//��һ�λᴴ��һ�� �˿ںͶ�Ӧ����Ϣ���ᱣ��
                	{
                		msg = msg.substring(8);
                		String []msgs = msg.split("#");
                		myFrame.addRow(msgs);
                		
                	}else{      //����ֱ�Ӹ��ĵ�ǰ�е����һ����Ϣ
                		myFrame.changeRow(msg,curRow,3);
                	}
				}
            }

        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}

class ManagementThread implements Runnable{
     private MyFrame myFrame = new MyFrame();
    @Override
    public void run() {
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);
    }

    public MyFrame getMyFrame() {
        return myFrame;
    }
}

class MyFrame extends JFrame
{
    private static final int DEFAULT_WIDTH = 2800;
    private static final int DEAFAULT_HEIGHT = 600;
    private static final int x = 500;
    private static final int y = 200;
    JTable table = null;
    DefaultTableModel defaultModel = null;

    public MyFrame() {
        intiComponent();
    }

    public void changeRow(Object value,int row,int column) {
        defaultModel.setValueAt(value,row,column);
    }

    /**
     * ��ʼ���������
     */
    private void intiComponent()
    {
        Vector columnNames = createColumnNames();
        Vector data = new Vector();

        // ����һ��Ĭ�ϵı��ģ��
        defaultModel = new DefaultTableModel(data, columnNames);

        table = new JTable(defaultModel);
        table.setPreferredScrollableViewportSize(new Dimension(1000, 600));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        /* ��JScrollPaneװ��JTable������������Χ���оͿ���ͨ�����������鿴 */
        JScrollPane scroll = new JScrollPane(table);
        add(scroll);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     //   this.setSize(DEFAULT_WIDTH, DEAFAULT_HEIGHT);
        this.setLocation(x,y);
        this.setTitle("����ƽ̨");
        this.pack();
    }

    public void insertRow(int row,Object[] objects){
        defaultModel.insertRow(row,objects);
    }

    public void addRow(Object[] objects){
        defaultModel.addRow(objects);
    }



    private Vector createColumnNames() {
        Vector<String> columnNames = new Vector<String>();
        columnNames.add("����");
        columnNames.add("IP");
        columnNames.add("�˿�");
        columnNames.add("����״̬");
        return columnNames;
    }

}

