package phase.impl;

import impl.Clazz;
import impl.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import phase.api.IPhase;
import Framework.ClassFinder;
import Framework.Framework;
import Framework.Framework.ProgressBox;
import Framework.Utility;
import api.IClass;
import api.IModel;
import asm.ClassDeclarationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;

import observer.api.Observer;
import Framework.TMXXreader;

public class ClassLoadingPhase implements IPhase {

	
	
	
	
	private IModel model;
	private TMXXreader reader;
	private List<String> classes;
	private List<Observer> observers;
	private ProgressBox box;

	
		

	
	
	
	public ClassLoadingPhase(IModel m, TMXXreader reader,ProgressBox box){
		this.observers = new ArrayList<Observer>();
		this.model = m;
		this.classes = new ArrayList<String>();
		this.reader = reader;
		this.box = box;
	}
	
	
	@Override
	public void execute() throws Exception {
		this.loadClassFromInputFolder();
		this.loadInputClasses();
	}

	
	
	
	
	private void loadClassFromInputFolder() throws IOException {
		System.out.println("loadClassFromInputFolder");
		if (this.reader.getInputFolder() == null) {
			return;
		}
		this.classes = this.loadClassNames();
		this.loadClassRecur();
	}

	private void loadInputClasses() throws IOException {
		System.out.println("loadInputClasses");
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
		System.out.println("loadClassRecur");
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


	private List<String> loadClassNames() {
		System.out.println("loadClassNames");
		this.changeProgress("Loading class names", -1);

		List<Class<?>> classes = ClassFinder.find(this.reader.getInputFolder());
		List<String> cs = new ArrayList<>();

		for (Class<?> clazz : classes) {
			cs.add(clazz.getName());
		}

		this.changeProgress("Loading class names finished",
				Framework.LOADING_CLASS_NAMES);
		System.out.println(cs);
		return cs;
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

	
	
}
