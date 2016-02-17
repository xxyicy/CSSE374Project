package app;

import impl.Clazz;
import impl.Model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import observer.api.Notifier;
import observer.api.Observer;
import api.IClass;
import api.IMethod;
import api.IModel;
import asm.ClassDeclarationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;
import pattern.api.IDetector;
import pattern.impl.AdapterDetector;
import pattern.impl.CompositeDetector;
import pattern.impl.DecoratorDetector;
import pattern.impl.SingletonDetector;
import visitor.impl.GraphVizOutputStream;
import visitor.impl.IOutputStream;
import visitor.impl.SDEditOutputStream;

public class Framework implements Notifier {

	private static final double LOADING_CLASS_NAMES = 10;
	private static final double LOADING_INPUT_CLASSES = 20;
	private static final double LOADING_CLASSES_FROM_FOLDER = 50;
	private static final double PATTERN_DETECTION = 20;
	private static final double GENERATE_DOT = 0;

	private static final String CLASS_LOADING = "Class-Loading";
	private static final String DECORATOR_DETECTION = "Decorator-Detection";
	private static final String COMPOSITE_DETECTION = "Composite-Detection";
	private static final String ADAPTER_DETECTION = "Adapter-Detection";
	private static final String SINGLETON_DETECTION = "Singleton-Detection";
	private static final String DOT_GENERATION = "DOT-Generation";

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

	private TMXXreader reader;
	private List<String> classes;
	private Set<IDetector> detectors;
	private IOutputStream out;
	private IModel model;
	private IMethod start;
	private List<Observer> observers;

	private ProgressBox box = new ProgressBox("Not Stared", 0);

	public Framework() throws FileNotFoundException, IOException {
		this.observers = new ArrayList<Observer>();
		this.model = new Model();
		this.detectors = new HashSet<IDetector>();
		this.classes = new ArrayList<String>();
		this.out = null;
		this.start = null;
		this.reader = null;
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
		
		if(loadFinished){
			try {
				this.reader.readFile();
			} catch (Exception e) {
				this.changeProgress("File Not Found", -1);
				loadFinished = false;
			}
		}
		if(loadFinished){
			this.changeProgress("Loading Configuration Finished", -1);
		}

		
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

	private void addDetectors(double totalProgess) {
		totalProgess = totalProgess / 4;
		System.out.println("this:::" + this.reader.getPhases());
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
			System.out.println("Adding");
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
			System.out.println("Adding");
			IDetector composite = new CompositeDetector();
			this.addDetector(composite);
		}

		this.changeProgress(null, this.box.getProgress() + totalProgess);
	}

	private void detectPatterns(double totalProgress) throws Exception {
		this.changeProgress("Detecting Patterns", -1);
		double currentProgress = this.box.getProgress();
		totalProgress = totalProgress * 3 / 4;
		int size = this.detectors.size();
		for (IDetector d : this.detectors) {
			this.changeProgress("Detecting" + d.toString(),
					this.box.getProgress() + totalProgress / size);
			d.detect(model);
		}

		this.changeProgress("Pattern Detection Finished", currentProgress + totalProgress);
	}

	public void Analyze() throws Exception {
		try {
			if (this.reader.getAppType().equals("SD")) {
				out = new SDEditOutputStream(new FileOutputStream(
						this.reader.getOutputDir()));
			} else {
				out = new GraphVizOutputStream(new FileOutputStream(
						this.reader.getOutputDir()));
			}

			this.processPhases();
		} catch (Exception e) {
			this.changeProgress("Analyze Failed", -1);
		}
		

	}

	private void processPhases() throws Exception {
		List<String> phases = this.reader.getPhases();
		if (phases.contains(Framework.CLASS_LOADING)) {
			this.loadClassFromInputFolder();
			this.loadInputClasses();
		} else {
			throw new Exception("Not Loading class??????!");
		}

		this.detectPattern();

		// TO DO
		if (phases.contains(Framework.DOT_GENERATION)) {
//			 System.out.println(model);
		}
		
		
		this.changeProgress("Analyzing Finished", 100);
		this.notifyObservers(this.model);
	}

	
	
	private void detectPattern() throws Exception {
		this.changeProgress("PATTERN DETECTION", -1);
		double totalProgress = Framework.PATTERN_DETECTION;
		int detectorCount = 0;
		for (String s : this.reader.getPhases()) {
			if (s.contains("Detection")) {
				detectorCount++;
			}
		}

		if (detectorCount == 0) {
			this.changeProgress("Finished Pattern Detection",
					this.box.getProgress() + totalProgress);
		}

		else {
			this.addDetectors(totalProgress);
			this.detectPatterns(totalProgress);
		}

	}

	private List<String> loadClassNames() {
		this.changeProgress("Loading class names", -1);

		List<Class<?>> classes = ClassFinder.find(this.reader.getInputFolder());
		List<String> cs = new ArrayList<>();

		for (Class<?> clazz : classes) {
			cs.add(clazz.getName());
		}

		this.changeProgress("Loading class names finished",
				Framework.LOADING_CLASS_NAMES);
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
		if(data instanceof ProgressBox){
			ProgressBox box = (ProgressBox) data;
			System.out.println(box.getCurrentTask() + " : " + box.getProgress());
		}
		
		for (Observer o : this.observers) {
			o.update(data);
		}

	}

	private void loadClassFromInputFolder() throws IOException {
		this.classes = this.loadClassNames();
		this.loadClassRecur();
	}

	private void loadInputClasses() throws IOException {
		double totalProgess = Framework.LOADING_INPUT_CLASSES;
		this.changeProgress("Loading Additional Classes", -1);
		double currentProgess = this.box.getProgress();

		if (this.reader.getInputClasses().isEmpty()) {
			this.changeProgress(null, this.box.getProgress() + totalProgess);
		} else {
			int size = this.reader.getInputClasses().size();

			for (String clazz : this.reader.getInputClasses()) {
				this.changeProgress("Loading " + clazz, -1);

				ClassReader reader = new ClassReader(clazz);

				IClass c = new Clazz();
				// make class declaration visitor to get superclass and
				// interfaces
				ClassVisitor decVisitor = new ClassDeclarationVisitor(
						Opcodes.ASM5, c, model);

				// DECORATE declaration visitor with field visitor
				ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
						decVisitor, c, model);

				// DECORATE field visitor with method visitor
				ClassVisitor methodVisitor = new ClassMethodVisitor(
						Opcodes.ASM5, fieldVisitor, c, model);

				reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

				if (!c.getName().contains("$")) {
					model.addClass(c);
				}

				this.changeProgress(null, this.box.getProgress() + totalProgess
						/ size);
			}

			this.changeProgress(null, currentProgess + totalProgess);
		}

	}

	private void loadClassRecur() throws IOException {

		double totalProgress = Framework.LOADING_CLASSES_FROM_FOLDER;

		double currentProgress = this.box.getProgress();

		double maxProgess = totalProgress + currentProgress;
		List<String> cs = this.classes;
		List<String> classRead = new ArrayList<>();

		double size = cs.size() * 3;

		while (!cs.isEmpty()) {
			String clazz = cs.get(0);
			cs.remove(0);

			this.changeProgress("Loading " + clazz, Math.min(
					this.box.getProgress() + totalProgress / size, maxProgess));

			ClassReader reader = new ClassReader(clazz);
			IClass c = new Clazz();
			// make class declaration visitor to get superclass and interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5,
					c, model, cs);
			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
					decVisitor, c, model);
			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5,
					fieldVisitor, c, model);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

			clazz = clazz.replaceAll("/", ".");
			if (!c.getName().contains("$") && !classRead.contains(clazz)) {
				model.addClass(c);
				classRead.add(clazz);
			}
		}

		model.setRelation(Utility.removeRelationNotInPackage(model));
		this.changeProgress(null, maxProgess);
	}

}
