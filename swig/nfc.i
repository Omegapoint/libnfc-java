%module nfc
%{
#include <nfc/nfc.h>
#include <nfc/nfc-types.h>
#include <nfc/nfc-emulation.h>
%}

// for some reason these functions caused linking failures on osx
%ignore iso14443b_crc;
%ignore iso14443b_crc_append;
%ignore iso14443a_locate_historical_bytes;



%include "arrays_java.i"
JAVA_ARRAYS_DECL(uint8_t, jbyte, Byte, Uint8)
JAVA_ARRAYS_IMPL(uint8_t, jbyte, Byte, Uint8)
JAVA_ARRAYS_TYPEMAPS(uint8_t, byte, jbyte, Uint8, "[B")
%apply uint8_t[] { uint8_t* }


%include <nfc/nfc.h>
%include <nfc/nfc-types.h>
%include <nfc/nfc-emulation.h>

%include "cpointer.i"
%pointer_functions(nfc_context*, SWIGTYPE_p_p_nfc_context)

