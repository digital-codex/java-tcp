#include <dev_codex_system_LibCWrapper.h>

#include <errno.h>
#include <unistd.h>
#include <linux/if.h>
#include <linux/if_tun.h>

#include <sys/ioctl.h>
#include <fcntl.h>

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jint JNICALL Java_dev_codex_system_LibCWrapper_open(JNIEnv *env, jclass class, jstring path, jint flags) {
	const char *pathname = (*env)->GetStringUTFChars(env, path, NULL);
	int result = open(pathname, flags);
	(*env)->ReleaseStringUTFChars(env, path, pathname);
	return result;
}

JNIEXPORT jint JNICALL Java_dev_codex_system_LibCWrapper_close(JNIEnv *env, jclass class, jint fd) {
	return close(fd);
}

JNIEXPORT jlong JNICALL Java_dev_codex_system_LibCWrapper_TUNSETIFF(JNIEnv *env, jclass class) {
	return TUNSETIFF;
}

JNIEXPORT jint JNICALL Java_dev_codex_system_LibCWrapper_ioctl(JNIEnv *env, jclass class, jint fd, jlong request, jlong arg) {
	return ioctl(fd, request, arg);
}

#ifdef __cplusplus
}
#endif