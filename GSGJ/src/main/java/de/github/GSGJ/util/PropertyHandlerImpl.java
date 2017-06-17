package de.github.GSGJ.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 * Read Properties from a Config File
 * Created by Kojy on 02.05.2016.
 */
public class PropertyHandlerImpl implements PropertyHandler {
    private Properties properties;
    private Logger logger = LoggerFactory.getLogger(PropertyHandlerImpl.class);

    /**
     * Constructor with build in FileName or Pathname
     *
     * @param filename
     */
    public PropertyHandlerImpl(String directory, String filename) {
        String currentDir = "";
        this.properties = new Properties();

        try {
            currentDir = this.getCurrentDirectory();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        if (!currentDir.equals("")) {
            if (checkIfJar(currentDir)) {
                // we are running inside jar
                directory = new File(currentDir).getParentFile().getPath() + "/config";
            } else {
                directory = currentDir + "config";
            }
        }

        if (new File(directory).exists()) {
            try {
                filename = directory + "/" + filename;
                File propertyFile = new File(filename);
                if (propertyFile.exists()) {
                    // parameter file exists
                    this.properties.load(new FileInputStream(propertyFile));
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            logger.error("Directory config does not exist!");
        }

    }

    /**
     * read a line from the Configuration data
     *
     * @param key
     * @return
     */
    public String read(String key) {
        key.toLowerCase();
        // return value can be null if the specified key is not found
        return properties.getProperty(key);
    }

    @Override
    public boolean write(String key, String value) {
        Object obj = properties.put(key, value);
        return obj != null;
    }

    private String getCurrentDirectory() throws Exception {
        return this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
    }

    /**
     * Executes a check if the currently started server application is started from a jar
     *
     * @param currentDir the current directory
     * @return
     */
    private boolean checkIfJar(String currentDir) {
        return currentDir.endsWith("jar");
    }
}
