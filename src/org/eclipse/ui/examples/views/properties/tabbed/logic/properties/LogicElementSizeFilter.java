/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.examples.views.properties.tabbed.logic.properties;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

import de.clausthal.tu.ielf.resusdesigner.model.ResusElement;
import de.clausthal.tu.ielf.resusdesigner.model.ResusSubpart;

/**
 * Filter for the size section, do not display when the size cannot be modified.
 * 
 * @author Anthony Hunter
 */
public class LogicElementSizeFilter
	implements IFilter {

	/**
	 * @inheritDoc
	 */
	public boolean select(Object object) {
		if (object instanceof EditPart) {
			ResusElement element = (ResusElement) ((EditPart) object)
				.getModel();
			if (element instanceof ResusSubpart) {
				Dimension dimension = ((ResusSubpart) element).getSize();
				if (dimension.width != -1 && dimension.height != -1) {
					return true;
				}
			}
		}
		return false;
	}
}