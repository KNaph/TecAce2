# TecAce sample program
## This program has been designed using the provided program spec, has been designed with Abstract classes, and includes documentation for code inspection.

The required Java files can be found here:
https://github.com/KNaph/TecAce2/tree/master/app/src/main/java/kylephan/com/tecace2

The required JSON files can be found here:
https://github.com/KNaph/TecAce2/tree/master/app/src/main/assets

## Java programming questions:

### Design Patterns:
### Singleton: 
A singleton is a class that allows only one instance of that specific class. These classes implement static methods and variables to allow only one instantiation of the class.

### Observer:
The observer pattern is a one to many relationship between objects. When the subject object changes it notifies it's dependent observer objects and automatically updates them. 

### Definition and How to:
### Equals:
Equals is a method used to compare comparable objects. It indicates if one object is "equal to" this object and returns a boolean if they are equal or not.

Example:
String str1 = new String("Are they equal?");
String str2 = new String("Are they equal?");

System.out.println(str1.equals(str2));

#### The result would print "true"
