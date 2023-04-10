/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import DAO.ScheduleDAO;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DONG
 */
public class updateStatusScheduleForFuture implements Runnable {

    int i = 1;
    private final LocalDateTime taskTime;

    public updateStatusScheduleForFuture(int hour, int minute) {
        this.taskTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(hour, minute));
    }

    private void doTask() {
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        scheduleDAO.updateStatusScheduleForfuture(taskTime.plusDays(5));
    }

    @Override
    public void run() {
        while (true) {
            LocalDateTime now = LocalDateTime.now();
            doTask();
            try {
                Thread.sleep(now.until(taskTime.plusDays(i++), ChronoUnit.MILLIS));
            } catch (InterruptedException ex) {
                Logger.getLogger(updateStatusScheduleForFuture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
