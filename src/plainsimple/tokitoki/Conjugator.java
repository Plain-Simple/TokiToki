package plainsimple.tokitoki;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Conjugator extends PartOfSpeechManipulator {
    public Conjugator() {
        try {
            rules = Files.readAllLines(Paths.get("languages/" + UserSettings.getCurrent_language() + "/verbs"));
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
    public boolean testRulePart(String[] attributes, String conditional) {
        /**** VERB ATTRIBUTES (in order): infinitive, tense, person, number, gender */
        String infinitive = attributes[0], tense = attributes[1], person = attributes[2], number = attributes[3], gender = attributes[4];
        /* all of this requires only one arg */
        String argument = stripQuotes(getArgs(conditional)[0]);
        switch (getFunctionName(conditional)) {
            case "startsIn":
            case "startsWith":
                int start_length = argument.length();
                return argument.equals(infinitive.substring(0, start_length));
            case "endsIn":
            case "endsWith":
                return stripFromEnd(infinitive, argument) != null;
            case "infinitiveIs":
            case "verbIs":
                return infinitive.equals(argument);
            case "tenseIs":
                return tense.equals(argument);
            case "personIs":
                return person.equals(argument);
            case "genderIs":
                return gender.equals(argument);
            case "numberIs":
                return number.equals(argument);
            default:
                System.out.println("Error: unknown function in testRulePart: " + getFunctionName(conditional));
                System.exit(1);
                return false;
        }
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