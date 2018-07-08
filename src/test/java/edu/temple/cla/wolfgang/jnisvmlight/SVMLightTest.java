/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.cla.wolfgang.jnisvmlight;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Paul
 */
public class SVMLightTest {
    
    @BeforeClass
    public static void loadDll() {
        System.err.println("java.library.path=" + System.getProperty("java.library.path"));
        System.loadLibrary("jnisvmlight");
    }
    
    public SVMLightTest() {
    }

    @Test
    public void testSvmTrain() {
        System.out.println("svmTrain");
        SVMProblem problem = new SVMProblem();
        problem.l = 10;
        SVMParameter param = null;
        SVMLight instance = new SVMLight();
        SVMModel expResult = null;
        SVMModel result = instance.svmTrain(problem, param);
        assertEquals(expResult, result);
    }

    
}
