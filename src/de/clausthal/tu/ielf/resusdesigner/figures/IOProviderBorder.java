/*******************************************************************************
 * Copyright (c) 2000, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package de.clausthal.tu.ielf.resusdesigner.figures;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

import de.clausthal.tu.ielf.resusdesigner.model.IOProvider;

public class IOProviderBorder extends AbstractBorder {

	public IOProviderBorder(IOProvider src){
		source=src;
	}
	protected IOProvider source=null;
	protected static Insets insets = new Insets(8, 6, 8, 6);
	protected static PointList inputConnector = new PointList();
	protected static PointList outputConnector = new PointList();

	static {
		
		// for each connector we make shape like this : 
		//	.-.
		//  | |
		//	/_/
		//   
		// it will be used for each connector. it means we have 4 times for input and 4 times for output
		inputConnector .addPoint(-3, 5);
		inputConnector .addPoint(-1, 3);
		inputConnector .addPoint(3, 3);
		inputConnector .addPoint(3, -4);
		inputConnector .addPoint(-1, -4);
		//inputConnector .addPoint(-2, -2);
		inputConnector .addPoint(-3, -5);

		outputConnector.addPoint(-2, -1);
		outputConnector.addPoint(1, -1);
		outputConnector.addPoint(8, -2);
		outputConnector.addPoint(8, -6);
		outputConnector.addPoint(-1, -6);
		outputConnector.addPoint(-1, -2);
	}

	private void drawConnectors(Graphics g, Rectangle rec, int inputConnectors, int outputconnectors) {
		int x1 = rec.x, height = rec.height, y1, end = x1 + rec.width;
		g.setBackgroundColor(LogicColorConstants.connectorBlack);
//		int h=25*inputConnectors;
//		if (inputConnectors>4){
//			h=25*inputConnectors;
//			
//			
//		}
//		else h=100;
		
//		source.getSize().setHeight(h);
//		height=h;
		
		for (int i = 0; i < inputConnectors; i++) {
			y1 = rec.y + ( i) *25 +12;

			// Draw the "gap" for the connector
			g.setForegroundColor(ColorConstants.listBackground);
			g.drawLine(x1 , y1 -4, x1 , y1 +3);

			// Draw the connectors
			g.setForegroundColor(LogicColorConstants.connectorBlack);
			inputConnector.translate(x1, y1);
			g.fillPolygon(inputConnector);
			g.drawPolygon(inputConnector);
			inputConnector.translate(-x1, -y1);
			g.setForegroundColor(ColorConstants.listBackground);
			g.drawLine(x1 + 3, end - 3, x1 + 3, end- 3);
//			g.setForegroundColor(LogicColorConstants.connectorGreen);
//			outputConnector.translate(x1, end);
//			g.fillPolygon(outputConnector);
//			g.drawPolygon(outputConnector);
//			outputConnector.translate(-x1, -end);
		}
		
		
		// draw output
		y1 = rec.y + height / 2;
		x1=rec.x+rec.width;
		// Draw the "gap" for the connector
		g.setForegroundColor(ColorConstants.listBackground);
		g.drawLine(x1 , y1 -4, x1 , y1 +3);
		//System.out.println("x1="+x1+"   y1="+y1);
		// Draw the connectors
		g.setForegroundColor(LogicColorConstants.connectorBlack);
		inputConnector.translate(x1, y1);
		g.fillPolygon(inputConnector);
		g.drawPolygon(inputConnector);
		inputConnector.translate(-x1, -y1);
		g.setForegroundColor(ColorConstants.listBackground);
		g.drawLine(x1 + 3, end - 3, x1 + 3, end- 3);
		
	}

	public Insets getInsets(IFigure figure) {
		return insets;
	}

	public void paint(IFigure figure, Graphics g, Insets in) {
		Rectangle r = figure.getBounds().getCropped(in);

		g.setForegroundColor(LogicColorConstants.logicRed);
		g.setBackgroundColor(LogicColorConstants.logicRed);

		// Draw the sides of the border
		
		PointList triangle = new PointList();
		triangle.addPoint(r.x, r.y + 2);
		triangle.addPoint(r.right() - 1, r.y +(r.height/2));
		triangle.addPoint(r.x, r.bottom() - 3);
		
		g.fillPolygon(triangle);
		
		g.setForegroundColor(LogicColorConstants.logicTextWhite);
		
		g.drawText("Input Provider", r.x+5, r.y+40);
		g.drawText("ID:"+source.getId(), r.x+5,r.y+55);
		//g.fillOval(r.x+3, r.y+1, r.width-2, r.height-1);
//		g.fillOval(r.x, r.bottom() - 8, r.width, 6);
//		g.fillOval(r.x, r.y + 2, 6, r.height - 4);
//		g.fillOval(r.right() - 6, r.y + 2, 6, r.height - 4);

		// Outline the border
		g.setForegroundColor(LogicColorConstants.connectorBlack);
		g.drawLine(r.x, r.y + 2, r.right() - 1, r.y +(r.height/2));
		g.drawLine(r.x, r.bottom() - 3, r.right() - 1, r.bottom() -(r.height/2));
		g.drawLine(r.x+3, r.y + 2, r.x+3, r.bottom() - 3);
//		g.drawLine(r.right() - 1, r.bottom() - 3, r.right() - 1, r.y + 2);

		r.crop(new Insets(1, 1, 0, 0));
		r.expand(1, 1);
		r.crop(getInsets(figure));
		drawConnectors(g, figure.getBounds().getCropped(in), source.getNumberOfInputs(),1);
	}

}
