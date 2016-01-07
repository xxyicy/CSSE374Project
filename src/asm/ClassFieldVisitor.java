package asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

import api.IClass;
import api.IField;
import impl.Field;

public class ClassFieldVisitor extends ClassVisitor {
	private IClass c;

	public ClassFieldVisitor(int api, IClass c) {
		super(api);
		this.c = c;
	}

	public ClassFieldVisitor(int api, ClassVisitor decorated, IClass c) {
		super(api, decorated);
		this.c = c;
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc,
				signature, value);

		String type = Type.getType(desc).getClassName();
		String acc;

		if ((access & Opcodes.ACC_PRIVATE) != 0) {
			acc = "-";
		} else if ((access & Opcodes.ACC_PUBLIC) != 0) {
			acc = "+";
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			acc = "#";
		} else {
			acc = "";
		}

		IField f = new Field(name, type, acc);
		this.c.addField(f);
		if (signature != null && signature.contains("<") && signature.contains(">")) {
		
			 String result = signature
			 .substring(signature.lastIndexOf('<')+2, signature.indexOf('>')-1);
			 this.c.addAssociation(result);
		} else {
			this.c.addAssociation(type);
		}
		return toDecorate;
	};
}
