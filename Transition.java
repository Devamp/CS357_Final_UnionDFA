/*
* Transition class represents a single edge between two states of a DFA
*/

public class Transition {

    String source; // source state of the transition
    String alpha; // transition symbol
    String dest; // destination state of the transition

    // constructor
    public Transition(String sr, String a, String dest){
        source = sr;
        alpha = a;
        this.dest = dest;
    }

    // toString method to format transtions
    public String toString(){
        return source + " " + alpha + " " + dest; 
    }

}