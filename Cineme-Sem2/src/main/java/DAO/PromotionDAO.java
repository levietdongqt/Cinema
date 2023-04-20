/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.Promotion;
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
 * @author thuhuytran
 */
public class PromotionDAO extends GenericDAO<Promotion,Integer>{
    public List<Promotion> getPromoByDateTime(String attributeName){
        List<Promotion> lists = new ArrayList<>();
        try (Session session = HibernateUtils.getFACTORY().openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Promotion> criteriaQuery = builder.createQuery(Promotion.class);
            Root<Promotion> root = criteriaQuery.from(Promotion.class);
            
            LocalDateTime current = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            Predicate greaterThan = builder.greaterThanOrEqualTo(root.get(attributeName), current);
            Predicate equalThan = builder.equal(root.get("status"),true);
            criteriaQuery.where(builder.and(greaterThan,equalThan));
            lists = session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
             AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR).show();
        }
        return lists;
    }
}
