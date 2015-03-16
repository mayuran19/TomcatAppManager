/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mayuran19.tomcat.helper;

import com.mayuran19.tomcat.model.AppList;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

/**
 *
 * @author mayuran
 */
public class AntHelper {

    public void runTarget(File buildFile, String targetName, Map<String, String> properties) {
        ProjectHelper projectHelper = ProjectHelper.getProjectHelper();
        Project project = new Project();

        project.setUserProperty("ant.file", buildFile.getAbsolutePath());
        if (properties != null) {
            for (String key : properties.keySet()) {
                project.setProperty(key, properties.get(key));
            }
        }
        project.init();

        project.addReference("ant.projectHelper", projectHelper);
        projectHelper.parse(project, buildFile);
        try {
            project.executeTarget(targetName);
        } catch (BuildException e) {
            throw new RuntimeException(String.format("Run %s [%s] failed: %s", buildFile, targetName, e.getMessage()), e);
        }
    }
}
