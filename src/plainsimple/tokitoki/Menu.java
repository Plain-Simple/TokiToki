package plainsimple.tokitoki;

import java.util.Scanner;

/**
 * Created by stevenkarson on 5/20/16.
 */
public class Menu {

    public Menu() {
        System.out.println("Main Menu\nPlease Select one of the following:");
        System.out.println("1) Continue Learning\n2) Vocabulary Review\n3) Run Review\n4) Exit");
        Scanner scanner = new Scanner(System.in);

        switch (scanner.nextInt()) {
            case 1: continueLearning();
                break;
            case 2: runReview();
                break;
            case 3: viewLearningTrack();
                break;
            case 4: exit();
                break;
        }
    }

    public void continueLearning() {

    }

    public void runReview() {

    }

    public void viewLearningTrack() {

    }

    public void exit() {
        System.out.println("Thank you for using TokiToki!");
        System.exit(1);
    }
}
