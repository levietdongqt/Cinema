/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.Bill;
import Utils.AlertUtils;
import Utils.HibernateUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;

/**
 *
 * @author DONG
 */
public class BillDAO extends GenericDAO<Bill, Integer>{
    /*
        Ham nay de lay tat ca cac bill trong mot ngay can tim (date)
    */
    public List<Bill> getBillByDate(LocalDate date){
        List<Bill> lists = new ArrayList<>();
        try (Session session = HibernateUtils.getFACTORY().openSession()){
            session.getTransaction().begin();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Bill> criteriaQuery = builder.createQuery(Bill.class);
            Root<Bill> root = criteriaQuery.from(Bill.class);
            
            //Chuyen qua ngay gio day 00:00:00 -> endday 24:00:00
            LocalDateTime startDate = date.atStartOfDay();
            LocalDateTime endDate = startDate.with(LocalTime.MAX);
            
            Predicate greaterThan = builder.greaterThanOrEqualTo(root.get("printDate"),startDate);
            Predicate lessThan = builder.lessThanOrEqualTo(root.get("printDate"),endDate);
            
            criteriaQuery.where(builder.and(greaterThan,lessThan));
            
            lists = session.createQuery(criteriaQuery).getResultList();
            for (Bill list : lists) {
                list.getTickets().forEach((t) -> {
                    t.getSchedule().getFilm();
                });
                list.getProductBills().forEach((t) -> {
                    t.getProduct();
                });
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR).show();
        }
        return lists;
    }
    
}
