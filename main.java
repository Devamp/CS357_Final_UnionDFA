import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.*;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Scanner;

public class main {

    public static void main(String[] args) {

        ArrayList<String> alphabetOne = new ArrayList<String>();
        ArrayList<StateDFA> D1states = new ArrayList<StateDFA>();
        ArrayList<Transition> D1Transitions = new ArrayList<Transition>();


        String line;
        String temp;

        try{
            File myFile = new File("input4.txt");
            
            Scanner sc = new Scanner(myFile);

            while (sc.hasNextLine()){
                temp = sc.nextLine();

                while (temp.equalsIgnoreCase("alphabet:")){
                    line = sc.nextLine();
                    if (line.equalsIgnoreCase("states:")) {
                        temp = line;
                        break;
                    }
                    alphabetOne.add(line);
                   /// System.out.println(line);
                    
                }

                while (temp.equalsIgnoreCase("states:")){
                    line = sc.nextLine();
                    if (line.equalsIgnoreCase("initial_state:")) {
                        temp = line;
                        break;
                    }
                
                    D1states.add(new StateDFA(line, false, false));
                   
                }

                
                while (temp.equalsIgnoreCase("initial_state:")){
                    line = sc.nextLine();
                    if (line.equalsIgnoreCase("accepting_states:")) {
                        temp = line;
                        break;
                    }
                
                    int counter = 0;
                    for(StateDFA state : D1states) {
                        if (state.stateID.equals(line)){
                            StateDFA startStateSet = new StateDFA(line, true, false); 
                            D1states.set(counter, startStateSet); // replace old state with start state values
                            break;
                        }
                        counter++;
                    }
                    
                }

                while (temp.equalsIgnoreCase("accepting_states:")){
                    line = sc.nextLine();
                    if (line.equalsIgnoreCase("transitions:")) {
                        temp = line;
                        break;
                    }

                    int counter = 0;
                    for(StateDFA state : D1states) {
                        if (state.stateID.equals(line)){
                            if (state.isStart != true) {
                                StateDFA acceptSet = new StateDFA(line, false, true); 
                                D1states.set(counter, acceptSet);
                            }
                            else {
                                StateDFA acceptSet = new StateDFA(line, true, true); 
                                D1states.set(counter, acceptSet); 
                            }
                        }
                        counter++;
                    }
                    
                }
            
                while(sc.hasNextLine()) {
                    line = sc.nextLine();
                    String[] trParts = line.split(" ", 3);
                    String from = trParts[0];
                    String on = trParts[1];
                    String to = trParts[2];

                    Transition tr = new Transition(from, on, to);
                    temp = line;

                    D1Transitions.add(tr);
                
                }
            }
        
            sc.close();
            
        } catch (FileNotFoundException e){
            System.out.println("File opening Error");
        } catch (IOException e ){
            System.out.println("File IO");
        }

        ArrayList<String> alphabetTwo = new ArrayList<String>();
        ArrayList<StateDFA> D2states = new ArrayList<StateDFA>();
        ArrayList<Transition> D2Transitions = new ArrayList<Transition>();

        
        String line2;
        String temp2;

        try{
            File myFile = new File("input3.txt");
            
            Scanner sc = new Scanner(myFile);

            while (sc.hasNextLine()){
                temp = sc.nextLine();

                while (temp.equalsIgnoreCase("alphabet:")){
                    line = sc.nextLine();
                    if (line.equalsIgnoreCase("states:")) {
                        temp = line;
                        break;
                    }
                    alphabetTwo.add(line);
                    
                }

                while (temp.equalsIgnoreCase("states:")){
                    line = sc.nextLine();
                    if (line.equalsIgnoreCase("initial_state:")) {
                        temp = line;
                        break;
                    }
                
                    D2states.add(new StateDFA(line, false, false));
                }

                
                while (temp.equalsIgnoreCase("initial_state:")){
                    line = sc.nextLine();
                    if (line.equalsIgnoreCase("accepting_states:")) {
                        temp = line;
                        break;
                    }
                    int counter = 0;
                    for(StateDFA state : D2states) {
                        if (state.stateID.equals(line)){
                            StateDFA startStateSet = new StateDFA(line, true, false); 
                            D2states.set(counter, startStateSet);
                            
                        }
                        counter++;
                    }
                    
                }

                while (temp.equalsIgnoreCase("accepting_states:")){
                     line = sc.nextLine();
                    if (line.equalsIgnoreCase("transitions:")) {
                        temp = line;
                        break;
                    }
                    
                   int counter = 0;
                   for(StateDFA state : D1states) {
                       if (state.stateID.equals(line)){
                           if (state.isStart != true) {
                               StateDFA acceptSet = new StateDFA(line, false, true); 
                               D1states.set(counter, acceptSet);
                           }
                           else {
                               StateDFA acceptSet = new StateDFA(line, true, true); 
                               D1states.set(counter, acceptSet); 
                           }
                       }
                       counter++;
                }

                sc.nextLine();
                while(sc.hasNextLine()) {
                    line = sc.nextLine();
                    String[] trParts = line.split(" ", 3);
                    String from = trParts[0];
                    String on = trParts[1];
                    String to = trParts[2];

                    Transition tr = new Transition(from, on, to);
                    temp = line;
                    D2Transitions.add(tr);        
                }
            }
        }
            sc.close();

        } catch (FileNotFoundException e){
            System.out.println("File opening Error");
        } catch (IOException e ){
            System.out.println("File IO");
        }

        ArrayList<StateDFA> unionStates = new ArrayList<StateDFA>();

        for(int q = 0; q < D1states.size(); q++){ // loop q
            for (int r = 0; r < D2states.size(); r++) { // loop r
            
                String unionName;
                boolean unionStart = false;
                boolean unionAccept = false;

                unionName = D1states.get(q).stateID + D2states.get(r).stateID; // get UnionName
            
                if (D1states.get(q).isStart && D2states.get(r).isStart) {
                    unionStart = true;
                }

                if (D1states.get(q).isAccept || D2states.get(r).isAccept) {
                    unionAccept = true;
                }
                
                unionStates.add(new StateDFA(unionName, unionStart, unionAccept));
                
            }

            
        }


        ArrayList<Transition> unionTransitions = new ArrayList<Transition>();
        for (int qt = 0; qt < D1Transitions.size(); qt ++) {
            for (int rt = 0; rt < D2Transitions.size(); rt ++) {
                boolean matching = false;
                if (D1Transitions.get(qt).alpha.equals(D2Transitions.get(rt).alpha)) {
                    matching = true;
                }

                if (matching) {
                    String unionSrc = D1Transitions.get(qt).source + D2Transitions.get(rt).source;
                    String unionDest = D1Transitions.get(qt).dest + D2Transitions.get(rt).dest;

                    unionTransitions.add(new Transition(unionSrc, D1Transitions.get(qt).alpha, unionDest));
                }
            }
        }  

        // write to output file
        try {
            File myFile = new File("unionDFA.txt");
            FileWriter writer = new FileWriter(myFile);


            writer.write("alphabet:" + "\n");
            for(String x : alphabetOne){
                writer.write(x + "\n");
            }
            
            writer.write("states:" + "\n");
            for(StateDFA x : unionStates){
                writer.write(x.stateID + "\n");
            }

            writer.write("initial_state:" + "\n");
            for(StateDFA x : unionStates){
                if(x.isStart){
                    writer.write(x.stateID + "\n");
                }
            }

            writer.write("accepting_states:" + "\n");
            for(StateDFA x : unionStates){
                if(x.isAccept){
                    writer.write(x.stateID + "\n");
                }
            }

            writer.write("transitions:" + "\n");
            for (Transition r : unionTransitions){
                writer.write(r.toString() + "\n");
            }     

           
            writer.close();
            System.out.println("Successfully created UnionDFA in output.txt");

          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
}





