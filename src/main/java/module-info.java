module com.example.mergedtest1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens PlayerData to javafx.base; // Allow reflective access to PlayerData package
    exports PlayerData;

    opens MainPackage to javafx.fxml;
    exports MainPackage;
    opens Controllers to javafx.fxml; // Allows javafx.fxml to access the Controllers package
    exports Controllers;
}