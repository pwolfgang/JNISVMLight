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

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to represent an svm_light MODEL. This is only used in testing. Models
 * are written to files by svm_learn and read by svm_classify. 
 * @author Paul Wolfgang
 */
public class Model {
  public int      sv_num;	
  public int      at_upper_bound;
  public double   b;
  public Doc[]    supvec;
  public double[] alpha;
  public int[]    index;        /* index from docnum to position in model */
  public int      totwords;     /* number of features */
  public int      totdoc;       /* number of training documents */
  KernelParam     kernel_parm;  /* kernel */
  double[]        linWeights;   /* weights for linear case using folding */
  
  public static Model readModel(BufferedReader in) {
      Scanner scanner = new Scanner(in);
      String line = scanner.nextLine();
      Pattern pattern = Pattern.compile("SVM-light Version (.*)");
      Matcher matcher = pattern.matcher(line);
      if (matcher.matches()) {
          String version = matcher.group(1);
          if (!"V6.02".equals(version)) {
              return null;
          }
      }
      Model model = new Model();
      model.kernel_parm = new KernelParam();
      model.kernel_parm.kerneType = scanner.nextInt();
      scanner.nextLine();
      model.kernel_parm.polyDegree = scanner.nextInt();
      scanner.nextLine();
      model.kernel_parm.rbfGamma = scanner.nextDouble();
      scanner.nextLine();
      model.kernel_parm.coefLin = scanner.nextDouble();
      scanner.nextLine();
      model.kernel_parm.coefConst = scanner.nextDouble();
      scanner.nextLine();
      model.kernel_parm.custom = scanner.findInLine("[^#]*");
      scanner.nextLine();
      model.totwords = scanner.nextInt();
      scanner.nextLine();
      model.totdoc = scanner.nextInt();
      scanner.nextLine();
      model.sv_num = scanner.nextInt();
      scanner.nextLine();
      model.b = scanner.nextDouble();
      scanner.nextLine();
      model.supvec = new Doc[model.sv_num=1];
      model.alpha = new double[model.sv_num-1];
      model.index = null;
      model.linWeights = null;
      for (int i = 0; i < model.sv_num-1; i++) {
          line = scanner.nextLine();
          String[] tokens = line.split("\\s+");
          model.alpha[i] = Double.parseDouble(tokens[0]);
          Word[] words = new Word[tokens.length-1];
          SVector[] sVector = new SVector[tokens.length-1];
          for (int j = 0; j < words.length; j++) {
              words[j] = parseWord(tokens[j+1]);
              sVector[j] = new SVector(words, "", 1);
          }
          model.supvec[i] = new Doc(-1, 0, 0, 0, sVector);
      }    
      return model;    
  }
  
  private static Word parseWord(String w) {
      String[] parts = w.split(":");
      int index = Integer.parseInt(parts[0]);
      double weight = Double.parseDouble(parts[1]);
      return new Word(index, (float)weight);
  }
  
  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null) return false;
      if (this.getClass() == o.getClass()) {
          Model other = (Model) o;
          if (sv_num != other.sv_num
                  || at_upper_bound != other.at_upper_bound
                  || b != other.b
                  || totwords != other.totwords
                  || totdoc != other.totdoc
                  || !kernel_parm.equals(other.kernel_parm))
                  return false;
           if (!compareAlphas(alpha, other.alpha)) return false;
           return Arrays.equals(supvec, other.supvec);
      } else {
          return false;        
      }
  }
  
  private boolean compareAlphas(double[] d1, double[] d2) {
      if (d1.length != d2.length) return false;
      for (int i = 0; i < d1.length; i++) {
          if (Math.abs(d1[i] - d2[i])/Math.abs(d1[i]) > 1e-6) return false;
      }
      return true;
  }
  
}