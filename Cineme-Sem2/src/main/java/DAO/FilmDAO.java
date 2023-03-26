/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.*;
import Utils.HibernateUtils;
import java.util.Set;
import org.hibernate.Session;

/**
 *
 * @author thuhuytran
 */
public class FilmDAO extends GenericDAO<Film, String>{
    public static Film getByID(String id){
        Film film = null;
        Session session = HibernateUtils.getFACTORY().openSession();
        session.getTransaction().begin();
        film = session.get(Film.class, id);
        session.getTransaction().commit();
        return film;
    }
    public void saveActorsforFilm(String id,Set<Actors> actors){
        Film film = null;
        Session session = HibernateUtils.getFACTORY().openSession();
        session.getTransaction().begin();
        film =session.get(Film.class, id);
        film.setListActors(actors);
        session.save(film);
        session.getTransaction().commit();
    }
    public void saveGenresforFilm(String id,Set<FilmGenre> genres){
        Film film = null;
        Session session = HibernateUtils.getFACTORY().openSession();
        session.getTransaction().begin();
        film =session.get(Film.class, id);
        film.setListGenre(genres);
        session.save(film);
        session.getTransaction().commit();
    }
}
