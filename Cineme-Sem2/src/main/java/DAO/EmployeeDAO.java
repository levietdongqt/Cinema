/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.Employee;
import Utils.HibernateUtils;
import Utils.SessionUtil;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javax.persistence.Query;
import org.hibernate.Session;

/**
 *
 * @author BE BAU
 */
public class EmployeeDAO extends GenericDAO<Employee, String> {

        //mã hoá password sql 
    public String encodePassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    //mã hoá password sql 
    public String encode(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(Integer.toHexString(b & 0xff));
        }
        return sb.toString();
    }
    

    //7. Try vấn thông tin đăng nhập (SELECT * FROM user WHERE Username= ? and Password = ?)
    public boolean checkaccount(String username, String password) throws Exception {
        Session session = HibernateUtils.getFACTORY().openSession();
        List<Employee> list = new ArrayList<>();
        try {
            session.getTransaction().begin();

            var hql = "FROM Employee WHERE userName =:userName  AND password =:password";

            Query query = session.createQuery(hql);
            query.setParameter("userName", username);
            query.setParameter("password", encodePassword(password));
            System.out.println(hql);
            list = query.getResultList();
            session.getTransaction().commit();
            if (!list.isEmpty()) {
                SessionUtil.setEmployee(list.get(0));
//                System.out.println("Logged in successfully");
                // hiện thông báo đăng nhập thành công
             //   new Alert(Alert.AlertType.INFORMATION, "Logged in successfully").show();
                return true;
            } else {
                new Alert(Alert.AlertType.ERROR, "Username or password is incorrect").show();
                return false;
            }

        } catch (Exception e) {
            session.getTransaction().rollback();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();

        } finally {
            session.close();
        }

        return true;

    }
}
