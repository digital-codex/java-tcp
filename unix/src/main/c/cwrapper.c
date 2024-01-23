#include <dev_codex_system_LibCWrapper.h>
#include <stdio.h>

/**
 * fd - the fc to turn into TUN or TAP
 * name - the name to use. If empty, kernel will assign something by itself.
 *     Must be buffered with capacity at least 33.
 * mode - 1 = TUN, 2 = TAP.
 * packet_info - if packet info should be provided, if the given value is 0 it will not prepend packet info.
 */
JNIEXPORT jint JNICALL Java_dev_codex_system_LibCWrapper_ioctl(JNIEnv *env, jclass this, jint fd, jbyteArray name, jint mode) {
	jbyte *_name = (jbyte *) (*env)->GetByteArrayElements(env, name, NULL);
	printf("%s\n", _name);
	_name[0] = 'h';
	(*env)->ReleaseByteArrayElements(env, name, _name, 0);
	return 0;
}