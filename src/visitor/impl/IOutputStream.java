package visitor.impl;

import java.io.FilterOutputStream;
import java.io.OutputStream;

import api.IMethod;
import api.IModel;

public abstract class IOutputStream extends FilterOutputStream {
	public IOutputStream(OutputStream out) {
		super(out);
	}
	
	public void start(){}
	public void write(IMethod m){}
	public void write(IModel m){}
	public void end(){}
}
