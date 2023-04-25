/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.FilmGenre;
import Utils.AlertUtils;
import Utils.HibernateUtils;
import java.util.*;
import java.util.List;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author thuhuytran
 */
public class FilmGenreDAO extends GenericDAO<FilmGenre, Integer>{
    public static List<FilmGenre> getGenreAllOrByName(String kw){
        List<FilmGenre> listGenre = new ArrayList<>();
        try (Session session = HibernateUtils.getFACTORY().openSession()){
            String hql = "FROM FilmGenre";
            if(!kw.isEmpty()){
                hql += " WHERE fGenreName like:kw";
            }
            Query query = session.createQuery(hql).setCacheable(true);
            if(!kw.isEmpty()){
                query.setParameter("kw", '%'+kw+'%');
            }     
            
            listGenre = query.setCacheable(true).getResultList();
        } catch (Exception e) {
            AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR).show();
        }       
        return listGenre;
    }
}
