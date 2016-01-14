package problem;

public class MpegEncoder implements IReceiver {
	public static final char MASK = 0x01;
	public static final int THRESHOLD = 1024;
	private StringBuffer buffer;
	
	public MpegEncoder() {
		buffer = new StringBuffer();
	}

	@Override
	public void received(Object source, Data d) {
		char[] content = d.getContent();
		Util.transform(MASK, content);
		buffer.append(content);
		
		if(DataLine.TOTAL_BYTES > THRESHOLD) {
			DataLine s = (DataLine)source;
			Data transformed = new Data(MASK, content);
			for(IReceiver r : s.getReceivers()) {
				r.received(this, transformed);
			}
		}
	}
}
