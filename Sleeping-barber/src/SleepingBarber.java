import java.util.concurrent.*;

public class SleepingBarber extends Thread {
  
    public static Semaphore customers = new Semaphore(0);
    public static Semaphore barber = new Semaphore(0);
    public static Semaphore accessSeats = new Semaphore(1);
    public static int CHAIRS = 5;
    public static int freeSeats = CHAIRS;

class Customer extends Thread {
  int id;
  boolean notCut=true;
  public Customer(int i) {
    id = i;
  }

  public void run() {   
    while (notCut) { 
      try {
      accessSeats.acquire();  
      if (freeSeats > 0) {  
        System.out.println("Customer "+ this.id +" sat down.");
        freeSeats--;  
        customers.release(); 
        accessSeats.release();  
        try {
	    barber.acquire();  
        notCut = false;  
        this.get_haircut();  
        } catch (InterruptedException ex) {}
      }   
      else  { 
        System.out.println("No free seats. Customer " + this.id + " left shop.");
        accessSeats.release();  
        notCut=false;
      }
     }
      catch (Exception ex) {}
    }
  }
  
  public void get_haircut(){
    System.out.println("Customer " +this.id + " getting hair cut");
    try {
    sleep(5000);
    } catch (Exception ex) {}
  }

}

class Barber extends Thread {
  public void run() {
    while(true) {  
      try {
      customers.acquire(); 
      accessSeats.release(); 
      freeSeats++; 
      barber.release();
      accessSeats.release(); 
      this.do_haircut();  
    } catch (Exception ex) {}
    }
  } 
  public void do_haircut(){
    System.out.println("The barber is cutting hair");
    try {
      sleep(5000);
    } catch (Exception ex){ }
  }
}       

  public static void main(String args[]) {
    
    SleepingBarber barberShop = new SleepingBarber();  
    barberShop.start();  
  }

  public void run(){   
   Barber b= new Barber();  
   b.start();  
   for (int i=1; i<12; i++) {
     Customer c = new Customer(i);
     c.start();
     try {
       sleep(2000);
     } catch(Exception ex) {};
   }
  } 
}