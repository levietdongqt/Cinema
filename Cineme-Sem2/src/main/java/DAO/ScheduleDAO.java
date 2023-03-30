/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.RoomTypeDetails;
import POJO.Schedule;
import Utils.HibernateUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author DONG
 */
public class ScheduleDAO extends GenericDAO<Schedule, String> {
//    public List<Schedule> getByDate(LocalDate date) throws Exception{
//        List<Schedule> list;
//        try (Session ses = HibernateUtils.getFACTORY().openSession()) {
//            ses.getTransaction().begin();
//            String hql = "FROM Schedule where startTime > '" + date + "' and startTime < '" + date.plusDays(1) + "'";
//            Query query = ses.createQuery(hql);
//            list = query.getResultList();
//            ses.getTransaction().commit();
//            if(list.isEmpty()){
//                throw new Exception("Not found!");
//            }
//        }
//        return list;
//    }

    public List<Schedule> getToView(LocalDateTime startDate, LocalDateTime endDate, List<RoomTypeDetails> rtDetailsList) throws Exception {
        List<Schedule> list;
        try ( Session ses = HibernateUtils.getFACTORY().openSession()) {
            ses.getTransaction().begin();
           String hql = "FROM Schedule WHERE roomTypeDetail IN (:list) AND (startTime > :currDate AND startTime < :nextDate)";
            Query query = ses.createQuery(hql).setCacheable(true);
            query.setParameter("list", rtDetailsList);
            System.out.println("selectDay: "  + startDate);
            query.setParameter("currDate", startDate);
            query.setParameter("nextDate", endDate);
            list = query.getResultList();
//            CriteriaBuilder builder = ses.getCriteriaBuilder();
//            CriteriaQuery<Schedule> criteriaQuery = builder.createQuery(Schedule.class);
//            Root<Schedule> root = criteriaQuery.from(Schedule.class);
//            // criteriaQuery.where();
//            criteriaQuery.where(builder.and(
//                    builder.equal(root.get("startTime"), startDate),
//                    root.get("roomTypeDetail").in(rtDetailsList)
//            ));
//
//            list = ses.createQuery(criteriaQuery).setCacheable(true).getResultList();
            ses.getTransaction().commit();
            if (list.isEmpty()) {
                throw new Exception("Not found!");

            }
        }
        return list;

    }

    public List<Schedule> checkTime(LocalDateTime startTime, LocalDateTime endTime, List<RoomTypeDetails> rtDetailsList) throws Exception {
        List<Schedule> list;
        try ( Session ses = HibernateUtils.getFACTORY().openSession()) {
            ses.getTransaction().begin();
            String hql = "FROM Schedule  WHERE roomTypeDetail IN (:list) AND ((startTime <= :startTime AND endTime >= :startTime)"
                    + "or (startTime <= :endTime AND endTime >= :endTime))";
            Query query = ses.createQuery(hql);
            query.setParameter("list", rtDetailsList);
            query.setParameter("startTime", startTime);
            query.setParameter("endTime", endTime);
            list = query.getResultList();
            ses.getTransaction().commit();
        }
        return list;

    }
}
