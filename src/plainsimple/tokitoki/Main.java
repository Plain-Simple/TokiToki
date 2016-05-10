package plainsimple.tokitoki;


public class Main {
    static Conjugator conj = new Conjugator();
    static Pronoun_Getter pron = new Pronoun_Getter();
    public static void main(String[] args) {
        System.out.print(new Verb("tener", "conditional", "second", "plural", "null").conjugate());
    }
}