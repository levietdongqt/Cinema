
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import POJO.Employee;
import POJO.Film;
import POJO.Product;
import POJO.Ticket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thuhuytran
 */
public class SessionUtil {
    private static List<Ticket> ticketList;
    private static Map<Product,Integer> productList = new HashMap<>();
    
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
<<<<<<< HEAD
     * @return the ticketList
     */
    public static List<Ticket> getTicketList() {
        return ticketList;
    }

    /**
     * @param aTicketList the ticketList to set
     */
    public static void setTicketList(List<Ticket> aTicketList) {
        ticketList = aTicketList;
    }

    /**
     * @return the productList
     */
    public static Map<Product,Integer> getProductList() {
        return productList;
    }

    /**
     * @param aProductList the productList to set
     */
    public static void setProductList(Map<Product,Integer> aProductList) {
        productList = aProductList;
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
