package plainsimple.tokitoki;

public class Pronoun implements PartOfSpeech {
    String person, number, gender, grammatical_case;

    public Pronoun(String person, String number, String gender, String grammatical_case) {
        this.person = person;
        this.number = number;
        this.gender = gender;
        this.grammatical_case = grammatical_case;
    }
    @Override
    public String[] getAttributes() {
        return new String[]{person, number, gender, grammatical_case};
    }
    public String getPronoun() {
        return Main.pron.getPronoun(this);
    }

}
