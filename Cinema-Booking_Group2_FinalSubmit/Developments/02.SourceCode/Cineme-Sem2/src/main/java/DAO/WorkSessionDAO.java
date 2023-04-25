/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.Employee;
import POJO.WorkSession;
import Utils.HibernateUtils;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
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

    public Map<String, Double> getTotalWorkTimeByUserAndMonth(int month) {
        Session session = HibernateUtils.getFACTORY().openSession();
        String hql = "SELECT ws.employee.userName, sum(hour(ws.endTime) - hour(ws.startTime)) "
                + "FROM WorkSession ws "
                + "WHERE MONTH(ws.startTime) = :month "
                + "GROUP BY ws.employee.userName";
        Query query = session.createQuery(hql);
        query.setParameter("month", month);
        List<Object[]> results = query.getResultList();

        Map<String, Double> totalWorkTimeByUser = new HashMap<>();
        for (Object[] result : results) {
            String userName = (String) result[0];

            long totalSeconds = (long) result[1];
            double x = totalSeconds / 8.0;
            Employee employee = getEmployeeByUsername(userName);
            String name = employee.getUserName();
            totalWorkTimeByUser.put(name, x);
            System.out.println(name + ": " + (x));

        }
        return totalWorkTimeByUser;
    }

    private Employee getEmployeeByUsername(String username) {
        Session session = HibernateUtils.getFACTORY().openSession();
        
        String hql = "FROM Employee e WHERE e.userName = :username";
        Query query = session.createQuery(hql);
        query.setParameter("username", username);
        return (Employee) query.getSingleResult();
    }

    public List<WorkSession> getTime(String username, int month) throws Exception {

        List<WorkSession> list = new LinkedList<>();
        Session session = HibernateUtils.getFACTORY().openSession();
        try {

            session.getTransaction().begin();
            var hql = "FROM WorkSession w WHERE w.employee.userName = :employee AND MONTH(startTime) = :month"; //w.employee.userName  -- employee : tên đặt trong pojo | userName : tên thực tế ở database
            Query query = session.createQuery(hql).setCacheable(true);
            query.setParameter("employee", username);
            query.setParameter("month", month);
            list = query.getResultList();

            if (list == null) {
                setMessGetAll("He Thong chua co du lieu");
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            setMessGetAll("Ten toi tuong khong hop le");
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return list;
    }

}
