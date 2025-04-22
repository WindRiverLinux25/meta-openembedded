SUMMARY = "The implementation of SOME/IP"
DESCRIPTION = "The vsomeip stack implements the http://some-ip.com/ \
(Scalable service-Oriented MiddlewarE over IP (SOME/IP)) protocol."
HOMEPAGE = "https://github.com/COVESA/vsomeip"
SECTION = "net"

LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9741c346eef56131163e13b9db1241b3"

GTEST_VER = "1.16.0"
SRC_URI = "git://github.com/GENIVI/${BPN}.git;branch=master;protocol=https;name=vsomeip \
           https://github.com/google/googletest/releases/download/v${GTEST_VER}/googletest-${GTEST_VER}.tar.gz;name=gtest;subdir=git/ \
           file://0001-Fix-pkgconfig-dir-for-multilib.patch \
           file://0002-Install-example-configuration-files-to-etc-vsomeip.patch \
           file://0004-Do-not-specify-PIE-flag-explicitly.patch \
           file://0005-test-common-CMakeLists.txt-add-missing-link-with-dlt.patch \
          "

SRCREV = "cdb1160b908ee35763ae00c530e90ff9cd88cbb3"
SRC_URI[gtest.sha256sum] = "78c676fc63881529bf97bf9d45948d905a66833fbfa5318ea2cd7478cb98f399"

COMPATIBLE_HOST:mips = "null"
COMPATIBLE_HOST:mips64 = "null"
COMPATIBLE_HOST:powerpc = "null"
COMPATIBLE_HOST:libc-musl = 'null'

DEPENDS = "boost dlt-daemon"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

python __anonymous() {
    if not bb.utils.contains("DISTRO_FEATURES", "vsomeip", True, False, d):
        msg = "\nThe vsomeip requries boost 1.86.x.\n"
        msg += "Please add the following lines to local.conf:\n"
        msg += "    DISTRO_FEATURES:append = ' vsomeip'\n"
        msg += "    PREFERRED_VERSION_boost = '1.86.%'\n"
        msg += "    PREFERRED_VERSION_boost-build-native = '1.86.%'\n"
        raise bb.parse.SkipPackage(msg)
}

EXTRA_OECMAKE = "-DINSTALL_LIB_DIR:PATH=${baselib} \
                 -DINSTALL_CMAKE_DIR:PATH=${baselib}/cmake/vsomeip3 \
                 -DGTEST_ROOT=${S}/googletest-${GTEST_VER} \
                "

# For vsomeip-test
EXTRA_OECMAKE += "-DTEST_IP_MASTER=10.0.3.1 \
                  -DTEST_IP_SLAVE=10.0.3.2 \
                  -DTEST_IP_SLAVE_SECOND=10.0.3.3 \
                  -DTEST_UID=1000 -DTEST_GID=1000 \
                 "

RDEPENDS:${PN}-test = "bash lsof"

OECMAKE_TARGET_COMPILE += "vsomeip_ctrl examples build_tests"

do_compile:prepend() {
    sed -i -e 's#${S}/build#/opt/${PN}-test#g' ${S}/test/unit_tests/security_policy_manager_impl_tests/policy_manager_impl_unit_test_macro.hpp
}

do_install:append() {
    install -d ${D}/opt/${PN}-test/examples
    install -m 0755 ${B}/examples/*-sample ${D}/opt/${PN}-test/examples
    install -d ${D}/opt/${PN}-test/examples/routingmanagerd
    install -m 0755 ${B}/examples/routingmanagerd/routingmanagerd \
        ${D}/opt/${PN}-test/examples/routingmanagerd

    install -d ${D}/opt/${PN}-test/test/test/common
    cp -rf ${S}/test/common/examples_policies \
        ${D}/opt/${PN}-test/test/test/common/

    install -d ${D}/opt/${PN}-test/test/common
    install -m 0755 ${B}/test/common/libvsomeip_utilities.so \
        ${D}/opt/${PN}-test/test/common/

    for d in unit_tests network_tests; do
        install -d ${D}/opt/${PN}-test/test/$d
        cp -rf ${B}/test/$d/*_tests ${D}/opt/${PN}-test/test/$d
        find ${D}/opt/${PN}-test/test/$d -maxdepth 2 \( -name "*.cmake" -o -name "CMakeFiles" \) -exec rm -rf {} \;
    done
    sed -i -e 's#../..${B}#/opt/${PN}-test#g' ${D}/opt/${PN}-test/test/network_tests/lazy_load_tests/vsomeip/vsomeip_policy_extensions.json
}

PACKAGES += "${PN}-test"

FILES:${PN}-dbg += " \
   /opt/${PN}-test/.debug/* \
   "
FILES:${PN}-test = " \
   /opt/${PN}-test \
   "
