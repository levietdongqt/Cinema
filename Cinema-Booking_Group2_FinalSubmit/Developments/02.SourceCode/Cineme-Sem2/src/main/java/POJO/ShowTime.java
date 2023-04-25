/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package POJO;

import java.time.LocalTime;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author DONG
 */
@Entity
@Table(name = "ShowTime")
public class ShowTime {
    @Id
    private String sTimeID;
    private LocalTime startTime;
//     @OneToMany(mappedBy = "showTime")
//    private Set<TimeDetail> timeDetailList;

     
     
    public ShowTime() {
    }

   
}
