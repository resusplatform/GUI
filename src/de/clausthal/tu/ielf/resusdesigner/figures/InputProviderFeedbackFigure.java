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

import org.eclipse.draw2d.RectangleFigure;

import de.clausthal.tu.ielf.resusdesigner.model.InputProvider;

public class InputProviderFeedbackFigure extends RectangleFigure {

	public InputProviderFeedbackFigure(InputProvider src) {
		this.setFill(false);
		this.setXOR(true);
		setBorder(new InputProviderFeedbackBorder(src));
	}

	protected boolean useLocalCoordinates() {
		return true;
	}
}
