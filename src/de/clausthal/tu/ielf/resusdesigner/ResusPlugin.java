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
package de.clausthal.tu.ielf.resusdesigner;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;

import org.eclipse.gef.palette.PaletteStack;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.gef.tools.MarqueeSelectionTool;




import de.clausthal.tu.ielf.resusdesigner.model.InputProvider;

import de.clausthal.tu.ielf.resusdesigner.model.ResultConverter;

import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;
import de.clausthal.tu.ielf.resusdesigner.tools.LogicCreationTool;

public class ResusPlugin extends org.eclipse.ui.plugin.AbstractUIPlugin {

	private static ResusPlugin singleton;

	public static Dimension getMaximumSizeFor(Class modelClass) {
		
		/*
		if (GroundOutput.class.isAssignableFrom(modelClass)) {
			return GroundFigure.SIZE;
		} else 
			if (LiveOutput.class.equals(modelClass)) {
			return LiveOutputFigure.SIZE;
			
		}
		*/
		//PlatformUI.getWorkbench().getPerspectiveRegistry().setDefaultPerspective("org.eclipse.gef.examples.logic"); 

		return IFigure.MAX_DIMENSION;
	}

	public void stop(BundleContext context) throws Exception {
		//plugin = null;
	            // Code to clean up here...
		System.out.println("Plugin exits");
		super.stop(context);
	}
	public static Dimension getMinimumSizeFor(Class modelClass) {
		if (ResusModel.class.equals(modelClass)) {
			return new Dimension(25, 20);		
		//} else if (LiveOutput.class.equals(modelClass)) {
		//	return LiveOutputFigure.SIZE;
		}
		return IFigure.MIN_DIMENSION;
	}

	static private List createCategories(PaletteRoot root) {
		List categories = new ArrayList();

		categories.add(createControlGroup(root));
		categories.add(createComponentsDrawer());
		//categories.add(createComplexPartsDrawer());
		// categories.add(createTemplateComponentsDrawer());
		// categories.add(createComplexTemplatePartsDrawer());

		return categories;
	}

	

	static private PaletteContainer createComponentsDrawer() {

		PaletteDrawer drawer = new PaletteDrawer(
				ResusMessages.LogicPlugin_Category_Components_Label,
				ImageDescriptor.createFromFile(ResusModel.class, "icons/comp.gif"));//$NON-NLS-1$

		List entries = new ArrayList();

		CombinedTemplateCreationEntry combined; 


		combined = new CombinedTemplateCreationEntry(
				ResusMessages.ResusPlugin_Tool_CreationTool_ResusModel_Label,
				ResusMessages.ResusPlugin_Tool_CreationTool_ResusModel_Description,
				new SimpleFactory(ResusModel.class), ImageDescriptor
						.createFromFile(ResusModel.class, "icons/resusModel16.gif"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(ResusModel.class,
						"icons/resusModel24.gif")//$NON-NLS-1$
		);
		combined.setToolClass(LogicCreationTool.class);
		entries.add(combined);

		
		combined = new CombinedTemplateCreationEntry(
				ResusMessages.LogicPlugin_Tool_CreationTool_ResusIOProvider_Label,
				ResusMessages.LogicPlugin_Tool_CreationTool_ResusIOProvider_Description,
				new SimpleFactory(InputProvider.class), ImageDescriptor
						.createFromFile(InputProvider.class, "icons/ResusIOProvider16.gif"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(InputProvider.class,
						"icons/ResusIOProvider24.gif")//$NON-NLS-1$
		);
		combined.setToolClass(LogicCreationTool.class);
		entries.add(combined);

		combined = new CombinedTemplateCreationEntry(
				ResusMessages.LogicPlugin_Tool_CreationTool_ResusResultConverter_Label,
				ResusMessages.LogicPlugin_Tool_CreationTool_ResusResultConverter_Description,
				new SimpleFactory(ResultConverter.class), ImageDescriptor
						.createFromFile(ResultConverter.class, "icons/ResultConverter16.gif"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(ResultConverter.class,
						"icons/ResultConverter24.gif")//$NON-NLS-1$
		);
		combined.setToolClass(LogicCreationTool.class);
		entries.add(combined);
		
		
//		entries.add(new PaletteSeparator());
//
//		combined = new CombinedTemplateCreationEntry(
//				ResusMessages.LogicPlugin_Tool_CreationTool_Label_Label,
//				ResusMessages.LogicPlugin_Tool_CreationTool_Label_Description,
//				new SimpleFactory(LogicLabel.class),
//				ImageDescriptor.createFromFile(ResusModel.class,
//						"icons/label16.gif"), //$NON-NLS-1$
//				ImageDescriptor.createFromFile(ResusModel.class,
//						"icons/label24.gif")//$NON-NLS-1$
//		);
//		
//		combined.setToolClass(LogicCreationTool.class);
//		entries.add(combined);


		drawer.addAll(entries);
		return drawer;
	}

	static private PaletteContainer createControlGroup(PaletteRoot root) {
		PaletteGroup controlGroup = new PaletteGroup(
				ResusMessages.LogicPlugin_Category_ControlGroup_Label);

		List entries = new ArrayList();

		ToolEntry tool = new PanningSelectionToolEntry();
		entries.add(tool);
		root.setDefaultEntry(tool);

		PaletteStack marqueeStack = new PaletteStack(
				ResusMessages.Marquee_Stack, "", null); //$NON-NLS-1$

		// NODES CONTAINED (default)
		marqueeStack.add(new MarqueeToolEntry());

		// NODES TOUCHED
		MarqueeToolEntry marquee = new MarqueeToolEntry();
		marquee.setToolProperty(MarqueeSelectionTool.PROPERTY_MARQUEE_BEHAVIOR,
				new Integer(MarqueeSelectionTool.BEHAVIOR_NODES_TOUCHED));
		marqueeStack.add(marquee);

		// NODES CONTAINED AND RELATED CONNECTIONS

		marquee = new MarqueeToolEntry();
		marquee.setToolProperty(
				MarqueeSelectionTool.PROPERTY_MARQUEE_BEHAVIOR,
				new Integer(
						MarqueeSelectionTool.BEHAVIOR_NODES_CONTAINED_AND_RELATED_CONNECTIONS));
		marqueeStack.add(marquee);

		// NODES TOUCHED AND RELATED CONNECTIONS
		marquee = new MarqueeToolEntry();
		marquee.setToolProperty(
				MarqueeSelectionTool.PROPERTY_MARQUEE_BEHAVIOR,
				new Integer(
						MarqueeSelectionTool.BEHAVIOR_NODES_TOUCHED_AND_RELATED_CONNECTIONS));
		marqueeStack.add(marquee);

		// CONNECTIONS CONTAINED
		marquee = new MarqueeToolEntry();
		marquee.setToolProperty(
				MarqueeSelectionTool.PROPERTY_MARQUEE_BEHAVIOR,
				new Integer(MarqueeSelectionTool.BEHAVIOR_CONNECTIONS_CONTAINED));
		marqueeStack.add(marquee);

		// CONNECTIONS TOUCHED
		marquee = new MarqueeToolEntry();
		marquee.setToolProperty(MarqueeSelectionTool.PROPERTY_MARQUEE_BEHAVIOR,
				new Integer(MarqueeSelectionTool.BEHAVIOR_CONNECTIONS_TOUCHED));
		marqueeStack.add(marquee);

		marqueeStack
				.setUserModificationPermission(PaletteEntry.PERMISSION_NO_MODIFICATION);
		//entries.add(marqueeStack);

		tool = new ConnectionCreationToolEntry(
				ResusMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Label,
				ResusMessages.LogicPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
				null, ImageDescriptor.createFromFile(ResusModel.class,
						"icons/connection16.gif"),//$NON-NLS-1$
				ImageDescriptor.createFromFile(ResusModel.class,
						"icons/connection24.gif")//$NON-NLS-1$
		);
		entries.add(tool);
		controlGroup.addAll(entries);
		return controlGroup;
	}

	static PaletteRoot createPalette() {
		PaletteRoot logicPalette = new PaletteRoot();
		logicPalette.addAll(createCategories(logicPalette));
		return logicPalette;
	}

	public static ResusPlugin getDefault() {
		return singleton;
	}

	public ResusPlugin() {
		if (singleton == null) {
			singleton = this;
		}
	}

}
