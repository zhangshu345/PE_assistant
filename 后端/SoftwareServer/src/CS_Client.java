import java.net.*;
import java.io.*;
import java.util.Scanner;

public class CS_Client {
	public static void main(String[] args) {
		try {
			Socket sk = new Socket("172.18.16.156", 38380);
//			Socket sk = new Socket("120.79.75.215", 38380);
//			OutputStream ps = sk.getOutputStream();
			PrintStream ps = new PrintStream(sk.getOutputStream());// 将客户端套接字的输出流用printStream包装起来，类似于C语言中的fprintf类型转换
			ps.println("zhuce#小红211#zxcasdqwe#12123@qq.com");// 把控制台输入的内容送入被printstream类包装的输出流里面
//			ps.write((str+"\n").getBytes());
			
			BufferedReader bfr = new BufferedReader(new InputStreamReader(sk.getInputStream()));

			String readLine = bfr.readLine();
			System.out.println(readLine);
			
			ps.close();// 关闭输出流包装
			sk.close();// 关闭socket套接字，已经传完数据，才能关闭
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
