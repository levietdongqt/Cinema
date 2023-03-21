/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.RoomSeatDetail;
import POJO.RoomType;
import POJO.SeatMap;
import POJO.SeatType;
import Utils.HibernateUtils;
import com.group2.cineme.sem2.FXMLSeatMapController;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;

/**
 *
 * @author DONG
 */
public class RoomSeatDetailDAO extends GenericDAO<RoomSeatDetailDAO, Integer>{
    public void addList(){
        List<SeatMap> seatList = new ArrayList<>();
        SeatMapDAO smapDAO = new SeatMapDAO();
        Session ses = HibernateUtils.getFACTORY().openSession();
        try {
            ses.getTransaction().begin();
           smapDAO.getAll("SeatMap").forEach(p-> seatList.add(p));
            System.out.println("size: " + seatList.size());
            for (SeatMap item : seatList) {
                
                RoomSeatDetail rtdetail = new RoomSeatDetail();
                
                RoomType rt = ses.get(RoomType.class, "RT1");
                rtdetail.setRoomType(rt);
                rtdetail.setSeatMap(item);
                SeatType st =ses.get(SeatType.class, "ST1");
                rtdetail.setSeatType(st);
                String a = "STD" + rt.getrTypeID().substring(2) + st.getsTypeID().substring(2) + item.getsMapID() ;
                rtdetail.setRsDetailsID(a);
                ses.save(rtdetail);
            }

//            Room room = ses.get(Room.class, "P01");
//            System.out.println("Name: " + room.getRoomName());
//            room.getRoomType().getRoomSeatDetailList().forEach(p->seatList.add(p.getSeatMap().getsMapID()));
//            seatList.stream().forEach(p ->System.out.println(p + "\n"));
//            System.out.println(seatList.size());
            ses.getTransaction().commit();
            ses.close();
        } catch (Exception ex) {
            Logger.getLogger(FXMLSeatMapController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
