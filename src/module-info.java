/**
 * 
 */
/**
 * @author mawer
 *
 */
module MotFleche {
	requires java.desktop;
	requires junit;

    exports util;
    exports model;

    opens ihm to javafx.fxml;
    exports ihm;
}