/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import POJO.Room;
import POJO.RoomTypeDetails;
import Utils.HibernateUtils;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author DONG
 */
public class RoomDAO extends GenericDAO<Room, String>{
    public List<RoomTypeDetails> getRoomTypeDetailsList(Room room){
        List<RoomTypeDetails> list  = new  ArrayList<>();
        Session ses = HibernateUtils.getFACTORY().openSession();
        ses.getTransaction().begin();
        ses.load(room, room.getRoomID());
        list =List.copyOf(room.getRoomTypeDetailList());
        ses.getTransaction().commit();
        ses.close();
        return list;
    }
}
