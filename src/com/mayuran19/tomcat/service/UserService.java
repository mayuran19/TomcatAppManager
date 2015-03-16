/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mayuran19.tomcat.service;

import com.mayuran19.tomcat.model.User;
/**
 *
 * @author mayuran
 */
public class UserService {
    public User getUser(){
        User user = new User();
        user.setPassword("tomcat");
        user.setUsername("tomcat");
        
        return user;
    }
}
