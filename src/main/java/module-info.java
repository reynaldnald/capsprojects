module com.mycompany.capsprojects {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires com.google.gson;
    requires java.net.http;

    opens com.mycompany.capsprojects to javafx.fxml;
    exports com.mycompany.capsprojects;
}
