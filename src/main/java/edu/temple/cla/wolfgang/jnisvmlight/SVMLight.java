/* 
 * Copyright (c) 2019, Temple University
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * * All advertising materials features or use of this software must display 
 *   the following  acknowledgement
 *   This product includes software developed by Temple University
 * * Neither the name of the copyright holder nor the names of its 
 *   contributors may be used to endorse or promote products derived 
 *   from this software without specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package edu.temple.cla.wolfgang.jnisvmlight;

import cz.adamh.utils.NativeUtils;
import java.util.List;
import java.util.SortedMap;

/**
 * Main class for SVMLight.
 * This is a very limited JNI bridge to <a href="http://svmlight.joachims.org/"> svm_light </a>.
 * It provides for training and classification using a linear kernel and other
 * default parameters of svm_learn and svm_classify.
 * @author Paul Wolfgang
 */
public class SVMLight {
    static {
        String libName;
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            libName = "/jnisvmlight.dll";
        } else {
            libName = "/libjnisvmlight.so";
        }
        try {
            NativeUtils.loadLibraryFromJar(libName);
        } catch (Exception ex) {
            throw new RuntimeException("Load of library failed", ex);
        }
    }

    /**
     * Method to learn an SVM model. Equivalent to running svm_learn with
     * the default parameters. 
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
     * Method to classify against an SVM model. Equivalent to running svm_classify
     * with default parameters.
     * @param modelFile The file containing the model
     * @param attributeSets Attribute sets to be classified
     * @param totDocs The number of classification cases
     * @return Array of classification results.
     */
    public native double[] SVMClassify(String modelFile, 
            List<SortedMap<Integer, Double>> attributeSets, int totDocs);
           
}
