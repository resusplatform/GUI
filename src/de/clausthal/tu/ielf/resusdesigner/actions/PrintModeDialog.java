/*******************************************************************************
 * Copyright (c) 2003, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package de.clausthal.tu.ielf.resusdesigner.actions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.draw2d.PrintFigureOperation;

import de.clausthal.tu.ielf.resusdesigner.ResusMessages;

/**
 * @author Eric Bordeau
 */
public class PrintModeDialog extends Dialog {

	private Button tile, fitPage, fitWidth, fitHeight;

	public PrintModeDialog(Shell shell) {
		super(shell);
	}

	protected void cancelPressed() {
		setReturnCode(-1);
		close();
	}

	protected void configureShell(Shell newShell) {
		newShell.setText(ResusMessages.PrintDialog_Title);
		super.configureShell(newShell);
	}

	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		tile = new Button(composite, SWT.RADIO);
		tile.setText(ResusMessages.PrintDialog_Tile);
		tile.setSelection(true);

		fitPage = new Button(composite, SWT.RADIO);
		fitPage.setText(ResusMessages.PrintDialog_Page);

		fitWidth = new Button(composite, SWT.RADIO);
		fitWidth.setText(ResusMessages.PrintDialog_Width);

		fitHeight = new Button(composite, SWT.RADIO);
		fitHeight.setText(ResusMessages.PrintDialog_Height);

		return composite;
	}

	protected void okPressed() {
		int returnCode = -1;
		if (tile.getSelection())
			returnCode = PrintFigureOperation.TILE;
		else if (fitPage.getSelection())
			returnCode = PrintFigureOperation.FIT_PAGE;
		else if (fitHeight.getSelection())
			returnCode = PrintFigureOperation.FIT_HEIGHT;
		else if (fitWidth.getSelection())
			returnCode = PrintFigureOperation.FIT_WIDTH;
		setReturnCode(returnCode);
		close();
	}
}
