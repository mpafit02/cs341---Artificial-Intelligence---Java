#!/bin/sh

die () {
   echo "*** mkconfig.sh: $*" 1>&2
   exit 1
}

[ -f makefile ] || die "can not find 'makefile'"

cat<<EOF
/*************************************************************/
/* Automatically generated by './mkconfig.sh': do note edit! */
/*************************************************************/
EOF

echo "#define LGL_OS \"`uname -srmn`\""
echo "#define LGL_COMPILED \"`date`\""
cat<<EOF
#define LGL_RELEASED "Fri Feb  7 22:21:52 CET 2014"
#define LGL_VERSION "ats1"
#define LGL_ID "ce8c04fc97ef07cf279c0c5dcbbc7c5d9904230a"
EOF
