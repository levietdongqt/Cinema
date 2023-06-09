/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import DAO.EmployeeDAO;
import DAO.RoomSeatDetailDAO;
import DAO.SeatMapDAO;
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
        Properties props = new Properties();
        props.put(Environment.DIALECT, "org.hibernate.dialect.SQLServer2012Dialect");
        props.put(Environment.DRIVER, "com.microsoft.sqlserver.jdbc.SQLServerDriver");
//Kết nối DB trên Azure
        props.put(Environment.URL, "jdbc:sqlserver://cinema-sem2.database.windows.net;database=CinemaDB");
        props.put(Environment.USER, "admin123");
        props.put(Environment.PASS, "Vietdong123");
//Kết nối DB Local
//      props.put(Environment.URL, "jdbc:sqlserver://127.0.0.1:1433;database=CinemaDB");
//      props.put(Environment.USER, "sa");    
//      props.put(Environment.PASS, "123");
        props.put(Environment.SHOW_SQL, "true");
        props.put(Environment.FORMAT_SQL, "false");
        // Cấu hình cache 
        props.put(Environment.USE_SECOND_LEVEL_CACHE, "true");
        props.put(Environment.CACHE_REGION_FACTORY, "org.hibernate.cache.ehcache.EhCacheRegionFactory");
        props.put(Environment.USE_QUERY_CACHE, "true");

        props.put("hibernate.cache.ehcache.missing_cache_strategy", "create");
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
        conf.addAnnotatedClass(Bill.class);
        conf.addAnnotatedClass(WorkSession.class);
        conf.addAnnotatedClass(Customer.class);
        conf.addAnnotatedClass(Promotion.class);
        conf.addAnnotatedClass(Employee.class);
        conf.addAnnotatedClass(RoomTypeDetails.class);
        conf.addAnnotatedClass(ActorOfFilm.class);
        conf.addAnnotatedClass(FilmGenreDetails.class);
        conf.addAnnotatedClass(ProductBill.class);
        conf.addAnnotatedClass(Product.class);
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
}
