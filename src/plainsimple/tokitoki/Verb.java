package plainsimple.tokitoki;

public class Verb extends PartOfSpeech {
    public Verb(String infinitive, String tense, String person, String number, String gender) {
        this.base_form = infinitive;
        this.tense = tense;
        this.person = person;
        this.number = number;
        this.gender = gender;
    }

    public String conjugate_target() {
        return Main.conj_targ.conjugate(this);
    }
    public String conjugate_base() {
        return Main.conj_base.conjugate(this);
    }
}
