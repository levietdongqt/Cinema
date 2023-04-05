
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import POJO.Employee;
import POJO.Film;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thuhuytran
 */
public class SessionUtil {
    private static Employee employee;
    private static List<Film> mapFilm;

    /**
     * @return the mapFilm
     */
    public static List<Film> getMapFilm() {
        return mapFilm;
    }

    /**
     * @param aMapFilm the mapFilm to set
     */
    public static void setMapFilm(List<Film> aMapFilm) {
        mapFilm = aMapFilm;
    }

    /**
     * @return the employee
     */
    public static Employee getEmployee() {
        return employee;
    }

    /**
     * @param aEmployee the employee to set
     */
    public static void setEmployee(Employee aEmployee) {
        employee = aEmployee;
    }

    
    
}
