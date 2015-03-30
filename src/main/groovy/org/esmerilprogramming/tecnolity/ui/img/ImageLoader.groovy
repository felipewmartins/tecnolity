package org.esmerilprogramming.tecnolity.ui.img

import javax.swing.ImageIcon

@Singleton
class ImageLoader {

  ImageIcon icon(String ico) {
    new ImageIcon(getClass().getResource('/org/esmerilprogramming/tecnolity/ui/img/' + ico))
  }

}
