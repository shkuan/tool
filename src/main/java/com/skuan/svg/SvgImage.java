package com.skuan.svg;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

class SvgImage extends JComponent{

    public BufferedImage bufferedImage;

    @Override
    protected void paintComponent(Graphics g) {
      if (bufferedImage == null) {
        return;
      }
      Graphics2D gsd = (Graphics2D) g;
      gsd.drawImage(bufferedImage, null, 0, 0);
      super.paintComponent(g);
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
      this.bufferedImage = bufferedImage;
    }

}
