package app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TMXXreader {
	private static final String INPUT_FOLDER = "Input-Folder";
	private static final String INPUT_CLASSES = "Input-Classes";
	private static final String OUTPUT_DIR = "Output-Directory";
	private static final String DOT_PATH = "Dot-Path";
	private static final String PHASES = "Phases";
	private static final String APP_TYPE = "App-Type";


	private String inputFolder;
	private List<String> inputClasses;
	private String outputDir;
	private String dotPath;
	private List<String> phases;
	private Map<String, String> attributeMap;
	private String appType;
	private BufferedReader reader;

	public TMXXreader(String path) throws FileNotFoundException {
		this.inputClasses = new ArrayList<String>();
		this.phases = new ArrayList<String>();
		this.attributeMap = new HashMap<>();
		this.reader = new BufferedReader(new FileReader(path));
	}

	public void readFile() throws IOException {
		String line;
		while ((line = reader.readLine()) != null) {
			String content = this.getContent(line);
			if (line.startsWith(TMXXreader.INPUT_FOLDER)) {
				this.inputFolder = content;
			} else if (line.startsWith(TMXXreader.APP_TYPE)) {
				this.appType = content;
			} else if (line.startsWith(TMXXreader.INPUT_CLASSES)) {
				this.splitAndAddClasses(content);
			} else if (line.startsWith(TMXXreader.OUTPUT_DIR)) {
				this.outputDir = content;
			} else if (line.startsWith(TMXXreader.PHASES)) {
				this.splitAndAddPhases(content);
			} else if (line.startsWith(TMXXreader.DOT_PATH)) {
				this.dotPath = content;
			} else {
				int index = line.indexOf(":");
				if (index < 0) {
					continue;
				}
				String header = line.substring(0, line.indexOf(":"));
				this.attributeMap.put(header, content);
			}
		}
	}

	private String getContent(String line) {
		int index = line.indexOf(":");
		return line.substring(index + 1).trim();
	}

	private void splitAndAddPhases(String content) {
		String[] arr = content.split(",");
		for (String s : arr) {
			this.phases.add(s);
		}
	}

	private void splitAndAddClasses(String content) {
		String[] arr = content.split(",");
		for (String s : arr) {
			this.inputClasses.add(s);
		}
	}

//	public Framework constructFramework() throws IOException {
//		// read the input file
//		this.readFile();
//
//		// create a framework based on read in information
//		Framework fw = new Framework(appType, inputFolder, outputDir, dotPath);
//		for (String s : this.inputClasses) {
//			fw.addInputClass(s);
//		}
//
//		for (String s : this.phases) {
//			fw.addPhase(s);
//		}
//
//		for (String s : this.attributeMap.keySet()) {
//			fw.addAttribute(s, this.attributeMap.get(s));
//		}
//
//		return fw;
//
//	}

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
	
	public String getInputFolder(){
		return this.inputFolder;
	}
	
	public String getOutputDir(){
		return this.outputDir;
	}
	
	public String getAppType(){
		return this.appType;
		
	}
	
	public Map<String, String> getAttrMap(){
		return this.attributeMap;
	}
	
	public List<String> getPhases(){
		return this.phases;
	}
	
	public List<String> getInputClasses(){
		return this.inputClasses;
	}
	
	public String getDotPath(){
		return this.dotPath;
	}

}
