#include <dev_codex_io_TunTap.h>
#include <stdlib.h>
#include <stdint.h>
#include <string.h>
#include <sys/socket.h>
#include <linux/if.h>
#include <linux/if_tun.h>
#include <sys/ioctl.h>
#include <errno.h>

/**
 * fd - the fc to turn into TUN or TAP
 * name - the name to use. If empty, kernel will assign something by itself.
 *     Must be buffered with capacity at least 33.
 * mode - 1 = TUN, 2 = TAP.
 * packet_info - if packet info should be provided, if the given value is 0 it will not prepend packet info.
 */
JNIEXPORT jstring JNICALL Java_dev_codex_io_TunTap_tuntap_1setup(JNIEnv *env, jobject instance, jint fd, jstring name, jint mode, jint packet_info) {
	struct ifreq ifr;
	memset(&ifr, 0, sizeof(struct ifreq));
	switch (mode) {
		case 0:
			ifr.ifr_flags = IFF_TUN;
			break;
		case 1:
			ifr.ifr_flags = IFF_TAP;
			break;
		default:
			return NULL;
	}

	if (!packet_info) {
		ifr.ifr_flags |= IFF_NO_PI;
	}

	char *_name = (char *) (*env)->GetStringUTFChars(env, name, NULL);
	strncpy(ifr.ifr_name, _name, IFNAMSIZ - 1);

	int ioresult = ioctl(fd, TUNSETIFF, &ifr);
	if (ioresult < 0) {
		printf("%s\n", strerror(errno));
		return NULL;
	}
	printf("HERE3\n");
	strncpy(_name, ifr.ifr_name, IFNAMSIZ < 32 ? IFNAMSIZ : 32);
	jstring result = (*env)->NewStringUTF(env, _name);
	(*env)->ReleaseStringUTFChars(env, name, _name);
	return result;
}
