/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.cla.wolfgang.jnisvmlight;

import cz.adamh.utils.NativeUtils;
import java.util.List;
import java.util.SortedMap;

/**
 *
 * @author Paul
 */
public class SVMLight {
    static {
        try {
            NativeUtils.loadLibraryFromJar("/jnisvmlight.dll");
        } catch (Exception ex) {
            throw new RuntimeException("Load of library failed", ex);
        }
    }

    /**
     * Method to learn an SVM model.
     * @param attributeSets The training example attribute sets.
     * @param lables The training labels.
     * @param totDocs The number of training cases
     * @param totWords The number of attributes (unique words)
     * @param modelFile The name of the file where the model is to be written
     *
     */
    public native void SVMLearn(List<SortedMap<Integer, Double>> attributeSets, 
            double[] lables, int totDocs, int totWords, String modelFile);
    
    /**
     * Method to classify against an SVM model.
     * @param modelFile The file containing the model
     * @param attributeSets Attribute sets to be classified
     * @param totDocs The number of classification cases
     * @return Array of classification results.
     */
    public native double[] SVMClassify(String modelFile, 
            List<SortedMap<Integer, Double>> attributeSets, int totDocs);
           
}
