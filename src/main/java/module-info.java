module com.mycompany.capsprojects {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.capsprojects to javafx.fxml;
    exports com.mycompany.capsprojects;
}
