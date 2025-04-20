package Utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigHandler {

    public String getPropertyValue(String propname) throws IOException {

        Properties prop = new Properties();
        InputStream input = new FileInputStream("src/main/resources/config.properties");
        prop.load(input);
        return prop.getProperty(propname);


    }



}
