
package Utils;


import POJO.Film;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;
import java.io.*;
import java.nio.file.*;
import java.util.jar.*;

public class AlertUtils {
    public static Alert getAlert(String content,Alert.AlertType typeAlert){
        Alert alert = new Alert(typeAlert);
        alert.setHeaderText(content);
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
    
    //Ham chinh comboBox
    public static void formatLocalDateTimeInComboBox(ComboBox comboBox,String pattern){
        StringConverter<LocalDateTime> converter = new StringConverter<LocalDateTime>() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDateTime dateTime) {
                if (dateTime != null) {
                    return formatter.format(dateTime);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDateTime fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDateTime.parse(string, formatter);
                } else {
                    return null;
                }
            }
        };
        comboBox.setConverter(converter);
    }
//    public static void extract(String jarFile, String destDir, String dirToExtract) throws IOException {
//        try (JarFile jar = new JarFile(jarFile)) {
//            Path destDirPath = Paths.get(destDir);
//            if (!Files.exists(destDirPath)) {
//                Files.createDirectories(destDirPath);
//            }
//
//            jar.stream().filter(jarEntry -> jarEntry.getName().startsWith(dirToExtract)).forEach(jarEntry -> {
//                try {
//                    Path path = destDirPath.resolve(jarEntry.getName());
//                    if (jarEntry.isDirectory()) {
//                        Files.createDirectories(path);
//                    } else {
//                        Files.copy(jar.getInputStream(jarEntry), path);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//    }
    

    
}
