import java.io.*;  
import java.util.*; 
class Serial{  
 public static void main(String args[]){  
  try{  
  Scanner sc = new Scanner(System.in);   
  int i=0;
  while(i==0) {
  System.out.println("1.write obj or 2.exit");
  int input=sc.nextInt();
  
  if(input==1) {
  System.out.println("Enter id and name");
  int id=sc.nextInt();
  String name=sc.nextLine();
  
  FileOutputStream fout=new FileOutputStream("f.txt");  
  ObjectOutputStream out=new ObjectOutputStream(fout);  
  out.writeObject(new Employee(id,name));  
  out.flush();  
  out.close();  
  System.out.println("object has been serialized");
  }
 
  
  else {
	  i=1;
  }
  }
  }catch(Exception e){System.out.println(e);}  
 }  
}  