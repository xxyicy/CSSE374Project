## Table of contents
1. [Milestone 1](#milestone-1)
2. [Milestone 2](#milestone-2)
3. [Milestone 3](#milestone-3)
4. [Milestone 4](#milestone-4)







## Milestone 1
### Design of the tool

![UML Diagram](/UML/milestone1_manually.png "UML Diagram")

Our project consists of three parts.

The first part is the modified ASM part that reads in all the class files and puts data into the internal representation of classes.(asm package)
The second part is both the internal representation of classes and the traversers of the visitor pattern. It stores all the information about classes and implements all the "accept" logic.(api and impl package)
The third part is the visitor of the visitor pattern, it holds a stringBuffer to parse all the class information by traversing through each part of the classes. (visitor.api and visitor.impl)

Principles applied:
1.Program to an interface, not an implementation : We have created interfaces for every class and  most of the objects are declared using names of interfaces.
2. Strive for loosely coupled designs between objects that interact : How we parse the internal representation follows the visitor pattern to achieve loose coupling.
3. Each method should be either a command or a query: Our design use all the methods which are either a command or a query
4. Util class created to increase cohesion.


### Who-Did-What

| *Name*         | *Who did what*                                                                                                        
|----------------|--------------------------------------------------------
| Xinyu Xiao     | create code using ASM to read in Java code 
|                                                                                           
| 			     | write automated tests              
|                                                                                            
| 				 | manually create UML for project  
|
|			     | update Readme File                                                                          
|
| Tianjiao Mo    | create code parsing GraphViz textual representation
|
|				 | auto generate UML for lab 1-3 & project
|
|				 | create Readme File
|			 


### How to use

#### Before start
Open a terminal window and navigate to the directory of this project.

#### Run java Code
```
java -classpath ./bin:./lib/asm-all-5.0.4.jar app.App <Path-to-package>

java -classpath ./bin:./lib/asm-all-5.0.4.jar app.App UML <Path-to-package>

```
<Path-to-package> : the path to the directory being read and parsed.
(this step will print out the parsed GV texture representation and also write it to output/output.txt under the project directory)

#### Generate UML diagram
```
dot -Tpng output/output.txt > <output-file-name>
```
please use “.png” as the suffix of the output file.







## Milestone 2
### Design of the tool

![UML Diagram](/UML/milestone2_manually.png "UML Diagram")

We added more logic into the ASM part(ClassMethodVisitor and ClassFieldVisitor) to read in information about assciation and uses.

We added more logic to parse the internal representation so the GV texture could have arrows representing uses and associations.

We also created a Utility class that stores all kinds of "Util" functions (i.e. function to get the last part of a name like java.lang.String -> String).

Overall, our project still follows the visitor pattern.


### Who-Did-What

| *Name*         | *Who did what*                                                                                                        
|----------------|--------------------------------------------------------
| Xinyu Xiao     | update Readme File 
|                                                                                           
| 			     | create UML for Abstract Factory PizzaStore & project                                                                                                      
|
|			     | automated tests                                                                          
|
| Tianjiao Mo    | update Readme File
|
|				 | create code to use ASM to get Uses and Association information.
|
|				 | update internal representation of class.

			
### How to use

#### Before start
Open a terminal window and navigate to the directory of this project.

#### Run java Code
```
java -classpath ./bin:./lib/asm-all-5.0.4.jar app.App UML <Path-to-package>
dot -Tpng output/output.txt > <output-file-name>
```
<Path-to-package> : the path to the directory being read and parsed.
(this step will print out the parsed GV texture representation and also write it to output/output.txt under the project directory)

#### Generate UML diagram
```
dot -T <output/output.txt > <output-file-name>
```
please use “.png” as the suffix of the output file.







## Milestone 3
### Design of the tool


![UML Diagram](/UML/milestone3_manually.png "UML Diagram")

We created one more data structure to maintain the information about Method calls, basically it's a set of one to many relation that means this method calls the other methods in its body.

We added more logic into the ASM part(ClassMethodVisitor) to read in information about Method calls. 

Also, we wrote a parser to parse the Method call relations into a text representation that can be accepted by SDEdit.

Overall, our project still follows the visitor pattern.

### Who-Did-What

| *Name*         	| *Who did what*                                                                                                        
|-------------------|----------------------------------------------------------------------------------------
| Xinyu Xiao     	| Write the code to parse to SDEdit texture representation 
|                                                                                           
| 			     	| Manually draw the sequence diagram for our designed tool.              
|                                                                                            
| 				 	| Help design the parsing structure of ASM.  
|                                                                         
| Tianjiao Mo    	| Create methodRelation data structure to store information needed in sequence diagram.
|
|				 	| Write the Driver(main method) for the designed tool.
|
|				 	| Manually draw the sequence diagram for shuffle.
|
| Ashok Vardhan Raja| Store all the methodRelation information into model.
|
|					| finish transfers java code into internal representation.
|
|					| Generate test cases to test if ASM can correctly read in method call information.

     
### How to use

#### Before start
Open a terminal window and navigate to the directory of this project.

#### Run java Code
```
java -classpath ./bin:./lib/asm-all-5.0.4.jar app.App SD <Path-to-function> <int:Depth>
```

#### Generate UML diagram
The output will be written to output/SequenceDiagramOutput.txt
 





## Milestone 4
### Design of the tool
We create one more int field called code in the Declaration class to represent different attributes of certain classes.
Also, we created one more patternCode class to store the code to determine certain patterns. (i.e. the singleton pattern
is 1111, see definitions for each digit in that class);

More logics to determine the attribute of class are added to both the ClassFieldVisitor and ClassMethodVisitor.

More logics in the parser are added to check if <<Singleton>> label should be added.





### Who-Did-What

| *Name*         	| *Who did what*                                                                                                        
|-------------------|----------------------------------------------------------------------------------------
| Xinyu Xiao     	| Updated the parse logic to draw the sequence diagram & readme file
|                                                                                           
| 			     	| Write the parse logic to check if a class is Singleton.           
|                                                                                            
| 				 	| Create sample test classes
|                                                                         
| Tianjiao Mo    	| Create patternCode class and update content in the Declaration class.
|
|				 	| Update logics in both ClassMethodVisitor and ClassFieldVisitor. 
|
|				 	| Updated UML diagrams for milestone 3
|
| Ashok Vardhan Raja| Write the test cases.
|
|					| Create UML for singleton class manually
|
|					| Updated test cases for Milestone 3


### How to use

#### Before start
Open a terminal window and navigate to the directory of this project.

#### Run java Code
```
java -classpath ./bin:./lib/asm-all-5.0.4.jar app.App <Path-to-package>

java -classpath ./bin:./lib/asm-all-5.0.4.jar app.App UML <Path-to-package>

```
<Path-to-package> : the path to the directory being read and parsed.
(this step will print out the parsed GV texture representation and also write it to output/output.txt under the project directory)



