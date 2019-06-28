import java.sql.Connection;
import java.sql.DriverManager;

/**
 * JDBC连接Oracle数据库的示例代码
 *
 * @author：yanyunfan
 */

public class SQLUtil {
    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /***
     * 返回一个数据库连接
     */
    public static Connection getConnection() {
        Connection connection = null;// 创建一个数据库连接
        try {
            String url = "jdbc:oracle:thin:@127.0.0.1:1521:mydb";//Oracle的默认数据库名
            String user = "zhuqi";// 系统默认的用户名
            String password = "zhuge";// 安装时设置的密码
            connection = DriverManager.getConnection(url, user, password);// 获取连接
            System.out.println("数据库打开OK");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}