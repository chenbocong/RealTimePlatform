package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static final int PORT = 8000;//�����Ķ˿ں�   

	public static void main(String[] args) {
		Server server = new Server();  
		server.init();  
	}  

	public void init() {
		ServerSocket serverSocket = null;
		try {  
			serverSocket = new ServerSocket(PORT);  
			while (true) {
				Socket client = serverSocket.accept();  
				//һ���ͻ������ӾͿ��������̴߳����д  
				new Thread(new ReadHandlerThread(client)).start();  
				new Thread(new WriteHandlerThread(client)).start(); 
			}
		} catch (Exception e) {  
			e.printStackTrace();  
		} finally{
			try {
				if(serverSocket != null){
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}  
}  

/*
 *������������߳� 
 */
class ReadHandlerThread implements Runnable{
	private Socket client;

	public ReadHandlerThread(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		DataInputStream dis = null;
		try{
			while(true){
				//��ȡ�ͻ�������  
				dis = new DataInputStream(client.getInputStream());
				String reciver = dis.readUTF();
				System.out.println("�ͻ��˷�����������:" + reciver); 
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(dis != null){
					dis.close();
				}
				if(client != null){
					client = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

/*
 * ����д�������߳�
 */
class WriteHandlerThread implements Runnable{
	private Socket client;

	public WriteHandlerThread(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		DataOutputStream dos = null;
		BufferedReader br = null;
		try{
			while(true){
				//��ͻ��˻ظ���Ϣ  
				dos = new DataOutputStream(client.getOutputStream());  
				System.out.print("������:\t");  
				// ����¼��  
				br = new BufferedReader(new InputStreamReader(System.in));
				String send = br.readLine();  
				//��������
				dos.writeUTF(send);  
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(dos != null){
					dos.close();
				}
				if(br != null){
					br.close();
				}
				if(client != null){
					client = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}