package tmvc.DBUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

public class Props {


    private static Props logInstance = null;
    private static Props DBInstance = null;
    private static Properties properties = null;
    private static HashMap<String,Boolean> DBPropMap= new HashMap<String,Boolean>();
    private static HashMap<String,Boolean> logPropMap = new HashMap<String,Boolean>();

    protected Props(String path) throws IOException {

        properties = new Properties();
        FileInputStream is = new FileInputStream(path);
        properties.load(is);
        setPropMap(properties,path);

    }

    private void setPropMap(Properties properties, String path) {
        Set<String> propSet = properties.stringPropertyNames();
        for (String propKey : propSet) {
            if (path.equals(System.getProperty("user.dir")+"/log.properties")) {
                if(!propKey.equals("fileSize")) {
                    Boolean propValue = Boolean.parseBoolean(properties.getProperty(propKey));
                    System.out.println("property : "+propKey+" --- "+propValue);
                    logPropMap.put(propKey, propValue);
                }else {
                    System.out.println("the max size is: " + properties.getProperty(propKey));
                }
            } else {

                DBPropMap.put(propKey,Boolean.parseBoolean(properties.getProperty(propKey)));

            }
        }
    }

    public static Props getLogPropsInstance() {//method  for singleton instance requesting
        if(logInstance==null || logInstance.getProp("fileSize")==null) {
            try {
                System.out.println("creating log props instance");
                String path =System.getProperty("user.dir")+"/log.properties";
                logInstance = new Props(path);
                System.out.println("the max file size is : "+logInstance.getProp("fileSize"));
                System.out.println(logInstance.toString());


            } catch (IOException e) {
                e.printStackTrace();
            }
            return logInstance;
        }else{

            System.out.println("returning already created  log props instance");
            System.out.println("the max file size is : "+logInstance.getProp("fileSize"));
            return logInstance;
        }
    }

    public static Props getDBPropsInstance() {//method  for singleton instance requesting
        if(DBInstance==null) {
            try {

                DBInstance = new Props(System.getProperty("user.dir")+"/database.properties");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return DBInstance;
        }else{
            return DBInstance;
        }
    }

    public  String getProp(String key) {
        return properties.getProperty(key);
    }

    public HashMap getDBPropMap(String propType){
        return DBPropMap;

    }

    public HashMap getLogPropMap(){
        return logPropMap;

    }

}
