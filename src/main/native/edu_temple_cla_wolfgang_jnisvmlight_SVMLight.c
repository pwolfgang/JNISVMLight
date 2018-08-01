#include "edu_temple_cla_wolfgang_jnisvmlight_SVMLight.h"
#include "svm_common.h"
#include <stdio.h>
void print_doc(DOC*);
void print_svector(SVECTOR*);
JNIEXPORT jobject JNICALL Java_edu_temple_cla_wolfgang_jnisvmlight_SVMLight_SVMLearn
  (JNIEnv *env, jobject thiz, jobject x, jdoubleArray jlables) {
    jclass list_class = (*env)->FindClass(env, "Ljava/util/List;");
    jmethodID list_get = (*env)->GetMethodID(env, list_class, "get", "(I)Ljava/lang/Object;");
    jmethodID list_size = (*env)->GetMethodID(env, list_class, "size", "()I");
    jclass map_class = (*env)->FindClass(env, "Ljava/util/Map;");
    jmethodID map_size = (*env)->GetMethodID(env, map_class, "size", "()I");
    jmethodID entry_set = (*env)->GetMethodID(env, map_class, "entrySet", "()Ljava/util/Set;");
    jclass set_class = (*env)->FindClass(env, "Ljava/util/Set;");
    jmethodID iterator = (*env)->GetMethodID(env, set_class, "iterator", "()Ljava/util/Iterator;");
    jclass iterator_class = (*env)->FindClass(env, "Ljava/util/Iterator;");
    jmethodID hasNext = (*env)->GetMethodID(env, iterator_class, "hasNext", "()Z");
    jmethodID next = (*env)->GetMethodID(env, iterator_class, "next", "()Ljava/lang/Object;");
    jclass map_entry_class = (*env)->FindClass(env, "Ljava/util/Map$Entry;");
    jmethodID get_key = (*env)->GetMethodID(env, map_entry_class, "getKey", "()Ljava/lang/Object;");
    jmethodID get_value = (*env)->GetMethodID(env, map_entry_class, "getValue", "()Ljava/lang/Object;");
    jclass integer_class = (*env)->FindClass(env, "Ljava/lang/Integer;");
    jmethodID int_value = (*env)->GetMethodID(env, integer_class, "intValue", "()I");
    jclass double_class = (*env)->FindClass(env, "Ljava/lang/Double;");
    jmethodID float_value = (*env)->GetMethodID(env, double_class, "floatValue", "()F");
    int num_vectors = (*env)->CallIntMethod(env, x, list_size);
    DOC **docs = (DOC**)malloc((num_vectors + 1)*sizeof(DOC*));
    double *clables = (double*)malloc((num_vectors + 1)*sizeof(double));
    for (int i = 0; i < num_vectors; ++i) {
        jobject training_case = (*env)->CallObjectMethod(env, x, list_get, i);
        int training_case_size = (*env)->CallIntMethod(env, training_case, map_size);
        WORD* words = (WORD*)malloc((training_case_size + 1)*sizeof(WORD));
        int j = 0;
        jobject entries = (*env)->CallObjectMethod(env, training_case, entry_set);
        jobject itr = (*env)->CallObjectMethod(env, entries, iterator);
        while ((*env)->CallBooleanMethod(env, itr, hasNext)) {
            jobject item = (*env)->CallObjectMethod(env, itr, next);
            jobject key = (*env)->CallObjectMethod(env, item, get_key);
            jobject value = (*env)->CallObjectMethod(env, item, get_value);
            words[j].wnum = (*env)->CallIntMethod(env, key, int_value);
            words[j].weight = (*env)->CallFloatMethod(env, value, float_value);
            ++j;
        }
        words[j].wnum = 0;
        words[j].weight = 0;
        docs[i] = create_example(i, 0, 0, 1, create_svector(words, "", 1.0));
        free(words);
    }
    for (int i = 0; i < num_vectors; ++i) {
        print_doc(docs[i]);
    }
    return 0;
}

void print_doc(DOC* doc) {
    fprintf(stderr,"doc: %016x\n", doc);
    fprintf(stderr,"docnum: %d\n", doc->docnum);
    print_svector(doc->fvec);
}

void print_svector(SVECTOR* svect) {
    WORD* pword = svect->words;
    while (pword->wnum) {
        fprintf(stderr,"%d:%f ", pword->wnum, pword->weight);
        ++pword;
    }
    fprintf(stderr,"\n");
}

