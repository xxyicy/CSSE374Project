1. Design of the tool

The first part is designed to read code from specified java files to parse them using asm
Since asm is designed in visitor pattern, we need to implement its `visit` methods for ClassDeclarations, ClassFields and ClassMethods.
It consists of the following packages:
* ragdoll.app
* ragdoll.util
* ragdoll.asm

The second part is designed to use our own visitor pattern to output the class structure in GraphViz format.
It consists of the following packages:
* ragdoll.code.api
* ragdoll.code.impl
* ragdoll.code.visitor.api
* ragdoll.code.visitor.impl

Overall, the project uses Decorator Pattern as well as two Visitor Patterns.

### Design Principles
The project follows the following design principles:
* *Identify the aspects of your application that vary and separate them from what stays the same.* According to the above discussion, we separated the read-in functionality from the write-out functionality.
* *Program to an interface, not an implementation.* As the UML diagram shows, we are programming to various interfaces that well represents the key components of the project, such as IVisitor, ITraverser, and IClassComponents.
* *Strive for loosely coupled designs between objects that interact*. Since we are using the Visitor Pattern which has the Message Coupling, we are achieving the loose coupling in our design.
* *Classes should be open for extension, but closed for modification.* The Decorator Pattern we are using allows us to wrap up any level of visitors without modifying the existing code.
* *Each method should be either a command or a query.* We tried our best to make methods that we implemented in the project to either be a Command Method (Performs an action but has no return value) or a Query Method (Returns data but has no side effects).

2. Who-Did-What

Xinyu Xiao: uses ASM to read in Java code
			automated tests
			UML for project (manually created)


Tianjiao Mo: outputs the GraphViz textual representation of the UML
			 UML for lab 1-3 
			 UML for project (generated)
			 Readme File

3. Instructions on Usage