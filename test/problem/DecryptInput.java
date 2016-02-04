package problem;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DecryptInput extends FilterInputStream{
    IDecryption decrypt;
	protected DecryptInput(InputStream in,IDecryption decrypt) {
		super(in);
		// TODO Auto-generated constructor stub
		this.decrypt = decrypt;
	}
	public int read() throws IOException {
		// TODO Auto-generated method stub
		 int  ch =super.read();
		 if(ch==-1)
			 return ch;
		return  decrypt.decrypt((char) ch);
		 
	}

	public int read(byte[] b, int off, int len) throws IOException {
		// TODO Auto-generated method stub
		int total =  super.read(b, off, len);
		for(int i = off;i<off+total;++i){
			b[i] =(byte)decrypt.decrypt((char) b[i]);
		}
		return total;
	}

}
