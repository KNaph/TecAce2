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
Equals is a method used to compare comparable objects. It indicates if one object is "equal to" this object and returns a boolean if they are equal or not. A simple 
Example:

```Java
String str1 = new String("Are they equal?");
String str2 = new String("Are they equal?");
System.out.println(str1.equals(str2));
```
#### The result would print "true"

### hashCode:
hashCode is a method that returns a hash code value, a 32-bit signed integer. This method must consistently return the same value when ran on the same code.
Example:
```Java
String str1 = new String("Generate hash code");
System.out.println(str1.hashCode());
```
#### The result would print 2130623732

### Clone:
Clone is a method that creates an exact copy of an object, creating a new instance of the object and initializing all of its fields with the same exact values as the original object. 
For an object to be cloneable it must implement Cloneable and have a public clone() method that throws CloneNotSupportedException and calls super.clone().

### toString:
toString is a method that returns a string that represents the object in text. For primitives the toString method works fine and will generally give an appropriate String representation of the object. For user defined classes and objects you must Override the toString method in order to gain any intelligible data.
```Java
class ToStringExample {
  private int x;
  private int y;
  
  public ToStringExample(int theX, int theY) {
    x = theX;
    y = theY;
  }

  @Override
  public String toString() {
    return "X = " + x + ", Y = " + y;
  }
}
```
#### If you were to NOT override the toString method you would get ToStringExample@XXXXXXX where XXXXXXX represents the hashCode.
#### If you instantiate a new ToStringExample(5, 9) and called it's toString you would be returned with "X = 5, Y = 9"

### Comparable:
Comparable is an interface used to compare objects of the same class. You can use this interface to sort a list of things by logical order. For example sorting a list of movies by their year of release. This method will return either a negative, zero, positive number. A negative means this object is less than the specified object, zero means it is equal to, and a positive means it is greater than the specified object.

```Java
String str1 = new String("How comparable?");
String str2 = new String("hello");

System.out.println(str1.compareTo(str2));
```
#### This would print "32" meaning that str1 is greater than the specified object (str2)

### Comparator:
Comparator is an interface to be used in a separate class for comparing objects. If we want to compare two objects by attributes of different objects then we can use comparator. 

For example if we wanted to compare movies by either rating or year we can implement Comparator classes to sort by each.

### Iterator:
An iterator is a method in which you can cycle through all of the objects in a collection. 
Given a collection, you can use a while loop in conjunction with the iterator method hasNext() to iterate through all of the objects in the collection.

```Java
ArrayList al = new ArrayList();
al.add("A");
al.add("B");
al.add("C");

Iterator itr = al.iterator();
      
while(itr.hasNext()) {
  Object element = itr.next();
  System.out.print(element + " ");
}
```
#### This while loop will check if there are still elements in the collection, if true it will get the element and print it, this will continue until there are no longer anymore elements left in the collection.

#### The resulting print wouldl be A B C

### Generics:
Generics are used to create classes and methods that are flexible and capable of handling any type appropriately.
```Java
for(E element : inputArray) {
  System.out.printf("%s ", element);
}
```
#### This for enhanced loop is capable of accepting any type of of primitive given to it. It doesn't matter if inputArray contains Integers, Doubles, or Characters, it would be able to print them all appropriately.

### Collections:
Collections are a framework in Java that allow users to store and manipulate a group of objects. These collections allow you to sort, search, insert, remove, or modify objects stored inside of these collections. They are essentially a group of objects.

```Java
ArrayList al = new ArrayList();
al.add("A");
al.add("B");
al.add("C");
al.get(1);
al.remove(2);
```
#### An ArrayList is an example of a collection, I am able to to add, search for the object at a given index, and remove an object at a given index.
