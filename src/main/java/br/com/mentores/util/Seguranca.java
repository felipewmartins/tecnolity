package br.com.mentores.util;

import java.security.*;
import sun.misc.*;

public class Seguranca
{
    public String cryptograph(final String string) throws Exception {
        final MessageDigest md = MessageDigest.getInstance("MD5");
        final byte[] stringBytes = string.getBytes("UTF8");
        final byte[] stringCriptBytes = md.digest(stringBytes);
        return new BASE64Encoder().encode(stringCriptBytes);
    }
}
