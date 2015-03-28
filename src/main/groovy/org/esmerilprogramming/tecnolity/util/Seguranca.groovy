package org.esmerilprogramming.tecnolity.util

import java.security.*
import sun.misc.*

class Seguranca {
  String cryptograph(final String string) throws Exception {
    final MessageDigest md = MessageDigest.getInstance("MD5")
    final byte[] stringBytes = string.getBytes("UTF8")
    final byte[] stringCriptBytes = md.digest(stringBytes)
    new BASE64Encoder().encode(stringCriptBytes)
  }
}
