/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import DAO.ScheduleDAO;
import POJO.Schedule;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DONG
 */
public class updateStatusScheduleForPass implements Runnable {

    ScheduleDAO scheDAO = new ScheduleDAO();
    List<Schedule> list;

    ;
    @Override
    public void run() {
        boolean running = true;
        int coutDay = 1;
        while (running) {
            System.out.println("ReStart Thread");
            list = scheDAO.getScheuleListByToday();
            LocalDateTime now = LocalDateTime.now();
            scheDAO.updateStatusScheduleForPass(LocalDateTime.MAX);
            if (list.isEmpty()) {
                try {
                    System.out.println("No Schedule today, Thread Slepping to next day");
                    Thread.sleep(now.until(LocalDate.now().atStartOfDay().plusDays(coutDay++), ChronoUnit.MILLIS));
                    continue;
                } catch (InterruptedException ex) {
                    Logger.getLogger(updateStatusScheduleForPass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for (Schedule schedule : list) {
                if (schedule.getStartTime().isAfter(now)) {
                    try {
                        System.out.println("Thread Slepping to next Schdule( " + schedule.getStartTime().toLocalTime() + ")" );
                        Thread.sleep(now.until(schedule.getStartTime(), ChronoUnit.MILLIS));
                        break;
                    } catch (InterruptedException ex) {
                        Logger.getLogger(updateStatusScheduleForPass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }

    }

}
