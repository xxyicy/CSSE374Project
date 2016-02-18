package phase.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import observer.api.Notifier;
import observer.api.Observer;
import pattern.api.IDetector;
import phase.api.IPhase;
import Framework.Framework;
import Framework.Framework.ProgressBox;

import api.IModel;

public class PatternDetectionPhase implements IPhase, Notifier {

	
	private List<Observer> observers;
	private ProgressBox box;
	private Set<IDetector> detectors;
	private IModel model;
	
		

	
	
	
	public PatternDetectionPhase(IModel m, ProgressBox box,Set<IDetector> detectors){
		this.model = m;
		this.observers = new ArrayList<Observer>();
		this.box = box;
		this.detectors = detectors;

	}
	
	
	@Override
	public void execute() throws Exception {
		this.detectPattern();
	}

	
	
	private void detectPatterns() throws Exception {
		this.changeProgress("Detecting Patterns", -1);
		double currentProgress = this.box.getProgress();
		double totalProgress = Framework.PATTERN_DETECTION * 3 / 4;
		int size = this.detectors.size();
		for (IDetector d : this.detectors) {
			this.changeProgress("Detecting" + d.toString(),
					this.box.getProgress() + totalProgress / size);

			d.detect(model);

		}

		this.changeProgress("Pattern Detection Finished", currentProgress
				+ totalProgress);
	}

	
	private void detectPattern() throws Exception {
		this.changeProgress("PATTERN DETECTION", -1);
		
		int detectorCount = this.detectors.size();
		
		if (detectorCount == 0) {
			this.changeProgress("Finished Pattern Detection",
					this.box.getProgress() + Framework.PATTERN_DETECTION);
		}

		else {
			this.detectPatterns();
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
