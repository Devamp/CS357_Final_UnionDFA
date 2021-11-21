

public class Transition {

    String source;
    String alpha;
    String dest;

    public Transition(String sr, String a, String dest){
        source = sr;
        alpha = a;
        this.dest = dest;
    }


    public String toString(){
        return source + " " + alpha + " " + dest; 
    }

}