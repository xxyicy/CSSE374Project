package app;

import impl.Model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import api.IMethod;
import api.IModel;
import pattern.api.IDetector;
import pattern.impl.AdapterDetector;
import pattern.impl.CompositeDetector;
import pattern.impl.DecoratorDetector;
import pattern.impl.SingletonDetector;
import visitor.impl.GraphVizOutputStream;
import visitor.impl.IOutputStream;
import visitor.impl.SDEditOutputStream;


public class Framework {
	private TMXXreader reader;
	private List<String> classes;
	private Set<IDetector> detectors;
	private IOutputStream out;
	private IModel model;
	private IMethod start;
	
	private String currentTask = "Not Started";
	private int progress = 0;
	
	
	public Framework() throws FileNotFoundException, IOException{
		this.currentTask = "Initializing";
		this.model = new Model();
		this.detectors = new HashSet<IDetector>();
		this.classes = new ArrayList<String>();
		this.out = null;
		this.start = null;	
		this.reader = null;
	}
	
	
	public void loadConfig(String path) throws IOException{
		
		this.reader = new TMXXreader(path);
		this.reader.readFile();
	}
	
	
	
	private void changeTask(String task){
		this.currentTask = task;
	}
	
	private void changeProgress(int pro){
		this.progress = pro;
	}
	
	
	private void addDetector(IDetector d){
		this.detectors.add(d);
	}
	
	
	
	private void addDetectors(){
		if(this.reader.getPhases().contains("Decorator-Detection")){
			String mdstr = this.reader.getAttrMap().get("Decorator-MethodDelegation");
			int methodDelegation = 0;
			if(mdstr != null){
				methodDelegation = Integer.parseInt(mdstr);
			}
			IDetector decorator = new DecoratorDetector(methodDelegation);
			this.addDetector(decorator);
		}
		
		if(this.reader.getPhases().contains("Adapter-Detection")){
			String mdstr = this.reader.getAttrMap().get("Adapter-MethodDelegation");
			int methodDelegation = 0;
			if(mdstr != null){
				methodDelegation = Integer.parseInt(mdstr);
			}
			IDetector adapter = new AdapterDetector(methodDelegation);
			this.addDetector(adapter);
		}
		
		if(this.reader.getPhases().contains("Singleton-Detection")){
			String mdstr = this.reader.getAttrMap().get("Singleton-RequireGetInstance");
			int methodDelegation = 0;
			if(mdstr != null){
				methodDelegation = Integer.parseInt(mdstr);
			}
			IDetector singleton = new SingletonDetector(methodDelegation);
			this.addDetector(singleton);
		}
		
		if(this.reader.getPhases().contains("Composite-Detection")){
			
			IDetector composite = new CompositeDetector();
			this.addDetector(composite);
		}
		
		
	}
	
	
	
	private void initialize() throws FileNotFoundException, IOException{
		this.classes = this.loadClassNames();
		
		
		if(this.reader.getAppType().equals("SD")){
			out = new SDEditOutputStream(new FileOutputStream(this.reader.getOutputDir()));
		}
		else{
			out = new GraphVizOutputStream(new FileOutputStream(this.reader.getOutputDir()));
		}
		
		this.addDetectors();
	}
	
	
	private List<String> loadClassNames(){
		List<Class<?>> classes = ClassFinder.find(this.reader.getInputFolder());
		List<String> cs = new ArrayList<>();
		
		for (Class<?> clazz : classes) {
			cs.add(clazz.getName());
		}
		return cs;
	}
	
	
	
	
	
	
	
	
	
	
	public String toString() {
		String result = "";
		result += "input-folder : " + this.reader.getInputFolder() + "\n";
		result += "output-dir : " + this.reader.getOutputDir() + "\n";
		result += "dot-path : " + this.reader.getDotPath() + "\n";
		result += "app-type : " + this.reader.getAppType() + "\n";
		result += "reader.getPhases() : " + this.reader.getPhases() + "\n";
		result += "input-classes : " + this.reader.getInputClasses() + "\n";
		for (String s : this.reader.getAttrMap().keySet()) {
			result += s + " : " + this.reader.getAttrMap().get(s) + "\n";
		}
		return result;

	}
	
	
	
	
	
	
}
