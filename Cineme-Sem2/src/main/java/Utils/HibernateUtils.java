/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Utils;

import POJO.*;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author DONG
 */
public class HibernateUtils {
  private static final SessionFactory FACTORY;
    static {
        Configuration conf = new Configuration();
       // conf.configure("hihi.cfg.xml");
        Properties props = new Properties();
        props.put(Environment.DIALECT, "org.hibernate.dialect.SQLServerDialect");
        props.put(Environment.DRIVER, "com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        props.put(Environment.URL, "jdbc:sqlserver://cinema-123.database.windows.net;database=dbCinema");
//        props.put(Environment.USER, "admin123");
//        props.put(Environment.PASS, "Thuhuy123");
         props.put(Environment.URL, "jdbc:sqlserver://cinema-sem2.database.windows.net;database=Cinema-sem2");
        props.put(Environment.USER, "admin123");
        props.put(Environment.PASS, "Vietdong123");
        props.put(Environment.SHOW_SQL, "true"); 
        props.put(Environment.FORMAT_SQL, "false"); 
            // Cấu hình cache 
        props.put(Environment.USE_SECOND_LEVEL_CACHE, "true"); 
         props.put(Environment.CACHE_REGION_FACTORY, "org.hibernate.cache.ehcache.EhCacheRegionFactory"); 
       props.put(Environment.USE_QUERY_CACHE, "false");
     
        // Cài đặt cache tự động xoá các đối tượng ít sử dụng
       props.put("hibernate.cache.eviction_strategy", "LRU");    //LRU (Least Recently Used), chiến lược sử dụng giá trị thời gian để xác định đối tượng ít sử dụng nhất
        props.put("hibernate.cache.evict_by", "ACCESS_COUNT");     //xác định số lượng truy cập để xác định đối tượng ít sử dụng
        props.put("hibernate.cache.evict_by_type", "ENTITY");       // chỉ xóa các đối tượng thay vì xóa toàn bộ cache
        props.put("hibernate.cache.usage_threshold", "100"); 
        //chỉ định giới hạn tối đa cho số lượng đối tượng được lưu trữ trong cache
        
        conf.setProperties(props);
        //Khai bao Annotation
        conf.addAnnotatedClass(Actors.class);
        conf.addAnnotatedClass(FilmGenre.class);
        conf.addAnnotatedClass(Film.class);
        conf.addAnnotatedClass(Room.class);
        conf.addAnnotatedClass(RoomSeatDetail.class);
        conf.addAnnotatedClass(RoomType.class);
        conf.addAnnotatedClass(Schedule.class);
        conf.addAnnotatedClass(SeatMap.class);
        conf.addAnnotatedClass(SeatType.class);
        conf.addAnnotatedClass(ShowTime.class);
        conf.addAnnotatedClass(Ticket.class);
        conf.addAnnotatedClass(TimeDetail.class);
        conf.addAnnotatedClass(Bill.class);
        conf.addAnnotatedClass(WorkSession.class);
        conf.addAnnotatedClass(Customer.class);
        conf.addAnnotatedClass(Promotion.class);
        conf.addAnnotatedClass(Employee.class);

        ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        FACTORY = conf.buildSessionFactory(registry);
//        System.out.println(props.getProperty(Environment.DRIVER));
        
    }
    /**
     * @return the FACTORY
     */
    public static SessionFactory getFACTORY() {
        return FACTORY;
    }
    public static void main(String[] args) {
        if(HibernateUtils.getFACTORY() == null)
        {
            System.out.println("No");
        }
        else
            System.out.println("YEs");
    }

  
}
