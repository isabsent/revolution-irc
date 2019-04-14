package io.mrarm.chatlib.util;

import android.util.Base64;

public class Base64UtilImpl extends Base64Util {

    @Override
    public String encodeData(byte[] data) {
        return Base64.encodeToString(data, Base64.NO_WRAP);
    }

}
