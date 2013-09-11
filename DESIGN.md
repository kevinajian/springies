PhysicalObject Package
======================

all instance variables private

Vector Class
==============

instance vars
double x, double y
methods
  //static method sums a list of Vector 

  //helper method that sums two vectors
  public addVector(Vector x)
    
Mass (Fixed)
==============
instance vars
  Vector acceleration vector
methods
  //returns the vector associated with the position
  public Vector getPosition x y

Moving Mass extends Mass
=========================
instance vars
  double mass
methods
  //returns the vector associated with the position
  Vector getPosition x y

  //updates velocity based on force
  void applyForce(Vector force)
  
//updates position based on current position and velocity
  void updatePosition() 

  get/set velocity, acceleration

Spring (two masses)
===================
instance vars
  Reference to two masses on either end
  double restLength
  double currentLength
  double springyness constant (default 1)
methods
  //get start / end vectors from the mass’ x,y
double getCurrentLength()

//updates position based on current position and velocity
  void updatePosition();

Muscle extends Spring
=====================
instance vars
  double amplitude
methods
  @override 
void update(change in time) considers forces + own contract / expand
contract / expand 

World Package
==============

World
=====
instance vars
  double frequency
methods
  update velocity

Possible Base Force Class


Gravity 
=========
direction (angle), magnitude
//returns force to be applied
public Vector getForce()

Viscosity 
=========
magnitude

//returns force to be applied based on Mass
public getForce(Mass)

Center of mass 
================
magnitude, exponent

//returns force to be applied based on Mass
public getForce(Mass)

Base Wall repulsion 
====================
id, magnitude, exponent

Left,Right,Top,Bottom extends Base Wall repulsion
===========================================
  
//returns force to be applied based on Mass
getForce(Mass)


