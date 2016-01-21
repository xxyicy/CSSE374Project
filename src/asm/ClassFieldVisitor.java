package asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;
import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;
import api.IClass;
import api.IField;
import api.IModel;
import impl.Field;
import impl.Relation;

public class ClassFieldVisitor extends ClassVisitor {
	private IClass c;
	private IModel m;

	public ClassFieldVisitor(int api, IClass c, IModel m) {
		super(api);
		this.c = c;
		this.m = m;
	}

	public ClassFieldVisitor(int api, ClassVisitor decorated, IClass c, IModel m) {
		super(api, decorated);
		this.c = c;
		this.m = m;
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
//		System.out.println("Class :"+this.c.getName()+" Start visiting fields");
		FieldVisitor toDecorate = super.visitField(access, name, desc,
				signature, value);

		
		
		String type = Type.getType(desc).getClassName();
		String acc;

		if ((access & Opcodes.ACC_PRIVATE) == Opcodes.ACC_PRIVATE) {
			acc = "-";
		} else if ((access & Opcodes.ACC_PUBLIC) == Opcodes.ACC_PUBLIC) {
			acc = "+";
		} else if ((access & Opcodes.ACC_PROTECTED) == Opcodes.ACC_PROTECTED) {
			acc = "#";
		} else {
			acc = "";
		}
		
		boolean isStatic = (access & Opcodes.ACC_STATIC) == Opcodes.ACC_STATIC;
		
	
		
		if(  isStatic &&type.equals("-") && this.c.getName().equals(type) ){
			this.c.getDeclaration().orWithCode(4);
		}
		
		IField f = new Field(name, type, acc);
		f.setStatic(isStatic);
		
		this.c.addField(f);
		if (signature != null && signature.contains("<")
				&& signature.contains(">")) {

			String result = signature.substring(signature.lastIndexOf('<') + 2,
					signature.indexOf('>') - 1);
			// this.c.addAssociation(result);
			
		
			this.m.addRelation(new Relation(this.c.getName(), result,
					"association"));
		} else {
			// this.c.addAssociation(type);
			
			this.m.addRelation(new Relation(this.c.getName(), type,
					"association"));
		}
		
		return toDecorate;
	};
}
