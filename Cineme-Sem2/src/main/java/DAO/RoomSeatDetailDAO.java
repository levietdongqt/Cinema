/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.Room;
import POJO.RoomSeatDetail;
import POJO.RoomType;
import POJO.SeatMap;
import POJO.SeatType;
import Utils.HibernateUtils;
import com.group2.cineme.sem2.FXMLTicketController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;

/**
 *
 * @author DONG
 */
public class RoomSeatDetailDAO extends GenericDAO<RoomSeatDetailDAO, Integer> {

    public void addList() {
        List<SeatMap> seatList = new ArrayList<>();
        SeatMapDAO smapDAO = new SeatMapDAO();
        Session ses = HibernateUtils.getFACTORY().openSession();
        try {
            ses.getTransaction().begin();
            smapDAO.getAll("SeatMap").forEach(p -> seatList.add(p));
            Collections.sort(seatList, (o1, o2) -> o1.getsMapID().compareTo(o2.getsMapID()));
            String[] rtype = {"RT4"};
            for (String roomtype : rtype) {
                RoomType rt = ses.get(RoomType.class, roomtype);

                for (SeatMap item : seatList) {
                    RoomSeatDetail rtdetail = new RoomSeatDetail();
                    rtdetail.setRoomType(rt);
                    rtdetail.setSeatMap(item);
                    SeatType st;
//                if("I,K".contains(item.getSeatRow())){
//                    break;
//                }
                    if ("A1,A2,A14,A13,B1,B14,K1,K14".contains(item.getsMapID())) {
                        continue;
                    }
                    if ("A,B".contains(item.getSeatRow())) {
                        st = ses.get(SeatType.class, "ST4");
                        rtdetail.setSeatType(st);
                    } else if ("K".contains(item.getSeatRow())) {
                        st = ses.get(SeatType.class, "ST6");
                        rtdetail.setSeatType(st);
                    } else {
                        st = ses.get(SeatType.class, "ST5");
                        rtdetail.setSeatType(st);
                    }
                    String a = item.getsMapID() + "." + rt.getrTypeID() + "." + st.getsTypeID();
                    rtdetail.setRsDetailsID(a);
                    ses.save(rtdetail);
                }
            }
//            Room room = ses.get(Room.class, "P01");
//            System.out.println("Name: " + room.getRoomName());
//            room.getRoomType().getRoomSeatDetailList().forEach(p->seatList.add(p.getSeatMap().getsMapID()));
//            seatList.stream().forEach(p ->System.out.println(p + "\n"));
//            System.out.println(seatList.size());
            ses.getTransaction().commit();
            ses.close();
        } catch (Exception ex) {
            Logger.getLogger(FXMLTicketController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
