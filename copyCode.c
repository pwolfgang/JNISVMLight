FIELD ID DECLARATIONS
    jfieldID sv_num_fid = (*env)->GetFieldID(env, model_class, "sv_num", "I");
    jfieldID at_upper_bound_fid = (*env)->GetFieldID(env, model_class, "at_upper_bound", "I");
    jfieldID b_fid = (*env)->GetFieldID(env, model_class, "b", "D");
    jfieldID supvec_fid = (*env)->GetFieldID(env, model_class, "supvec", "[Ledu/temple/cla/wolfgang/jnisvmlight/Doc;");
    jfieldID docnum_fid = (*env)->GetFieldID(env, doc_class, "docnum", "J");
    jfieldID queryid_fid = (*env)->GetFieldID(env, doc_class, "queryid", "J");
    jfieldID costfactor_fid = (*env)->GetFieldID(env, doc_class, "costfactor", "D");
    jfieldID slackid_fid = (*env)->GetFieldID(env, doc_class, "slackid", "J");
    jfieldID fvec_fid = (*env)->GetFieldID(env, doc_class, "fvec", "[Ledu/temple/cla/wolfgang/jnisvmlight/SVector;");
    jfieldID words_fid = (*env)->GetFieldID(env, sVector_class, "words", "[Ledu/temple/cla/wolfgang/jnisvmlight/Word;");
    jfieldID wnum_fid = (*env)->GetFieldID(env, word_class, "wnum", "I");
    jfieldID weight_fid = (*env)->GetFieldID(env, word_class, "weight", "F");
    jfieldID twonorm_sq_fid = (*env)->GetFieldID(env, sVector_class, "twonorm_sq", "D");
    jfieldID userdefined_fid = (*env)->GetFieldID(env, sVector_class, "userdefined", "Ljava/lang/String;");
    jfieldID kernel_id_fid = (*env)->GetFieldID(env, sVector_class, "kernel_id", "I");
    jfieldID next_fid = (*env)->GetFieldID(env, sVector_class, "next", "Ljava/lang/Object;");
    jclass object_class = (*env)->FindClass(env, "Ljava/lang/Object;");
    jmethodID object_constructor = (*env)->GetMethodID(env, object_class, "<init>", "V");
    jfieldID factor_fid = (*env)->GetFieldID(env, sVector_class, "factor", "D");
    jfieldID alpha_fid = (*env)->GetFieldID(env, model_class, "alpha", "[D");
    jfieldID index_fid = (*env)->GetFieldID(env, model_class, "index", "[I");
    jfieldID totwords_fid = (*env)->GetFieldID(env, model_class, "totwords", "I");
    jfieldID totdoc_fid = (*env)->GetFieldID(env, model_class, "totdoc", "I");
    jfieldID kernel_parm_fid = (*env)->GetFieldID(env, model_class, "kernel_parm", "Ledu/temple/cla/wolfgang/jnisvmlight/KernelParam;");
    jclass kernelParam_class = (*env)->FindClass(env, "Ledu/temple/cla/wolfgang/jnisvmlight/KernelParam;");
    jmethodID kernelParam_constructor = (*env)->GetMethodID(env, kernelParam_class, "<init>", "V");
    jfieldID kerneType_fid = (*env)->GetFieldID(env, kernelParam_class, "kerneType", "I");
    jfieldID polyDegree_fid = (*env)->GetFieldID(env, kernelParam_class, "polyDegree", "I");
    jfieldID rbfGamma_fid = (*env)->GetFieldID(env, kernelParam_class, "rbfGamma", "D");
    jfieldID coefLin_fid = (*env)->GetFieldID(env, kernelParam_class, "coefLin", "D");
    jfieldID coefConst_fid = (*env)->GetFieldID(env, kernelParam_class, "coefConst", "D");
    jfieldID custom_fid = (*env)->GetFieldID(env, kernelParam_class, "custom", "Ljava/lang/String;");
COPY IN CODE
    c_model->sv_num = (*env)->GetIntField(env, jModel, sv_num_fid);
    c_model->at_upper_bound = (*env)->GetIntField(env, jModel, at_upper_bound_fid);
    c_model->b = (*env)->GetDoubleField(env, jModel, b_fid);
    c_model->supvec = (struct doc*)malloc(supvec_length * sizeof(struct doc*);
    for (int j = 0; j < supvec_length; j++) {
    jobject supvec_item = (*env)->GetObjectArrayElement(env, supvec, j);
    c_model->supvec[j]->docnum = (*env)->GetLongField(env, supvec_item, docnum_fid);
    c_model->supvec[j]->queryid = (*env)->GetLongField(env, supvec_item, queryid_fid);
    c_model->supvec[j]->costfactor = (*env)->GetDoubleField(env, supvec_item, costfactor_fid);
    c_model->supvec[j]->slackid = (*env)->GetLongField(env, supvec_item, slackid_fid);
    c_model->supvec[j]->fvec = (struct s_vector*)malloc(fvec_length * sizeof(struct s_vector*);
    for (int j = 0; j < fvec_length; j++) {
    jobject fvec_item = (*env)->GetObjectArrayElement(env, fvec, j);
    c_model->supvec[j]->fvec[j]->words = (struct word*)malloc(words_length * sizeof(struct word*);
    for (int j = 0; j < words_length; j++) {
    jobject words_item = (*env)->GetObjectArrayElement(env, words, j);
    c_model->supvec[j]->fvec[j]->words[j]->wnum = (*env)->GetIntField(env, words_item, wnum_fid);
    c_model->supvec[j]->fvec[j]->words[j]->weight = (*env)->GetFloatField(env, words_item, weight_fid);
    }
    c_model->supvec[j]->fvec[j]->twonorm_sq = (*env)->GetDoubleField(env, fvec_item, twonorm_sq_fid);
    jobject userdefined = (*env)->GetObjectField(env, fvec_item, userdefined_fid);
    int userdefined_length = (*env)->GetStringLength(env, userdefined);
    int userdefined_UTF_length = (*env)->GetStringUTFLength(env, userdefined);
    c_model->supvec[j]->fvec[j]->userdefined = (char*)malloc((userdefined_UTF_length+1)*sizeof(char));
    (*env)->GetStringUTFRegion(env, userdefined, 0, userdefined_length, c_model->supvec[j]->fvec[j]->userdefined);
    c_model->supvec[j]->fvec[j]->kernel_id = (*env)->GetIntField(env, fvec_item, kernel_id_fid);
    jobject next = (*env)->GetObjectField(env, fvec_item, next_fid);
    c_model->supvec[j]->fvec[j]->next = (struct object*)malloc(sizeof(struct object));
    c_model->supvec[j]->fvec[j]->factor = (*env)->GetDoubleField(env, fvec_item, factor_fid);
    }
    }
    jdoubleArray alpha_array = (*env)->GetObjectField(env, jModel, alpha_fid);
    int alpha_array_length = (*env)->GetArrayLength(env, alpha_array);
    c_model->alpha = (double*)malloc(alpha_array_length * sizeof(double));
    (*env)->GetDoubleArrayRegion(env, alpha_array, 0, alpha_array_length, c_model->alpha);
    jintArray index_array = (*env)->GetObjectField(env, jModel, index_fid);
    int index_array_length = (*env)->GetArrayLength(env, index_array);
    c_model->index = (int*)malloc(index_array_length * sizeof(int));
    (*env)->GetIntArrayRegion(env, index_array, 0, index_array_length, c_model->index);
    c_model->totwords = (*env)->GetIntField(env, jModel, totwords_fid);
    c_model->totdoc = (*env)->GetIntField(env, jModel, totdoc_fid);
    jobject kernel_parm = (*env)->GetObjectField(env, jModel, kernel_parm_fid);
    c_model->kernel_parm = (struct kernel_param*)malloc(sizeof(struct kernel_param));
    c_model->kernel_parm->kerneType = (*env)->GetIntField(env, kernel_parm, kerneType_fid);
    c_model->kernel_parm->polyDegree = (*env)->GetIntField(env, kernel_parm, polyDegree_fid);
    c_model->kernel_parm->rbfGamma = (*env)->GetDoubleField(env, kernel_parm, rbfGamma_fid);
    c_model->kernel_parm->coefLin = (*env)->GetDoubleField(env, kernel_parm, coefLin_fid);
    c_model->kernel_parm->coefConst = (*env)->GetDoubleField(env, kernel_parm, coefConst_fid);
    jobject custom = (*env)->GetObjectField(env, kernel_parm, custom_fid);
    int custom_length = (*env)->GetStringLength(env, custom);
    int custom_UTF_length = (*env)->GetStringUTFLength(env, custom);
    c_model->kernel_parm->custom = (char*)malloc((custom_UTF_length+1)*sizeof(char));
    (*env)->GetStringUTFRegion(env, custom, 0, custom_length, c_model->kernel_parm->custom);
COPY OUT CODE
    (*env)->SetIntField(env, jModel, sv_num_fid, c_model->sv_num);
    (*env)->SetIntField(env, jModel, at_upper_bound_fid, c_model->at_upper_bound);
    (*env)->SetDoubleField(env, jModel, b_fid, c_model->b);
    jobject supvec = (*env)->NewObjectArray(env, doc_class, supvec_length, NULL);
    for (int j = 0; j < supvec_length; j++) {
    jobject supvec_item = (*env)->NewObject(env, doc_class, doc_constructor);
    (*env)->SetLongField(env, supvec_item, docnum_fid, c_model->supvec[j]->docnum);
    (*env)->SetLongField(env, supvec_item, queryid_fid, c_model->supvec[j]->queryid);
    (*env)->SetDoubleField(env, supvec_item, costfactor_fid, c_model->supvec[j]->costfactor);
    (*env)->SetLongField(env, supvec_item, slackid_fid, c_model->supvec[j]->slackid);
    jobject fvec = (*env)->NewObjectArray(env, sVector_class, fvec_length, NULL);
    for (int j = 0; j < fvec_length; j++) {
    jobject fvec_item = (*env)->NewObject(env, sVector_class, sVector_constructor);
    jobject words = (*env)->NewObjectArray(env, word_class, words_length, NULL);
    for (int j = 0; j < words_length; j++) {
    jobject words_item = (*env)->NewObject(env, word_class, word_constructor);
    (*env)->SetIntField(env, words_item, wnum_fid, c_model->supvec[j]->fvec[j]->words[j]->wnum);
    (*env)->SetFloatField(env, words_item, weight_fid, c_model->supvec[j]->fvec[j]->words[j]->weight);
    (*env)->SetObjectArrayElement(env, words, j, words_item);
    }
    (*env)->SetObjectField(env, fvec_item, words_fid, words);
    (*env)->SetDoubleField(env, fvec_item, twonorm_sq_fid, c_model->supvec[j]->fvec[j]->twonorm_sq);
    jstring userdefined = (*env)->NewStringUTF(env, c_model->supvec[j]->fvec[j]->userdefined);
    (*env)->SetObjectField(env, fvec_item, userdefined_fid, userdefined);
    (*env)->SetIntField(env, fvec_item, kernel_id_fid, c_model->supvec[j]->fvec[j]->kernel_id);
    jobject next = (*env)->NewObject(env, object_class, object_constructor);
    (*env)->SetObjectField(env, fvec_item, next_fid, next);
    (*env)->SetDoubleField(env, fvec_item, factor_fid, c_model->supvec[j]->fvec[j]->factor);
    (*env)->SetObjectArrayElement(env, fvec, j, fvec_item);
    }
    (*env)->SetObjectField(env, supvec_item, fvec_fid, fvec);
    (*env)->SetObjectArrayElement(env, supvec, j, supvec_item);
    }
    (*env)->SetObjectField(env, jModel, supvec_fid, supvec);
    jdoubleArray alpha_array = (*env)->NewDoubleArray(env, alpha_length);
    (*env)->SetDoubleArrayRegion(env, alpha_array, 0, alpha_length, c_model->alpha);
    (*env)->SetObjectField(env, jModel, alpha_fid, alpha_array);
    jintArray index_array = (*env)->NewIntArray(env, index_length);
    (*env)->SetIntArrayRegion(env, index_array, 0, index_length, c_model->index);
    (*env)->SetObjectField(env, jModel, index_fid, index_array);
    (*env)->SetIntField(env, jModel, totwords_fid, c_model->totwords);
    (*env)->SetIntField(env, jModel, totdoc_fid, c_model->totdoc);
    jobject kernel_parm = (*env)->NewObject(env, kernelParam_class, kernelParam_constructor);
    (*env)->SetIntField(env, kernel_parm, kerneType_fid, c_model->kernel_parm->kerneType);
    (*env)->SetIntField(env, kernel_parm, polyDegree_fid, c_model->kernel_parm->polyDegree);
    (*env)->SetDoubleField(env, kernel_parm, rbfGamma_fid, c_model->kernel_parm->rbfGamma);
    (*env)->SetDoubleField(env, kernel_parm, coefLin_fid, c_model->kernel_parm->coefLin);
    (*env)->SetDoubleField(env, kernel_parm, coefConst_fid, c_model->kernel_parm->coefConst);
    jstring custom = (*env)->NewStringUTF(env, c_model->kernel_parm->custom);
    (*env)->SetObjectField(env, kernel_parm, custom_fid, custom);
    (*env)->SetObjectField(env, jModel, kernel_parm_fid, kernel_parm);
CLEANUP CODE
    free(c_model->supvec);
    free(c_model->supvec[j]->fvec);
    free(c_model->supvec[j]->fvec[j]->words);
    free(c_model->supvec[j]->fvec[j]->next);
    free(c_model->alpha);
    free(c_model->index);
    free(c_model->kernel_parm);
