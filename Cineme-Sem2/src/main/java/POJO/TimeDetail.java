/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

import java.time.LocalDate;
import java.util.BitSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author DONG
 */
@Entity
@Table(name = "TimeDetails")
public class TimeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int timeDetailsID;
    
    @ManyToOne
    @JoinColumn(name = "roomID")
    private Room room;
    
    @ManyToOne
    @JoinColumn(name = "sTimeID")
    private ShowTime showTime;
    
    @OneToMany(mappedBy = "timeDetail")
    private Set<Schedule> schedules;
    
    private LocalDate date;
    private boolean status;

}
