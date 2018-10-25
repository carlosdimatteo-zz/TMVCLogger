package org.tmvc.logger;

import org.tmvc.DBUtils.Props;

public interface LoggerInterface {
        // public method ti be called by classes to create thread for the logger to run in background call getProps from
        // Props class (create method), checkProps and call methods accordingly
        void log(String level);
        // insert log in DB
        void DBLog(String level,String className,String methodName);
        // insert log in .txt file (checking the file size)
        void TextLog(String level,String className,String methodName);
        // check current file size and if file is greater than the max file size in props,create another if size is > or =
        void checkFileSize();
        //call the socket method that will send the log to the console client
        void logToConsole(String level,String className,String methodName);


//    how to get the calling class or method :
//    class : Thread.currentThread().getStackTrace()[2].getClassName
//    mehod : Thread.currentThread().getStackTrace()[2].getMethodName()

}
