require python3-django.inc
inherit python_setuptools_build_meta

SRC_URI += "file://CVE-2025-64459-1.patch \
            file://CVE-2025-64459-2.patch \
            file://CVE-2025-57833.patch \
            file://CVE-2025-59681.patch \
            file://CVE-2026-4277.patch \
           "
SRC_URI[sha256sum] = "29019a5763dbd48da1720d687c3522ef40d1c61be6fb2fad27ed79e9f655bc11"

RDEPENDS:${PN} += "\
    python3-sqlparse \
    python3-asgiref \
"

CVE_STATUS[CVE-2025-27556] = "not-applicable-platform: vulnerability affects only Windows"
