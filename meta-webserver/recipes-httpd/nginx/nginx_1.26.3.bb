require nginx.inc

SRC_URI:append = " file://CVE-2026-32647.patch \
                   file://CVE-2026-27784.patch \
                   file://CVE-2026-28753.patch \
                   file://CVE-2026-27654.patch \
                   file://CVE-2026-27651.patch \
                   file://CVE-2026-40701.patch \
                   file://CVE-2026-40460.patch \
                   file://CVE-2026-42934.patch \
"

LIC_FILES_CHKSUM = "file://LICENSE;md5=a6547d7e5628787ee2a9c5a3480eb628"

SRC_URI[sha256sum] = "69ee2b237744036e61d24b836668aad3040dda461fe6f570f1787eab570c75aa"

