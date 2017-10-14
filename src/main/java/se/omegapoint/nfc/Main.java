package se.omegapoint.nfc;

import se.omegapoint.nfc.libnfc.*;

import javax.smartcardio.CommandAPDU;

public class Main {

    static {
        try {
            System.loadLibrary("nfcjni");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Failed to load libnfcjni.{so,dylib}\n" + e);
            System.exit(1);
        }
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes, int count) {
        int len = Math.min(bytes.length, count);
        char[] hexChars = new char[len * 3];
        for (int j = 0; j < len; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = hexArray[v >>> 4];
            hexChars[j * 3 + 1] = hexArray[v & 0x0F];
            hexChars[j * 3 + 2] = ' ';
        }
        return new String(hexChars);
    }

    public static String bytesToHex(byte[] bytes) {
        return bytesToHex(bytes, bytes.length);
    }

    public static void main(String[] args) {
        SWIGTYPE_p_p_nfc_context ctxPtr = nfc.new_SWIGTYPE_p_p_nfc_context();
        nfc.nfc_init(ctxPtr);
        SWIGTYPE_p_nfc_context ctx = nfc.SWIGTYPE_p_p_nfc_context_value(ctxPtr);
        nfc.delete_SWIGTYPE_p_p_nfc_context(ctxPtr);

        SWIGTYPE_p_nfc_device reader = nfc.nfc_open(ctx, null);
        System.out.println("NFC version: "+nfc.nfc_version());

        System.out.println("Initiator init result: "+nfc.nfc_initiator_init(reader));

        nfc_modulation mod = new nfc_modulation();
        mod.setNbr(nfc_baud_rate.NBR_106);
        mod.setNmt(nfc_modulation_type.NMT_ISO14443A);

        nfc_target target = new nfc_target();
        System.out.println("Select passive target result: "+nfc.nfc_initiator_select_passive_target(reader, mod, new byte[0], 0, target));
        nfc_iso14443a_info nai = target.getNti().getNai();
        System.out.println("ATS: " + bytesToHex(nai.getAbtAts(), (int) nai.getSzAtsLen()));

        byte[] result = new byte[256];
        byte[] command = new CommandAPDU(0x00, 0xA4, 0x04, 0x00, "1PAY.SYS.DDF01".getBytes()).getBytes();

        int count = nfc.nfc_initiator_transceive_bytes(reader, command, command.length, result, result.length, 0);
        System.out.println("Response length: " + count);
        System.out.println("Response data: " + bytesToHex(result, count));

        nfc.nfc_close(reader);
        nfc.nfc_exit(ctx);
    }
}
