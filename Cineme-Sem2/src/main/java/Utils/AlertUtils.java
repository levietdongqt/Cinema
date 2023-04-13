
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
    //Ham de viet hoa cac ki tu dau
    public static String capitalizeWords(String str){
        StringBuilder sb = new StringBuilder();
        boolean capitalizeNext = true;
        for (char c : str.toCharArray()) {
            if(Character.isWhitespace(c)){
                capitalizeNext = true;
                sb.append(c);
            }else if(capitalizeNext){
                sb.append(Character.toUpperCase(c));
                capitalizeNext = false;
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
