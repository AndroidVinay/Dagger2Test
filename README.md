<b>a) What does Dagger do? Explain in words that an high-school Java programmer can understand.</b>

Dagger is little design pattern.It use to simplifie complexcity of code with structured data. it make code lossley coupled which help to testing the code. 
Dagger has uses following annotations:

	1) @Module and @Provides: define classes and methods which provide dependencies
	2) @inject: request dependencies. Can be used on a constructor, a field, or a method
	3) @Component: enable selected modules and used for performing dependency injection
	4) @Singleton: Single instance of this provided object is created and shared.

	
	
<b> b) Why do we need DI? </b>

	Dependancy injection is a software design pattern that implements object oriented programming for resolving dependencies. Dependency injection in application allow us to have Testable classes, Re-usable and interchangeable Components.
	
  
	<b> without DI </b>
  
  class CodffeeMaker{
  
	private final Heater heater;
	private final Pump pump
  
	        coffeMaker()  {
	            this.heate = new ElectricHeater();
	             this.pump = new  Thermosiphon(heater);
	        }
          
	  coffee makeCoffee(){/* _ */}
    
  }

 class CoffeeMain{
 
	      public static void main(String[] args){
	      Coffee coffee = new  CoffeeMaker().makeCoffee();
        
        }
  }



<b>With Manual Di</b>

class CoffeMaker{

	private final Heater heater;
	private final Pump pump;
  
	CoffeeMaker(Heater heater,Pump pump){
  
	      this.heater = checkNotNull(heater);
	      this.pump = checkNOtNull(pump);
        
	}
  
	Coffee makeCoffee(){/* _ */}
  
}

	class CoffeMain{
  
	    public static void main(String[] args){
      
		      Heater heater = new ElectricHeater();
		      Pump pump = new Thermosiphon(heater);
		      Coffee coffee = new  CoffeeMaker(heater,pump).makeCoffee();
          
	    }
  }


<b> c)How is Dagger implemented? </b>

	- We need to identify the dependent objects and its dependencies.
	- Create a class with the @Module annotation, using the @Provides annotation for every method that returns a dependency.
	- Request dependencies in your dependent objects using the @Inject annotation.
	- Create an interface using the @Component annotation and add the classes with the @Module annotation created in the second step.
	- Create an object of the @Component interface to instantiate the dependent object with its dependencies.
