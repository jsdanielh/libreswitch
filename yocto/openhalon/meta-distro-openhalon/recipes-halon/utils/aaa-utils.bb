SUMMARY = "Halon Configuration Daemon"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "halon-ovsdb"

RDEPENDS_${PN} = "python-argparse python-json python-halon-ovsdb python-distribute python-pam"

SRC_URI = "git://git.openhalon.io/openhalon/aaa-utils;protocol=http \
           file://aaautils.service \
           file://server \
           file://useradd \
         "

SRCREV="${AUTOREV}"

# When using AUTOREV, we need to force the package version to the revision of git
# in order to avoid stale shared states.
PV = "git${SRCPV}"

S = "${WORKDIR}/git"

do_install_prepend() {
     install -d ${D}${systemd_unitdir}/system
     install -m 0644 ${WORKDIR}/aaautils.service ${D}${systemd_unitdir}/system/
     install -d ${D}${sysconfdir}/raddb
     install -m 0644 ${WORKDIR}/server ${D}${sysconfdir}/raddb/server
     install -d ${D}${sysconfdir}/sudoers.d
     install -m 0644 ${WORKDIR}/useradd ${D}${sysconfdir}/sudoers.d/useradd
}
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "aaautils.service"

inherit halon setuptools systemd
FILES_${PN}   += "${sysconfdir}/raddb/ ${sysconfdir}/sudoers.d/"
