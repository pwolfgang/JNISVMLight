/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.cla.wolfgang.jnisvmlight;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

/**
 *
 * @author Paul
 */
public class SVMLight {

    public native Model SVMLearn(List<SortedMap<Integer, Double>> x, double[] lables);
    
    public native double[] SVMClassify(Model model, List<SortedMap<Integer, Double>> x);
        
}
