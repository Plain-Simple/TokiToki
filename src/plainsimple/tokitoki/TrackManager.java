package plainsimple.tokitoki;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public final class TrackManager {
    public TrackManager() {
        loadSteps();
    }
    public void loadSteps() {
        try {
            steps = Files.readAllLines(Paths.get("languages/" + UserSettings.getTarget_language() +
                                                                      "/course"));
            steps = steps.subList(2, steps.size());
        } catch (Exception e) {
            System.out.println("unable to read track file. Exiting");
        }
        try {
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream("languages/" + UserSettings.getTarget_language() +
                                                                                         "/current_step"));
            current_step = (int) reader.readObject();
            reader.close();
        } catch (Exception e) {
            //nothing happens - just start from step 0
        }
    }
    public void saveSteps() {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("languages/" + UserSettings.getTarget_language() +
                                                                                            "/current_step"));
            writer.writeObject(current_step);
            writer.close();
        } catch (Exception e) {
            System.out.println("unable to save steps");
        }
    }
    public List<String> steps;
    private int current_step;
    public void explain(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("languages/" + UserSettings.getTarget_language() +
                                                                  "/explanations/" + filename));
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println("unable to open explanation file \"" + "languages/" + UserSettings.getTarget_language() +
                                       "explanations/" + filename + '\"');
        }
    }
    public void doNextStep() {
        if (current_step >= steps.size()) {
            System.out.println("Congratulations! You've finished this track!");
            System.exit(0);
        } else {
            String current_step_contents = steps.get(current_step);
            String command_name = current_step_contents.substring(0, current_step_contents.indexOf(' '));
            String filename = current_step_contents.substring(current_step_contents.indexOf('\"') + 1,
                                                                  current_step_contents.lastIndexOf('\"'));
            switch (command_name) {
                case "explain":
                    explain(filename);
                    break;
                case "learn":
                    WordLearning.addWordsFromFile(filename);
                    WordLearning.learnNewWords();
                    break;
            }
        }
        current_step++;
        saveSteps();
    }
}
