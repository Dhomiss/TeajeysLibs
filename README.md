# TeajeysLibs
A set of tools that I created while working on some Java-based passion projects. The highlights and their functions are as follows:
## SimpleGraphics
Is a child class of a SunGraphics2D wrapper, the aptly named ​ SunGraphics2DWrapper, ​ whichallows me to override SunGraphics2D’s methods despite its being a final class.
SimpleGraphics is full of customized implementations of Java’s default 2D drawing methods,so that I can control drawing operations of the graphics component of my programs on myown terms.
## TJSketch
A window object that implements SimpleGraphics for drawing. Intended to be used formaking graphical art, like ​ Processing​ . TJSketch handles its own frame rate along withkeyboard and mouse input.
## Complex & Quaternion
Complex numbers and quaternions are two dimensional and four dimensional numbers,respectively. A complex number is two dimensional, basically, because it’s made up of twonumbers; a quaternion, four numbers. They are very useful in graphics programming. Acomplex number can define a position on a flat plane, such as on a computer screen. Aquaternion can be used to define a position in three or four dimensional space, or a rotationin three dimensional space!
## Vector2D & Vector3D
These vector implementations are what I was using before complex numbers andquaternions. They provide the same results, but I find vectors less elegant 😊
## RoughOpenSimplexNoise
A copy of Kurt Spencers OpenSimplexNoise Java implementation, with the sumOctave()function that I added in, for generating noise with extra detail.
## TJNoise
An extension for RoughOpenSimplexNoise, which adds a few utility functions for making theprocess of generating noise a little easier to think about.
## ClassMap
I wanted a collection implementation for storing objects in a game, in which I could iteratethrough every object; or, only objects of a particular type along with the objects of itssubtypes. This was my best attempt at that implementation. For me, it was a formativeexercise in the use of and understanding generics.
Objects are stored in an Arraylist inside a HashMap which uses the object class as a key. Theobjects are then also added recursively to arraylists that correspond with their supertypes,all the way up to the native Object type. This then makes it quite easy to loop through allobjects in the ‘ClassMap’ that partake of a given type.
## FinalClassWrapGenerator
The class I set up for creating SunGraphics2DWrapper. It uses the Java Reflection API toexamine a given final class, and from it creates a new .java file that describes an extendablewrapper of the given class, ready to be compiled.
## TreeNode
The design which preceded the ClassMap, using a recursive tree structure to store objects bytheir type hierarchically.
## TypeTree
The first design exploring the idea of storing objects by their type, which preceded the TreeNode.
