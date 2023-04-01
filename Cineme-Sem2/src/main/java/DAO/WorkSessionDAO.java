/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.WorkSession;
import Utils.HibernateUtils;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javax.persistence.Query;
import org.hibernate.Session;

public class WorkSessionDAO extends GenericDAO<WorkSession, Float> {

public void update() throws Exception{
    Session session = HibernateUtils.getFACTORY().openSession();
    try {
        session.getTransaction().begin();
        Query query = session.createQuery("update WorkSession set endTime = :currentTime where endTime is null");
        query.setParameter("currentTime", LocalDateTime.now());
       query.executeUpdate();
       session.getTransaction().commit();
        System.out.println("cap nhat endTime thanh cong");
        
    } catch (Exception e) {
    System.out.println(e.getMessage());
        session.getTransaction().rollback();
    } finally {
        session.close();
    }
     
}
    


}
