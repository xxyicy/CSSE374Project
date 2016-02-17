package app;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pattern.api.IDetector;
import pattern.impl.AdapterDetector;
import pattern.impl.CompositeDetector;
import pattern.impl.DecoratorDetector;
import pattern.impl.SingletonDetector;
import visitor.impl.GraphVizOutputStream;
import visitor.impl.IOutputStream;
import visitor.impl.SDEditOutputStream;


public class Framework {
	private String inputFolder;
	private List<String> inputClasses;
	private String outputDir;
	private String dotPath;
	private List<String> phases;
	private Map<String,String> attributeMap;
	private NewBeeFramework framework;
	private String appType;
	
	
	
	public Framework(String appType,String inputFolder, String outputDir, String dotPath) throws FileNotFoundException, IOException{
		this.inputClasses = new ArrayList<String>();
		this.phases = new ArrayList<String>();
		this.attributeMap = new HashMap<>();
		this.appType = appType;
		this.inputFolder = inputFolder;
		this.outputDir = outputDir;
		this.dotPath = dotPath;
		//this.initialize();
	}
	
	
	
	private void addDetectors(){
		if(this.phases.contains("Decorator-Detection")){
			String mdstr = this.attributeMap.get("Decorator-MethodDelegation");
			int methodDelegation = 0;
			if(mdstr != null){
				methodDelegation = Integer.parseInt(mdstr);
			}
			IDetector decorator = new DecoratorDetector(methodDelegation);
			this.framework.addDetector(decorator);
		}
		
		if(this.phases.contains("Adapter-Detection")){
			String mdstr = this.attributeMap.get("Adapter-MethodDelegation");
			int methodDelegation = 0;
			if(mdstr != null){
				methodDelegation = Integer.parseInt(mdstr);
			}
			IDetector adapter = new AdapterDetector(methodDelegation);
			this.framework.addDetector(adapter);
		}
		
		if(this.phases.contains("Singleton-Detection")){
			String mdstr = this.attributeMap.get("Singleton-RequireGetInstance");
			int methodDelegation = 0;
			if(mdstr != null){
				methodDelegation = Integer.parseInt(mdstr);
			}
			IDetector singleton = new SingletonDetector(methodDelegation);
			this.framework.addDetector(singleton);
		}
		
		if(this.phases.contains("Composite-Detection")){
			
			IDetector composite = new CompositeDetector();
			this.framework.addDetector(composite);
		}
		
		
	}
	
	
	
	private void initialize() throws FileNotFoundException, IOException{
		List<String> cs = this.loadClassNames();
		IOutputStream out;
		
		if(this.appType.equals("SD")){
			out = new SDEditOutputStream(new FileOutputStream(this.outputDir));
		}
		else{
			out = new GraphVizOutputStream(new FileOutputStream(this.outputDir));
		}
		
		this.framework = new NewBeeFramework(this.appType,cs,this.inputClasses,out);
		this.addDetectors();
	}
	
	
	private List<String> loadClassNames(){
		List<Class<?>> classes = ClassFinder.find(this.inputFolder);
		List<String> cs = new ArrayList<>();
		
		for (Class<?> clazz : classes) {
			cs.add(clazz.getName());
		}
		return cs;
	}
	
	
	
	public void addInputClass(String clazz){
		this.inputClasses.add(clazz);
	}
	
	public void addPhase(String p){
		this.phases.add(p);
	}
	
	public void addAttribute(String key,String value){
		this.attributeMap.put(key, value);
	}
	
	
	public String toString() {
		String result = "";
		result += "input-folder : " + this.inputFolder + "\n";
		result += "output-dir : " + this.outputDir + "\n";
		result += "dot-path : " + this.dotPath + "\n";
		result += "app-type : " + this.appType + "\n";
		result += "phases : " + this.phases + "\n";
		result += "input-classes : " + this.inputClasses + "\n";
		for (String s : this.attributeMap.keySet()) {
			result += s + " : " + this.attributeMap.get(s) + "\n";
		}
		return result;

	}
	
	
	
	
	
	
}
