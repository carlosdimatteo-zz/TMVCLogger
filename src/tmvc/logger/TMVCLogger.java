package tmvc.logger;

import tmvc.DBUtils.DB;
import tmvc.DBUtils.Props;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.jws.WebService;

@WebService(endpointInterface = "tmvc.logger.LoggerInterface")

public class TMVCLogger implements LoggerInterface,Runnable{
    private String path = System.getProperty("user.dir");
    private String fileName = "Log";
    private int counter = 0;

    @Override
    public void log(String level,String message) {
        Props props = Props.getLogPropsInstance();

        HashMap<String,Boolean> propMap = props.getLogPropMap();
        System.out.println("the size of the propMap is : "+propMap.size());
        StackTraceElement nameSource = Thread.currentThread().getStackTrace()[2];
        String className = nameSource.getClassName();
        String methodName = nameSource.getMethodName();
        System.out.println(" the class : "+className+"through the method"+methodName+" logged the following message : "
                +message+" with level :"+level);
        System.out.println("the level is "+level+" and its value in the property file is : "+propMap.get(level));
        if(propMap.get(level)){
            if(propMap.get("dbActive")){
                DBLog(level,message,className,methodName);
            }
            if(propMap.get("textFile")){
                textLog(level,message,className,methodName);
            }
            if(propMap.get("echo")){
                logToConsole(level,message,className,methodName);
            }
            System.out.println("this level is not active and will not log ");
        }
    }

    @Override
    public void DBLog(String level,String message, String className, String methodName) {
        DB db = DB.getInstance();
        db.insertLog(level,message,className,methodName);
    }

    @Override
    public void textLog(String level,String message, String className, String methodName) {
        File logFile = new File(getCurrentFilePath());
        if(!checkFileSize(logFile)) {
            logFile = new File(getCurrentFilePath());
        }
        try {
            FileWriter fileWriter = new FileWriter(logFile);
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String logText ="fileLog :  "+timeStamp+"-- Class :"+className+"-- Method: "+methodName+" -- Level: "+level
                    +"-- Message: "+message;
            fileWriter.append(logText);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkFileSize(File file) {
        Props props = Props.getLogPropsInstance();
        System.out.println("the maximum file size is : "+props.getProp("fileSize"));
        int maxFileSize = Integer.parseInt(props.getProp("fileSize"));
        System.out.println("Current file : "+file.getAbsolutePath()+"and filename is "+file.getName());
        if(file.length() >= maxFileSize){
            this.counter++;
            String path = getCurrentFilePath();
            System.out.println("Current file Path is: "+path);
            File logFile = new File(path);
            try {
                System.out.println("creating new file");
                logFile.createNewFile();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void logToConsole(String level,String message, String className, String methodName) {

        try {
            System.out.println("creando socket server");
            ServerSocket serverSocket = new ServerSocket(9999);
            while(true) {
                new ClientHandler(serverSocket.accept(),className,methodName,level,message).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private String getCurrentFilePath(){
        String name = this.counter>0 ? this.fileName+this.counter : this.fileName;
        String path = this.path+"/"+name+".txt";
        return path;
    }


    @Override
    public void run() {

    }
}

