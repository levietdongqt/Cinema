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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javafx.scene.control.Alert;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
    public List<Film> searchByDate(String attributeName){
        List<Film> listFilm= new ArrayList<>();
        Session session = HibernateUtils.getFACTORY().openSession();
        try {
            CriteriaBuilder builder =session.getCriteriaBuilder();
            CriteriaQuery<Film> criteriaQuery = builder.createQuery(Film.class);
            Root<Film> root = criteriaQuery.from(Film.class);
            
            Date currentDate = Date.valueOf(LocalDate.now());
            
            criteriaQuery.where(builder.greaterThanOrEqualTo(root.get(attributeName),currentDate));
            
            listFilm = session.createQuery(criteriaQuery).setCacheable(true).getResultList();
        } catch (Exception e) {
            AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR).show();
        }finally{
            session.close();
        }
        return listFilm;
        
    }
     public void updateActorsforFilm(String id, Set<Actors> actors) {
        Film film = null;
        Session session = HibernateUtils.getFACTORY().openSession();
        try {

            session.getTransaction().begin();
            film = session.get(Film.class, id);
            film.setListActors(actors);
            session.update(film);
            session.getTransaction().commit();
        } catch (Exception e) {
            AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR).show();
        } finally {
            session.close();
        }

    }

    public void updateGenresforFilm(String id, Set<FilmGenre> genres) {
        Film film = null;
        Session session = HibernateUtils.getFACTORY().openSession();
        try {
            session.getTransaction().begin();
            film = session.get(Film.class, id);
            film.setListGenre(genres);
            session.update(film);
            session.getTransaction().commit();
        } catch (Exception e) {
            AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR).show();
        } finally {
            session.close();
        }
    }
    public void deleteFilm(String id){
        Film film = null;
        Session session =HibernateUtils.getFACTORY().openSession();
        try {
            session.getTransaction().begin();
            film = session.get(Film.class, id);
            session.delete(film.getListGenre());
            session.delete(film.getListActors());
            session.delete(film);
            session.getTransaction().commit();
        } catch (Exception e) {
            AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR).show();
        }finally{
            session.close();
        }
    }

}
