package org.tmvc.DBUtils;

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

    }

    private void setPropMap(Properties properties, String path) {
        HashMap<String, Boolean> propMap = new HashMap<String, Boolean>();
        Set<String> propSet = properties.stringPropertyNames();
        for (String propKey : propSet) {
            if (path == "./../../../../log.properties") {
                logPropMap.put(propKey, Boolean.parseBoolean(properties.getProperty(propKey)));
            } else {

                DBPropMap.put(propKey,Boolean.parseBoolean(properties.getProperty(propKey)));

            }
        }
    }

    public static Props getLogPropsInstance() {//method  for singleton instance requesting
        if(logInstance==null) {
            try {
                logInstance = new Props("./../../../../log.properties");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return logInstance;
        }else{
            return logInstance;
        }
    }

    public static Props getDBPropsInstance() {//method  for singleton instance requesting
        if(DBInstance==null) {
            try {
                DBInstance = new Props("./../../../../database.properties");
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

    public HashMap getLogPropMap(String propType){
        return logPropMap;

    }

}
