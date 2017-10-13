#!/bin/bash

PACKAGE=se.omegapoint.nfc.libnfc
OUTDIR=../src/main/java/se/omegapoint/nfc/libnfc

NFCPATH=/usr/local/Cellar/libnfc/1.7.1
JAVAPATH=/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home

swig -java \
     -I$NFCPATH/include/ \
     -outdir ${OUTDIR} \
     -package ${PACKAGE} \
     Nfc.i

gcc -dynamiclib -I$NFCPATH/include/ -I$JAVAPATH/include/ -I$JAVAPATH/include/darwin/ -lnfc -L$NFCPATH/lib -o nfcjni.dylib nfc_wrap.c
