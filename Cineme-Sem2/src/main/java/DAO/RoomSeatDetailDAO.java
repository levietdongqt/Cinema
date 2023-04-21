/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.Room;
import POJO.RoomSeatDetail;
import POJO.RoomType;
import POJO.Schedule;
import POJO.SeatMap;
import POJO.SeatType;
import Utils.HibernateUtils;
import com.group2.cineme.sem2.FXMLTicketController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;

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
            Collections.sort(seatList, (o1, o2) -> {
                int seatNum1 = o1.getSeatNum();
                int seatNum2 = o2.getSeatNum();
                int result = o1.getSeatRow().compareTo(o2.getSeatRow());
                if (result != 0) {
                    return result;
                }
                return seatNum1 - seatNum2;
            });
            String[] rtype = {"RT1", "RT2"};
            for (String roomtype : rtype) {
                RoomType rt = ses.get(RoomType.class, roomtype);
                for (SeatMap item : seatList) {
                    RoomSeatDetail rtdetail = new RoomSeatDetail();
                    rtdetail.setRoomType(rt);
                    rtdetail.setSeatMap(item);
                    SeatType st;
                    if ("I,K".contains(item.getSeatRow())) {  //Bỏ 2 dãy kế I,K
                        break;
                    }
                    if ("A1,A14".contains(item.getsMapID())) {  //Bỏ 2 ghế A1,A14
                        continue;
                    }
                    if (roomtype.equalsIgnoreCase("RT1")) {
                        if ("A,B".contains(item.getSeatRow())) {    //Set A,B là loại ghế ST1
                            st = ses.get(SeatType.class, "ST1");
                            rtdetail.setSeatType(st);
                        } else if ("K".contains(item.getSeatRow())) {
                            st = ses.get(SeatType.class, "ST3");
                            rtdetail.setSeatType(st);
                        } else {
                            st = ses.get(SeatType.class, "ST2");
                            rtdetail.setSeatType(st);
                        }
                        String a = item.getsMapID() + "." + rt.getrTypeID() + "." + st.getsTypeID();
                        rtdetail.setRsDetailsID(a);
                        ses.save(rtdetail);
                    }
                    if (roomtype.equalsIgnoreCase("RT2")) {
                        if ("A,B".contains(item.getSeatRow())) {    //Set A,B là loại ghế ST4
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
            }
            ses.getTransaction().commit();
            ses.close();
        } catch (Exception ex) {
            Logger.getLogger(FXMLTicketController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int countSeatBySchedule(String id) {
    int count = 0;
    Session session = HibernateUtils.getFACTORY().openSession();
    try {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<RoomSeatDetail> root = criteriaQuery.from(RoomSeatDetail.class);
        Join<RoomSeatDetail, RoomType> roomJoin = root.join("roomType");
        criteriaQuery.select(builder.count(root));
        criteriaQuery.where(builder.equal(roomJoin.get("rTypeID"), id));
        Long countLong = session.createQuery(criteriaQuery).setCacheable(true).getSingleResult();
        count = (countLong != null)?countLong.intValue():0;
    } catch (Exception e) {
        System.out.println(e.getMessage());
    } finally {
        session.close();
    }
    return count;
}
}
