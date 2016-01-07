1. Design of the tool

MileStone 1:
Design:
Our project consists of three parts.

The first part is the modified ASM part that reads in all the class files and puts data into the internal representation of classes.(asm package)
The second part is both the internal representation of classes and the traversers of the visitor pattern. It stores all the information about classes and implements all the "accept" logic.(api and impl package)
The third part is the visitor of the visitor pattern, it holds a stringBuffer to parse all the class information by traversing through each part of the classes. (visitor.api and visitor.impl)

Principles applied:
1.Program to an interface, not an implementation : We have created interfaces for every class and  most of the objects are declared using names of interfaces.
2. Strive for loosely coupled designs between objects that interact : How we parse the internal representation follows the visitor pattern to achieve loose coupling.
3. Each method should be either a command or a query: Our design use all the methods which are either a command or a query

MileStone 2:

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
3. How to use:

Step 1: Open a terminal window and navigate to the directory of this project.

Step 2: java -classpath ./bin:./lib/asm-all-5.0.4.jar app.App <Path-to-package>
<Path-to-package> : the path to the directory being read and parsed.
(step 2 will print out the parsed GV texture representation and also write it to output/output.txt under the project directory)

Step 3: dot -Tpng output/output.txt > <output-file-name>
please use “.png” as the suffix of the output file.
