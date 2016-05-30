package org.eclipse.ui.views.properties.tabbed.resus.properties;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import javax.swing.Icon;
public  class ColorIcon implements Icon {

    private Dimension size;
    private Color color;
    private Shape shape;
    private Shape line;
    private String text;

    public ColorIcon(Dimension size, Color color,Shape shape, Shape line,String text) {
        this.size = size;
        this.color = color;
        this.shape=shape;
        this.line=line;
        this.text=text;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
    	 
    	
    	Graphics2D g2d = (Graphics2D) g;
        g2d.translate(15, 20);
       g2d.setClip(shape);
        
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_DEFAULT);
       
        g2d.setColor(color);
      
      
        g2d.fill(shape);
        g2d.fill(line);
      

        g2d.setClip(null);
       // g2d.drawString(text, shape.getBounds().x+10,shape.getBounds().y+5);
        
      
      
    }

    @Override
    public int getIconWidth() {
        return size.width;
    }

    @Override
    public int getIconHeight() {
        return size.height;
    }
}

