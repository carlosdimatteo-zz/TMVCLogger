package org.tmvc.DBUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Props {


    private static Props instance = null;
    private static Properties properties = null;

    protected Props(String path) throws IOException {

        properties = new Properties();
        FileInputStream is = new FileInputStream(path);
        properties.load(is);
    }

    public static Props getInstance(String type) {//method  for singleton instance requesting
        if(instance == null) {
            try {
        switch(type) {
            case "logProp" :
                instance = new Props("./../../../../log.properties");
                break;
            case "dbProp" :
                instance = new Props("./../../../../database.properties");
                break;
        }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public  String getProp(String key) {
        return properties.getProperty(key);
    }

}
