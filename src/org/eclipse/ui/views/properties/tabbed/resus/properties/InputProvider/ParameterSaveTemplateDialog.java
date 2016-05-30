package org.eclipse.ui.views.properties.tabbed.resus.properties.InputProvider;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import de.clausthal.tu.ielf.resusdesigner.model.IndexPairs;


public class ParameterSaveTemplateDialog  extends MessageDialog {

	  private Text templateNameText;
	  
	  private String templateName;
	  

	  public ParameterSaveTemplateDialog(Shell parentShell,String[] btns) {
	    
		  super
	    
	    (parentShell, "Save Template as..",null, "",  CONFIRM,btns, OK);
	  }

	  @Override
	  public void create() {
	    super.create();
	    // Set the title
	    

	  }

	  @Override
	  protected Control createDialogArea(Composite parent) {
		  
			parent.setSize(new Point(300, 150));
			Label lblTemplateName = new Label(parent, SWT.NONE);
			lblTemplateName.setText("Template Name");
			lblTemplateName.setBounds(10, 15, 55, 15);
			
			templateNameText = new Text(parent, SWT.BORDER);
			templateNameText.setBounds(76, 15, 220, 21);
			templateNameText.setSize(new Point(120, 20));
	    
	    
	    return parent;
	  }

	  private boolean isValidInput() {
	    boolean valid = true;
	    if (templateNameText.getText().length() == 0) {
	      //("Please enter the first name");
	      valid = false;
	    }
	    return valid;
	  }
	  
	  @Override
	  protected boolean isResizable() {
	    return true;
	  }

	  // We need to have the textFields into Strings because the UI gets disposed
	  // and the Text Fields are not accessible any more.
	  private void saveInput() {
	    templateName = templateNameText.getText();
	    

	  }

	  @Override
	  protected void okPressed() {
	    saveInput();
	    super.okPressed();
	  }

	  public String getTemplateName() {
	    return templateName;
	  }

	  
	} 