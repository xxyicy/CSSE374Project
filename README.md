1. Design of the tool

MileStone 1:

![UML Diagram](/UML/milestone1_manually.png "UML Diagram")

Our project consists of three parts.

The first part is the modified ASM part that reads in all the class files and puts data into the internal representation of classes.(asm package)
The second part is both the internal representation of classes and the traversers of the visitor pattern. It stores all the information about classes and implements all the "accept" logic.(api and impl package)
The third part is the visitor of the visitor pattern, it holds a stringBuffer to parse all the class information by traversing through each part of the classes. (visitor.api and visitor.impl)



MileStone 2:

![UML Diagram](/UML/milestone2_manually.png "UML Diagram")

We added more logic into the ASM part(ClassMethodVisitor and ClassFieldVisitor) to read in information about assciation and uses.

We added more logic to parse the internal representation so the GV texture could have arrows representing uses and associations.

We also created a Utility class that stores all kinds of "Util" functions (i.e. function to get the last part of a name like java.lang.String -> String).

Overall, our project still follows the visitor pattern.


MileStone 3:

![UML Diagram](/UML/AppForSeuenceDiagram.png "UML Diagram")

We created one more data structure to maintain the information about Method calls, basically it's a set of one to many relation that means this method calls the other methods in its body.

We added more logic into the ASM part(ClassMethodVisitor) to read in information about Method calls. 

Also, we wrote a parser to parse the Method call relations into a text representation that can be accepted by SDEdit.

Overall, our project still follows the visitor pattern.



Principles applied:
1.Program to an interface, not an implementation : We have created interfaces for every class and  most of the objects are declared using names of interfaces.
2. Strive for loosely coupled designs between objects that interact : How we parse the internal representation follows the visitor pattern to achieve loose coupling.
3. Each method should be either a command or a query: Our design use all the methods which are either a command or a query
4. Util class created to increase cohesion.



2. Who-Did-What

MileStone 1:
Xinyu Xiao: uses ASM to read in Java code
			automated tests
			UML for project (manually created)


Tianjiao Mo: outputs the GraphViz textual representation of the UML
			 UML for lab 1-3 
			 UML for project (generated)
			 Readme File
			 
MileStone 2:
Xinyu Xiao: update Readme File
			UML for Abstract Factory PizzaStore
			UML for project
			automated tests

Tianjiao Mo:
			update Readme File
			logic to read in Uses and Association information.
			update internal representation of class.

MileStone 3:

Ashok Vardhan Raja: Store all the methodRelation information into model.
					finish transfers java code into internal representation.
                    Generate test cases to test if ASM can correctly read in method call information.

XinyuXiao: Write the parser to parse the method call information into a texture representation accepted by SDEdit.
           Manually draw the sequence diagram for our designed tool.
           Help design the parsing structure of ASM.

Tianjiao Mo: Create methodRelation data structure to store information needed in sequence diagram.
             Write the Driver(main method) for the designed tool.
             Manually draw the sequence diagram for shuffle.
             


3. How to use:

Step 1: Open a terminal window and navigate to the directory of this project.

Step 2: java -classpath ./bin:./lib/asm-all-5.0.4.jar app.App <Path-to-package>
<Path-to-package> : the path to the directory being read and parsed.
(step 2 will print out the parsed GV texture representation and also write it to output/output.txt under the project directory)

Step 3: dot -Tpng output/output.txt > <output-file-name>
please use “.png” as the suffix of the output file.


For MileStone 3:

Step1 : The same

Step2 : java -classpath ./bin:./lib/asm-all-5.0.4.jar app.AppForSequenceDiagram <Path-to-package> <Path-to-function>

Step3 : The output will be written to output/SequenceDiagramOutput.txt 
