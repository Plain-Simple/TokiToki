package plainsimple.tokitoki;

public class Verb implements PartOfSpeech{
    String infinitive, tense, person, number, gender;

    public Verb(String infinitive, String tense, String person, String number, String gender) {
        this.infinitive = infinitive;
        this.tense = tense;
        this.person = person;
        this.number = number;
        this.gender = gender;
    }
    /**** VERB ATTRIBUTES: infinitive, tense, person, number, gender */
    public String conjugate() {
        return Main.conj.conjugate(this);
    }
    @Override
    public String[] getAttributes() {
        return new String[]{infinitive, tense, person, number, gender};
    }
}
