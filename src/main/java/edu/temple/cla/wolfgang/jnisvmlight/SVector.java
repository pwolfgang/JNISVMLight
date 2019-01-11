/*
 * Copyright (c) 2018, Temple University
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

/**
 * Class to represent an svm_light SVECTOR
 * @author Paul
 */
public class SVector {
    
  public Word[] words;         /* The features/values in the vector by
				  increasing feature-number. Feature
				  numbers that are skipped are
				  interpreted as having value zero. */
  public double  twonorm_sq;   /* The squared euclidian length of the
                                  vector. Used to speed up the RBF kernel. */
  public String  userdefined;  /* You can put additional information
				  here. This can be useful, if you are
				  implementing your own kernel that
				  does not work with feature/values
				  representations (for example a
				  string kernel). By default,
				  svm-light will put here the string
				  after the # sign from each line of
				  the input file. */
  public int    kernel_id;     /* Feature vectors with different
				  kernel_id's are orthogonal (ie. the
				  feature number do not match). This
				  is used for computing component
				  kernels for linear constraints which
				  are a sum of several different
				  weight vectors. (currently not
				  implemented). */
  public Object  next;         /* Let's you set up a list of SVECTOR's
				  for linear constraints which are a
				  sum of multiple feature
				  vectors. List is terminated by
				  NULL. */
  public double  factor;       /* Factor by which this feature vector
				  is multiplied in the sum. */
  
  public SVector(Word[] words, String userDefined, double factor) {
      this.words = words;
      this.userdefined = userDefined;
      this.factor = factor;
      twonorm_sq = 0.0;
      for (Word word : words) {
          twonorm_sq += word.weight * word.weight;
      }
      next = null;
      kernel_id = 0;
  }
  
  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null) return false;
      if (this.getClass() == o.getClass()) {
          SVector other = (SVector) o;
          if (words.length != other.words.length) return false;
          for (int i = 0; i < words.length; i++) {
              if (words[i].wnum != other.words[i].wnum) return false;
              if (Math.abs(words[i].weight - other.words[i].weight)/Math.abs(words[i].weight) > 1e-5) {
                  return false;
              }
          }
          return true;
      } else {
          return false;
      }
  }
    
    
}
