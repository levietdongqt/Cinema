/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.SeatMap;
import Utils.HibernateUtils;
import org.hibernate.Session;

/**
 *
 * @author DONG
 */
public class SeatMapDAO extends GenericDAO<SeatMap, String> {
    
    public void addSeatMapList() {
        Session ses = HibernateUtils.getFACTORY().openSession();
        try {
            ses.getTransaction().begin();
            String[] a = {"A","B", "C", "D", "E", "F", "G", "H", "I", "K"};
            for (String row : a) {
                for (int i = 1; i <= 14; i++) {
                    String map = row + i;
                    SeatMap seatMap = new SeatMap();
                    seatMap.setsMapID(map);
                    seatMap.setSeatRow(row);
                    seatMap.setSeatNum(i);
                    ses.save(seatMap);
                }
            }
            ses.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            ses.close();
        }
    }

//    public static void main(String[] args) {
//        SeatMapDAO stDAO = new SeatMapDAO();
//        stDAO.addSeatMapList();
//    }
}
