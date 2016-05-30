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
import org.eclipse.draw2d.IScrollableFigure;
import org.eclipse.draw2d.PuristicScrollPane;
import org.eclipse.draw2d.ScrollPane;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.handles.HandleBounds;

import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;



public class ResusModelFigure extends NodeFigure implements HandleBounds {

	private ScrollPane scrollpane;
	private ResusModel source;
	public ResusModelFigure(ResusModel src) {
		source=src;
		setBorder(new ResusModelBorder(src));
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
		inputConnectionAnchors.clear();
		outputConnectionAnchors.clear();
		
		FixedConnectionAnchor in, out;
		
		
		
		//output pins
		for (int i = 0; i < source.outputPins.length; i++) {
			out = new FixedConnectionAnchor(this);
			out.topDown = true;
			out.offsetV =(25*i)+12;
			out.offsetH= out.offsetH=source.getSize().width;
			setOutputConnectionAnchor(i, out);
			outputConnectionAnchors.addElement(out);
		}
		
		
		
				
		//inputpins pins
		for (int i = 0; i < source.inputPins.length; i++) {
			in= new FixedConnectionAnchor(this);				
			in.topDown = true;
			in.offsetV = (25*i)+12;
			
			
			setInputConnectionAnchor(i, in);
				
			inputConnectionAnchors.addElement(in);
		}
		
	}

	

	protected FixedConnectionAnchor getInputConnectionAnchor(int i) {
		return (FixedConnectionAnchor) connectionAnchors
				.get(source.inputPins[i]);
	}

	/**
	 * @see org.eclipse.gef.handles.HandleBounds#getHandleBounds()
	 */
	public Rectangle getHandleBounds() {
		return getBounds().getCropped(new Insets(2, 0, 2, 0));
	}

	protected FixedConnectionAnchor getOutputConnectionAnchor(int i) {
		return (FixedConnectionAnchor) connectionAnchors
				.get(source.outputPins[i]);
	}

	public Dimension getPreferredSize(int w, int h) {
		Dimension prefSize = super.getPreferredSize(w, h);
		Dimension defaultSize = new Dimension(100, 100);
		prefSize.union(defaultSize);
		return prefSize;
	}

	protected void layoutConnectionAnchors() {
		
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
		connectionAnchors.put(source.inputPins[i], c);
	}

	public void setOutputConnectionAnchor(int i, ConnectionAnchor c) {
		connectionAnchors.put(source.outputPins[i], c);
	}

	public String toString() {
		return "ResusModelBoardFigure"; //$NON-NLS-1$
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
