package application;

public enum Colors {
    BORDER_ERROR_COLOR("-fx-border-color: red;"),
    BORDER_DEFAULT_COLOR("-fx-border-color: #0077b6;");
    private final String color;
    Colors(String color) {
        this.color = color;
    }
    public String getColor() {
        return color;
    }
}
