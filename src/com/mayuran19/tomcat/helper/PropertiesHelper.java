/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mayuran19.tomcat.helper;

import com.mayuran19.tomcat.constant.IConstant;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author mayuran
 */
public class PropertiesHelper {

    public String getAppListURL() throws Exception {
        return this.getProperty(new File(IConstant.ConfConstant.CONF_FILE_PATH), IConstant.ConfConstant.APP_LIST_URL);
    }
    
    public String getStopAppURL() throws Exception {
        return this.getProperty(new File(IConstant.ConfConstant.CONF_FILE_PATH), IConstant.ConfConstant.APP_STOP_URL);
    }
    
    public String getStartAppURL() throws Exception {
        return this.getProperty(new File(IConstant.ConfConstant.CONF_FILE_PATH), IConstant.ConfConstant.APP_START_URL);
    }
    
    public File getTrustStore() throws Exception{
        String trustStorePath = this.getProperty(new File(IConstant.ConfConstant.CONF_FILE_PATH), IConstant.ConfConstant.TRUST_STORE_PATH);
        return new File(trustStorePath);
    }
    
    public String getTrustStorePath() throws Exception{
        String trustStorePath = this.getProperty(new File(IConstant.ConfConstant.CONF_FILE_PATH), IConstant.ConfConstant.TRUST_STORE_PATH);
        return trustStorePath;
    }
    
    public String getTomcatAntPath() throws Exception{
        return this.getProperty(new File(IConstant.ConfConstant.CONF_FILE_PATH), IConstant.ConfConstant.TOMCAT_ANT_FILE_PATH);
    }
    
    public File getTomcatAntFile() throws Exception{
        return new File(IConstant.ConfConstant.TOMCAT_ANT_FILE_PATH);
    }

    private String getProperty(File file, String key) throws Exception {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(file));
            return properties.getProperty(key);
        } catch (FileNotFoundException ex) {
            throw ex;
        } catch (IOException ex) {
            throw ex;
        }
    }
}
