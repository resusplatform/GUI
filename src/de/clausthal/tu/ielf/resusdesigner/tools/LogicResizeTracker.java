package de.clausthal.tu.ielf.resusdesigner.tools;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.ResizeTracker;

import de.clausthal.tu.ielf.resusdesigner.ResusPlugin;

public final class LogicResizeTracker extends ResizeTracker {

	public LogicResizeTracker(GraphicalEditPart owner, int direction) {
		super(owner, direction);
	}

	protected Dimension getMaximumSizeFor(ChangeBoundsRequest request) {
		return ResusPlugin.getMaximumSizeFor(getOwner().getModel().getClass());
	}

	protected Dimension getMinimumSizeFor(ChangeBoundsRequest request) {
		return ResusPlugin.getMinimumSizeFor(getOwner().getModel().getClass());
	}
}
