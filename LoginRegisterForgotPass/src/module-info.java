//Default / Original
//module LoginRegisterForgotPass {
//	requires javafx.controls;
//	
//	opens application to javafx.graphics, javafx.fxml;
//}

module LoginRegisterForgotPass {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires javafx.base;
    
    opens loginregisterforgotpass to javafx.fxml;
    exports loginregisterforgotpass;
}