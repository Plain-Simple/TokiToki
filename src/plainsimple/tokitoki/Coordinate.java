package plainsimple.tokitoki;

public class Coordinate extends WordLearning {
    public Coordinate(int first, int second) {
        coordinates[0] = first;
        coordinates[1] = second;
    }

    private final int[] coordinates = new int[2];

    public void incrementReps() {
        WordLearning.all_known_words.get(coordinates[0]).words.get(coordinates[1]).incrementReps();
    }

    public void introduce() {
        all_known_words.get(coordinates[0]).words.get(coordinates[1]).introduce();
    }

    public boolean isFullyLearned() {
        return all_known_words.get(coordinates[0]).words.get(coordinates[1]).isFullyLearned();
    }

    public boolean quizAndGrade() {
        return all_known_words.get(coordinates[0]).words.get(coordinates[1]).quizAndGrade();
    }

    public boolean quizNoGrade() {
        return all_known_words.get(coordinates[0]).words.get(coordinates[1]).quizNoGrade();
    }

    public boolean timeToReview() {
        return all_known_words.get(coordinates[0]).words.get(coordinates[1]).timeToReview();
    }
}
