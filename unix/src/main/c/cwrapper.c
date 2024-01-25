#include <dev_codex_system_LibCWrapper.h>

#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <errno.h>
#include<string.h>

#include <linux/if.h>
#include <linux/if_tun.h>
#include <sys/ioctl.h>

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jstring JNICALL Java_dev_codex_system_LibCWrapper_strerror(JNIEnv *env, jclass class) {
	char *error = strerror(errno);

	int i = 0; while (error[i] != '\0') i++;
	jbyteArray error_array = (*env)->NewByteArray(env, i);
	(*env)->SetByteArrayRegion(env, error_array, 0, i, (const jbyte *) error);

	jclass string_class = (*env)->FindClass(env, "java/lang/String");
	jmethodID constructor = (*env)->GetMethodID(env, string_class, "<init>", "([B)V");
	jstring error_string = (jstring) (*env)->NewObject(env, string_class, constructor, error_array);
	return error_string;
}

JNIEXPORT jint JNICALL Java_dev_codex_system_LibCWrapper_open(JNIEnv *env, jclass class, jstring path, jint flags) {
	const char *pathname = (*env)->GetStringUTFChars(env, path, NULL);
	int result = open(pathname, flags);
	(*env)->ReleaseStringUTFChars(env, path, pathname);
	return result;
}

JNIEXPORT jint JNICALL Java_dev_codex_system_LibCWrapper_close(JNIEnv *env, jclass class, jint fd) {
	return close(fd);
}

JNIEXPORT jint JNICALL Java_dev_codex_system_LibCWrapper_write(JNIEnv *env, jclass class, jint fd, jbyteArray buffer, jint count) {
	char *buf = (char *) (*env)->GetByteArrayElements(env, buffer, NULL);
	int result = write(fd, buf, count);
	(*env)->ReleaseByteArrayElements(env, buffer, (jbyte *) buf, JNI_ABORT);
	return result;
}

JNIEXPORT jint JNICALL Java_dev_codex_system_LibCWrapper_read(JNIEnv *env, jclass class, jint fd, jbyteArray buffer, jint count) {
	char *buf = (char *) malloc(count * sizeof(char));
	int result = read(fd, buf, count);
	(*env)->SetByteArrayRegion(env, buffer, 0, count, (const jbyte *) buf);
	return result;
}

JNIEXPORT jlong JNICALL Java_dev_codex_system_LibCWrapper_TUNSETIFF(JNIEnv *env, jclass class) {
	return TUNSETIFF;
}

JNIEXPORT jint JNICALL Java_dev_codex_system_LibCWrapper_ioctl(JNIEnv *env, jclass class, jint fd, jlong request, jlong arg) {
	printf("fd: %d\n", fd);
	printf("name: %s\n", ((struct ifreq *) arg)->ifr_name);
	printf("flags: %d\n", ((struct ifreq *) arg)->ifr_flags);
	return ioctl(fd, request, arg);
}

#ifdef __cplusplus
}
#endif