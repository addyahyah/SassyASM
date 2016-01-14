# SassyLadiesUML


MILESTONE 1
Who did what:

We drew out the UML diagram on paper together first. Addison then implemented the UML diagram for the design on UMLet. We pair programmed the milestone. Lexi was the driver for the first half for structuring the basics by coding SassyField, SassyMethod, and SassyClass, as well as the visitors and the DesignParser. Once these were set up, Addison became the driver for the UMLDrawer class and the testing. 


![Alt text](https://github.com/addyahyah/SassyASM/blob/master/files/milestone1umlmanual.png
 "UML OF OUR DESIGN")


Description of Design: 
ClassDeclarationVisitor is the visitor class for visiting classes when they are declared. It sets the class name and superclass if applicable, as well as any interfaces it implements. 
ClassFieldVisitor is the visitor class for visiting fields. It sets the field’s name, privacy, and type, and adds it to the SassyClass’s field ArrayList (the SassyClass that was passed into the ClassFieldVisitor constructor).
ClassMethodVisitor is the visitor class for visiting methods. It sets the method name, privacy level, return type, and argument types. 

DesignParser is the main driver class where all visitors are created, adds a new class to the parser, and tells the UML drawer to draw the diagram.

SassyClass is the concrete implementation of a class. Six fields are recorded in this class, className, list of class methods, list of class it implements, classExtends, its fields and a boolean value recording whether the class is an interface or not. Getters and setters for these fields are also included.

SassyField is the concrete class to represent fields. Three fields are recorded in this class, fieldName, fieldType, and fieldAccess. Getter and setters for these fields are also included.
SassyMethod is the concrete class to record methods. Four fields are recorded in this class, methodName, methodReturnType, methodAccess and argTypes. Getter and setters for these fields are also included.

UMLDrawer parses the indicated classes and prints out the GraphViz code to the console. If this text is copied and then run with the executable (see instructions below), it will display the UML in a specified file. UMLDrawer contains addMethods, addFields, addClass, addExtensionArrows, and addInterfaceArrows methods to handle the different cases for parsing classes. 

MILESTONE 2 DESIGN:
We added the class SassyMethodVisitor that extends MethodVisitor from ASM so we can return the newly decorated MethodVisitor that contains the return types. We had to add a lot of parsing to handle the arguments of methods, using ';', '>', and '$' as flags for when to remove and split. We added a parameter to all of our decorators that takes an ArrayList of the class names as Strings so that parsing return types becomes simple by asking if the given return type contains any of the classes. We added a method to draw the arrows that represent uses and association by adding ArrayLists in SassyClass to store these properties as they are discovered. The UMLDrawer then can acces each class's uses classes and draw the appropriate arrow. 

MILESTONE 2 
WHO DID WHAT
1. evaluate problem together and discuss possible ways to solve them

Addison drove for the first half:
She discovered the MethodVisitor class so she created our new class, SassyMethodVisitor that extends MethodVisitor. She overrode the visitMethodInsn method so she could add the ability to update a Class's uses arrayList. She then had to add this field, along with an ArrayList of associationClasses in SassyClass. 

Lexi drove for the second half:
She added an ArrayList of Strings of the classes to be passed into the decorators so that parsing the return types could be simplified by checking the return type string for a substring equal to a class in the ArrayList. She also handled parsing fields. This caused lots of difficulty when ArrayLists were present because the delimiters were off, so we added flags to indicate when to split. Then added the arrow methods to loop through the uses and associations and draw appropriate arrow type. 

To use the code:

Link the source files for the project you wish to run the code on if they are outside of the package problem.asm (For example: Lab 1-3). Then in the DesignParser class, edit the classes to contain a list of the classes you want to display the UML diagram for. Then run the DesignParser class. It will output text to the console. Copy this text into the text.dot file and save it. Then in the command line, run the dot executable with -Tpng text.dot -o output.png as parameters. Then open the output.png file to view the UML diagram.

MILESTONE 3 Design: 

![Alt text](https://github.com/addyahyah/SassyASM/blob/master/files/SassyASM_M2_manual.png
 "UML OF OUR DESIGN")

We made significant design changes for milestone 3 to fix our milestones 1 and 2. We used the visitor pattern and the decorator pattern. 

Addison:
Structured our new design pattern by incorporating both visitor pattern and decorator to make our lives much easier. 

Lexi:
Helped with the parsing logic for uses, association, and inheritance.

Derrick:
Created the UML for the project









