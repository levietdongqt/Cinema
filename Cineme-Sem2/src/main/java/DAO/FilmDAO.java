/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.*;
import Utils.AlertUtils;
import Utils.HibernateUtils;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.AbstractList;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.scene.control.Alert;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author thuhuytran
 */
public class FilmDAO extends GenericDAO<Film, String> {

    public static Film getByID(String id) {
        Film film = null;
        Session session = HibernateUtils.getFACTORY().openSession();
        try {
            session.getTransaction().begin();
            film = session.get(Film.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR).show();
        } finally {
            session.close();
        }
        return film;
    }

    public void saveActorsforFilm(String id, Set<Actors> actors) {
        Film film = null;
        Session session = HibernateUtils.getFACTORY().openSession();
        try {

            session.getTransaction().begin();
            film = session.get(Film.class, id);
            film.setListActors(actors);
            session.save(film);
            session.getTransaction().commit();
        } catch (Exception e) {
            AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR).show();
        } finally {
            session.close();
        }

    }

    public void saveGenresforFilm(String id, Set<FilmGenre> genres) {
        Film film = null;
        Session session = HibernateUtils.getFACTORY().openSession();
        try {
            session.getTransaction().begin();
            film = session.get(Film.class, id);
            film.setListGenre(genres);
            session.save(film);
            session.getTransaction().commit();
        } catch (Exception e) {
            AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR).show();
        } finally {
            session.close();
        }
    }

    public List<Film> searchByDate(String attributeName) {
        List<Film> listFilm = new ArrayList<>();
        Session session = HibernateUtils.getFACTORY().openSession();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Film> criteriaQuery = builder.createQuery(Film.class);
            Root<Film> root = criteriaQuery.from(Film.class);

            Date currentDate = Date.valueOf(LocalDate.now());
            Predicate greaterThan = builder.greaterThanOrEqualTo(root.get(attributeName), currentDate);
            Predicate equalThan = builder.equal(root.get("status"), true);
            criteriaQuery.where(builder.and(greaterThan, equalThan));
            listFilm = session.createQuery(criteriaQuery).setCacheable(true).getResultList();
        } catch (Exception e) {
            AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR).show();
        } finally {
            session.close();
        }
        return listFilm;

    }

    public List<FilmGenre> getFilmGenreByID(String id) {
        List<FilmGenre> list = null;
        try ( Session session = HibernateUtils.getFACTORY().openSession()) {
            String hql = "SELECT t FROM FilmGenre t JOIN t.listFilm p WHERE p.filmID =: id";
            Query<FilmGenre> query = session.createQuery(hql, FilmGenre.class);
            query.setParameter("id", id);
            list = query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Actors> getFilmActorsByID(String id) {
        List<Actors> list = null;
        try ( Session session = HibernateUtils.getFACTORY().openSession()) {
            String hql = "SELECT t FROM Actors t JOIN t.listFilm p WHERE p.filmID =: id";
            Query<Actors> query = session.createQuery(hql, Actors.class);
            query.setParameter("id", id);
            list = query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Film> getScheduleByDateTime(LocalDateTime sTime) {
        List<Film> listFilm = new ArrayList<>();
        Session session = HibernateUtils.getFACTORY().openSession();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Film> criteriaQuery = builder.createQuery(Film.class);
            Root<Film> filmRoot = criteriaQuery.from(Film.class);
            Join<Film, Schedule> scheduleJoin = filmRoot.join("listSchedule", JoinType.INNER);

            LocalDateTime endDate = LocalDateTime.of(sTime.toLocalDate(), LocalTime.MAX);
            Predicate greaterThan = builder.greaterThanOrEqualTo(scheduleJoin.get("startTime"), sTime);
            Predicate lessThan = builder.lessThanOrEqualTo(scheduleJoin.get("startTime"), endDate);
            Predicate statusEqual = builder.equal(scheduleJoin.get("status"), true);
            criteriaQuery.select(filmRoot)
                    .distinct(true)
                    .where(builder.and(greaterThan, lessThan, statusEqual));
            listFilm = session.createQuery(criteriaQuery).setCacheable(true).getResultList();
        } catch (Exception e) {
            AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR).show();
        } finally {
            session.close();
        }
        return listFilm;
    }

    public List<Schedule> getScheduleByFilmDate(Film film, LocalDateTime date) {
        List<Schedule> list = new ArrayList<>();
        LocalDateTime endDate = date.plusDays(1);
        Session session = HibernateUtils.getFACTORY().openSession();
        session.getTransaction().begin();
        String hql = "FROM Schedule WHERE film like :film AND (startTime > :currDate AND startTime < :nextDate)";
        Query query = session.createQuery(hql).setCacheable(true);
        query.setParameter("film", film);
        query.setParameter("currDate", date);
        query.setParameter("nextDate", endDate);
        list = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    //Ham de update lai status cho Film khi endDate<now()
    public void updateByDate(String attributeName) {
        List<Film> listFilm = new ArrayList<>();
        Session session = HibernateUtils.getFACTORY().openSession();
        session.getTransaction().begin();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Film> criteriaQuery = builder.createQuery(Film.class);
            Root<Film> root = criteriaQuery.from(Film.class);

            Date currentDate = Date.valueOf(LocalDate.now());

            criteriaQuery.where(builder.lessThan(root.get(attributeName), currentDate));

            listFilm = session.createQuery(criteriaQuery).setCacheable(true).getResultList();
            listFilm.forEach((t) -> {
                if (t.getStatus() == true) {
                    t.setStatus(false);
                    session.update(t);
                }
            });
            session.getTransaction().commit();
        } catch (Exception e) {
            AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR).show();
        } finally {
            session.close();
        }
    }

    //Lay list theo ngay va nam de xuat report
    public List<Film> getFilmForReport(int year, int month) {
        List<Film> lists = new ArrayList<>();
        try ( Session session = HibernateUtils.getFACTORY().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Film> criteriaQuery = builder.createQuery(Film.class);
            Root<Film> root = criteriaQuery.from(Film.class);
            Predicate greaterEqualThan;
            Predicate lessEqualThan;
            if (month == 0) {
                LocalDate checkYear = LocalDate.of(year, 1, 1);
                LocalDate checkEndYear = checkYear.with(TemporalAdjusters.lastDayOfYear());
                greaterEqualThan = builder.greaterThanOrEqualTo(root.get("startDate"), Date.valueOf(checkYear));
                lessEqualThan = builder.lessThanOrEqualTo(root.get("startDate"), Date.valueOf(checkEndYear));
            } else {
                LocalDate checkYearMonth = LocalDate.of(year, month, 1);
                LocalDate checkEndYearMonth = checkYearMonth.with(TemporalAdjusters.lastDayOfMonth());
                greaterEqualThan = builder.greaterThanOrEqualTo(root.get("startDate"), Date.valueOf(checkYearMonth));
                lessEqualThan = builder.lessThanOrEqualTo(root.get("startDate"), Date.valueOf(checkEndYearMonth));
            }
            criteriaQuery.select(root).where(builder.and(greaterEqualThan, lessEqualThan));
            lists = session.createQuery(criteriaQuery).setCacheable(true).getResultList();
        } catch (Exception e) {
            AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR).show();
        }
        return lists;
    }
    public int countScheduleByFilmID(String id,LocalDate startDate,LocalDate endDate){
        int count = 0;
        try ( Session session = HibernateUtils.getFACTORY().openSession()){
            session.getTransaction().begin();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
            Root<Film> root = criteriaQuery.from(Film.class);
            Join<Film, Schedule> scheJoin = root.join("listSchedule");
            
            LocalDateTime startDateToStartDateTime = startDate.atStartOfDay();
            LocalDateTime endDateToEndDateTime = endDate.atStartOfDay().with(LocalTime.MAX);
            
            Predicate equalID = builder.equal(root.get("filmID"), id);
            Predicate greaterThan = builder.greaterThanOrEqualTo(scheJoin.get("startTime"), startDateToStartDateTime);
            Predicate lessThan = builder.lessThanOrEqualTo(scheJoin.get("startTime"), endDateToEndDateTime);
            
            criteriaQuery.select(builder.count(scheJoin));
            criteriaQuery.where(builder.and(equalID,greaterThan,lessThan));
            Long countLong = session.createQuery(criteriaQuery).setCacheable(true).getSingleResult();
            count = (countLong != null)?countLong.intValue():0;
            session.getTransaction().commit();
        } catch (Exception e) {
            AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR).show();
        }
        return count;
    }
    public long sumTicketByFilmID(String id,LocalDate startDate,LocalDate endDate){
        long sum = 0;
        try ( Session session = HibernateUtils.getFACTORY().openSession()){
            session.getTransaction().begin();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
            Root<Film> root = criteriaQuery.from(Film.class);
            Join<Film, Schedule> scheJoin = root.join("listSchedule");
            Join<Schedule,Ticket> ticketJoin = scheJoin.join("listTicket");
            
            LocalDateTime startDateToStartDateTime = startDate.atStartOfDay();
            LocalDateTime endDateToEndDateTime = endDate.atStartOfDay().with(LocalTime.MAX);
            
            Predicate equalID = builder.equal(root.get("filmID"), id);
            Predicate greaterThan = builder.greaterThanOrEqualTo(scheJoin.get("startTime"), startDateToStartDateTime);
            Predicate lessThan = builder.lessThanOrEqualTo(scheJoin.get("startTime"), endDateToEndDateTime);
            
            Expression<Long> sumEx = builder.sum(ticketJoin.get("price"));
            Expression<Long> coalesceEx = builder.coalesce(sumEx, 0L);
            
            criteriaQuery.where(builder.and(equalID,greaterThan,lessThan));
            criteriaQuery.select(coalesceEx);
            sum =session.createQuery(criteriaQuery).setCacheable(true).getSingleResult();      
            session.getTransaction().commit();
        } catch (Exception e) {
            AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR).show();
        }
        return sum;
    }
    
//    public int countTicket(String id,LocalDate startDate,LocalDate endDate){
//        int count = 0;
//        try ( Session session = HibernateUtils.getFACTORY().openSession()){
//            session.getTransaction().begin();
//            CriteriaBuilder builder = session.getCriteriaBuilder();
//            CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
//            Root<Film> root = criteriaQuery.from(Film.class);
//            Join<Film, Schedule> scheJoin = root.join("listSchedule");
//            Join<Schedule,Ticket> ticketJoin=scheJoin.join("listTicket");
//            
//            LocalDateTime startDateToStartDateTime = startDate.atStartOfDay();
//            LocalDateTime endDateToEndDateTime = endDate.atStartOfDay().with(LocalTime.MAX);
//            
//            Predicate equalID = builder.equal(root.get("filmID"), id);
//            Predicate greaterThan = builder.greaterThanOrEqualTo(scheJoin.get("startTime"), startDateToStartDateTime);
//            Predicate lessThan = builder.lessThan(scheJoin.get("startTime"), endDateToEndDateTime);
//            
//            criteriaQuery.select(builder.count(ticketJoin));
//            criteriaQuery.where(builder.and(equalID,greaterThan,lessThan));
//            Long countLong = session.createQuery(criteriaQuery).setCacheable(true).getSingleResult();
//            count = (countLong != null)?countLong.intValue():0;
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR).show();
//        }
//        return count;
//    }
}
