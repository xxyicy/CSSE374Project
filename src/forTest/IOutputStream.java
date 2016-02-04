package forTest;

import java.io.FilterOutputStream;
import java.io.OutputStream;

import api.IMethod;
import api.IModel;

public abstract class IOutputStream extends FilterOutputStream {
	public IOutputStream(OutputStream out) {
		super(out);
	}
	
	public void start(){
		throw new UnsupportedOperationException();
	}
	public void write(IMethod m){
		throw new UnsupportedOperationException();
	}
	public void write(IModel m){
		throw new UnsupportedOperationException();
	}
	public void end(){
		throw new UnsupportedOperationException();
	}
}
