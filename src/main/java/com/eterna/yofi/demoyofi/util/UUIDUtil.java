package com.eterna.yofi.demoyofi.util;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class UUIDUtil {

    private static final String HEX_STRING = "0123456789ABCDEF";

    private UUIDUtil() {
        throw new IllegalStateException("UUID Utility class");
    }

    public static String getUuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }

    public static byte[] getRawUuid() {
        return stringToByte(getUuid());
    }

    public static String byteToString(byte[] array) {
        if (null != array && array.length != 0) {
            char[] hexArray = HEX_STRING.toCharArray();
            char[] hex = new char[32];

            for (int i = 0; i < array.length; ++i) {
                int v = array[i] & 255;
                hex[i * 2] = hexArray[v >>> 4];
                hex[i * 2 + 1] = hexArray[v & 15];
            }
            return new String(hex);
        }
        return null;
    }

    public static byte[] stringToByte(String uuid) {
        if (StringUtils.isEmpty(uuid))
            return new byte[0];

        if (uuid.length() == 36) {
            uuid = uuid.replace("-", "");
        }

        byte[] b = new byte[16];
        int len = uuid.length();

        for (int i = 0; i < len; i += 2) {
            int firstRadix = Character.digit(uuid.charAt(i), 16) << 4;
            int secondRadix = Character.digit(uuid.charAt(i + 1), 16);
            b[i / 2] = (byte) (firstRadix + secondRadix);
        }

        return b;
    }

    public static boolean verifyUuid(String uuid) {
        char[] cArrayUuId = uuid.toCharArray();
        return cArrayUuId.length == 32 || cArrayUuId.length == 36;
    }

    public static UUID toUuid(String uuidString) {
        if (uuidString == null)
            return null;

        String plainUuid = uuidString.trim().replace("-", "");
        int len = plainUuid.length();

        if (len != 32)
            throw new IllegalArgumentException("Invalid UUID format");

        plainUuid = plainUuid.toLowerCase();
        char[] uuidChars = new char[36];
        int i = 0;

        int originalIdx;
        for (originalIdx = 0; i < 8; ++i) {
            uuidChars[i] = plainUuid.charAt(originalIdx++);
        }

        uuidChars[i++] = '-';

        for (int j = 0; j < 3; ++j) {
            for (int k = 0; k < 4; ++k) {
                uuidChars[i++] = plainUuid.charAt(originalIdx++);
            }

            uuidChars[i++] = '-';
        }

        while (i < 36) {
            uuidChars[i] = plainUuid.charAt(originalIdx++);
            ++i;
        }

        return UUID.fromString(String.valueOf(uuidChars));
    }

}
