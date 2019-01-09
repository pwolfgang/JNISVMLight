/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.cla.wolfgang.jnisvmlight;

import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;
import org.junit.Test;
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
        SVMProblem problem = buildProblem(testData);
        SVMLight svmLight = new SVMLight();
        int numWords = problem.x.stream()
                .mapToInt(SortedMap::lastKey)
                .max()
                .orElse(0);
        svmLight.SVMLearn(problem.x, problem.y, problem.l, numWords, "testModelFile");
    }

    String classificationData = "0 1:0.4405725913859815 2:2.2479275134435857 "
            + "4:1.2479275134435854 16:1.2479275134435854 20:1.2479275134435854";
    
    String[] testData = {"1 1:0.27064758994366894 2:2.078002512001273 "
            + "3:1.0780025120012733 4:1.0780025120012733 5:1.0780025120012733 "
            + "6:1.0780025120012733 7:2.078002512001273 8:1.0780025120012733",
            "1 1:0.11864449649861922 3:0.9259994185562233 4:0.9259994185562233 "
            + "5:0.9259994185562233 6:0.9259994185562233 8:0.9259994185562233 "
            + "21:1.9259994185562235 22:1.9259994185562235 23:1.9259994185562235",
            "-1 1:0.025535092107137794 9:1.2479275134435854 10:1.2479275134435854 "
            + "11:0.24792751344358552 12:1.2479275134435854 13:1.2479275134435854 "
            + "14:1.2479275134435854 15:1.2479275134435854 16:1.2479275134435854 "
            + "17:1.2479275134435854 18:1.2479275134435854 19:1.2479275134435854 "
            + "20:0.24792751344358552",
            "-1 11:2.662965012722429 20:2.662965012722429 24:3.6629650127224296"};

    private SVMProblem buildProblem(String[] testData) {
        SVMProblem problem = new SVMProblem();
        problem.l = testData.length;
        problem.y = new double[problem.l];
        problem.x = new ArrayList<>();
        for (int i = 0; i < problem.l; i++) {
            String[] tokens = testData[i].split("\\s+");
            problem.y[i] = Double.parseDouble(tokens[0]);
            problem.x.add(buildSV(tokens));
        }
        return problem;
    }
    
    private SortedMap<Integer, Double> buildSV(String[] tokens) {
        SortedMap<Integer, Double> stateVector = new TreeMap<>();
        for (int j = 1; j < tokens.length; j++) {
            String[] parts = tokens[j].split(":");
            int index = Integer.parseInt(parts[0]);
            double value = Double.parseDouble(parts[1]);
            stateVector.put(index, value);
        }
        return stateVector;    
    }
    
//    private SVMModel createExpectedSVMModel(SVMParameter param, SVMProblem problem) {
//        SVMModel model = new SVMModel();
//        model.param = param;
//        model.nr_class = 2;
//        model.l = 4;
//        model.rho = new double[]{0.07602199167013168};
//        model.label = new int[]{1, -1};
//        model.nSV = new int[]{2, 2};
//        model.SV = problem.x;
//        model.sv_coef = new double[][]{{1, 1, -1, -1}};
//        return model;
//    }

    
}
