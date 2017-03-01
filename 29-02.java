import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {
    public static Factory[] factories;
    public static LinkedList<Troop> troops;
    public static String nextMove;
    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int factoryCount = in.nextInt(); // the number of factories
        
        factories = new Factory[factoryCount];
        troops = new LinkedList<>();
        
        for (int i=0; i<factories.length; i++) factories[i]=new Factory();
        
        int linkCount = in.nextInt(); // the number of links between factories
        for (int i = 0; i < linkCount; i++) {
            int factory1 = in.nextInt();
            int factory2 = in.nextInt();
            int distance = in.nextInt();
            
            factories[factory1].connections.add(new Integer[]{factory2, distance});    //initialize factory-connections
            factories[factory2].connections.add(new Integer[]{factory1, distance});
        }

        // game loop
        while (true) {
            int entityCount = in.nextInt(); // the number of entities (e.g. factories and troops)
            for (int i = 0; i < entityCount; i++) {
                int entityId = in.nextInt();
                String entityType = in.next();
                int arg1 = in.nextInt();
                int arg2 = in.nextInt();
                int arg3 = in.nextInt();
                int arg4 = in.nextInt();
                int arg5 = in.nextInt();
                
                
               if (entityType.equals("FACTORY")){
                   factories[entityId].init(arg1, arg2, arg3);
               } else {
                    troops.add (new Troop(entityId, arg1, arg2, arg3, arg4, arg5));
               }
            }
            
             diagnostics();  
             
             if (underAttack()==-1) {
                nextMove=findTarget();
             }

             else System.out.println(nextMove);
        }
    }
    
    public static void diagnostics(){
         
           
             for (int j=0; j<factories.length; j++) 
                {System.err.println("Factory "+j+":\n"+ factories[j]);}  //print out Factories
            
             System.err.println("Troops: ");
             for (Troop t:troops)System.err.println(t);    //print out Troops
             
             troops = new LinkedList<Troop>();
    }
    
    public static int underAttack(){
        return -1;
    }
    
    public static String findTarget (){
        String output="";
                          
        for (int i=0; i<factories.length; i++) {
         
          if (factories[i].owner==1)   {                                                 //wenn factory mir gehört
         
            for (int k=0; k<factories[i].connections.size(); k++) {                           //gehe connections durch
         
                Factory currentFac=factories[factories[i].connections.get(k)[0]];                //currentFac ist aktuelle connection
         
                if (currentFac.owner==0||currentFac.owner==-1){                                 //wenn currentFac nicht mir gehört
         
                    if (currentFac.nrCyborgs < factories[i].nrCyborgs) {                             //und ich genug Cyborgs zum einnehmen habe
         
                        output+="MOVE "+ i+" "+factories[i].connections.get(k)[0]+" "+(currentFac.nrCyborgs+1)+";";
                                                                                                         //schicke Anzahl Cyborgs der Zielfabrik plus 1
                    }
                }
            }
              
          }
            
        }
       if (output.length()>0)output=output.substring(0,output.length()-1); 
       else output="WAIT";
       return output; 
    }
    
}

class Factory {
    public LinkedList<Integer[]> connections = new LinkedList<Integer[]>();
    public int owner, nrCyborgs, production;

    
    public void init (int owner, int nrCyborgs, int production){
        this.owner=owner; 
        this.nrCyborgs=nrCyborgs;
        this.production=production;
    }
    
    public String toString (){
        String output="";
        
        for (Integer[] k:connections) output+=Arrays.toString(k)+"\n";
        output += owner + " "+nrCyborgs+" "+production+"\n";
        return output;
    }
    
    
    
}

class Troop {
    public int owner, id, factoryFrom, factoryTo, nrCyborgs, timeToArrival;
    
    public Troop (int id, int owner, int factoryFrom, int factoryTo, int nrCyborgs, int timeToArrival){
        this.id=id;
        this.factoryFrom=factoryFrom;
        this.factoryTo=factoryTo;
        this.nrCyborgs=nrCyborgs;
        this.timeToArrival=timeToArrival;
        this.owner=owner;
    }
    
    public String toString(){
        return "ID: "+id+" Owner: "+owner+" From: "+factoryFrom+" To: "+factoryTo+" NrCyborgs: "+nrCyborgs+" Arrival: "+timeToArrival;
        
    }
    
}