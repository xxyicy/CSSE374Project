package Framework;

import impl.Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import observer.api.Notifier;
import observer.api.Observer;
import pattern.api.IDetector;
import pattern.impl.AdapterDetector;
import pattern.impl.CompositeDetector;
import pattern.impl.DecoratorDetector;
import pattern.impl.SingletonDetector;
import phase.api.IPhase;
import phase.impl.ClassLoadingPhase;
import phase.impl.PatternDetectionPhase;
import api.IModel;

public class Framework implements Notifier, Observer {
	
	
	public static final double LOADING_CLASS_NAMES = 10;
	public static final double LOADING_INPUT_CLASSES = 20;
	public static final double LOADING_CLASSES_FROM_FOLDER = 50;
	public static final double PATTERN_DETECTION = 20;
	public static final double GENERATE_DOT = 0;

	public static final String CLASS_LOADING = "Class-Loading";
	public static final String DECORATOR_DETECTION = "Decorator-Detection";
	public static final String COMPOSITE_DETECTION = "Composite-Detection";
	public static final String ADAPTER_DETECTION = "Adapter-Detection";
	public static final String SINGLETON_DETECTION = "Singleton-Detection";
	public static final String DOT_GENERATION = "DOT-Generation";
	
	
	
	
	public class ProgressBox {

		private String currentTask;
		private double progress;

		public ProgressBox(String task, int progress) {
			this.currentTask = task;
			this.progress = progress;
		}

		public String getCurrentTask() {
			return currentTask;
		}

		public void setCurrentTask(String currentTask) {
			this.currentTask = currentTask;
		}

		public double getProgress() {
			return progress;
		}

		public void setProgress(double progress) {
			this.progress = progress;
		}
	}

	public class DataBox {
		private IModel model;
		private TMXXreader reader;

		public DataBox(IModel model2, TMXXreader reader2) {
			model = model2;
			reader = reader2;
		}

		public IModel getModel() {
			return model;
		}

		public void setModel(IModel model) {
			this.model = model;
		}

		public TMXXreader getReader() {
			return reader;
		}

		public void setReader(TMXXreader reader) {
			this.reader = reader;
		}
	}

	private TMXXreader reader;
	private Set<IDetector> detectors;
	private IModel model;
	private List<Observer> observers;
	private List<IPhase> phases;
	private ProgressBox box = new ProgressBox("Not Stared", 0);

	public Framework() throws FileNotFoundException, IOException {
		this.observers = new ArrayList<Observer>();
		this.model = new Model();
		this.detectors = new HashSet<IDetector>();
		
		this.reader = null;
		this.phases = new ArrayList<IPhase>();
	}

	
	
	
	
	public void loadConfig(String path) {
		this.changeProgress("Loading Configuration", -1);
		boolean loadFinished = true;
		try {
			this.reader = new TMXXreader(path);
		} catch (Exception e) {
			this.changeProgress("File Not Found", -1);
			loadFinished = false;
		}

		if (loadFinished) {
			try {
				this.reader.readFile();
			} catch (Exception e) {
				this.changeProgress("File Not Found", -1);
				loadFinished = false;
			}
		}
		if (loadFinished) {
			this.changeProgress("Loading Configuration Finished", -1);
		}

	}
	
	private void addPhase(IPhase phase){
		this.phases.add(phase);
	}
	
	private void loadPhases() throws Exception{
		List<String> phaseNames = this.reader.getPhases();

		if (phaseNames.contains(Framework.CLASS_LOADING)) {
			IPhase phase = new ClassLoadingPhase(model, this.reader,this.box);
			phase.registerObserver(this);
			this.addPhase(phase);
		
		} else {
			throw new Exception("Not Loading class??????!");
		}
		
		
		
		this.addDetectors();
		IPhase patternPhase = new PatternDetectionPhase(this.model, box, this.detectors);
		patternPhase.registerObserver(this);
		this.addPhase(patternPhase);
		
		
		
//		this.detectPattern();
//
//		this.changeProgress("Analyzing Finished", 100);
//
//		this.notifyObservers(new DataBox(this.model, this.reader));
	}
	
	
	
	
	
	
	
	
	
	
	private void changeProgress(String task, double progress) {
		if (task != null) {
			this.box.setCurrentTask(task);
		}
		if (progress != -1) {
			this.box.setProgress(progress);
		}

		this.notifyObservers(box);
	}

	
	private void addDetector(IDetector d) {
		this.detectors.add(d);
	}

	private void addDetectors() {
		double totalProgess = Framework.PATTERN_DETECTION / 4;

		this.changeProgress("Creating Detectors", -1);
		if (this.reader.getPhases().contains(Framework.DECORATOR_DETECTION)) {

			String mdstr = this.reader.getAttrMap().get(
					"Decorator-MethodDelegation");
			int methodDelegation = 0;
			if (mdstr != null) {
				methodDelegation = Integer.parseInt(mdstr);
			}
			IDetector decorator = new DecoratorDetector(methodDelegation);
			this.addDetector(decorator);
		}

		if (this.reader.getPhases().contains(Framework.ADAPTER_DETECTION)) {

			String mdstr = this.reader.getAttrMap().get(
					"Adapter-MethodDelegation");
			int methodDelegation = 0;
			if (mdstr != null) {
				methodDelegation = Integer.parseInt(mdstr);
			}
			IDetector adapter = new AdapterDetector(methodDelegation);
			this.addDetector(adapter);
		}

		if (this.reader.getPhases().contains(Framework.SINGLETON_DETECTION)) {

			String mdstr = this.reader.getAttrMap().get(
					"Singleton-RequireGetInstance");
			int methodDelegation = 0;
			if (mdstr != null) {
				methodDelegation = 1;
			}
			IDetector singleton = new SingletonDetector(methodDelegation);
			this.addDetector(singleton);
		}

		if (this.reader.getPhases().contains(Framework.COMPOSITE_DETECTION)) {

			IDetector composite = new CompositeDetector();
			this.addDetector(composite);
		}

		this.changeProgress(null, this.box.getProgress() + totalProgess);
	}

	

	public void Analyze() throws Exception {
		try {
			this.loadPhases();
			this.processPhases();
		} catch (Exception e) {
			this.changeProgress("Analyze Failed", -1);
			e.printStackTrace();
		}

	}

	private void processPhases() throws Exception {
		for(IPhase phase: this.phases){
			phase.execute();
		}

		this.changeProgress("Analyzing Finished", 100);
		this.notifyObservers(new DataBox(this.model, this.reader));
	
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

	@Override
	public void registerObserver(Observer o) {
		this.observers.add(o);

	}

	@Override
	public void removeObserver(Observer o) {
		this.observers.remove(o);

	}

	@Override
	public void notifyObservers(Object data) {

		for (Observer o : this.observers) {
			o.update(data);
		}

	}

	

	

	@Override
	public void update(Object data) {
		ProgressBox box = (ProgressBox) data;
		this.changeProgress(box.getCurrentTask(), box.getProgress());
		
	}

}
