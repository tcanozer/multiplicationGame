module termProject {
	requires javafx.controls;
    requires javafx.fxml;
	requires junit;
	requires org.junit.jupiter.api;
    
	opens application to javafx.graphics, javafx.fxml, javafx.base;
}
