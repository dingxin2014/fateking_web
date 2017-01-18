package com.fateking.config;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.PropertyConfigurator;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by dingxin on 16/12/12.
 */
public class Configuration {

    /**
     * <p>
     * 此处需要加{@code volatile}保证即使发生初始化重排序也不会出错
     * 双重检查锁定可能在初始化重排序情况下抛空指针异常(高并发)
     * </p>
     * dingxin  2016-11-23
     */
    private static volatile Configuration configuration = null;

    private static Object lock = new Object();

    private PropertiesConfiguration propConfig;

    /**
     * 版本号
     */
    private String version;

    public static Configuration getInstance() {
        if (configuration == null) {
            synchronized (lock) {
                if (configuration == null) {
                    configuration = new Configuration();
                }
            }
        }
        return configuration;
    }

    private Configuration() {

        propConfig = new PropertiesConfiguration();
        try {
            propConfig.setEncoding("utf-8");
            propConfig.load("global.properties");

            // 初始化日志文件
            System.setProperty("appPath","fateking");
            String logFile = propConfig.getFile().getParent() + "/config/"
                    + "log4j.properties";
            PropertyConfigurator.configure(logFile);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private String getFileContent(String filename) {
        BufferedReader br = null;
        try {
            String path = "config/json/" + filename;
            InputStream in = Configuration.class.getClassLoader()
                    .getResourceAsStream(path);
            br = new BufferedReader(new InputStreamReader(
                    new BufferedInputStream(in), "UTF-8"));
            String line = null;
            StringBuffer buffer = new StringBuffer();
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
    }


    public String getString(String url) {
        return propConfig.getString(url);
    }

    public String getProperty(String name) {
        return this.propConfig.getString(name);
    }

    public Map<String, String> getProperties(String prefix) {
        Map<String, String> rs = new HashMap<String, String>();
        String key = null;
        for (Iterator<String> iter = propConfig.getKeys(); iter.hasNext();) {
            key = iter.next();
            if (key.startsWith(prefix)) {
                rs.put(key, propConfig.getString(key));
            }

        }
        return rs;
    }

    /**
     * 开发时给系统js文件作为版本使用，不必每次都加载系统js文件
     * @return
     */
    public String getSysVersion() {
        return version;
    }

    /**
     * @return
     */
    public Properties getProperties() {
        Properties pros = new Properties();
        for(Iterator<String> iter = propConfig.getKeys() ; iter.hasNext() ; ){
            String key = iter.next();
            pros.put(key, propConfig.getProperty(key));
        }
        return pros;
    }


}
