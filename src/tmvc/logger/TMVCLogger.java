package tmvc.logger;

import tmvc.DBUtils.DB;
import tmvc.DBUtils.Props;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class TMVCLogger implements LoggerInterface {
    private String path = "./../../../../";
    private String fileName = "Log";
    private int counter = 0;

    @Override
    public void log(String level,String message) {
        HashMap<String,Boolean> props = Props.getLogPropsInstance().getLogPropMap();
        StackTraceElement nameSource = Thread.currentThread().getStackTrace()[2];
        String className = nameSource.getClassName();
        String methodName = nameSource.getMethodName();
        System.out.println(" the class : "+className+"through the method"+methodName+" logged the following message : "
                +message+" with level :"+level);
        if(props.get(level)){
            if(props.get("dbActive")){
                DBLog(level,message,className,methodName);
            }else if(props.get("textFile")){
                textLog(level,message,className,methodName);
            }else if(props.get("echo")){
                logToConsole(level,message,className,methodName);
            }
        }
    }

    @Override
    public void DBLog(String level,String message, String className, String methodName) {
        DB db = DB.getInstance();
        db.insertLog(level,message,className,methodName);
    }

    @Override
    public void textLog(String level,String message, String className, String methodName) {
        File logFile = new File(getCurrentFile());
        if(!checkFileSize(logFile)) {
            logFile = new File(getCurrentFile());
        }
        try {
            FileWriter fileWriter = new FileWriter(logFile);
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            fileWriter.write(timeStamp+"-- Class :"+className+"-- Method: "+methodName+" -- Level: "+level
                    +"-- Message: "+message);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkFileSize(File file) {
        int size = Integer.parseInt(Props.getLogPropsInstance().getProp("fileSize"));
        if(file.length() >= size){
            this.counter++;
            File logFile = new File(getCurrentFile());
            try {
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

    }

    private String getCurrentFile(){
        String name = this.counter>0 ? this.fileName+this.counter : "";
        String path = this.path+name+".txt";
        return path;
    }


}
