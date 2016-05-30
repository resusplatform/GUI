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

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.RoutingAnimator;

import de.clausthal.tu.ielf.resusdesigner.model.Connector;
import de.clausthal.tu.ielf.resusdesigner.model.InputProvider;
import de.clausthal.tu.ielf.resusdesigner.model.ResultConverter;
import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;

public class FigureFactory {

	public static PolylineConnection createNewBendableWire(Connector wire) {
		PolylineConnection conn = new PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());
		// conn.setSourceDecoration(new PolygonDecoration());
		// conn.setTargetDecoration(new PolylineDecoration());
		return conn;
	}

	public static PolylineConnection createNewWire(Connector wire) {

		PolylineConnection conn = new PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());
		PolygonDecoration arrow;

		if (wire == null )
			arrow = null;
		else {
			arrow = new PolygonDecoration();
			arrow.setTemplate(PolygonDecoration.INVERTED_TRIANGLE_TIP);
			arrow.setScale(5, 2.5);
		}
		conn.setSourceDecoration(arrow);

		if (wire == null )
			arrow = null;
		else {
			arrow = new PolygonDecoration();
			arrow.setTemplate(PolygonDecoration.INVERTED_TRIANGLE_TIP);
			arrow.setScale(5, 2.5);
		}
		conn.setTargetDecoration(arrow);
		return conn;
	}

	

	public static IFigure createNewResusModel(ResusModel src) {
		ResusModelFigure f = new ResusModelFigure(src);
		return f;
	}
	public static IFigure createNewInputProvider(InputProvider src){
		InputProviderFigure f = new InputProviderFigure(src);
		return f;
	}
	public static IFigure createNewResultConverter(ResultConverter src){
		ResultConverterFigure f = new ResultConverterFigure(src);
		return f;
	}

}
