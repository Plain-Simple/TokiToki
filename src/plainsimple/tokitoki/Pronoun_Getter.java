package plainsimple.tokitoki;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Pronoun_Getter extends PartOfSpeechManipulator {

    public Pronoun_Getter(String language) {
        try {
            rules = Files.readAllLines(Paths.get("languages/" + UserSettings.getTarget_language() + "/pronouns"));
        } catch (Exception e) {
            System.out.println("error: couldn't find pronoun rules");
            System.exit(1);
        }
    }

    @Override
    public String doRulePart(String infinitive, String instruction_name, String[] args) {
        switch (instruction_name) {
            case "return":
            case "replaceAll":
                return stripQuotes(args[0]);
        }
        System.out.println("Error: unable to execute instruction: " + instruction_name);
        System.exit(1);
        return null;
    }

    public String getPronoun(Pronoun pronoun) {
        return performAppropriateRule(pronoun);
    }

}
