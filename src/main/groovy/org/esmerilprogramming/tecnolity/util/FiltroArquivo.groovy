package org.esmerilprogramming.tecnolity.util

import javax.swing.filechooser.*



class FiltroArquivo extends FileFilter {
  String description
  ArrayList exts

   FiltroArquivo() {
    this.exts = new ArrayList()
  }

  void addType(final String type) {
    this.exts.add(type)
  }

  boolean accept(final File f) {
    if (f.isDirectory()) {
      return true
    }
    if (f.isFile()) {
      final Iterator it = this.exts.iterator()
        while (it.hasNext()) {
          if (f.getName().endsWith(it.next())) {
            return true
          }
        }
    }
    return false
  }

}
