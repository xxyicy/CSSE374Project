package forTest;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;
import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;


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
		// System.out.println("Class :"+this.c.getName()+" Start visiting fields");
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

		boolean isStatic = (access & Opcodes.ACC_STATIC) != 0;

		// String className = this.c.getName().replaceAll("/", ".");

		// if( isStatic && acc.equals("-") && className.equals(type) ){
		// this.c.getDeclaration().orWithCode(4);
		// }

		IField f = new Field(name, type, acc,this.c.getName());
		f.setStatic(isStatic);

		this.c.addField(f);
		if (signature != null && signature.contains("<")
				&& signature.contains(">")) {

			String result;
			if (signature.contains("java/util")) {
				result = signature.substring(signature.indexOf('<') + 2,
						signature.lastIndexOf('>') - 1);
			}
			else{
				result = signature.substring(signature.indexOf('<') + 1, signature.lastIndexOf('>') );
			}
			// this.c.addAssociation(result);
			f.setInnerType(result);

		
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
