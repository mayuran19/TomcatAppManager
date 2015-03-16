/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mayuran19.tomcat.service;

import com.mayuran19.tomcat.model.AppList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mayuran
 */
public class TomcatServiceTest {
    
    public TomcatServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAppList method, of class TomcatService.
     */
    @Test
    public void testGetAppList() throws Exception {
        System.out.println("getAppList");
        TomcatService instance = new TomcatService();
        List<AppList> expResult = null;
        List<AppList> result = instance.getAppList();
    }
    
    @Test
    public void testStopApp() throws Exception {
        System.out.println("getAppList");
        TomcatService instance = new TomcatService();
        instance.stopApp();
    }
    
    @Test
    public void testStartApp() throws Exception {
        System.out.println("getAppList");
        TomcatService instance = new TomcatService();
        instance.startApp();
    }
    
}
