:: Generate Header File For JniWrapper

set cwd=%~dp0
 
set CURR_DIR=%~dp0
echo '### Compiling JniWrapper.java ###'
cd "src\main\java"
cd "edu\temple\cla\wolfgang\jnismvlight"
javac SVMLight.java
 
echo '### Generating JNI Header File For JniWrapper.java ###'
cd %CURR_DIR%
cd "src\main\java"
javah -verbose -jni -d ..\native\include edu.temple.cla.wolfgang.svmlight
 
cd %CURR_DIR%