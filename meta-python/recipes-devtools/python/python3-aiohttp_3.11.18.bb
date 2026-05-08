SUMMARY = "Async http client/server framework"
DESCRIPTION = "Asynchronous HTTP client/server framework for asyncio and Python"
HOMEPAGE = "https://github.com/aio-libs/aiohttp"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=748073912af33aa59430d3702aa32d41"

SRC_URI[sha256sum] = "ae856e1138612b7e412db63b7708735cff4d38d0399f6a5435d3dac2669f558a"

SRC_URI += "file://CVE-2025-53643.patch \
            file://CVE-2026-34520.patch \
            file://CVE-2025-69224.patch \
            file://CVE-2025-69225.patch \
            file://CVE-2025-69226.patch \
            file://CVE-2025-69227.patch \
            file://CVE-2025-69228.patch \
            file://CVE-2025-69229-1.patch \
            file://CVE-2025-69229-2.patch \
"

inherit python_setuptools_build_meta pypi

RDEPENDS:${PN} = "\
    python3-aiohappyeyeballs \
    python3-aiosignal \
    python3-async-timeout \
    python3-attrs \
    python3-frozenlist \
    python3-misc \
    python3-multidict \
    python3-yarl \
    python3-aiodns \
"
