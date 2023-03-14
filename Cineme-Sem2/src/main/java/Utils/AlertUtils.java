
package Utils;


import javafx.scene.control.Alert;

public class AlertUtils {
    public static Alert getAlert(String content,Alert.AlertType typeAlert){
        Alert alert = new Alert(typeAlert);
        alert.setContentText(content);
        return alert;
    }
}
