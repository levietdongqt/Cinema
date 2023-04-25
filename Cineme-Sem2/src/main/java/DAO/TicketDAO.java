/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.ProductBill;
import POJO.Schedule;
import POJO.Ticket;
import Utils.HibernateUtils;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;

/**
 *
 * @author DONG
 */
public class TicketDAO extends GenericDAO<Ticket, Integer> {

    public void addListTicketAndProduct(List<Ticket> ticketList, List<ProductBill> proBillList) {
        Session ses = HibernateUtils.getFACTORY().openSession();
        ses.getTransaction().begin();
        try {
            ticketList.forEach((t) -> {
               ses.save(t);
            });
            proBillList.forEach((t) -> {
                ses.save(t);
            });           
            ses.getTransaction().commit();
            System.out.println("Luu OK\n");
        } catch (Exception e) {
            ses.getTransaction().rollback();
        } finally {
            ses.close();
        }

    }
    public List<Ticket> getTicketBySchedule (Schedule schedule){
         List<Ticket> list = new ArrayList<>();
        Session session = HibernateUtils.getFACTORY().openSession();
        try {
            session.getTransaction().begin();
            var hql = "FROM Ticket" + " WHERE schedule LIKE :value";
            Query query = session.createQuery(hql);
            query.setParameter("value", schedule);
            list= query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return list;
    }

}
