/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.cla.wolfgang.jnisvmlight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Paul
 */
public class SVMLightTest {

    public SVMLightTest() {
    }

    @Test
    public void testSvmTrainAndClassify() {
        SVMProblem problem = buildProblem(testData);
        SVMLight svmLight = new SVMLight();
        int numWords = problem.x.stream()
                .mapToInt(SortedMap::lastKey)
                .max()
                .orElse(0);
        svmLight.SVMLearn(problem.x, problem.y, problem.l, numWords, "testModelFile");
        assertTrue(verifyModel("testModelFile", expectedModel));
        SVMProblem classifyProblem = buildProblem(new String[]{classificationData});
        double[] result = svmLight.SVMClassify("testModelFile", classifyProblem.x, classifyProblem.l);
        assertEquals(0.2030068, result[0], 9e-9);
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

    String expectedModel = "SVM-light Version V6.02\n"
            + "0 # kernel type\n"
            + "3 # kernel parameter -d \n"
            + "1 # kernel parameter -g \n"
            + "1 # kernel parameter -s \n"
            + "1 # kernel parameter -r \n"
            + "empty# kernel parameter -u \n"
            + "24 # highest feature index \n"
            + "4 # number of training documents \n"
            + "5 # number of support vectors plus 1 \n"
            + "-0.080011943 # threshold b, each following line is a SV (starting with alpha*y)\n"
            + "-0.055663608629776047 1:0.025535092 9:1.2479275 10:1.2479275 11:0.24792752 "
            + "12:1.2479275 13:1.2479275 14:1.2479275 15:1.2479275 16:1.2479275 17:1.2479275 "
            + "18:1.2479275 19:1.2479275 20:0.24792752 #\n"
            + "-0.036467694025608691 11:2.6629651 20:2.6629651 24:3.6629651 #\n"
            + "0.048182685706176551 1:0.27064759 2:2.0780025 3:1.0780025 4:1.0780025 "
            + "5:1.0780025 6:1.0780025 7:2.0780025 8:1.0780025 #\n"
            + "0.043948680647092633 1:0.1186445 3:0.9259994 4:0.9259994 5:0.9259994 "
            + "6:0.9259994 8:0.9259994 21:1.9259994 22:1.9259994 23:1.9259994 #";

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

    private boolean verifyModel(String modelFile, String expectedModel) {
        try (
             BufferedReader model = new BufferedReader(new FileReader(modelFile));
             BufferedReader expected = new BufferedReader(new StringReader(expectedModel));
        ) {
            Model model1 = Model.readModel(model);
            Model model2 = Model.readModel(expected);
            return (model1.equals(model2));
        } catch (IOException ioex) {
            System.out.println(ioex);
            return false;
        }
    }

}
