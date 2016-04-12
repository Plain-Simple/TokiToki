package plainsimple.tokitoki;

public class Main {
    static Conjugator conj = new Conjugator("spanish");
    static Pronoun_Getter pron = new Pronoun_Getter("spanish");
    public static void main(String[] args) {
        //System.out.println(conj.conjugate(new Verb("ver", "imperfect", "first", "singular", null)));
        System.out.println(pron.getPronoun(new Pronoun("first", "plural", "male", "subject")));
    }
}
