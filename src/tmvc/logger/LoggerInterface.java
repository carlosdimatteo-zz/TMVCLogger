package tmvc.logger;
import javax.jws.WebService;
import tmvc.DBUtils.Props;

import java.io.File;

@WebService
public interface LoggerInterface {
        // public method ti be called by classes to create thread for the logger to run in background call getProps from
        // Props class (create method), checkProps and call methods accordingly
        void log(String level,String message);
        // insert log in DB
        void DBLog(String level,String message,String className,String methodName);
        // insert log in .txt file (checking the file size)
        void textLog(String level,String message,String className,String methodName);
        //call the socket method that will send the log to the console client
        void logToConsole(String level,String message,String className,String methodName);


//    how to get the calling class or method :
//    class : Thread.currentThread().getStackTrace()[2].getClassName
//    mehod : Thread.currentThread().getStackTrace()[2].getMethodName()

}
