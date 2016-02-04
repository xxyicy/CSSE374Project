package problem;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class EncryptOutput extends FilterOutputStream {
	IEncryption encrypt;

	public EncryptOutput(OutputStream out, IEncryption encrypt) {
		super(out);
		// TODO Auto-generated constructor stub
		this.encrypt = encrypt;
	}

	public void write(int b) throws IOException {
		// TODO Auto-generated method stub
		
		super.write(encrypt.encrypt((char) b));

	}

	public void write(byte[] b, int off, int len) throws IOException {
		// TODO Auto-generated method stub
		super.write(b, off, len);
		for (int i = off; i < off + len; ++i) {
			b[i] = (byte) encrypt.encrypt((char) b[i]);
		}
	}

}
