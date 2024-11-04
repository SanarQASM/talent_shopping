package application;

public enum SecurityQuestions {
    FIRST_CAR("What was the first car you owned?"),
    FIRST_TEACHER("Who was your first teacher?"),
    FIRST_JOB("Where was your first job?"),
    FIRST_ALBUM("What was the first album you owned?"),
    FIRST_KISS_CITY("In which city were you first kissed?"),
    FAVORITE_FOOD("What is the name of your favorite food?");
    private final String question;
    SecurityQuestions(String question) {
        this.question = question;
    }
    public String getQuestion() {
        return question;
    }
}

