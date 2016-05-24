package plainsimple.tokitoki;


public class AdjectiveInflector extends PartOfSpeechManipulator {
    @Override
    public String doRulePart(String base_adjective, String instruction_name, String[] args) {
        switch (getFunctionName(instruction_name)) {
            case "return":
            case "replaceAll":
                return stripQuotes(args[0]);
            case "replaceEnd":
                return stripFromEnd(base_adjective, stripQuotes(args[0])) + stripQuotes(args[1]);
            case "replaceStart":
                return base_adjective.substring(stripQuotes(args[0]).length());
        }
        System.out.println("Error: unable to execute instruction: " + instruction_name);
        System.exit(1);
        return null;
    }
}
