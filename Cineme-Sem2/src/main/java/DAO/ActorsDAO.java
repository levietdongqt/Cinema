/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.Actors;
import Utils.AlertUtils;
import Utils.HibernateUtils;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author thuhuytran
 */
public class ActorsDAO extends GenericDAO<Actors, Integer>{
     public static List<Actors> getActorsAllOrByName(String kw){
        List<Actors> listActors = new ArrayList<>();
        try (Session session = HibernateUtils.getFACTORY().openSession()){
            String hql = "FROM Actors";
            if(!kw.isEmpty()){
                hql += " WHERE actorName like:kw";
            }
            Query query = session.createQuery(hql).setCacheable(true);
            if(!kw.isEmpty()){
                query.setParameter("kw", '%'+kw+'%');
            }     
            
            listActors = query.getResultList();
        } catch (Exception e) {
            AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR).show();
        }       
        return listActors;
    }
}
