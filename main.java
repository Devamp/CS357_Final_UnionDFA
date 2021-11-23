/*
 * @Author: Devam Patel and Harrison Winters
 * @Date: 11/22/2021 
 * 
 * This program takes in two distinct DFA as a .txt file and returns the union of those two DFAs in the
 * unionDFA.txt file. 
 * 
 * Read README file for more information.
 * 
 */

 // java imports used in this program
import java.io.FileNotFoundException;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
        public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        
        // Read in file 1 
        System.out.println("Enter file name for DFA 1: ");
        String file1 = input.nextLine();

        // Make sure its a .txt file
        while(!file1.endsWith(".txt")){
            System.out.println("Incorrect file format. Please enter a .txt file:");
            file1 = input.nextLine();
        }

        // Read in file 2
        System.out.println("Enter file name for DFA 2: ");
        String file2 = input.nextLine();

        // Make sure its a .txt file
        while(!file2.endsWith(".txt")){
            System.out.println("Incorrect file format. Please enter a .txt file:");
            file2 = input.nextLine();
        }

        // Make sure file 1 and file 2 are not the same files
        while (file2.equalsIgnoreCase(file1)){
            System.out.println("Files cannot be the same! Enter another file name:");
            file2 = input.nextLine();
        }

        // create ArrayLists to hold the alphabet, states, and transitions for DFA 1
        ArrayList<String> alphabetOne = new ArrayList<String>();
        ArrayList<StateDFA> D1states = new ArrayList<StateDFA>();
        ArrayList<Transition> D1Transitions = new ArrayList<Transition>();

        // temp variables used to help with File I/O
        String line;
        String temp;

        try{
            File myFile = new File(file1); // create File object with given file name
            Scanner sc = new Scanner(myFile); // scanner used to read the .txt files


            while (sc.hasNextLine()){ // read from the scanner until the end of file
                temp = sc.nextLine();

                while (temp.equalsIgnoreCase("alphabet:")){ // read in all of the alphabet symbols
                    line = sc.nextLine();
                    if (line.equalsIgnoreCase("states:")) { // break when reached states tag
                        temp = line;
                        break;
                    }
                    alphabetOne.add(line); // add symbols to the alphabet Array List for DFA 1
                }


                while (temp.equalsIgnoreCase("states:")){ // read in all of the states from DFA 1
                    line = sc.nextLine();
                    if (line.equalsIgnoreCase("initial_state:")) { // break when initial_state tag is reached
                        temp = line;
                        break;
                    }
                
                    D1states.add(new StateDFA(line, false, false)); // create new stateDFA object and add to Array List for DFA 1 states
                   
                }

                
                while (temp.equalsIgnoreCase("initial_state:")){ // read in all of the initial states for DFA 1
                    line = sc.nextLine();
                    if (line.equalsIgnoreCase("accepting_states:")) {
                        temp = line;
                        break;
                    }
                
                    int counter = 0;
                    boolean hasMatch = false;
                    for(StateDFA state : D1states) { // loop to all DFA 1 states to find initial state and change its boolean values
                        if (state.stateID.equals(line)){
                            StateDFA startStateSet = new StateDFA(line, true, false); // temp state to replace old state
                            D1states.set(counter, startStateSet); // replace old state with start state values
                            break;
                        }
                        counter++;
                    }

                    // Make sure initial state is part of Q given set of state
                    for (StateDFA x : D1states){
                        if(line.equalsIgnoreCase(x.stateID)){
                            hasMatch = true;
                        } 
                    }
                    if(!hasMatch) { // if initial state is not in set Q, return error and exit program
                        System.out.println("ERROR: Invalid INITIAL STATE in File 1");
                        System.exit(0);
                    }
                }

                while (temp.equalsIgnoreCase("accepting_states:")){ // read in all of the accepting states
                    line = sc.nextLine();
                    if (line.equalsIgnoreCase("transitions:")) {
                        temp = line;
                        break;
                    }

                    int counter = 0;
                    for(StateDFA state : D1states) { // loop DFA 1 states to update states to accepting boolean values
                        if (state.stateID.equals(line)){
                            if (state.isStart != true) { // make sure it is not an initial state
                                StateDFA acceptSet = new StateDFA(line, false, true); // temp state to replace
                                D1states.set(counter, acceptSet); // replace old state with new accepting values
                            }
                            else {
                                StateDFA acceptSet = new StateDFA(line, true, true); // temp state to replace
                                D1states.set(counter, acceptSet); // replace old state with new accepting values
                            }
                        }
                        counter++;
                    }
                    
                }
            
                while(sc.hasNextLine()) { // parse the transtions read in for DFA 1
                    line = sc.nextLine();
                    String[] trParts = line.split(" ", 3);
                    String from = trParts[0]; // source state
                    String on = trParts[1]; // transition symbol
                    String to = trParts[2]; // dest state

                    boolean hasMatch = false;
                    for(StateDFA state : D1states) { // loop through all DFA 1 states to make sure src states and in Q
                        if (state.stateID.equals(from)){
                            hasMatch = true;
                        }
                    }
                    if (!hasMatch) {
                        System.out.println("ERROR: Incorrect source state in transitions for File 1!");
                        System.exit(0);
                    }


                    hasMatch = false;
                    for(StateDFA state : D1states) {
                        if (state.stateID.equals(to)){ // loop through all DFA 1 states to make sure dest states are in Q
                            hasMatch = true;
                        }
                    }
                    if (!hasMatch) {
                        System.out.println("ERROR: Incorrect destination state in transitions for File 1!");
                        System.exit(0);
                    }

                    hasMatch = false;
                    for(String alpha: alphabetOne) { // make sure alphabet is in the set sigma for DFA 1
                        if (alpha.equals(on)){
                            hasMatch = true;
                        }
                    }
                    if (!hasMatch) {
                        System.out.println("ERROR: Incorrect transition symbol in transitions for File 1!");
                        System.exit(0);
                    }


                    Transition tr = new Transition(from, on, to); // create new transition using the values read in for DFA 1
                    temp = line;

                    D1Transitions.add(tr); // add it to the DFA 1 transitions ArrayList
                
                }
            }
        
            sc.close(); // once finished, close scanner.
            
        } catch (FileNotFoundException e){ // catch any FILE errors
            System.out.println("File opening Error");
        } 

        // ArrayLists to hold the alphabet, states, and transitions for DFA 2 
        ArrayList<String> alphabetTwo = new ArrayList<String>();
        ArrayList<StateDFA> D2states = new ArrayList<StateDFA>();
        ArrayList<Transition> D2Transitions = new ArrayList<Transition>();

        try{
            File myFile = new File(file2); // create file obj using file 2
            
            Scanner sc = new Scanner(myFile); // create scanner to read in the file

            while (sc.hasNextLine()){ // read file 2 to the end of file
                temp = sc.nextLine();

                while (temp.equalsIgnoreCase("alphabet:")){ // read in the alphabet of DFA 2
                    line = sc.nextLine();
                    if (line.equalsIgnoreCase("states:")) {
                        temp = line;
                        break;
                    }
                    alphabetTwo.add(line);
                    
                }

                while (temp.equalsIgnoreCase("states:")){ // read in the states of DFA 2
                    line = sc.nextLine();
                    if (line.equalsIgnoreCase("initial_state:")) {
                        temp = line;
                        break;
                    }
                
                    D2states.add(new StateDFA(line, false, false));
                }

                boolean hasMatch = false;
                while (temp.equalsIgnoreCase("initial_state:")){ // read in the initial state for DFA 2
                    line = sc.nextLine();
                    if (line.equalsIgnoreCase("accepting_states:")) {
                        temp = line;
                        break;
                    }
                    int counter = 0;
                    for(StateDFA state : D2states) { // set correct initial states in DFA 2 like we did for DFA 1
                        if (state.stateID.equals(line)){
                            StateDFA startStateSet = new StateDFA(line, true, false); 
                            D2states.set(counter, startStateSet);
                            
                        }
                        counter++;
                    }

                    // Make sure initial state is part of Q given set of state
                    for (StateDFA x : D2states){
                        if(line.equalsIgnoreCase(x.stateID)){
                            hasMatch = true;
                        } 
                    }
                    if(!hasMatch) {
                        System.out.println("ERROR: Invalid INITIAL STATE in File 2");
                        System.exit(0);
                    }
                    
                }

                while (temp.equalsIgnoreCase("accepting_states:")){ // read in the accepting states of DFA 2
                     line = sc.nextLine();
                    if (line.equalsIgnoreCase("transitions:")) {
                        temp = line;
                        break;
                    }
                    
                   int counter = 0;
                   for(StateDFA state : D2states) {
                       if (state.stateID.equals(line)){
                           if (state.isStart != true) { // set correct accepting states like we did for DFA 1
                               StateDFA acceptSet = new StateDFA(line, false, true); 
                               D2states.set(counter, acceptSet);
                           }
                           else {
                               StateDFA acceptSet = new StateDFA(line, true, true); 
                               D2states.set(counter, acceptSet); 
                           }
                       }
                       counter++;
                }

                sc.nextLine();
                while(sc.hasNextLine()) { // parse transitions for DFA 2
                    line = sc.nextLine(); 
                    String[] trParts = line.split(" ", 3);
                    String from = trParts[0];
                    String on = trParts[1];
                    String to = trParts[2];


                    hasMatch = false;
                    for(StateDFA state : D2states) { // make sure src state is in set Q for DFA 2
                        if (state.stateID.equals(from)){
                            hasMatch = true;
                        }
                    }
                    if (!hasMatch) {
                        System.out.println("ERROR: Incorrect source state in transitions for File 2!");
                        System.exit(0);
                    }


                    hasMatch = false;
                    for(StateDFA state : D2states) {
                        if (state.stateID.equals(to)){ // make sure dest state is in set Q for DFA 2
                            hasMatch = true;
                        }
                    }
                    if (!hasMatch) {
                        System.out.println("ERROR: Incorrect destination state in transitions for File 2!");
                        System.exit(0);
                    }

                    hasMatch = false;
                    for(String alpha: alphabetTwo) { // make sure alphabet is in sigma for DFA 2
                        if (alpha.equals(on)){
                            hasMatch = true;
                        }
                    }
                    if (!hasMatch) {
                        System.out.println("ERROR: Incorrect transition symbol in transitions for File 2!");
                        System.exit(0);
                    }

                    // create new transition object an add it to Array List of transitions for DFA 2
                    Transition tr = new Transition(from, on, to); 
                    temp = line;
                    D2Transitions.add(tr);        
                }
            }
        }
            sc.close(); // close scanner

        } catch (FileNotFoundException e){ // catch any FIle errors
            System.out.println("File opening Error");
        }

        
        // check to make sure alphabets of DFAs are the same
        if(!(alphabetOne.containsAll(alphabetTwo)) || !(alphabetTwo.containsAll(alphabetOne))){
            System.out.println("ERROR: Input alphabets are not the same!");
            System.exit(0);
        }
        
        // create Array List to hold the states of the unioned DFA
        ArrayList<StateDFA> unionStates = new ArrayList<StateDFA>();

        // loop through all possible combinations of states from the D1 and D2states Array Lists
        for(int q = 0; q < D1states.size(); q++){ // loop q
            for (int r = 0; r < D2states.size(); r++) { // loop r
                
                // attributes for new unioned states
                String unionName;
                boolean unionStart = false;
                boolean unionAccept = false;

                unionName = D1states.get(q).stateID + D2states.get(r).stateID; // get UnionName
            
                if (D1states.get(q).isStart && D2states.get(r).isStart) { // determine if its initial or not
                    unionStart = true;
                }

                if (D1states.get(q).isAccept || D2states.get(r).isAccept) { // determine if its accept or not
                    unionAccept = true;
                }
                
                unionStates.add(new StateDFA(unionName, unionStart, unionAccept)); // add to unionStates list
            }
        }

        // create Array List to hold the transitions of the unioned DFA
        ArrayList<Transition> unionTransitions = new ArrayList<Transition>();

        // again loop through all possible combinations of the transitions of DFA 1 and DFA 2
        for (int qt = 0; qt < D1Transitions.size(); qt ++) {
            for (int rt = 0; rt < D2Transitions.size(); rt ++) {
                boolean matching = false;
                if (D1Transitions.get(qt).alpha.equals(D2Transitions.get(rt).alpha)) { // check transition symbol match
                    matching = true;
                }

                if (matching) { // if they match, concat the original sources and destination states to make union transition
                    String unionSrc = D1Transitions.get(qt).source + D2Transitions.get(rt).source;
                    String unionDest = D1Transitions.get(qt).dest + D2Transitions.get(rt).dest;

                    // create new unioned transtion and add it to the transitions arrayList
                    unionTransitions.add(new Transition(unionSrc, D1Transitions.get(qt).alpha, unionDest));
                }
            }
        }  

        // write to output file
        try {
            File myFile = new File("unionDFA.txt"); // output is returned in file unionDFA.txt
            FileWriter writer = new FileWriter(myFile); // writer to write back to a file

            // Format the output to match input files and write out the outputs by looping through the
            // respective array lists

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

            writer.close(); // close the writer 

            System.out.println("Successfully created UnionDFA in unionDFA.txt"); // print success message

          } catch (IOException e) { // print any caught errors
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
}





