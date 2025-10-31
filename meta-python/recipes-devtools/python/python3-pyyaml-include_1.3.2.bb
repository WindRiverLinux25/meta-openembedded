SUMMARY = "Extending PyYAML with a custom constructor for including YAML files within YAML files"
HOMEPAGE = "https://github.com/tanbro/pyyaml-include"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d32239bcb673463ab874e80d47fae504"
DEPENDS += "python3-setuptools-scm-native"
SRCREV = "36b6975aa8fc7a6cbf37de40aa2ed6d996b2f7be"

SRC_URI = " \
            git://github.com/tanbro/pyyaml-include;protocol=https;branch=main \
          "

S = "${WORKDIR}/git"

inherit python_setuptools_build_meta ptest-python-pytest

do_compile:prepend() {
    export SETUPTOOLS_SCM_PRETEND_VERSION=${PV}
}

RDEPENDS:${PN} += " \
    python3-pyyaml \
    python3-fsspec \
"
RDEPENDS:${PN}-ptest += " \
    python3-fsspec \
    python3-aiohttp \
    python3-requests \
"
BBCLASSEXTEND = "native nativesdk"
