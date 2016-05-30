package org.eclipse.ui.examples.views.properties.tabbed.logic.properties.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.examples.views.properties.tabbed.logic.properties.AbstractSection;
import org.eclipse.ui.examples.views.properties.tabbed.logic.properties.TextChangeHelper;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import de.clausthal.tu.ielf.resus.RowNumberLabelProvider;
import de.clausthal.tu.ielf.resusdesigner.ResusMessages;
import de.clausthal.tu.ielf.resusdesigner.model.ResusModel;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel.SetResusModelExecutorBreakIfExitCodeNotZeroTimeoutCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel.SetResusModelExecutorCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel.SetResusModelExecutorParametersCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel.SetResusModelExecutorTimeoutCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel.SetResusModelInputFilesCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel.setResusModelExecutorLogFileBreakIfHappendCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel.setResusModelExecutorLogFileCriticalWordsCommand;
import de.clausthal.tu.ielf.resusdesigner.model.commands.ResusModel.setResusModelExecutorLogFileFileNameCommand;

public class ModelExecutorLogFileSection extends AbstractSection {

	public ModelExecutorLogFileSection() {}
		// TODO Auto-generated constructor stub

		
		private Text txtLogFilename;
		private Text txtCriticalWords;		
		private Button chkBreakIfHappend;	
		 		 
			
			
		private TextChangeHelper txtLogFilenameChangelistener = new TextChangeHelper() {
			public void textChanged(Control control) {
						
						setResusModelExecutorLogFileFileNameCommand rnc=new setResusModelExecutorLogFileFileNameCommand();
						rnc.setPart((ResusModel)getElement());				
						rnc.setFileName(txtLogFilename.getText().trim());
						
						runCommand(rnc);
					}
			};	
		private TextChangeHelper txtCriticalWordsChangelistener = new TextChangeHelper() {
			public void textChanged(Control control) {
						
						setResusModelExecutorLogFileCriticalWordsCommand rnc=new setResusModelExecutorLogFileCriticalWordsCommand();
						rnc.setPart((ResusModel)getElement());				
						rnc.setCriticalWords(txtCriticalWords.getText().trim());
						
						runCommand(rnc);
					}
			};	
			
		private TextChangeHelper ChkBreakIfHappenedChangelistener = new TextChangeHelper() {
			public void textChanged(Control control) {
						
						setResusModelExecutorLogFileBreakIfHappendCommand rnc=new setResusModelExecutorLogFileBreakIfHappendCommand();
						rnc.setPart((ResusModel)getElement());				
						rnc.setSelection(chkBreakIfHappend.getSelection());
						
						runCommand(rnc);
					}
			};	
		
		
		
		/**
		 * @wbp.parser.entryPoint
		 */
		public void createControls(Composite parent,
				TabbedPropertySheetPage tabbedPropertySheetPage) {
			super.createControls(parent, tabbedPropertySheetPage);
			
			
			
			
			final Composite composite = getWidgetFactory().createFlatFormComposite(parent);
			composite.setBounds(28, 10, 900, 252);
			composite.setLayout(null);
			
			
			//-------------LOGFILE NAME TEXTBOX------------------------------
			Label lblLogFileName= new Label(composite, SWT.NONE);
			lblLogFileName.setBounds(5, 5,95, 15);
			lblLogFileName.setText("Log File ");
			
			
			txtLogFilename = new Text(composite, SWT.BORDER);
			txtLogFilename.setBounds(105, 5, 500, 21);
			
			Button btnBrowsLogFile = new Button(composite, SWT.NONE);
			btnBrowsLogFile.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) 
					{
						
						FileDialog fileDialog = new FileDialog(composite.getShell());
					    // Set the text
						fileDialog.setText("Select log File directory");
					    // Set filter on .txt files
						
					    // Put in a readable name for the filter
					
					    // Open Dialog and save result of selection
					    String selected = fileDialog.open();
					    
					   
					    if(selected!=null){
					    	txtLogFilename.setText(selected);				    	
					    }
						 
					}
			});
			btnBrowsLogFile.setBounds(610, 3, 75, 25);
			btnBrowsLogFile.setText("brows");
			///---------------------------------------------------------------
			
			
			///----------------------- LOG FILE CRITICAL WORDS ----------------
			Label lblCriticalWords= new Label(composite, SWT.NONE);
			lblCriticalWords.setBounds(5, 30,95, 15);
			lblCriticalWords.setText("Critical Words");
			
			
			txtCriticalWords = new Text(composite, SWT.BORDER);
			txtCriticalWords .setBounds(105, 30, 500, 21);
			//-----------------------------------------------------------------
			
			
			
			
			// ----------- break if critical words found in log file ------------
			 chkBreakIfHappend= new Button(composite, SWT.CHECK);
			 chkBreakIfHappend.setBounds(300, 60, 200, 15);
			chkBreakIfHappend.setText("Break if Critical Words Found");
			
			//--------------------------------------------------------------------
			
			
			
			
			
	        Label lblHelpTip = new Label(composite, SWT.WRAP | SWT.BORDER);
			lblHelpTip.setText(ResusMessages.Model_Executor_logfile_information);
			lblHelpTip.setBounds(composite.getBounds().width+composite.getLocation().x, 10, 500, 200);

				
			
			
			
			txtLogFilenameChangelistener.startListeningForEnter(txtLogFilename);
			txtLogFilenameChangelistener.startListeningTo(txtLogFilename);
			
			
			txtCriticalWordsChangelistener.startListeningForEnter(txtCriticalWords);
			txtCriticalWordsChangelistener.startListeningTo(txtCriticalWords);
			
			ChkBreakIfHappenedChangelistener.startListeningTo(chkBreakIfHappend);
			ChkBreakIfHappenedChangelistener.startListeningForEnter(chkBreakIfHappend);
			
			
		}
		 
		/**
		 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
		 */
		public void refresh() {
			if(!(getElement() instanceof ResusModel)) throw new AssertionError("Object is not a Model");
			
			if(((ResusModel) getElement()).getResusModelLogFile()==null) return;
			txtLogFilenameChangelistener.startNonUserChange();			
			txtCriticalWordsChangelistener.startNonUserChange();			
			ChkBreakIfHappenedChangelistener.startNonUserChange();
			
			try {				
				txtLogFilename.setText(((ResusModel) getElement()).getResusModelLogFile().getFileName());
				txtCriticalWords.setText(((ResusModel) getElement()).getResusModelLogFile().getCriticalWords());
				chkBreakIfHappend.setSelection(((ResusModel) getElement()).getResusModelLogFile().isBreakIfHappend());
				
			} finally {
				
				txtLogFilenameChangelistener.finishNonUserChange();			
				txtCriticalWordsChangelistener.finishNonUserChange();			
				ChkBreakIfHappenedChangelistener.finishNonUserChange();
			}
		}
	}
