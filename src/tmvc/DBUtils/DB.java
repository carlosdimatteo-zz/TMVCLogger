package tmvc.DBUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DB {

   private Props prop = Props.getDBPropsInstance();
    private static Connection con = null;
    private static DB ins = null;


    protected DB() {
        try {
            System.out.println("driver from properties : "+prop.getProp("driver"));
            Class.forName(prop.getProp("driver"));
            con = DriverManager.getConnection(prop.getProp("url"), prop.getProp("user"), prop.getProp("password"));
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void insertLog(String level,String message,String className,String methodName) {
        try {
        PreparedStatement prepStatement = con.prepareStatement("INSERT INTO log (level,message,class,method,date) VALUES(?,?,?,?,?)");
            prepStatement.setString(1,level);
            prepStatement.setString(2,message);
            prepStatement.setString(3,className);
            prepStatement.setString(4,methodName);
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            prepStatement.setString(5,timeStamp);
            prepStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public static DB getInstance() {
        if (ins == null) {
            ins = new DB();

        }
        return ins;
    }

}
