/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.Employee;
import POJO.WorkSession;
import Utils.HibernateUtils;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import net.sf.ehcache.hibernate.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class WorkSessionDAO extends GenericDAO<WorkSession, Float> {

// cập nhật endTime khi logout     
    public void update() throws Exception {
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

  
//    
//    public Map<String, Long> getTotalWorkTimeByUserAndMonth(int month) {
//    Session session = HibernateUtils.getFACTORY().openSession();
//    String hql = "SELECT ws.employee.userName, sum(hour(ws.endTime) - hour(ws.startTime)) "
//                + "FROM WorkSession ws "
//                + "WHERE MONTH(ws.startTime) = :month "
//                + "GROUP BY ws.employee.userName";
//    Query query = session.createQuery(hql);
//    query.setParameter("month", month);
//    List<Object[]> results = query.getResultList();
//
//    Map<String, Long> totalWorkTimeByUser = new HashMap<>();
//    for (Object[] result : results) {
//        String userName = (String) result[0];
//        long totalSeconds = (long) result[1];
//        Employee employee = getEmployeeByUsername(userName);
//        String name = employee.getEmpName();
//        totalWorkTimeByUser.put(name, totalSeconds);
//        System.out.println(name + ": " + Duration.ofHours(totalSeconds));
//    }
//    return totalWorkTimeByUser;
//}
//
//private Employee getEmployeeByUsername(String username) {
//    Session session = HibernateUtils.getFACTORY().openSession();
//    String hql = "FROM Employee e WHERE e.userName = :username";
//    Query query = session.createQuery(hql);
//    query.setParameter("username", username);
//    return (Employee) query.getSingleResult();
//}
//
//    
    
    
    
}
