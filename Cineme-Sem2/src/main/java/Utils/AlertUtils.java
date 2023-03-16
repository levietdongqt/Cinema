
package Utils;


import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertUtils {
    public static Alert getAlert(String content,Alert.AlertType typeAlert){
        Alert alert = new Alert(typeAlert);
        alert.setContentText(content);
        return alert;
    }
    
    public static Alert getAlertHaveButton(String content, Alert.AlertType typeAlert){
        Alert alert = new Alert(typeAlert,content, ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()==ButtonType.OK){
            System.out.println("Yes");
        }else{
            System.out.println("Close");
        }
        return alert;
    }
}
