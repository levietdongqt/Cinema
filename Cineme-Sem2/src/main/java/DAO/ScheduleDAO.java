/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.Film;
import POJO.RoomSeatDetail;
import POJO.RoomTypeDetails;
import POJO.Schedule;
import Utils.AlertUtils;
import Utils.HibernateUtils;
import com.group2.cineme.sem2.FXMLTicketController;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.LocalTime;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author DONG
 */
public class ScheduleDAO extends GenericDAO<Schedule, String> {

    public List<RoomSeatDetail> getRoomSeatDetails(Schedule schedule) {
        List<RoomSeatDetail> list = new ArrayList<>();
        try ( Session ses = HibernateUtils.getFACTORY().openSession()) {
            ses.getTransaction().begin();
            ses.load(schedule, schedule.getScheduleID());
            //Schedule schedule1 = ses.get(Schedule.class, schedule.getScheduleID());
            schedule.getRoomTypeDetail().getRoomType().getRoomSeatDetailList()
                    .forEach(p -> list.add(p));
            ses.getTransaction().commit();
            ses.close();

        } catch (Exception ex) {
            Logger.getLogger(FXMLTicketController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Schedule> getToView(LocalDateTime startDate, List<RoomTypeDetails> rtDetailsList) throws Exception {
        List<Schedule> list;
        LocalDateTime endDate = startDate.plusDays(1);
        try ( Session ses = HibernateUtils.getFACTORY().openSession()) {
            ses.getTransaction().begin();
            String hql = "FROM Schedule WHERE roomTypeDetail IN (:list) AND (startTime > :currDate AND startTime < :nextDate)";
            Query query = ses.createQuery(hql).setCacheable(true);
            query.setParameter("list", rtDetailsList);
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

    public List<Schedule> checkStatus(Film film) {
        List<Schedule> list = null;
        try ( Session ses = HibernateUtils.getFACTORY().openSession()) {
            ses.getTransaction().begin();
            String hql = "FROM Schedule WHERE film = :film AND status = :status";
            Query query = ses.createQuery(hql);
            query.setParameter("film", film);
            query.setParameter("status", true);
            list = query.getResultList();
            ses.getTransaction().commit();
        } catch (Exception e) {
            AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR).show();
        }
        return list;
    }

    public List<Schedule> getScheduleByDateTime(String id, LocalDateTime sTime) {
        List<Schedule> listFilm = new ArrayList<>();
        Session session = HibernateUtils.getFACTORY().openSession();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Schedule> criteriaQuery = builder.createQuery(Schedule.class);
            Root<Schedule> root = criteriaQuery.from(Schedule.class);
            Join<Schedule, Film> filmJoin = root.join("film");

            LocalDateTime endDate = LocalDateTime.of(sTime.toLocalDate(), LocalTime.MAX);
            Predicate greaterThan = builder.greaterThanOrEqualTo(root.get("startTime"), sTime);
            Predicate lessThan = builder.lessThanOrEqualTo(root.get("startTime"), endDate);
            Predicate filmIdEqual = builder.equal(filmJoin.get("filmID"), id);

            criteriaQuery.where(builder.and(greaterThan, lessThan, filmIdEqual));
            listFilm = session.createQuery(criteriaQuery).setCacheable(true).getResultList();
        } catch (Exception e) {
            AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR).show();
        } finally {
            session.close();
        }
        return listFilm;
    }

    public void updateStatusSchedule(LocalDateTime date) {
        try ( Session ses = HibernateUtils.getFACTORY().openSession()) {
            ses.getTransaction().begin();
            String hql = "Update Schedule set status = :status where startTime < :date and startTime > :beforDate";
            Query query = ses.createQuery(hql);
            query.setParameter("date", date);
            query.setParameter("beforDate", date.minusDays(1));
            query.setParameter("status", true);
            int i = query.executeUpdate();
            System.out.println("i: "+ i + ". Update Status of Schedule for 5th day after today");
            ses.getTransaction().commit();
        }
    }

}
