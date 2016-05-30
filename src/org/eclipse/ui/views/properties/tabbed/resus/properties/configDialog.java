package org.eclipse.ui.views.properties.tabbed.resus.properties;

import org.eclipse.draw2d.GridData;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.jface.viewers.TableViewer;

public class configDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text txtID;
	private Text txtExecutorPath;
	private Text txtName;
	private Text txtModelType;
	private Text txtDescription;
	private Text text;
	private Text text_1;
	private Text txtRegex;
	private Text txtExecutor;
	private Text txtColumndelimiter;
	private Text txtInputFile;
	private Text txtOutputfile;
	private Table table;
	private Text txtIndex;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public configDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(703, 576);
		shell.setText(getText());
		shell.setLayout(null);
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.heightHint = 200;
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setLayout(null);
		composite_1.setBounds(38, 268, 600, 252);
		
		Label lblID = new Label(composite_1, SWT.NONE);
		lblID.setText("ID");
		lblID.setBounds(5, 5, 55, 15);
		
		txtID = new Text(composite_1, SWT.BORDER);
		txtID.setBounds(84, 5, 76, 21);
		
		txtExecutorPath = new Text(composite_1, SWT.BORDER);
		txtExecutorPath.setBounds(84, 77, 362, 21);
		
		txtName = new Text(composite_1, SWT.BORDER);
		txtName.setBounds(84, 29, 176, 21);
		
		Label lblName = new Label(composite_1, SWT.NONE);
		lblName.setText("Name");
		lblName.setBounds(5, 29, 55, 15);
		
		txtModelType = new Text(composite_1, SWT.BORDER);
		txtModelType.setBounds(84, 53, 176, 21);
		
		Label lblModelType = new Label(composite_1, SWT.NONE);
		lblModelType.setText("Model Type");
		lblModelType.setBounds(5, 53, 76, 15);
		
		Button button = new Button(composite_1, SWT.NONE);
		button.setText("brows");
		button.setBounds(452, 75, 75, 25);
		
		txtDescription =new Text(composite_1, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		txtDescription.setBounds(84, 104, 362, 97);
		
		Label lblDescription = new Label(composite_1, SWT.NONE);
		lblDescription.setText("Description");
		lblDescription.setBounds(5, 107, 55, 15);
		
		Label lblNumberOfInput = new Label(composite_1, SWT.NONE);
		lblNumberOfInput.setBounds(287, 29, 125, 15);
		lblNumberOfInput.setText("Number Of Input Pins");
		
		Label label = new Label(composite_1, SWT.NONE);
		label.setText("Number Of Input Pins");
		label.setBounds(287, 56, 125, 15);
		
		text = new Text(composite_1, SWT.BORDER);
		text.setBounds(414, 29, 176, 21);
		
		text_1 = new Text(composite_1, SWT.BORDER);
		text_1.setBounds(414, 53, 176, 21);
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(null);
		composite.setBounds(38, 10, 600, 252);
		
		Label lblRegex = new Label(composite, SWT.NONE);
		lblRegex.setText("regex");
		lblRegex.setBounds(5, 5, 55, 15);
		
		Label lblExecutor = new Label(composite, SWT.NONE);
		lblExecutor.setText("Executor");
		lblExecutor.setBounds(5, 55, 55, 15);
		
		txtRegex = new Text(composite, SWT.BORDER);
		txtRegex.setBounds(112, 5, 455, 21);
		
		txtExecutor = new Text(composite, SWT.BORDER);
		txtExecutor.setBounds(112, 52, 362, 21);
		
		txtColumndelimiter = new Text(composite, SWT.BORDER);
		txtColumndelimiter.setBounds(112, 29, 176, 21);
		
		Label lblColumnDelimiter = new Label(composite, SWT.NONE);
		lblColumnDelimiter.setText("Column Delimiter");
		lblColumnDelimiter.setBounds(5, 29, 123, 15);
		
		Button btnBrowsexecutor = new Button(composite, SWT.NONE);
		btnBrowsexecutor.setText("brows");
		btnBrowsexecutor.setBounds(480, 50, 75, 25);
		
		Label lblInputFile = new Label(composite, SWT.NONE);
		lblInputFile.setText("Input File ");
		lblInputFile.setBounds(5, 82, 76, 15);
		
		txtInputFile = new Text(composite, SWT.BORDER);
		txtInputFile.setBounds(112, 79, 362, 21);
		
		Button btnBrowsInputfile = new Button(composite, SWT.NONE);
		btnBrowsInputfile.setText("brows");
		btnBrowsInputfile.setBounds(480, 79, 75, 25);
		
		Label lblOutputFile = new Label(composite, SWT.NONE);
		lblOutputFile.setText("Output File");
		lblOutputFile.setBounds(5, 110, 86, 15);
		
		txtOutputfile = new Text(composite, SWT.BORDER);
		txtOutputfile.setBounds(112, 106, 362, 21);
		
		Button btnBrowsOutputfile = new Button(composite, SWT.NONE);
		btnBrowsOutputfile.setText("brows");
		btnBrowsOutputfile.setBounds(480, 105, 75, 25);
		
		TableViewer tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setBounds(112, 133, 134, 85);
		
		Button btnAddToList = new Button(composite, SWT.NONE);
		btnAddToList.setBounds(333, 133, 75, 25);
		btnAddToList.setText("Add To List");
		
		txtIndex = new Text(composite, SWT.BORDER);
		txtIndex.setBounds(252, 133, 76, 21);

	}
}
