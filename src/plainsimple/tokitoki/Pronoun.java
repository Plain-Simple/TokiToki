package plainsimple.tokitoki;

public class Pronoun extends PartOfSpeech {
    public Pronoun(String person, String number, String gender, String grammatical_case) {
        this.person = person;
        this.number = number;
        this.gender = gender;
        this.grammatical_case = grammatical_case;
    }

    public String getTargetPronoun() {
        return Main.pron_targ.getPronoun(this);
    }
    public String getBasePronoun() {
        return Main.pron_base.getPronoun(this);
    }

}
