PACKAGE=se.omegapoint.nfc.libnfc
OUTDIR=../src/main/java/se/omegapoint/nfc/libnfc

JAVAPATH=$(JAVA_HOME)/include

all:
	@echo "Run make linux or make mac"

mac: LIBPATH = /usr/local/Cellar/libnfc/1.7.1/include/
mac: swig
	gcc -dynamiclib -I$(LIBPATH) -I$(JAVAPATH) -I$(JAVAPATH)/darwin/ -lnfc -o libnfcjni.dylib nfc_wrap.c

linux: LIBPATH = /usr/include/
linux: swig
	gcc -c -I$(JAVAPATH) -I$(JAVAPATH)/linux/ -o libnfcjni.o nfc_wrap.c
	ld -shared -lnfc libnfcjni.o -o libnfcjni.so

swig:
	@echo "Package: " $(PACKAGE)
	@echo "Outdir: " $(OUTDIR)
	@echo "Libpath:" $(LIBPATH)
	swig -java -I$(LIBPATH) -outdir $(OUTDIR) -package $(PACKAGE) nfc.i

clean:
	rm -f $(OUTDIR)/*.java libnfcjni.{dylib,so} ibnfcjni.o nfc_wrap.c

.PHONY: mac linux clean
