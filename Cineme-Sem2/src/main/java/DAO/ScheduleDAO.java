/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.Film;
import POJO.RoomSeatDetail;
import POJO.RoomTypeDetails;
import POJO.Schedule;
import POJO.Ticket;
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

    public List<RoomSeatDetail> getRoomSeatDetails(Schedule schedule){
        List<RoomSeatDetail> list = new ArrayList<>();
        try(Session ses = HibernateUtils.getFACTORY().openSession()) {
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
        public List<Ticket> getTicketList(Schedule schedule){
        List<Ticket> list = new ArrayList<>();
        try(Session ses = HibernateUtils.getFACTORY().openSession()) {
            ses.getTransaction().begin();
            ses.load(schedule, schedule.getScheduleID());
            //Schedule schedule1 = ses.get(Schedule.class, schedule.getScheduleID());
            schedule.getListTicket()
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
            Query query = ses.createQuery(hql).setCacheable(true);
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
            Predicate statusEqual = builder.equal(root.get("status"), true);

            criteriaQuery.where(builder.and(greaterThan, lessThan, filmIdEqual,statusEqual));
            listFilm = session.createQuery(criteriaQuery).setCacheable(true).getResultList();
        } catch (Exception e) {
            AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR).show();
        } finally {
            session.close();
        }
        return listFilm;
    }

    public void updateStatusScheduleForfuture(LocalDateTime date) {
        try ( Session ses = HibernateUtils.getFACTORY().openSession()) {
            ses.getTransaction().begin();
            String hql1 = "Update Schedule set status = true where startTime < :date and startTime > :beforDate";
            Query query1 = ses.createQuery(hql1);
            query1.setParameter("date", date);
            query1.setParameter("beforDate", date.minusDays(1));
            int i = query1.executeUpdate();
            System.out.println( "Update Status of " + i +" Schedule for 5th day after today");
            ses.getTransaction().commit();
        }
    }
    public void updateStatusScheduleForPass(LocalDateTime date) {
        try ( Session ses = HibernateUtils.getFACTORY().openSession()) {
            ses.getTransaction().begin();
            String hql2 = "Update Schedule set status = false where startTime < :now and status = true";
            Query query2 = ses.createQuery(hql2);
            query2.setParameter("now", LocalDateTime.now());
            int j = query2.executeUpdate();
            System.out.println( "Update Status of " + j + " schedule befor now");
            ses.getTransaction().commit();
        }
    }
    public List<Schedule> getScheuleListByToday(){
        
        List<Schedule> list = new ArrayList<>();
                try ( Session ses = HibernateUtils.getFACTORY().openSession()) {
            ses.getTransaction().begin();
                String hql2 = "FROM Schedule where startTime > :startToday and startTime < :endToday and status = true ORDER BY startTime ASC";
            Query query2 = ses.createQuery(hql2);
            query2.setParameter("startToday", LocalDate.now().atStartOfDay());
            query2.setParameter("endToday", LocalDate.now().atStartOfDay().plusDays(1));
            list = query2.getResultList();
            ses.getTransaction().commit();
        }
        return list;
    }
    
    public List<Schedule> ScheduleByFilmID(String id,LocalDate startDate,LocalDate endDate){
        List<Schedule> lists = new ArrayList<>();
        try ( Session session = HibernateUtils.getFACTORY().openSession()){
            session.getTransaction().begin();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Schedule> criteriaQuery = builder.createQuery(Schedule.class);
            Root<Film> root = criteriaQuery.from(Film.class);
            Join<Film, Schedule> scheJoin = root.join("listSchedule");
            
            LocalDateTime startDateToStartDateTime = startDate.atStartOfDay();
            LocalDateTime endDateToEndDateTime = endDate.atStartOfDay().with(LocalTime.MAX);
            
            Predicate equalID = builder.equal(root.get("filmID"), id);
            Predicate greaterThan = builder.greaterThanOrEqualTo(scheJoin.get("startTime"), startDateToStartDateTime);
            Predicate lessThan = builder.lessThanOrEqualTo(scheJoin.get("startTime"), endDateToEndDateTime);
            
            criteriaQuery.select(scheJoin);
            criteriaQuery.where(builder.and(equalID,greaterThan,lessThan));
            lists= session.createQuery(criteriaQuery).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR).show();
        }
        return lists;
    }
}
