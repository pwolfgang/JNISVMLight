/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.cla.wolfgang.jnisvmlight;

import java.util.List;
import java.util.SortedMap;

/**
 *
 * @author Paul
 */
public class SVMLight {

    public native void SVMLearn(List<SortedMap<Integer, Double>> attributeSets, double[] lables, String modelFile);
    
    public native double[] SVMClassify(String modelFile, List<SortedMap<Integer, Double>> attributeSets);
           
}
