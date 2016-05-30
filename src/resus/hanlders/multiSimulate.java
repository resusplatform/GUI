package resus.hanlders;





	import java.io.BufferedReader;
	import java.io.BufferedWriter;
	import java.io.File;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	import java.io.OutputStream;
	import java.io.OutputStreamWriter;
	import java.util.Scanner;

import javax.swing.JOptionPane;

import org.eclipse.core.commands.AbstractHandler;
	import org.eclipse.core.commands.ExecutionEvent;
	import org.eclipse.core.commands.ExecutionException;
	import org.eclipse.core.resources.IFile;
	import org.eclipse.core.resources.IMarker;
	import org.eclipse.core.resources.IProject;
	import org.eclipse.core.resources.IResource;
	import org.eclipse.core.runtime.CoreException;
	import org.eclipse.core.runtime.IProgressMonitor;
	import org.eclipse.core.runtime.IStatus;
	import org.eclipse.core.runtime.Status;
	import org.eclipse.core.runtime.jobs.Job;
	
	import org.eclipse.swt.graphics.Device;
	import org.eclipse.swt.graphics.GCData;
	import org.eclipse.swt.widgets.Display;
	import org.eclipse.ui.IEditorPart;
	import org.eclipse.ui.IFileEditorInput;
	import org.eclipse.ui.IWorkbenchPage;
	import org.eclipse.ui.PlatformUI;
	import org.eclipse.ui.console.ConsolePlugin;
	import org.eclipse.ui.console.IConsole;
	import org.eclipse.ui.console.IConsoleManager;
	import org.eclipse.ui.console.MessageConsole;
	import org.eclipse.ui.console.MessageConsoleStream;
	import org.eclipse.ui.handlers.HandlerUtil;
	import org.eclipse.ui.progress.IProgressConstants;

import de.clausthal.tu.ielf.resus.wizards.SampleTemplateSelectDialog;
import de.clausthal.tu.ielf.resusdesigner.ResusEditor;
	import de.clausthal.tu.ielf.resusdesigner.ResusSettings;

	public class multiSimulate  extends AbstractHandler {

		private class mydevice extends Device {

			@Override
			public int internal_new_GC(GCData data) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public void internal_dispose_GC(int hDC, GCData data) {
				// TODO Auto-generated method stub
				
			}
			
			

			
		}
		
		

		@Override
		public Object execute(ExecutionEvent event) throws ExecutionException {
			final ExecutionEvent e=event;
			
			if(ResusSettings.chekReSUS_HOME_core()==null){
				throw new ExecutionException("resusecore.exe could not found. cancelling the simulation");			
			}
			
			Display.getDefault().syncExec(new Runnable() {
			    public void run() {
			    	
			    	
			    	
			    	String[] buttons={"OK","Cancel"};
			    	selectThreadsDialog smppage=new selectThreadsDialog(null,buttons );
			    	
			    	smppage.create();
			    	int dialogResult=smppage.open();
			    	if(dialogResult==1) return;
			    	
			    	 final String numOfThreads=smppage.getNumbreOfThreads();
			    	
			    	
			    	
			    	IEditorPart editor = PlatformUI.getWorkbench()
			    	        .getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			    	     IFileEditorInput input = (IFileEditorInput)editor.getEditorInput() ;
			    	     final IFile file = input.getFile();
			    	     IProject activeProject = file.getProject();
			    	     IResource res = (IResource) activeProject;

			    	     IMarker m;
			    	     try {
			    	            m = res.createMarker("com.examples.problem");
			    	            m.setAttribute(IMarker.LOCATION, 0);
			    	            m.setAttribute(IMarker.MESSAGE, "Hello");
			    	            m.setAttribute(IMarker.LINE_NUMBER, 0);
			    	      } catch (CoreException e) {
			    	        // TODO Auto-generated catch block
			    	        e.printStackTrace();
			    	      }
					
					
					
					// ---------------job--------------------
					final Job job = new Job("Resus Simulation Job: "+file.getName()+"|  Parallelized with "+numOfThreads+" threads") {
						@Override
						protected IStatus run(IProgressMonitor monitor) {
							
							
							
							
							
							
							String msg="Running Resus Simulation Model: "+(file.getName().toString());
							monitor.beginTask(msg, 100);
							/*
							 * Each page in the console is represented by an org.eclipse.ui.console.IConsole object. 
							 * To write to the console, you need to create your own IConsole instance and connect it to the Console view. 
							 * To do this, you have to add a new dependency to org.eclipse.ui.console in the plugin.xml of your plugin. 
							 * For a console containing a simple text document, you can instantiate a MessageConsole instance. 
							 */
							
							
							final MessageConsole myConsole = findConsole("Console");
							IWorkbenchPage selection = HandlerUtil.getActiveWorkbenchWindow(e)
									.getActivePage();
						
							
							ResusEditor le = (ResusEditor) selection.getActiveEditor();
							
							le.doSave(null );
							MessageConsoleStream out = myConsole.newMessageStream();
							
							
							
							// Slow process------------
							myConsole.activate();
							myConsole.clearConsole();
							String inputparameterame = ((IFileEditorInput) le
									.getEditorInput()).getFile().getLocation().toString();
							//inputparameterame+=" -t "+numOfThreads;
							//System.out.println(inputparameterame);
							//out.setColor(new Color(new mydevice(), 100, 2, 2));
							out.println("Starting Resus Simulator (parallelized with "+numOfThreads+" threads ");
							out.println("input file : " + inputparameterame);

							try {
								String line;
								Scanner scan = new Scanner(System.in);

								
								ProcessBuilder builder = new ProcessBuilder(
										le.getSettings().getCore()
										//"D:\\resusParts\\resusCore\\resusCoreBuild-Qt-msvc2012-32bi\\debug\\resusCore.exe"
										,inputparameterame,"-t", numOfThreads);

								builder.redirectErrorStream(true);
								Process process;

								process = builder.start();

								OutputStream stdin = process.getOutputStream();
								InputStream stderr = process.getErrorStream();
								InputStream stdout = process.getInputStream();

								BufferedReader reader = new BufferedReader(
										new InputStreamReader(stdout));
								BufferedWriter writer = new BufferedWriter(
										new OutputStreamWriter(stdin));

								int i=0;
								while ((line = reader.readLine()) != null) {
//									
									out.println(line);
									i++;
									if(i>1000)
									{
										i=0;
										myConsole.clearConsole();
										
									}
									out.flush();
									
									if(monitor.isCanceled()) {
										process.destroy();
										
										out.println("jobs cancelled");
										return Status.CANCEL_STATUS;
									}
									
								}
								
								
								
								try {
									process.waitFor();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								

							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							// --------- end of slow process ----------

							
							monitor.done();
							out.println("jobs finished");
							
							return Status.OK_STATUS;
						}

					};
					job.schedule();
					
					job.setProperty(IProgressConstants.KEEP_PROPERTY, true);
					
					
					
					//job.setUser(true);
					
					

					
					

					// ---------------end of job--------------
			    	
			    	
			    }
			});
			
			
			 
			
			return null;

		}

		private MessageConsole findConsole(String name) {
			ConsolePlugin plugin = ConsolePlugin.getDefault();
			IConsoleManager conMan = plugin.getConsoleManager();
			IConsole[] existing = conMan.getConsoles();
			for (int i = 0; i < existing.length; i++)
				if (name.equals(existing[i].getName()))
					return (MessageConsole) existing[i];
			// no console found, so create a new one
			MessageConsole myConsole = new MessageConsole(name, null);
			
			conMan.addConsoles(new IConsole[] { myConsole });
			
			
			
			return myConsole;
			
			
		}

	}

