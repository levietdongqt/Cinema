/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TEST;

import DAO.ActorsDAO;
import DAO.FilmDAO;
import DAO.FilmGenreDAO;
import POJO.Actors;
import POJO.Film;
import POJO.FilmGenre;
import Utils.HibernateUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;

/**
 *
 * @author thuhuytran
 */
public class testServices {
    public static void main(String[] args) throws Exception {
        FilmGenreDAO fgd = new FilmGenreDAO();
        FilmGenre fg = new FilmGenre();
        fg.setfGenreName("Action");
        FilmGenre fg1 = new FilmGenre();
        fg1.setfGenreName("Adventure");
        FilmGenre fg2 = new FilmGenre();
        fg2.setfGenreName("Sci-fi");
        FilmGenre fg3 = new FilmGenre();
        fg3.setfGenreName("Fantasy");
        FilmGenre fg4 = new FilmGenre();
        fg4.setfGenreName("Drama");
        FilmGenre fg5 = new FilmGenre();
        fg5.setfGenreName("Comedy");
        FilmGenre fg6 = new FilmGenre();
        fg6.setfGenreName("Horror");
        FilmGenre fg7 = new FilmGenre();
        fg7.setfGenreName("Musical");
        FilmGenre fg8 = new FilmGenre();
        fg8.setfGenreName("Romance");
        FilmGenre fg9 = new FilmGenre();
        fg9.setfGenreName("Thriller");
        FilmGenre fg10 = new FilmGenre();
        fg10.setfGenreName("Historical");
        FilmGenre fg11 = new FilmGenre();
        fg11.setfGenreName("Animated");
        
        fgd.add(fg);
        fgd.add(fg1);
        fgd.add(fg2);
        fgd.add(fg3);
        fgd.add(fg4);
        fgd.add(fg5);
        fgd.add(fg6);
        fgd.add(fg7);
        fgd.add(fg8);
        fgd.add(fg9);
        fgd.add(fg10);
        fgd.add(fg11);
        
        
        
        
        
    }
}
