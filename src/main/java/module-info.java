module org.example.lippudemang {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.lippudemang to javafx.fxml;
    exports org.example.lippudemang;
}