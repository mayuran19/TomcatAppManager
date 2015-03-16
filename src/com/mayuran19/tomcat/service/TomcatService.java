/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mayuran19.tomcat.service;

import com.mayuran19.tomcat.helper.AntHelper;
import com.mayuran19.tomcat.helper.HttpHelper;
import com.mayuran19.tomcat.helper.PropertiesHelper;
import com.mayuran19.tomcat.model.AppList;
import com.mayuran19.tomcat.model.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 *
 * @author mayuran
 */
public class TomcatService {

    static {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
    }

    public List<AppList> getAppList() throws Exception {
        UserService userService = new UserService();
        User user = userService.getUser();

        PropertiesHelper propertiesHelper = new PropertiesHelper();
        String listUrl = propertiesHelper.getAppListURL();

        HttpHelper httpHelper = new HttpHelper();
        try {
            String appListString = httpHelper.execute(listUrl, user.getUsername(), user.getPassword(), propertiesHelper.getTrustStore(), null, null, null, -1);
            System.out.println(appListString);
        } catch (Exception ex) {
            throw ex;
        }
        return null;
    }

    public void stopApp() throws Exception {
        PropertiesHelper propertiesHelper = new PropertiesHelper();
        String trustStorePath = propertiesHelper.getTrustStorePath();
        Map<String, String> map = new HashMap<String, String>();
        map.put("javax.net.ssl.keyStoreType", "jks");
        map.put("javax.net.ssl.trustStore", trustStorePath);

        AntHelper antHelper = new AntHelper();
        antHelper.runTarget(propertiesHelper.getTomcatAntFile(), "stop", map);
    }

    public void startApp() throws Exception {
        PropertiesHelper propertiesHelper = new PropertiesHelper();
        String trustStorePath = propertiesHelper.getTrustStorePath();
        Map<String, String> map = new HashMap<String, String>();
        map.put("javax.net.ssl.keyStoreType", "jks");
        map.put("javax.net.ssl.trustStore", trustStorePath);

        AntHelper antHelper = new AntHelper();
        antHelper.runTarget(propertiesHelper.getTomcatAntFile(), "start", map);
    }
}
