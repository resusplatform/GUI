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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PuristicScrollPane;
import org.eclipse.draw2d.ScrollPane;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.handles.HandleBounds;

import de.clausthal.tu.ielf.resusdesigner.model.ResultConverter;

public class ResultConverterFigure extends NodeFigure implements HandleBounds {

	private ScrollPane scrollpane;
	private ResultConverter source;

	public ResultConverterFigure(ResultConverter src) {
		source=src;
		setBorder(new ResultConverterBorder(src));
		scrollpane = new PuristicScrollPane();
		IFigure pane = new FreeformLayer();
		pane.setLayoutManager(new FreeformLayout());
		setLayoutManager(new StackLayout());
		add(scrollpane);
		scrollpane.setViewport(new FreeformViewport());
		scrollpane.setContents(pane);

		createConnectionAnchors();
		setBackgroundColor(ColorConstants.listBackground);
		setOpaque(true);
		
	}

	protected void createConnectionAnchors() {
//		FixedConnectionAnchor inputConnectionAnchorA, inputConnectionAnchorB;
//		inputConnectionAnchorA = new FixedConnectionAnchor(this);
//		inputConnectionAnchorA.offsetH = 4;
//		inputConnectionAnchorB = new FixedConnectionAnchor(this);
//		inputConnectionAnchorB.offsetH = 10;
//		inputConnectionAnchors.addElement(inputConnectionAnchorA);
//		inputConnectionAnchors.addElement(inputConnectionAnchorB);
//		connectionAnchors.put(Gate.TERMINAL_A, inputConnectionAnchorA);
//		connectionAnchors.put(Gate.TERMINAL_B, inputConnectionAnchorB);
		
		//System.err.println("connections anchors are created");
		inputConnectionAnchors.clear();
		outputConnectionAnchors.clear();
		
		FixedConnectionAnchor in, out;
		
		
		//for (int i = 0; i < 8; i++) {
		out = new FixedConnectionAnchor(this);
		out.topDown = true;
		out.offsetV =out.offsetH=source.getSize().height/2;
		out.offsetH= out.offsetH=source.getSize().width;
		setOutputConnectionAnchor(0, out);
		outputConnectionAnchors.addElement(out);
				
			
		for (int i = 0; i < source.InputPins.length; i++) {
			in= new FixedConnectionAnchor(this);				
			in.topDown = true;
			in.offsetV = (25*i)+12;
			
			
			setInputConnectionAnchor(i, in);
				
			inputConnectionAnchors.addElement(in);
		}
		
	}

	

	protected FixedConnectionAnchor getInputConnectionAnchor(int i) {
		return (FixedConnectionAnchor) connectionAnchors
				.get(source.InputPins[i]);
	}

	/**
	 * @see org.eclipse.gef.handles.HandleBounds#getHandleBounds()
	 */
	public Rectangle getHandleBounds() {
		return getBounds().getCropped(new Insets(2, 0, 2, 0));
	}

	protected FixedConnectionAnchor getOutputConnectionAnchor() {
		return (FixedConnectionAnchor) connectionAnchors
				.get(source.outputPin);
	}

	public Dimension getPreferredSize(int w, int h) {
		Dimension prefSize = super.getPreferredSize(w, h);
		Dimension defaultSize = new Dimension(100, 100);
		prefSize.union(defaultSize);
		return prefSize;
	}

	protected void layoutConnectionAnchors() {
		int x;
//		for (int i = 0; i < 4; i++) {
//			x = (2 * i + 1) * getSize().width / 8;
//			getOutputConnectionAnchor(i + 4).setOffsetH(x - 1);
//			getInputConnectionAnchor(i).setOffsetH(x - 1);
//			getInputConnectionAnchor(i + 4).setOffsetH(x);
//			getOutputConnectionAnchor(i).setOffsetH(x);
//		}
	}

	/**
	 * @see org.eclipse.draw2d.Figure#paintFigure(Graphics)
	 */
	protected void paintFigure(Graphics graphics) {
		Rectangle rect = getBounds().getCopy();
		rect.crop(new Insets(2, 0, 2, 0));
		graphics.fillRectangle(rect);
		createConnectionAnchors();
	}

	public void setInputConnectionAnchor(int i, ConnectionAnchor c) {
		connectionAnchors.put(source.InputPins[i], c);
	}

	public void setOutputConnectionAnchor(int i, ConnectionAnchor c) {
		connectionAnchors.put(source.outputPin, c);
	}

	public String toString() {
		return "ResultConverterFigure"; //$NON-NLS-1$
	}

	public void validate() {
		if (isValid())
			return;
		layoutConnectionAnchors();
		super.validate();
	}

	protected boolean useLocalCoordinates() {
		return true;
	}

	
}
