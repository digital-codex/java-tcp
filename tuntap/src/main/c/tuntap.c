#include <jni.h>
#include <stdio.h>

/**
 * fd - the fc to turn into TUN or TAP
 * name - the name to use. If empty, kernel will assign something by itself.
 *     Must be buffered with capacity at least 33.
 * mode - 1 = TUN, 2 = TAP.
 * packet_info - if packet info should be provided, if the given value is 0 it will not prepend packet info.
 */
JNIEXPORT jint JNICALL Java_dev_digitalcodex_io_TunTap_tuntap_1setup(JNIEnv *env, jobject obj, jint fd, jstring name, jint mode, jint packet_info) {
	const char *native_name = (*env)->GetStringUTFChars(env, name, 0);
	printf("%s\n", native_name);
	(*env)->ReleaseStringUTFChars(env, name, native_name);
	return 0;
}
