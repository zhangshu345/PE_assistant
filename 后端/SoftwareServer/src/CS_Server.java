import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CS_Server {
    public static void main(String[] args) {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(38380);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("server-start");

        while (true) {
            Socket sk = null;
            try {
                sk = ss.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ServerThread st = new ServerThread(sk);// 创建一个线程，用线程创建一个套接字
            st.start();
        }
    }
}

//服务器线程类
class ServerThread extends Thread {
    Socket sk;
    private SimpleDateFormat df;
    private InetAddress localAddress;

    public ServerThread(Socket sk) {
        this.sk = sk;
    }

    public void run() {
        BufferedReader br = null;
        try {
            // 设置日期格式
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            br = new BufferedReader(new InputStreamReader(sk.getInputStream()));

            String line = br.readLine();

            localAddress = sk.getLocalAddress();

            if (line.startsWith("denglv#")) {
                signIn(line);
            } else if (line.startsWith("zhuce#")) {
                zhuce(line);
            }
            br.close();
            sk.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void zhuce(String line) {
        String[] split = line.split("#");

        Connection con = SQLUtil.getConnection();

        PreparedStatement pstm = null;
        boolean state = false;
        try {
            String username = split[1];
            String password = split[2];
            String email = split[3];
            pstm = con.prepareStatement("INSERT INTO usertable (username,password,email) VALUES(?,?,?)");
//            System.out.println("mmmm     -->"+username);
            pstm.setString(1, username);
            pstm.setString(2, password);
            pstm.setString(3, email);
//            pstm.executeUpdate();
            ResultSet resultSet = pstm.executeQuery();
            state = resultSet.next();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null)
                    pstm.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        PrintStream ps = null;
        try {
            ps = new PrintStream(sk.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (state) {
            ps.print("验证成功\r\n");
            ps.flush();
            ps.close();
            System.out.println(df.format(new Date()) + "-->客户地址：" + localAddress + "  注册成功");
        } else {
            ps.print("验证失败\r\n");
            ps.flush();
            ps.close();
            System.out.println(df.format(new Date()) + "-->客户地址：" + localAddress + "  注册失败");
        }
    }

    private void signIn(String line) {
        String[] split = line.split("#");

        Connection con = SQLUtil.getConnection();

        PreparedStatement pstm = null;
        boolean state = false;
        try {
            String username = "'" + split[1] + "'";
            String password = "'" + split[2] + "'";
            pstm = con.prepareStatement("SELECT * FROM usertable where username=" + username + "and password = " + password);
            ResultSet resultSet = pstm.executeQuery();
            state = resultSet.next();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null)
                    pstm.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        PrintStream ps = null;
        try {
            ps = new PrintStream(sk.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (state) {
            ps.print("验证成功\r\n");
            ps.flush();
            ps.close();
            System.out.println(df.format(new Date()) + "-->客户地址：" + localAddress + "  登陆查询成功");
        } else {
            ps.print("验证失败\r\n");
            ps.flush();
            ps.close();
            System.out.println(df.format(new Date()) + "-->客户地址：" + localAddress + "  登陆查询失败");
        }
    }
}
