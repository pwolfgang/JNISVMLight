# SVMLight JNI binding (limited)

This is a limited JNI binding to <a href="http://svmlight.joachims.org/"> SVMLight</a>

The SVMLearn method performs the equivalent to executing
```
svm_learn example_file model_file
```
Where the *example_file* has been parsed into an array of lables and a 
List<Map<Integer, Double>>. The resulting model is written to *model_file*.

The SVMClassify method performs the equivalent to executing
```
svm_classify example_file model_file output_file
```
Where the *model_file* is read but *example_file* has been parsed into a List<Map<Integer, Double>> and
the *output_file* values are returned as an array of doubles. 

To build this you must first obtain the source code for SVMLight from 
<a href="http://svmlight.joachims.org/"> SVMLight</a> and place it in a directory
`svm_light_602` one level above where you have cloned this repository. You will
then need to run `make libsvmlight_hideo` to create `libsvmlight.so`. If running
on Windows you will need to modify the Makefile to output `svmlight.dll`. 
You will also need to install the shared library to a place where the Java
loader can find it. (e.g. `/usr/local/lib` or add it to the PATH).
