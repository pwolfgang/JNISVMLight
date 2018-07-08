#include "edu_temple_cla_wolfgang_jnisvmlight_SVMLight.h"
#include <stdio.h>

JNIEXPORT jobject JNICALL Java_edu_temple_cla_wolfgang_jnisvmlight_SVMLight_svmTrain
  (JNIEnv *env, jobject thiz, jobject problem, jobject param) {
    
    jclass problem_class = (*env)->GetObjectClass(env, problem);
    jfieldID l_fid = (*env)->GetFieldID(env, problem_class, "l", "I");
    jint l = (*env)->GetIntField(env, problem, l_fid);
    
    printf("The value of l is %d\n", l);
    
    return 0;
}

