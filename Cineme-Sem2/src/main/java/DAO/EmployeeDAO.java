/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.Employee;
import Utils.HibernateUtils;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;

/**
 *
 * @author BE BAU
 */
public class EmployeeDAO extends GenericDAO<Employee, String>{
    //7. Try vấn thông tin đăng nhập (SELECT * FROM user WHERE Username= ? and Password = ?)
    public boolean checkaccount(String username, String password) throws Exception {
        Session session = HibernateUtils.getFACTORY().openSession();
        List<Employee> list = new ArrayList<>();
        try {
            session.getTransaction().begin();
            
            var hql = "FROM Employee WHERE userName =:userName  AND password =:password";
            
            Query query = session.createQuery(hql);
            query.setParameter("userName", username);
            query.setParameter("password", password);
            System.out.println(hql);
            list = query.getResultList();
            session.getTransaction().commit();
            System.out.println("aasds");
            if (!list.isEmpty()) {
                System.out.println("Logged in successfully");
                session.getTransaction().commit();
                return true;
            } else {
                System.out.println("Username or password is incorrect");
                session.getTransaction().commit();
                return false;
            }

        } catch (Exception e) {
            setMessGetAll("Error: " + e.getMessage());
        } finally {
            session.close();
        }

        return false;

    }
}
