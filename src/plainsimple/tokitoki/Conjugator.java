package plainsimple.tokitoki;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Conjugator extends PartOfSpeechManipulator {
    public Conjugator(String language) {
        try {
            rules = Files.readAllLines(Paths.get("languages/" + language + "/verbs"));
        } catch (Exception e) {
            System.out.println("error: couldn't find verb rules");
            System.exit(1);
        }
    }

    /**
     * Conjugate the given verb
     * @param verb the verb to conjugate
     * @return conjugated verb as a String
     */
    public String conjugate(Verb verb) {
        return performAppropriateRule(verb);
    }

    @Override
    public String doRulePart(String infinitive, String instruction_name, String[] args) {
        switch (instruction_name) {
            case "return":
            case "replaceAll":
                return stripQuotes(args[0]);
            case "replaceEnd":
                return stripFromEnd(infinitive, stripQuotes(args[0])) + stripQuotes(args[1]);
            case "conjugate":
                return conjugate(new Verb(stripQuotes(args[0]), stripQuotes(args[1]), stripQuotes(args[2]), stripQuotes(args[3]), stripQuotes(args[4])));
            case "replaceStart":
                return infinitive.substring(stripQuotes(args[0]).length());
        }
        System.out.println("Error: unable to execute instruction: " + instruction_name);
        System.exit(1);
        return null;
    }
}