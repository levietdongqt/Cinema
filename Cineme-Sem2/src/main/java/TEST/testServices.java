/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TEST;

import DAO.ActorsDAO;
import DAO.BillDAO;
import DAO.FilmDAO;
import DAO.FilmGenreDAO;
import DAO.PromotionDAO;
import DAO.RoomSeatDetailDAO;
import DAO.ScheduleDAO;
import DAO.TicketDAO;
import POJO.Actors;
import POJO.Bill;
import POJO.Film;
import POJO.FilmGenre;
import POJO.Promotion;
import POJO.RoomSeatDetail;
import POJO.RoomType;
import POJO.RoomTypeDetails;
import POJO.Schedule;
import POJO.Ticket;
import Utils.HibernateUtils;
import Utils.updateStatusScheduleForFuture;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;

/**
 *
 * @author thuhuytran
 */
public class testServices {
    public static void main(String[] args){
       FilmDAO fd = new FilmDAO();
       List<Film> lists = fd.getFilmForReport(2023, 0);
       lists.forEach((t) -> {
           System.out.println(t.getFilmName());
           long a = fd.countTicket(t.getFilmID(), t.getStartDate().toLocalDate(), t.getEndDate().toLocalDate());
           System.out.println(a);
       });
          
        
}
}