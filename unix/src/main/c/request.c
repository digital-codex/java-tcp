#include <dev_codex_net_InterfaceRequest.h>

#include <stdlib.h>
#include <string.h>

#include <linux/if.h>

#ifdef __cplusplus
extern "C" {
#endif

static jfieldID field;

static struct ifreq *address(JNIEnv *env, jobject this) {
	return (struct ifreq *) (*env)->GetLongField(env, this, field);
}

JNIEXPORT jlong JNICALL Java_dev_codex_net_InterfaceRequest_allocate(JNIEnv *env, jobject this) {
	struct ifreq *address = (struct ifreq *) malloc(sizeof(struct ifreq));
	memset(address->ifr_name, 0, IFNAMSIZ);
	address->ifr_flags = 0;
	return (long) address;
}

JNIEXPORT jbyteArray JNICALL Java_dev_codex_net_InterfaceRequest_getName(JNIEnv *env, jobject this) {
	char name[IFNAMSIZ] = {0};
	strncpy(name, (address(env, this))->ifr_name, IFNAMSIZ);
	jbyteArray name_array = (*env)->NewByteArray(env, IFNAMSIZ);
	(*env)->SetByteArrayRegion(env, name_array, 0, IFNAMSIZ, (const jbyte *) name);
	return name_array;
}

JNIEXPORT void JNICALL Java_dev_codex_net_InterfaceRequest_setName(JNIEnv *env, jobject this, jbyteArray name) {
	char *buf = (char *) (*env)->GetByteArrayElements(env, name, NULL);
	strncpy((address(env, this))->ifr_name, buf, IFNAMSIZ);
	(*env)->ReleaseByteArrayElements(env, name, (jbyte *) buf, JNI_ABORT);
}

JNIEXPORT jshort JNICALL Java_dev_codex_net_InterfaceRequest_getFlags(JNIEnv *env, jobject this) {
	return (address(env, this))->ifr_flags;
}

JNIEXPORT void JNICALL Java_dev_codex_net_InterfaceRequest_setFlags(JNIEnv *env, jobject this, jshort flag) {
	(address(env, this))->ifr_flags |= flag;
}

JNIEXPORT void JNICALL Java_dev_codex_net_InterfaceRequest_free(JNIEnv *env, jobject this) {
	free(address(env, this));
}

JNIEXPORT void JNICALL Java_dev_codex_net_InterfaceRequest_init(JNIEnv *env, jclass class) {
	field = (*env)->GetFieldID(env, class, "address", "J");
}

#ifdef __cplusplus
}
#endif