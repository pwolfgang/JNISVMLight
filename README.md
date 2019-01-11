# SVMLight JNI binding (limited)

This is a limited JNI binding to <a href="http://svmlight.joachims.org/"> SVMLight</a>

The SVMLearn method performs the equivalent to executing
```
svm_learn example_file model_file
```
Where the example_file has been parsed into an array of lables and a 
List<Map<Integer, Double>>. The resulting model is written to model_file.

The SVMClassify method performs the equivalent to executing
```
svm_classify example_file model_file output_file
```
Where the example_file has been parsed into a List<Map<Integer, Double>> and
the output_file values are returned as an array of doubles. The model_file is
read.
