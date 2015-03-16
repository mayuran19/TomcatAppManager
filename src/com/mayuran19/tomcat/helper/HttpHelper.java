/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mayuran19.tomcat.helper;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.KeyStore;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import org.apache.catalina.util.Base64;

/**
 *
 * @author mayuran
 */
public class HttpHelper {

    private static String CHARSET = "utf-8";

    static {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession ssls) {
                return true;
            }
        });
    }

    /**
     *
     * @param url
     * @param userName
     * @param password
     * @param trustStoreFile
     * @param trustStorePassword
     * @param istream
     * @param contentType
     * @param contentLength
     * @return
     * @throws Exception
     */
    public String execute(String url, String userName, String password, File trustStoreFile, String trustStorePassword, InputStream istream, String contentType, int contentLength) throws Exception {
        URLConnection conn = null;
        InputStreamReader reader = null;
        BufferedReader br = null;
        try {
            conn = new URL(new StringBuilder().append(url).toString()).openConnection();
            HttpsURLConnection hconn = (HttpsURLConnection) conn;

            if (trustStoreFile != null && trustStoreFile.exists()) {
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                if (trustStorePassword != null && !trustStorePassword.isEmpty()) {
                    keyStore.load(new FileInputStream(trustStoreFile), trustStorePassword.toCharArray());
                } else {
                    keyStore.load(new FileInputStream(trustStoreFile), null);
                }

                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init(keyStore);
                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                SSLContext sSLContext = SSLContext.getInstance("SSL");
                sSLContext.init(null, trustManagers, null);
                hconn.setSSLSocketFactory(sSLContext.getSocketFactory());
            }

            hconn.setAllowUserInteraction(false);
            hconn.setDoInput(true);
            hconn.setUseCaches(false);
            if (istream != null) {
                hconn.setDoOutput(true);
                hconn.setRequestMethod("PUT");
                if (contentType != null) {
                    hconn.setRequestProperty("Content-Type", contentType);
                }
                if (contentLength >= 0) {
                    hconn.setRequestProperty("Content-Length", new StringBuilder().append("").append(contentLength).toString());

                    hconn.setFixedLengthStreamingMode(contentLength);
                }
            } else {
                hconn.setDoOutput(false);
                hconn.setRequestMethod("GET");
            }

            hconn.setRequestProperty("User-Agent", "Catalina-Ant-Task/1.0");

            String input = new StringBuilder().append(userName).append(":").append(password).toString();
            String output = Base64.encode(input.getBytes(Charset.defaultCharset()));

            hconn.setRequestProperty("Authorization", new StringBuilder().append("Basic ").append(output).toString());

            hconn.connect();

            if (istream != null) {
                BufferedOutputStream ostream = new BufferedOutputStream(hconn.getOutputStream(), 1024);

                byte[] buffer = new byte[1024];
                while (true) {
                    int n = istream.read(buffer);
                    if (n < 0) {
                        break;
                    }
                    ostream.write(buffer, 0, n);
                }
                ostream.flush();
                ostream.close();
                istream.close();
            }
            reader = new InputStreamReader(hconn.getInputStream(), CHARSET);
            StringBuilder buff = new StringBuilder();
            br = new BufferedReader(reader);
            String read = br.readLine();
            while (read != null) {
                buff.append(read);
                buff.append(System.lineSeparator());
                read = br.readLine();
            }
            return buff.toString();
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (istream != null) {
                istream.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }
}
