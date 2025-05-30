module com.mycompany.capsprojects {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.capsprojects to javafx.fxml;
    exports com.mycompany.capsprojects;
}
