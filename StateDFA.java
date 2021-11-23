/*
* StateDFA represents a single state in a DFA
*/

public class StateDFA {
    
    public String stateID; // name of the state
    public boolean isStart = false; // is state initial state?
    public boolean isAccept = false; // is state accepting state?

    public StateDFA(String id, boolean start, boolean accept) { //constructor
        stateID = id;
        isStart = start;
        isAccept = accept;
    }
}

