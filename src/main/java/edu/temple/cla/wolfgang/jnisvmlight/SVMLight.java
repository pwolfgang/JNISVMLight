/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.cla.wolfgang.jnisvmlight;

/**
 *
 * @author Paul
 */
public class SVMLight {
    
    public native SVMModel svmTrain(SVMProblem problem, SVMParameter param);
    
    public native double svmPredict(SVMModel model, SVMNode[] x);
    
}
