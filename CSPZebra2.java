import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;

public class CSPZebra2 extends CSP {

    static Set<Object> varCol = new HashSet<Object>(
    Arrays.asList(new String[] {"blue", "green", "ivory", "red", "yellow"}));

    static Set<Object> varDri = new HashSet<Object>(
    Arrays.asList(new String[] {"coffee", "milk", "orange-juice", "tea", "water"}));

    static Set<Object> varNat = new HashSet<Object>(
    Arrays.asList(new String[] {"englishman", "japanese", "norwegian", "spaniard", "ukrainian"}));

    static Set<Object> varPet = new HashSet<Object>(
    Arrays.asList(new String[] {"dog", "fox", "horse", "snails", "zebra"}));

    static Set<Object> varCig = new HashSet<Object>(
    Arrays.asList(new String[] {"chesterfield", "kools", "lucky-strike", "old-gold", "parliament"}));

    public boolean isGood(Object X, Object Y, Object x, Object y) {
    //if X is not even mentioned in by the constraints, just return true
    //as nothing can be violated
        int diff = x.hashCode() - y.hashCode();

        if(!C.containsKey(X)){
            return true;
        }
        //check to see if there is an arc between X and Y
        //if there isn't an arc, then no constraint, i.e. it is good
        if(!C.get(X).contains(Y)){
            //System.out.println("HELLO1");
            return true;
        }
        //The Englishman lives in the red house.
        if(X.equals("englishman") && Y.equals("red") && !x.equals(y)){
            //System.out.println("HELLO2");
            return false;
        }
        //2. The Spaniard owns a dog.
        if(X.equals("spaniard") && Y.equals("dog") && !x.equals(y)){
            //System.out.println("HELLO3");
            return false;
        }
        // 3.Coffee is drunk in the green house.
        if(X.equals("coffee") && Y.equals("green") && !x.equals(y)){
            return false;
        }
        // 4.The Ukrainian drinks tea.
        if(X.equals("ukrainian") && Y.equals("tea") && !x.equals(y)){
            return false;
        }
        //5.The green house is directly to the right of the ivory house
        if(X.equals("ivory") && Y.equals("green") && diff != -1){
            return false;
        }
        // 6.The Old-Gold smoker owns snails.
        if(X.equals("old-gold") && Y.equals("snails") && !x.equals(y)){
            return false;
        }
        //7.Kools are being smoked in the yellow house.
        if(X.equals("yellow") && Y.equals("kools") && !x.equals(y)){
            return false;
        }
        //10.The Chesterfield smoker lives next to the fox owner.
        if(X.equals("chesterfield") && Y.equals("fox") && (diff != -1 || diff != 1)) {
            return false;
        }
        //11.Kools are smoked in the house next to the house where the horse is kept.
        if(X.equals("kools") && Y.equals("horse") && (diff != -1 || diff != 1)){
            return false;
        }
        //12.The Lucky-Strike smoker drinks orange juice.
        if(X.equals("lucky-strike") && Y.equals("orange-juice") && !x.equals(y)){
            return false;
        }
        //13.The Japanese smokes Parliament
        if(X.equals("japanese") && Y.equals("parliament") && !x.equals(y)){
            return false;
        }
        //14.The Norwegian lives next to the blue house
        if(X.equals("norwegian") && Y.equals("blue") && (diff != -1 || diff != 1)){
            return false;
        }

        //Uniqueness constraints
        if(varCol.contains(X) && varCol.contains(Y) && !X.equals(Y) && x.equals(y)){
            return false;
        }
        if(varDri.contains(X) && varDri.contains(Y) && !X.equals(Y) && x.equals(y)){
            return false;
        }
        if(varNat.contains(X) && varNat.contains(Y) && !X.equals(Y) && x.equals(y)){
            return false;
        }
        if(varPet.contains(X) && varPet.contains(Y) && !X.equals(Y) && x.equals(y)){
            return false;
        }
        if(varCig.contains(X) && varCig.contains(Y) && !X.equals(Y) && x.equals(y)){
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        CSPZebra2 csp = new CSPZebra2();
        Integer[] dom = {1,2,3,4,5};
        Integer[] dom_milk = {3};
        Integer[] dom_norw = {1};
        //8.Milk is drunk in the middle house.
        //9.The Norwegian lives in the first house on the left.

        for(Object X : varCol){
            csp.addDomain(X, dom);
        }

        for(Object X : varDri){
            if (X.equals("milk")){
                csp.addDomain(X, dom_milk);
            }
            else{
                csp.addDomain(X, dom);
            }
        }

        for(Object X : varNat){
            if (X.equals("norwegian")){
                csp.addDomain(X, dom_norw);
            }
            else{
                csp.addDomain(X, dom);
            }
        }

        for(Object X : varPet){
            csp.addDomain(X, dom);
        }

        for(Object X : varCig){
            csp.addDomain(X, dom);
        }
        //…
        //unary constraints: just remove values from domains
        //…
        //binary constraints: add constraint arcs
        //1. The Englishman lives in the red house.
        csp.addBidirectionalArc("englishman", "red");
        //2. The Spaniard owns a dog.
        csp.addBidirectionalArc("spaniard", "dog");
        // 3.Coffee is drunk in the green house.
        csp.addBidirectionalArc("coffee", "green");
        // 4.The Ukrainian drinks tea.
        csp.addBidirectionalArc("ukrainian", "tea");
        // 6.The Old-Gold smoker owns snails.
        csp.addBidirectionalArc("old-gold", "snails");
        //7.Kools are being smoked in the yellow house.
        csp.addBidirectionalArc("kools", "yellow");

        //10.The Chesterfield smoker lives next to the fox owner.
        //11.Kools are smoked in the house next to the house where the horse is kept.

        //12.The Lucky-Strike smoker drinks orange juice.
        csp.addBidirectionalArc("lucky-strike", "orange-juice");
        //13.The Japanese smokes Parliament.
        csp.addBidirectionalArc("japanese", "parliament");
        //14.The Norwegian lives next to the blue house
        //…
        //Uniqueness constraints
        for(Object X : varCol){
            for(Object Y : varCol){
                csp.addBidirectionalArc(X,Y);
            }
        }
        for(Object X : varDri){
            for(Object Y : varDri){
                csp.addBidirectionalArc(X,Y);
            }
        }
        for(Object X : varNat){
            for(Object Y : varNat){
                csp.addBidirectionalArc(X,Y);
            }
        }
        for(Object X : varPet){
            for(Object Y : varPet){
                csp.addBidirectionalArc(X,Y);
            }
        }
        for(Object X : varCig){
            for(Object Y : varCig){
                csp.addBidirectionalArc(X,Y);
            }
        }
        Search search = new Search(csp);
        System.out.println(search.BacktrackingSearch());
    }
}
