#include "edu_temple_cla_wolfgang_jnisvmlight_SVMLight.h"
#include "svm_common.h"
#include <stdio.h>
DOC** build_docs(JNIEnv*, jobject);
void create_defaults(LEARN_PARM*, KERNEL_PARM*);
void print_doc(DOC*);
void print_svector(SVECTOR*);
int num_vectors;
/*
 * Class:     edu_temple_cla_wolfgang_jnisvmlight_SVMLight
 * Method:    SVMLearn
 * Signature: (Ljava/util/List;[DLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_edu_temple_cla_wolfgang_jnisvmlight_SVMLight_SVMLearn
  (JNIEnv *env, jobject thiz, jobject attributeSets, jdoubleArray lables, 
        jint totDocs, jint totWords, jstring modelFile) {
    LEARN_PARM learn_parm;
    KERNEL_PARM kernel_parm;
    DOC **docs = build_docs(env, attributeSets);
    double* target = (*env)->GetDoubleArrayElements(env, lables, NULL);
    create_defaults(&learn_parm, &kernel_parm);
    MODEL *model = (MODEL*)malloc(sizeof(MODEL));
    svm_learn_classification(docs, target, totDocs, totWords, &learn_parm, 
            &kernel_parm, NULL, model, NULL);
    char* modelfile = (*env)->GetStringUTFChars(env, modelFile, NULL);
    write_model(modelfile, model);
    free_model(model, 0);
    (*env)->ReleaseStringUTFChars(env, modelFile, modelfile);
    for (int i = 0; i < totDocs; i++) {
        free_example(docs[i], 1);
    }
    free(docs);
    (*env)->ReleaseDoubleArrayElements(env, lables, target, 0);
}

DOC** build_docs(JNIEnv *env, jobject attributes) {
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
    num_vectors = (*env)->CallIntMethod(env, attributes, list_size);
    DOC **docs = (DOC**)malloc((num_vectors + 1)*sizeof(DOC*));
    for (int i = 0; i < num_vectors; ++i) {
        jobject training_case = (*env)->CallObjectMethod(env, attributes, list_get, i);
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
    return docs;
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

void create_defaults(LEARN_PARM *learn_parm, KERNEL_PARM *kernel_parm) {
  strcpy (learn_parm->predfile, "trans_predictions");
  strcpy (learn_parm->alphafile, "");
  learn_parm->biased_hyperplane=1;
  learn_parm->sharedslack=0;
  learn_parm->remove_inconsistent=0;
  learn_parm->skip_final_opt_check=0;
  learn_parm->svm_maxqpsize=10;
  learn_parm->svm_newvarsinqp=0;
  learn_parm->svm_iter_to_shrink=-9999;
  learn_parm->maxiter=100000;
  learn_parm->kernel_cache_size=40;
  learn_parm->svm_c=0.0;
  learn_parm->eps=0.1;
  learn_parm->transduction_posratio=-1.0;
  learn_parm->svm_costratio=1.0;
  learn_parm->svm_costratio_unlab=1.0;
  learn_parm->svm_unlabbound=1E-5;
  learn_parm->epsilon_crit=0.001;
  learn_parm->epsilon_a=1E-15;
  learn_parm->compute_loo=0;
  learn_parm->rho=1.0;
  learn_parm->xa_depth=0;
  kernel_parm->kernel_type=LINEAR;
  kernel_parm->poly_degree=3;
  kernel_parm->rbf_gamma=1.0;
  kernel_parm->coef_lin=1;
  kernel_parm->coef_const=1;
  strcpy(kernel_parm->custom,"empty");
  learn_parm->type=CLASSIFICATION;
  learn_parm->svm_iter_to_shrink=2;
}


/*
 * Class:     edu_temple_cla_wolfgang_jnisvmlight_SVMLight
 * Method:    SVMClassify
 * Signature: (Ljava/lang/String;Ljava/util/List;)[D
 */
JNIEXPORT jdoubleArray JNICALL Java_edu_temple_cla_wolfgang_jnisvmlight_SVMLight_SVMClassify
  (JNIEnv *env, jobject thiz, jstring modelFile, jobject attributeSets, jint totDocs) {
    char* modelfile = (*env)->GetStringUTFChars(env, modelFile, NULL);
    MODEL* model;
    model = read_model(modelfile);
    (*env)->ReleaseStringUTFChars(env, modelFile, modelfile);
    add_weight_vector_to_linear_model(model);
    DOC **docs = build_docs(env, attributeSets);
    double* results = (double*)malloc(totDocs * sizeof(double));
    for (int i = 0; i < totDocs; ++i) {
        results[i] = classify_example_linear(model, docs[i]);
    }
    jdoubleArray jResult = (*env)->NewDoubleArray(env, totDocs);
    (*env)->SetDoubleArrayRegion(env, jResult, 0, totDocs, results);
    free(results);
    for (int i = 0; i < totDocs; i++) {
        free_example(docs[i], 1);
    }
    free(docs);  
    return jResult;
}


