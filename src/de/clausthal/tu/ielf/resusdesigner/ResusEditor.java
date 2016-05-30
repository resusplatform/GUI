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


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.swt.SWT;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener;

import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.ViewportAwareConnectionLayerClippingStrategy;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.parts.ScrollableThumbnail;
import org.eclipse.draw2d.parts.Thumbnail;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.rulers.RulerProvider;
import org.eclipse.gef.ui.actions.ActionRegistry;

import org.eclipse.gef.ui.actions.CopyTemplateAction;

import org.eclipse.gef.ui.actions.GEFActionConstants;

import org.eclipse.gef.ui.actions.ToggleGridAction;
import org.eclipse.gef.ui.actions.ToggleRulerVisibilityAction;
import org.eclipse.gef.ui.actions.ToggleSnapToGeometryAction;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.gef.ui.rulers.RulerComposite;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.clausthal.tu.ielf.resusdesigner.edit.GraphicalPartFactory;
import de.clausthal.tu.ielf.resusdesigner.edit.TreePartFactory;
import de.clausthal.tu.ielf.resusdesigner.model.Connector;
import de.clausthal.tu.ielf.resusdesigner.model.InputProvider;
import de.clausthal.tu.ielf.resusdesigner.model.IndexPairs;
import de.clausthal.tu.ielf.resusdesigner.model.LogicRuler;
import de.clausthal.tu.ielf.resusdesigner.model.OutputPair;
import de.clausthal.tu.ielf.resusdesigner.model.ResultConverter;
import de.clausthal.tu.ielf.resusdesigner.model.ResusDiagram;
import de.clausthal.tu.ielf.resusdesigner.model.ResusElement;
import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;
import de.clausthal.tu.ielf.resusdesigner.model.ResusModelLogFile;
import de.clausthal.tu.ielf.resusdesigner.model.ResusSubpart;
import de.clausthal.tu.ielf.resusdesigner.palette.LogicPaletteCustomizer;
import de.clausthal.tu.ielf.resusdesigner.rulers.ResusRulerProvider;

public class ResusEditor extends GraphicalEditorWithFlyoutPalette  {

	
	class OutlinePage extends ContentOutlinePage implements IAdaptable {

		private PageBook pageBook;
		private Control outline;
		private Canvas overview;
		private IAction showOutlineAction, showOverviewAction;
		static final int ID_OUTLINE = 0;
		static final int ID_OVERVIEW = 1;
		private Thumbnail thumbnail;
		private DisposeListener disposeListener;

		public OutlinePage(EditPartViewer viewer) {
			super(viewer);
		}

		public void init(IPageSite pageSite) {
			super.init(pageSite);
			ActionRegistry registry = getActionRegistry();
			IActionBars bars = pageSite.getActionBars();
			String id = ActionFactory.UNDO.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
			id = ActionFactory.REDO.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
			id = ActionFactory.DELETE.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
			
			bars.updateActionBars();
			
			
		}

		protected void configureOutlineViewer() {
			getViewer().setEditDomain(getEditDomain());
			getViewer().setEditPartFactory(new TreePartFactory());
			ContextMenuProvider provider = new ResusContextMenuProvider(
					getViewer(), getActionRegistry());
			getViewer().setContextMenu(provider);
			getSite().registerContextMenu(
					"de.clausthal.tu.ielf.resus.outline.contextmenu", //$NON-NLS-1$ //org.eclipse.gef.examples.logic.outline.contextmenu
					provider, getSite().getSelectionProvider());
			getViewer().setKeyHandler(getCommonKeyHandler());
			getViewer()
					.addDropTargetListener(
							(TransferDropTargetListener) new TemplateTransferDropTargetListener(
									getViewer()));
			IToolBarManager tbm = getSite().getActionBars().getToolBarManager();
			showOutlineAction = new Action() {
				public void run() {
					showPage(ID_OUTLINE);
				}
			};
			showOutlineAction.setImageDescriptor(ImageDescriptor
					.createFromFile(ResusPlugin.class, "icons/outline.gif")); //$NON-NLS-1$
			showOutlineAction
					.setToolTipText(ResusMessages.LogicEditor_outline_show_outline);
			tbm.add(showOutlineAction);
			showOverviewAction = new Action() {
				public void run() {
					showPage(ID_OVERVIEW);
				}
			};
			showOverviewAction.setImageDescriptor(ImageDescriptor
					.createFromFile(ResusPlugin.class, "icons/overview.gif")); //$NON-NLS-1$
			showOverviewAction
					.setToolTipText(ResusMessages.LogicEditor_outline_show_overview);
			tbm.add(showOverviewAction);
			showPage(ID_OUTLINE);
			this.addSelectionChangedListener(new ISelectionChangedListener() {
				
				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					System.out.println("selection changed");
					
				}
			});
		}

		public void createControl(Composite parent) {
			pageBook = new PageBook(parent, SWT.NONE);
			outline = getViewer().createControl(pageBook);
			overview = new Canvas(pageBook, SWT.NONE);
			pageBook.showPage(outline);
			configureOutlineViewer();
			hookOutlineViewer();
			initializeOutlineViewer();
		}

		public void dispose() {
			unhookOutlineViewer();
			if (thumbnail != null) {
				thumbnail.deactivate();
				thumbnail = null;
			}
			super.dispose();
			ResusEditor.this.outlinePage = null;
			outlinePage = null;
		}

		public Object getAdapter(Class type) {
			if (type == ZoomManager.class)
				return getGraphicalViewer().getProperty(
						ZoomManager.class.toString());
			return null;
		}

		public Control getControl() {
			return pageBook;
		}

		protected void hookOutlineViewer() {
			getSelectionSynchronizer().addViewer(getViewer());
		}

		protected void initializeOutlineViewer() {
			setContents(getResusDiagram());
		}

		protected void initializeOverview() {
			LightweightSystem lws = new LightweightSystem(overview);
			RootEditPart rep = getGraphicalViewer().getRootEditPart();
			if (rep instanceof ScalableFreeformRootEditPart) {
				ScalableFreeformRootEditPart root = (ScalableFreeformRootEditPart) rep;
				thumbnail = new ScrollableThumbnail((Viewport) root.getFigure());
				thumbnail.setBorder(new MarginBorder(3));
				thumbnail.setSource(root
						.getLayer(LayerConstants.PRINTABLE_LAYERS));
				lws.setContents(thumbnail);
				disposeListener = new DisposeListener() {
					public void widgetDisposed(DisposeEvent e) {
						if (thumbnail != null) {
							thumbnail.deactivate();
							thumbnail = null;
						}
					}
				};
				getEditor().addDisposeListener(disposeListener);
			}
		}

		public void setContents(Object contents) {
			getViewer().setContents(contents);
		}

		protected void showPage(int id) {
			if (id == ID_OUTLINE) {
				showOutlineAction.setChecked(true);
				showOverviewAction.setChecked(false);
				pageBook.showPage(outline);
				if (thumbnail != null)
					thumbnail.setVisible(false);
			} else if (id == ID_OVERVIEW) {
				if (thumbnail == null)
					initializeOverview();
				showOutlineAction.setChecked(false);
				showOverviewAction.setChecked(true);
				pageBook.showPage(overview);
				thumbnail.setVisible(true);
			}
		}

		protected void unhookOutlineViewer() {
			getSelectionSynchronizer().removeViewer(getViewer());
			if (disposeListener != null && getEditor() != null
					&& !getEditor().isDisposed())
				getEditor().removeDisposeListener(disposeListener);
		}
	}

	private KeyHandler sharedKeyHandler;
	private PaletteRoot root;
	private OutlinePage outlinePage;
	private boolean editorSaving = false;

	// This class listens to changes to the file system in the workspace, and
	// makes changes accordingly.
	// 1) An open, saved file gets deleted -> close the editor
	// 2) An open file gets renamed or moved -> change the editor's input
	// accordingly
	class ResourceTracker implements IResourceChangeListener,
			IResourceDeltaVisitor {
		public void resourceChanged(IResourceChangeEvent event) {
			IResourceDelta delta = event.getDelta();
			try {
				if (delta != null)
					delta.accept(this);
			} catch (CoreException exception) {
				// What should be done here?
			}
		}

		public boolean visit(IResourceDelta delta) {
			if (delta == null
					|| !delta.getResource().equals(
							((IFileEditorInput) getEditorInput()).getFile()))
				return true;

			if (delta.getKind() == IResourceDelta.REMOVED) {
				Display display = getSite().getShell().getDisplay();
				if ((IResourceDelta.MOVED_TO & delta.getFlags()) == 0) { // if
																			// the
																			// file
																			// was
																			// deleted
					// NOTE: The case where an open, unsaved file is deleted is
					// being handled by the
					// PartListener added to the Workbench in the initialize()
					// method.
					display.asyncExec(new Runnable() {
						public void run() {
							if (!isDirty())
								closeEditor(false);
						}
					});
				} else { // else if it was moved or renamed
					final IFile newFile = ResourcesPlugin.getWorkspace()
							.getRoot().getFile(delta.getMovedToPath());
					display.asyncExec(new Runnable() {
						public void run() {
							superSetInput(new FileEditorInput(newFile));
						}
					});
				}
			} else if (delta.getKind() == IResourceDelta.CHANGED) {
				if (!editorSaving) {
					// the file was overwritten somehow (could have been
					// replaced by another
					// version in the respository)
					final IFile newFile = ResourcesPlugin.getWorkspace()
							.getRoot().getFile(delta.getFullPath());
					Display display = getSite().getShell().getDisplay();
					display.asyncExec(new Runnable() {
						public void run() {
							setInput(new FileEditorInput(newFile));
							getCommandStack().flush();
						}
					});
				}
			}
			return false;
		}
	}

	private IPartListener partListener = new IPartListener() {
		// If an open, unsaved file was deleted, query the user to either do a
		// "Save As"
		// or close the editor.
		public void partActivated(IWorkbenchPart part) {
			if (part != ResusEditor.this)
				return;
			if (!((IFileEditorInput) getEditorInput()).getFile().exists()) {
				Shell shell = getSite().getShell();
				String title = ResusMessages.GraphicalEditor_FILE_DELETED_TITLE_UI;
				String message = ResusMessages.GraphicalEditor_FILE_DELETED_WITHOUT_SAVE_INFO;
				String[] buttons = {
						ResusMessages.GraphicalEditor_SAVE_BUTTON_UI,
						ResusMessages.GraphicalEditor_CLOSE_BUTTON_UI };
				MessageDialog dialog = new MessageDialog(shell, title, null,
						message, MessageDialog.QUESTION, buttons, 0);
				if (dialog.open() == 0) {
					if (!performSaveAs())
						partActivated(part);
				} else {
					closeEditor(false);
				}
			}
		}

		public void partBroughtToTop(IWorkbenchPart part) {
		}

		public void partClosed(IWorkbenchPart part) {
		}

		public void partDeactivated(IWorkbenchPart part) {
		}

		public void partOpened(IWorkbenchPart part) {
		}
	};

	private ResusDiagram resusDiagram = new ResusDiagram();
	private ResourceTracker resourceListener = new ResourceTracker();
	private RulerComposite rulerComp;

	protected static final String PALETTE_DOCK_LOCATION = "Dock location"; //$NON-NLS-1$
	protected static final String PALETTE_SIZE = "Palette Size"; //$NON-NLS-1$
	protected static final String PALETTE_STATE = "Palette state"; //$NON-NLS-1$
	protected static final int DEFAULT_PALETTE_SIZE = 130;

	static {
		ResusPlugin.getDefault().getPreferenceStore()
				.setDefault(PALETTE_SIZE, DEFAULT_PALETTE_SIZE);
	}
	ResusSettings settings;
	
	public ResusEditor() {
		setEditDomain(new DefaultEditDomain(this));
		settings=new ResusSettings();
		if(ResusSettings.chekReSUS_HOME_core()==null){
			System.err.println("resusCore.exe not found. starting the simulation is impossible");			
		}
		}

	public ResusSettings getSettings(){
		return settings;
	}
	protected void closeEditor(boolean save) {
		getSite().getPage().closeEditor(ResusEditor.this, save);
	}

	public void commandStackChanged(EventObject event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(event);
	}
	
	public boolean isDirty() {
		return getCommandStack().isDirty();
	}

	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		ScrollingGraphicalViewer viewer = (ScrollingGraphicalViewer) getGraphicalViewer();

		ScalableFreeformRootEditPart root = new ScalableFreeformRootEditPart();

		// set clipping strategy for connection layer
		ConnectionLayer connectionLayer = (ConnectionLayer) root
				.getLayer(LayerConstants.CONNECTION_LAYER);
		connectionLayer
				.setClippingStrategy(new ViewportAwareConnectionLayerClippingStrategy(
						connectionLayer));

		List zoomLevels = new ArrayList(3);
		zoomLevels.add(ZoomManager.FIT_ALL);
		zoomLevels.add(ZoomManager.FIT_WIDTH);
		zoomLevels.add(ZoomManager.FIT_HEIGHT);
		root.getZoomManager().setZoomLevelContributions(zoomLevels);

		IAction zoomIn = new ZoomInAction(root.getZoomManager());
		IAction zoomOut = new ZoomOutAction(root.getZoomManager());
		getActionRegistry().registerAction(zoomIn);
		getActionRegistry().registerAction(zoomOut);
		getSite().getKeyBindingService().registerAction(zoomIn);
		getSite().getKeyBindingService().registerAction(zoomOut);

		viewer.setRootEditPart(root);

		viewer.setEditPartFactory(new GraphicalPartFactory());
		ContextMenuProvider provider = new ResusContextMenuProvider(viewer,
				getActionRegistry());
		viewer.setContextMenu(provider);
		getSite().registerContextMenu(
				"org.eclipse.gef.examples.logic.editor.contextmenu", //$NON-NLS-1$
				provider, viewer);
		viewer.setKeyHandler(new GraphicalViewerKeyHandler(viewer)
				.setParent(getCommonKeyHandler()));

		loadProperties();

		// Actions
		IAction showRulers = new ToggleRulerVisibilityAction(
				getGraphicalViewer());
		getActionRegistry().registerAction(showRulers);

		IAction snapAction = new ToggleSnapToGeometryAction(
				getGraphicalViewer());
		getActionRegistry().registerAction(snapAction);

		IAction showGrid = new ToggleGridAction(getGraphicalViewer());
		getActionRegistry().registerAction(showGrid);

		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				handleActivationChanged(event);
			}
		};
		getGraphicalControl().addListener(SWT.Activate, listener);
		getGraphicalControl().addListener(SWT.Deactivate, listener);
	}

	protected void writeToOutputStream(OutputStream os) throws IOException {
		try {
//			ObjectOutputStream out = new ObjectOutputStream(os);
//			out.writeObject(getResusDiagram());
//			out.close();

			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder;

			docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rt = getXMLRoot(doc);
			if (doc == null) {
				System.err.println("err by doc");
				return;
			}
			if (rt == null) {
				System.err.println("err by rt");
				return;
			}

			// doc.appendChild( rt);

			DOMSource source = new DOMSource(rt);
			
			StreamResult result = new StreamResult(os);

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer;

			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");


			transformer.transform(source, result);

			System.out.println("File saved!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
 
		

		
		
		
		
	}
	
	
	protected Element getXMLRoot(Document doc){
		
		try {
			Element rootElement = doc.createElement("simulation");
			
			
			
			ArrayList<ResusElement> childs = (ArrayList<ResusElement>) getResusDiagram().getChildren();
			for (int i = 0; i < childs.size(); i++) {
				rootElement.appendChild(childs.get(i).getXML(doc));
				childs.get(i).getConnections(doc, rootElement);
			}

			doc.appendChild(rootElement);
			return rootElement;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	protected CustomPalettePage createPalettePage() {
		return new CustomPalettePage(getPaletteViewerProvider()) {
			public void init(IPageSite pageSite) {
				super.init(pageSite);
				IAction copy = getActionRegistry().getAction(
						ActionFactory.COPY.getId());
				pageSite.getActionBars().setGlobalActionHandler(
						ActionFactory.COPY.getId(), copy);
			}
		};
	}

	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new PaletteViewerProvider(getEditDomain()) {
			private IMenuListener menuListener;

			protected void configurePaletteViewer(PaletteViewer viewer) {
				super.configurePaletteViewer(viewer);
				viewer.setCustomizer(new LogicPaletteCustomizer());
				viewer.addDragSourceListener(new TemplateTransferDragSourceListener(
						viewer));
			}

			protected void hookPaletteViewer(PaletteViewer viewer) {
				super.hookPaletteViewer(viewer);
				final CopyTemplateAction copy = (CopyTemplateAction) getActionRegistry()
						.getAction(ActionFactory.COPY.getId());
				viewer.addSelectionChangedListener(copy);
				if (menuListener == null)
					menuListener = new IMenuListener() {
						public void menuAboutToShow(IMenuManager manager) {
							manager.appendToGroup(
									GEFActionConstants.GROUP_COPY, copy);
						}
					};
				viewer.getContextMenu().addMenuListener(menuListener);
			}
		};
	}

	public void dispose() {
		getSite().getWorkbenchWindow().getPartService()
				.removePartListener(partListener);
		partListener = null;
		((IFileEditorInput) getEditorInput()).getFile().getWorkspace()
				.removeResourceChangeListener(resourceListener);
		super.dispose();
	}

	public void doSave(final IProgressMonitor progressMonitor) {
		editorSaving = true;
		Platform.run(new SafeRunnable() {
			public void run() throws Exception {
				saveProperties();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				writeToOutputStream(out);
				IFile file = ((IFileEditorInput) getEditorInput()).getFile();
				file.setContents(new ByteArrayInputStream(out.toByteArray()),
						true, false, progressMonitor);
				getCommandStack().markSaveLocation();
			}
		});
		editorSaving = false;
	}

	public void doSaveAs() {
		performSaveAs();
	}

	public Object getAdapter(Class type) {
		if (type == IContentOutlinePage.class) {
			outlinePage = new OutlinePage(new TreeViewer());
			return outlinePage;
		}
		if (type == ZoomManager.class)
			return getGraphicalViewer().getProperty(
					ZoomManager.class.toString());

		return super.getAdapter(type);
	}

	protected Control getGraphicalControl() {
		return rulerComp;
	}

	/**
	 * Returns the KeyHandler with common bindings for both the Outline and
	 * Graphical Views. For example, delete is a common action.
	 */
	protected KeyHandler getCommonKeyHandler() {
		if (sharedKeyHandler == null) {
			sharedKeyHandler = new KeyHandler();
			sharedKeyHandler.put(
					KeyStroke.getPressed(SWT.F2, 0),
					getActionRegistry().getAction(
							GEFActionConstants.DIRECT_EDIT));
		}
		return sharedKeyHandler;
	}

	protected ResusDiagram getResusDiagram() {
		return resusDiagram;
	}
	
	public ArrayList<ResultConverter> getResultConverters(){
		
		ArrayList<ResusDiagram> childs=(ArrayList<ResusDiagram>) resusDiagram.getChildren();
		ArrayList<ResultConverter> rca=new ArrayList<ResultConverter>();
		
		for (ResusDiagram ld : childs) {
			if(ld instanceof ResultConverter)
				rca.add((ResultConverter)ld);
		}
		
		
		return rca;
	}
	

	
	public ResultConverter getResultConverterById(String id){
		
		ArrayList<ResusDiagram> childs=(ArrayList<ResusDiagram>) resusDiagram.getChildren();
		
		
		for (ResusDiagram ld : childs) {
			if(ld instanceof ResultConverter)
				if(((ResultConverter)ld).getId().equals(id))
					return ((ResultConverter)ld);
		}
		
		
		return null;
	}

	protected PaletteRoot getPaletteRoot() {
		if (root == null) {
			root = ResusPlugin.createPalette();
		}
		return root;
	}

	public void gotoMarker(IMarker marker) {
	}

	protected void handleActivationChanged(Event event) {
		IAction copy = null;
		if (event.type == SWT.Deactivate)
			copy = getActionRegistry().getAction(ActionFactory.COPY.getId());
		if (getEditorSite().getActionBars().getGlobalActionHandler(
				ActionFactory.COPY.getId()) != copy) {
			getEditorSite().getActionBars().setGlobalActionHandler(
					ActionFactory.COPY.getId(), copy);
			getEditorSite().getActionBars().updateActionBars();
		}
	}

	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		getGraphicalViewer().setContents(getResusDiagram());

		getGraphicalViewer()
				.addDropTargetListener(
						(TransferDropTargetListener) new TemplateTransferDropTargetListener(
								getGraphicalViewer()));
		
//		getGraphicalViewer().addSelectionChangedListener(new ISelectionChangedListener() {
//			
//			@Override
//			public void selectionChanged(SelectionChangedEvent event) {
//				try {
//					
//					//System.out.println("proppppppppertys");
//				} catch (PartInitException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//			}
//		});
	}
//
//	protected void createActions() {
//		super.createActions();
//		ActionRegistry registry = getActionRegistry();
//		IAction action;
//
//		action = new CopyTemplateAction(this);
//		registry.registerAction(action);
//
//		action = new MatchSizeAction(this);
//		registry.registerAction(action);
//		getSelectionActions().add(action.getId());
//
//		action = new MatchWidthAction(this);
//		registry.registerAction(action);
//		getSelectionActions().add(action.getId());
//
//		action = new MatchHeightAction(this);
//		registry.registerAction(action);
//		getSelectionActions().add(action.getId());
//
//		action = new PasteTemplateAction(this);
//		registry.registerAction(action);
//		getSelectionActions().add(action.getId());
//
//		action = new IncrementDecrementAction(this, true);
//		registry.registerAction(action);
//		getSelectionActions().add(action.getId());
//
//		action = new IncrementDecrementAction(this, false);
//		registry.registerAction(action);
//		getSelectionActions().add(action.getId());
//
//		action = new DirectEditAction((IWorkbenchPart) this);
//		registry.registerAction(action);
//		getSelectionActions().add(action.getId());
//
//		action = new AlignmentAction((IWorkbenchPart) this,
//				PositionConstants.LEFT);
//		registry.registerAction(action);
//		getSelectionActions().add(action.getId());
//
//		action = new AlignmentAction((IWorkbenchPart) this,
//				PositionConstants.RIGHT);
//		registry.registerAction(action);
//		getSelectionActions().add(action.getId());
//
//		action = new AlignmentAction((IWorkbenchPart) this,
//				PositionConstants.TOP);
//		registry.registerAction(action);
//		getSelectionActions().add(action.getId());
//
//		action = new AlignmentAction((IWorkbenchPart) this,
//				PositionConstants.BOTTOM);
//		registry.registerAction(action);
//		getSelectionActions().add(action.getId());
//
//		action = new AlignmentAction((IWorkbenchPart) this,
//				PositionConstants.CENTER);
//		registry.registerAction(action);
//		getSelectionActions().add(action.getId());
//
//		action = new AlignmentAction((IWorkbenchPart) this,
//				PositionConstants.MIDDLE);
//		registry.registerAction(action);
//		getSelectionActions().add(action.getId());
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.ui.parts.GraphicalEditor#createGraphicalViewer(org.eclipse
	 * .swt.widgets.Composite)
	 */
	protected void createGraphicalViewer(Composite parent) {
		rulerComp = new RulerComposite(parent, SWT.NONE);
		super.createGraphicalViewer(rulerComp);
		rulerComp
				.setGraphicalViewer((ScrollingGraphicalViewer) getGraphicalViewer());
	}

	protected FigureCanvas getEditor() {
		return (FigureCanvas) getGraphicalViewer().getControl();
	}

	public boolean isSaveAsAllowed() {
		return true;
	}

	protected void loadProperties() {
		// Ruler properties
		LogicRuler ruler = getResusDiagram().getRuler(PositionConstants.WEST);
		RulerProvider provider = null;
		if (ruler != null) {
			provider = new ResusRulerProvider(ruler);
		}
		getGraphicalViewer().setProperty(RulerProvider.PROPERTY_VERTICAL_RULER,
				provider);
		ruler = getResusDiagram().getRuler(PositionConstants.NORTH);
		provider = null;
		if (ruler != null) {
			provider = new ResusRulerProvider(ruler);
		}
		getGraphicalViewer().setProperty(
				RulerProvider.PROPERTY_HORIZONTAL_RULER, provider);
		getGraphicalViewer().setProperty(
				RulerProvider.PROPERTY_RULER_VISIBILITY,
				new Boolean(getResusDiagram().getRulerVisibility()));

		// Snap to Geometry property
		getGraphicalViewer().setProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED,
				new Boolean(getResusDiagram().isSnapToGeometryEnabled()));

		// Grid properties
		getGraphicalViewer().setProperty(SnapToGrid.PROPERTY_GRID_ENABLED,
				new Boolean(getResusDiagram().isGridEnabled()));
		// We keep grid visibility and enablement in sync
		getGraphicalViewer().setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE,
				new Boolean(getResusDiagram().isGridEnabled()));

		// Zoom
		ZoomManager manager = (ZoomManager) getGraphicalViewer().getProperty(
				ZoomManager.class.toString());
		if (manager != null)
			manager.setZoom(getResusDiagram().getZoom());
		// Scroll-wheel Zoom
		getGraphicalViewer().setProperty(
				MouseWheelHandler.KeyGenerator.getKey(SWT.MOD1),
				MouseWheelZoomHandler.SINGLETON);

	}

	protected boolean performSaveAs() {
		SaveAsDialog dialog = new SaveAsDialog(getSite().getWorkbenchWindow()
				.getShell());
		dialog.setOriginalFile(((IFileEditorInput) getEditorInput()).getFile());
		dialog.open();
		IPath path = dialog.getResult();

		if (path == null)
			return false;

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IFile file = workspace.getRoot().getFile(path);

		if (!file.exists()) {
			WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
				public void execute(final IProgressMonitor monitor) {
					saveProperties();
					try {
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						writeToOutputStream(out);
						file.create(
								new ByteArrayInputStream(out.toByteArray()),
								true, monitor);
						out.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			try {
				new ProgressMonitorDialog(getSite().getWorkbenchWindow()
						.getShell()).run(false, true, op);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			superSetInput(new FileEditorInput(file));
			getCommandStack().markSaveLocation();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	protected void saveProperties() {
		getResusDiagram().setRulerVisibility(
				((Boolean) getGraphicalViewer().getProperty(
						RulerProvider.PROPERTY_RULER_VISIBILITY))
						.booleanValue());
		getResusDiagram().setGridEnabled(
				((Boolean) getGraphicalViewer().getProperty(
						SnapToGrid.PROPERTY_GRID_ENABLED)).booleanValue());
		getResusDiagram().setSnapToGeometry(
				((Boolean) getGraphicalViewer().getProperty(
						SnapToGeometry.PROPERTY_SNAP_ENABLED)).booleanValue());
		ZoomManager manager = (ZoomManager) getGraphicalViewer().getProperty(
				ZoomManager.class.toString());
		if (manager != null)
			getResusDiagram().setZoom(manager.getZoom());
	}

	protected void setInput(IEditorInput input) {
		superSetInput(input);

		IFile file = ((IFileEditorInput) input).getFile();
		try {
			InputStream is = file.getContents(false);
			//read input intomemory
			//ObjectInputStream ois = new ObjectInputStream(is);
							 
				DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();			 
				Document doc = dBuilder.parse(is);			 
				
				//parsing the content of XML file
				NodeList threadNodeList=doc.getElementsByTagName("threads");
				NodeList modelNodeList=doc.getElementsByTagName("model");
				NodeList IOProviderNodeList=doc.getElementsByTagName("IOProvider");
				NodeList connectionNodeList=doc.getElementsByTagName("connection");
				NodeList ResultConverterNodeList=doc.getElementsByTagName("resultConverter");
				
				
				// parsing number of threads
				int th=1;
				if(threadNodeList.getLength()>0){
					Element eElement = (Element) threadNodeList.item(0);
					String s=eElement.getTextContent();
					th=Integer.parseInt(s);
				}
				 
				
				
				//parsing model nodes
				for (int count = 0; count < modelNodeList.getLength(); count++) {					 
						Node tempNode = modelNodeList.item(count);
						ResusModel model=new ResusModel();				
					 
						// make sure it's element node.
						if (tempNode.getNodeType() == Node.ELEMENT_NODE) {				
							
							Element eElement = (Element) tempNode;
							model.setId(eElement.getElementsByTagName("id").item(0).getTextContent());
							model.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
							model.setModelType(eElement.getElementsByTagName("modelType").item(0).getTextContent());
							model.setDescription(eElement.getElementsByTagName("description").item(0).getTextContent());
							model.setExecutor(eElement.getElementsByTagName("executor").item(0).getTextContent());
							model.setWorkingDirectory(eElement.getElementsByTagName("workingDirectory").item(0).getTextContent());
							model.setNumberOfInputs(Integer.parseInt(eElement.getElementsByTagName("numberOfInputPins").item(0).getTextContent()));
							model.setNumberOfOutputs(Integer.parseInt(eElement.getElementsByTagName("numberOfOutputPins").item(0).getTextContent()));
							
							String execParam=null;							
							if(eElement.getElementsByTagName("executionParameters").item(0).getTextContent()!=null)
								execParam=eElement.getElementsByTagName("executionParameters").item(0).getTextContent();
							if(execParam!=null)
								model.setExecutionParameters(execParam);
							
							String timeout=null;
							if(eElement.getElementsByTagName("timeout").getLength()>0){
								if(eElement.getElementsByTagName("timeout").item(0).getTextContent()!=null)
									timeout=eElement.getElementsByTagName("timeout").item(0).getTextContent();
								if(timeout!=null)
									model.setTimeout(Long.parseLong(timeout));
							}
							
							String breakIfNotZero=null;							
							if(eElement.getElementsByTagName("breakIfNotZero").getLength()>0){
								if(eElement.getElementsByTagName("breakIfNotZero").item(0).getTextContent()!=null)
									breakIfNotZero=eElement.getElementsByTagName("breakIfNotZero").item(0).getTextContent();
								if(breakIfNotZero!=null)
									model.setBreakIfExitCodeNotZero(Boolean.parseBoolean(breakIfNotZero));
							}
							
							
							
							
							
							Element SizeElement = (Element)eElement.getElementsByTagName("size").item(0);
							Dimension dimension=new Dimension();
							dimension.setWidth(Integer.parseInt( SizeElement.getElementsByTagName("width").item(0).getTextContent()));
							dimension.setHeight(Integer.parseInt(SizeElement.getElementsByTagName("heigth").item(0).getTextContent()));
							model.setSize(dimension);							
							
							Element loactionElement = (Element)eElement.getElementsByTagName("location").item(0);
							Point point=new Point();
							point.setX(Integer.parseInt( loactionElement.getElementsByTagName("x").item(0).getTextContent()));
							point.setY(Integer.parseInt(loactionElement.getElementsByTagName("y").item(0).getTextContent()));
							model.setLocation(point);
							
							
							
							NodeList inputFileNameNodeList=eElement.getElementsByTagName("inputFileName");				
							int l=0;
							ArrayList<String> inputfilenames=new ArrayList<String>();
							if(inputfilenames!=null) l=inputFileNameNodeList.getLength();
							for (int i=0;i<l;i++){
								inputfilenames.add(inputFileNameNodeList.item(i).getTextContent());							
							}
							model.setInputFileNames(inputfilenames);
							
							
							NodeList outputFileNameNodeList=eElement.getElementsByTagName("outputFile");		
							l=0;
							ArrayList<OutputPair> outputFiles=new ArrayList<>();
							if(outputFileNameNodeList!=null) l=outputFileNameNodeList.getLength();
							for(int i=0;i<l;i++){
								OutputPair op=new OutputPair();
								
								Element e=(Element)outputFileNameNodeList.item(i);
								
								
								if(e.getElementsByTagName("fileName").item(0)!=null)								 
									op.setFileName(e.getElementsByTagName("fileName").item(0).getTextContent());
								if(e.getElementsByTagName("numberOfLines").item(0)!=null)
									op.setNumberOfLines(e.getElementsByTagName("numberOfLines").item(0).getTextContent());
								if(e.getElementsByTagName("minFileSize").item(0)!=null)
									op.setMinFileSize(e.getElementsByTagName("minFileSize").item(0).getTextContent());
								if(e.getElementsByTagName("breakIfHappend").item(0)!=null)
									op.setBreakIfHappend(Boolean.parseBoolean(e.getElementsByTagName("breakIfHappend").item(0).getTextContent()));
								
								outputFiles.add(op);
								
							}
							model.setOutputFileNames(outputFiles);

							
							// read log file for model---
							if(eElement.getElementsByTagName("logFile").getLength()>0){
								Element logFileElement=(Element)eElement.getElementsByTagName("logFile").item(0);
								ResusModelLogFile logFile=new ResusModelLogFile();
								
								if(logFileElement.getElementsByTagName("fileName").item(0)!=null)								 
									logFile.setFileName(logFileElement.getElementsByTagName("fileName").item(0).getTextContent());
								if(logFileElement.getElementsByTagName("criticalWords").item(0)!=null)
									logFile.setCriticalWords(logFileElement.getElementsByTagName("criticalWords").item(0).getTextContent());
								if(logFileElement.getElementsByTagName("breakIfHappend").item(0)!=null)
									logFile.setBreakIfHappend(Boolean.parseBoolean(logFileElement.getElementsByTagName("breakIfHappend").item(0).getTextContent()));
								
								model.setResusModelLogFile(logFile);	
							}
							
								
								
								
							}
							
							//---------------------------
						ResusDiagram d= getResusDiagram();
							d.addChild(model);
							
					 
						}
					 
				
				 
				
				 // result convertors
				 for (int count = 0; count < ResultConverterNodeList.getLength(); count++) {					 
						Node tempNode = ResultConverterNodeList.item(count);
						ResultConverter resultConverter=new ResultConverter();				
					 
						// make sure it's element node.
						if (tempNode.getNodeType() == Node.ELEMENT_NODE) {				
							
							Element eElement = (Element) tempNode;
							resultConverter.setId(eElement.getElementsByTagName("id").item(0).getTextContent());
							resultConverter.setNumberOfInputs(Integer.parseInt(eElement.getElementsByTagName("numberOfInputPins").item(0).getTextContent()));
							
							
							Element nameElement= (Element) tempNode;
							if(nameElement.getElementsByTagName("name")!=null && nameElement.getElementsByTagName("name").item(0)!=null ){
								resultConverter.setName(nameElement.getElementsByTagName("name").item(0).getTextContent());								
							}
							
							Element SizeElement = (Element)eElement.getElementsByTagName("size").item(0);
							Dimension dimension=new Dimension();
							dimension.setWidth(Integer.parseInt( SizeElement.getElementsByTagName("width").item(0).getTextContent()));
							dimension.setHeight(Integer.parseInt(SizeElement.getElementsByTagName("heigth").item(0).getTextContent()));
							resultConverter.setSize(dimension);							
							
							Element loactionElement = (Element)eElement.getElementsByTagName("location").item(0);
							Point point=new Point();
							point.setX(Integer.parseInt( loactionElement.getElementsByTagName("x").item(0).getTextContent()));
							point.setY(Integer.parseInt(loactionElement.getElementsByTagName("y").item(0).getTextContent()));
							resultConverter.setLocation(point);
	 
							
							Element regexElement = (Element) tempNode;
							resultConverter.setRegex(regexElement.getElementsByTagName("regex").item(0).getTextContent());
							
							
							Element columnDelimiterElement = (Element) tempNode;
							resultConverter.setColumnDelimiter(columnDelimiterElement.getElementsByTagName("columnDelimiter").item(0).getTextContent());
							
							
							Element inputFileNameElement = (Element) tempNode;
							resultConverter.setInputFileName(inputFileNameElement.getElementsByTagName("inputFileName").item(0).getTextContent());
							
							
						

							
							
							Element outputrows = (Element) tempNode;
							if(outputrows.getElementsByTagName("numberOfOutputRows").item(0)!=null){
								resultConverter.setNumberOfOutputRows(Integer.valueOf(outputrows.getElementsByTagName("numberOfOutputRows").item(0).getTextContent()));
	
								Element outputAffectedIndex = (Element) tempNode;
								
								int d=Integer.parseInt(outputAffectedIndex.getElementsByTagName("outputAffectedIndex").item(0).getTextContent());
								resultConverter.setOutputAffectedIndex(d);
							}
							
							
							Element executorNode = (Element) tempNode;
							if(executorNode.getElementsByTagName("executor")!=null && executorNode.getElementsByTagName("executor").item(0)!=null){
								Element executorElement=(Element)executorNode.getElementsByTagName("executor").item(0);
								NodeList nsFileName= executorElement.getElementsByTagName("fileName");								
								NodeList nsWorkingDirecotry= executorElement.getElementsByTagName("workingDirecotry");
								NodeList nsExecutorParameter= executorElement.getElementsByTagName("executorParameter");
								NodeList nsOutputFileName= executorElement.getElementsByTagName("executorOutputFileName");
								
								if(nsFileName.getLength()>0)
									resultConverter.setExecutor(nsFileName.item(0).getTextContent());
								if(nsWorkingDirecotry.getLength()>0)
									resultConverter.setExecutorWorkingDirectory(nsWorkingDirecotry.item(0).getTextContent());
								if(nsExecutorParameter.getLength()>0)
									resultConverter.setExecutorParameter(nsExecutorParameter.item(0).getTextContent());
								if(nsOutputFileName.getLength()>0)
									resultConverter.setExecutorOutput(nsOutputFileName.item(0).getTextContent());
								
							}
							
							Element outputFileNameElement = (Element) tempNode;
							String nls=outputFileNameElement.getElementsByTagName("outputFileName").item(0).getTextContent();
							resultConverter.setOutputFileName(nls);
							
							NodeList indexNodeList=eElement.getElementsByTagName("index");				
							int l=0;
							ArrayList<IndexPairs> indexes=new ArrayList<IndexPairs>();
							if(indexNodeList!=null) l=indexNodeList.getLength();
							for (int i=0;i<l;i++){
								Element index=(Element)indexNodeList.item(i);//.getElementsByTagName("index");	
								int id=Integer.parseInt(index.getElementsByTagName("id").item(0).getTextContent());
								String tag=index.getElementsByTagName("tag").item(0).getTextContent();
								String unit =index.getElementsByTagName("unit").item(0).getTextContent();
								String coefficient=index.getElementsByTagName("coefficient").item(0).getTextContent();
								String forward=index.getElementsByTagName("forward").item(0).getTextContent();
								IndexPairs p=new IndexPairs();
								p.tag=tag;
								p.id=id;
								p.unit=unit;
								p.forward=Boolean.parseBoolean(forward);
								p.coefficient=Double.parseDouble(coefficient);
								
								indexes.add(p);							
							}
							resultConverter.setColumnsIndexes(indexes);
							
							
							
							
					 
							ResusDiagram d= getResusDiagram();
							d.addChild(resultConverter);
							
					 
						}
					 
				}
				 for (int count = 0; count < IOProviderNodeList.getLength(); count++) {					 
						Node tempNode = IOProviderNodeList.item(count);
						InputProvider ioprovider=new InputProvider();				
					 
						// make sure it's element node.
						if (tempNode.getNodeType() == Node.ELEMENT_NODE) {				
							
							Element eElement = (Element) tempNode;
							ioprovider.setId(eElement.getElementsByTagName("id").item(0).getTextContent());
							ioprovider.setNumberOfInputs(Integer.parseInt(eElement.getElementsByTagName("numberOfInputPins").item(0).getTextContent()));
							
							
							Element SizeElement = (Element)eElement.getElementsByTagName("size").item(0);
							Dimension dimension=new Dimension();
							dimension.setWidth(Integer.parseInt( SizeElement.getElementsByTagName("width").item(0).getTextContent()));
							dimension.setHeight(Integer.parseInt(SizeElement.getElementsByTagName("heigth").item(0).getTextContent()));
							ioprovider.setSize(dimension);							
							
							Element loactionElement = (Element)eElement.getElementsByTagName("location").item(0);
							Point point=new Point();
							point.setX(Integer.parseInt( loactionElement.getElementsByTagName("x").item(0).getTextContent()));
							point.setY(Integer.parseInt(loactionElement.getElementsByTagName("y").item(0).getTextContent()));
							ioprovider.setLocation(point);
							
							String lf=null;							
							if(eElement.getElementsByTagName("logFile").item(0)!=null)
								lf=eElement.getElementsByTagName("logFile").item(0).getTextContent();
							if(lf!=null)
								ioprovider.setLogFileName(lf);
							
							
							String indx="";							
							if(eElement.getElementsByTagName("index").item(0)!=null)
								indx=eElement.getElementsByTagName("index").item(0).getTextContent();
							if(indx!=null)
								ioprovider.setIndex(indx);
														
							NodeList parametersFileNameNodeList=eElement.getElementsByTagName("parametersFileName");				
							int l=0;
							ArrayList<String> filenames=new ArrayList<String>();
							if(parametersFileNameNodeList!=null) l=parametersFileNameNodeList.getLength();
							for (int i=0;i<l;i++){
								filenames.add(parametersFileNameNodeList.item(i).getTextContent());							
							}
							ioprovider.setFileNames(filenames);
							
					 
							ResusDiagram d= getResusDiagram();
							d.addChild(ioprovider);
							
					 
						}
					 
				}
				 
				 
				 for (int count = 0; count < connectionNodeList.getLength(); count++) {					 
						Node tempNode = connectionNodeList.item(count);
						Connector wire=new Connector();
						
						// make sure it's element node.
						if (tempNode.getNodeType() == Node.ELEMENT_NODE) {				
							
							Element eElement = (Element) tempNode;
							Element sourceElement=(Element)eElement.getElementsByTagName("source").item(0);
							
							int sourceID=Integer.parseInt(sourceElement.getElementsByTagName("id").item(0).getTextContent());
							String sourcePin=sourceElement.getElementsByTagName("pin").item(0).getTextContent();
							
							
							Element targetElement=(Element)eElement.getElementsByTagName("target").item(0);
							
							int targetID=Integer.parseInt(targetElement.getElementsByTagName("id").item(0).getTextContent());
							String targetPin=targetElement.getElementsByTagName("pin").item(0).getTextContent();
							
							

							ResusDiagram d= getResusDiagram();
							
							for(int i=0;i<d.getChildren().size();i++)							
							{	ResusSubpart l=(ResusSubpart)d.getChildren().get(i);
								if(l.getId().equals(String.valueOf(targetID))){
									wire.setTarget(l);
									wire.setTargetTerminal(targetPin);
									l.connectInput(wire);
								}
							
								
							}	
							for(int i=0;i<d.getChildren().size();i++)							
							{	ResusSubpart l=(ResusSubpart)d.getChildren().get(i);
								if(l.getId().equals(String.valueOf(sourceID))){
									wire.setSource(l);
									wire.setSourceTerminal(sourcePin);
									l.connectOutput(wire);
								
								
								}
								
							}	
							
					 
						}
					 
				}
				 
				 
				 
					 
		    } catch (Exception e) {
				System.out.println(e.getMessage());
		    }



		if (!editorSaving) {
			if (getGraphicalViewer() != null) {
				getGraphicalViewer().setContents(getResusDiagram());
				loadProperties();
			}
			if (outlinePage != null) 
				outlinePage.setContents(getResusDiagram());
		}
		
	}

	
	
	
	public void setLogicDiagram(ResusDiagram diagram) {
		resusDiagram = diagram;
	}

	protected void superSetInput(IEditorInput input) {
		// The workspace never changes for an editor. So, removing and re-adding
		// the
		// resourceListener is not necessary. But it is being done here for the
		// sake
		// of proper implementation. Plus, the resourceListener needs to be
		// added
		// to the workspace the first time around.
		if (getEditorInput() != null) {
			IFile file = ((IFileEditorInput) getEditorInput()).getFile();
			file.getWorkspace().removeResourceChangeListener(resourceListener);
		}

		super.setInput(input);

		if (getEditorInput() != null) {
			IFile file = ((IFileEditorInput) getEditorInput()).getFile();
			file.getWorkspace().addResourceChangeListener(resourceListener);
			setPartName(file.getName());
		}
	}

	protected void setSite(IWorkbenchPartSite site) {
		super.setSite(site);
		getSite().getWorkbenchWindow().getPartService()
				.addPartListener(partListener);
	}

}
