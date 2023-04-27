package DAO;

import POJO.Employee;
import Utils.HibernateUtils;
import Utils.SessionUtil;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javax.persistence.Query;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author BE BAU
 */
public class EmployeeDAO extends GenericDAO<Employee, String> {

    public List<Employee> getById(String user) throws Exception {
        List<Employee> list = new LinkedList<>();
        Session session = HibernateUtils.getFACTORY().openSession();
        try {
            Query query = session.createQuery("FROM Employee WHERE userName = :user", Employee.class).setCacheable(true);
            query.setParameter("user", user);
            list = query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list;

    }

    //mã hoá password sql 
    public String encodePassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    //mã hoá password sql 
    public String encode(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(Integer.toHexString(b & 0xff));
        }
        return sb.toString();
    }

    //Try vấn thông tin đăng nhập (SELECT * FROM user WHERE Username= ? and Password = ?)
    public boolean checkaccount(String username, String password) throws Exception {
        Session session = HibernateUtils.getFACTORY().openSession();
        List<Employee> list = new ArrayList<>();
        try {
            session.getTransaction().begin();

            var hql = "FROM Employee WHERE userName =:userName  AND password =:password";

            Query query = session.createQuery(hql);
            query.setParameter("userName", username);
            query.setParameter("password", encodePassword(password));
            System.out.println(hql);
            list = query.getResultList();
            session.getTransaction().commit();
            if (!list.isEmpty()) {
                SessionUtil.setEmployee(list.get(0));
//                System.out.println("Logged in successfully");
                // hiện thông báo đăng nhập thành công
                //   new Alert(Alert.AlertType.INFORMATION, "Logged in successfully").show();
                return true;
            } else {
                new Alert(Alert.AlertType.ERROR, "Username or password is incorrect").show();
                return false;
            }

        } catch (Exception e) {
            session.getTransaction().rollback();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();

        } finally {
            session.close();
        }

        return true;

    }

    //check trùng username
   public boolean checkUser(String username) throws Exception {
        Session session = HibernateUtils.getFACTORY().openSession();
        List<Employee> list = new ArrayList<>();
        try {
            session.getTransaction().begin();
            var hql = "FROM Employee WHERE userName =:userName";
            Query query = session.createQuery(hql);
            query.setParameter("userName", username);
            System.out.println(hql);
            list = query.getResultList();
            session.getTransaction().commit();
            if (!list.isEmpty()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();

        } finally {
            session.close();
        }

        return true;

    }

    
    
    
    //update thông tin employee trừ pass
    public void updateEmployee(Employee employee) {
        try ( Session session = HibernateUtils.getFACTORY().openSession()) {
            session.beginTransaction();
            Employee existingEmployee = session.get(Employee.class, employee.getUserName());
            if (existingEmployee != null) {
                // Update all fields except password
                existingEmployee.setEmpName(employee.getEmpName());
                existingEmployee.setPosition(employee.getPosition());
                existingEmployee.setBirthDate(employee.getBirthDate());
                existingEmployee.setStartDate(employee.getStartDate());
                existingEmployee.setEmail(employee.getEmail());
                existingEmployee.setStatus(employee.isStatus());
                existingEmployee.setGender(employee.isGender());
                existingEmployee.setEmpPhone(employee.getEmpPhone());
                existingEmployee.setTotalWorkTime(employee.getTotalWorkTime());
                session.update(existingEmployee);
                session.getTransaction().commit();
                setMessUpdate("Cập nhật dữ liệu thành công");
            } else {
                setMessUpdate("Không tìm thấy dữ liệu để cập nhật");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            setMessUpdate("Cập nhật dữ liệu không thành công");
        }
    }
    
       // update status thành false
       public void updateStatus(Employee employee) {
        try ( Session session = HibernateUtils.getFACTORY().openSession()) {
            session.beginTransaction();
            Employee existingEmployee = session.get(Employee.class, employee.getUserName());
            if (existingEmployee != null) {
                existingEmployee.setStatus(false);
                session.update(existingEmployee);
                session.getTransaction().commit();
                setMessUpdate("Cập nhật dữ liệu thành công");
            } else {
                setMessUpdate("Không tìm thấy dữ liệu để cập nhật");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            setMessUpdate("Cập nhật dữ liệu không thành công");
        }
    }
       
       
       //update password
    public void updatePassword(Employee employee, String pass) throws Exception {
        Session session = HibernateUtils.getFACTORY().openSession();
        try {

            session.getTransaction().begin();
            Employee existingEmployee = session.get(Employee.class, employee.getUserName());
            if (existingEmployee != null) {
                existingEmployee.setPassword(pass);
                session.update(existingEmployee);
                session.getTransaction().commit();
                setMessUpdate("Chỉnh sửa mật khẩu thành công");
            } else {
                setMessUpdate("Không tìm thấy thông tin employee để cập nhật mật khẩu");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
            setMessUpdate("Chỉnh sửa mật khẩu không thành công");
        } finally {
            session.close();
        }
    }
    
    public List<Employee> getAll() throws Exception{

        List<Employee> list = new LinkedList<>();
        Session session = HibernateUtils.getFACTORY().openSession();
        try {

//            if (!"Course,Batch,Student".contains(className)) {
//                throw new Exception();
//            }


            session.getTransaction().begin();
            var hql = "FROM Employee WHERE status = true ";
            Query query = session.createQuery(hql).setCacheable(true);
            list = query.getResultList();
            
            if (list == null) {
                setMessGetAll("He Thong chua co du lieu");
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            setMessGetAll("Ten toi tuong khong hop le");
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return list;
    }
    
    
    
    // tạo user admin 
    public void insert() throws Exception {
        Session session = HibernateUtils.getFACTORY().openSession();
        Employee e = new Employee();
        e.setUserName("admin");
        e.setEmpName("BOSSSSSS");
        e.setEmail("admin@gmail.com");
        e.setEmpPhone("0123456789");
        e.setPassword("123456");
        e.setGender(true);
        e.setStatus(true);
        e.setPosition("Manager");
        e.setStartDate(LocalDate.now());
        
        try {
            session.getTransaction().begin();
            session.save(e);
            session.getTransaction().commit();
            setMessAdd("Thêm dữ liệu thành công");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            session.getTransaction().rollback();
            setMessAdd("Thêm dữ liệu không thành công");

        } finally {
            session.close();
        }
    }

    //set màu cho button ds2 is2 là màu khi hover
       public void setShadowBtn(Color ds, Color is, Color ds2, Color is2, Button btn, Button hover) {

        DropShadow drop = new DropShadow();
        drop.setRadius(127);
        drop.setHeight(130);
        drop.setWidth(255);
        drop.setSpread(0.5);
        drop.setColor(ds);

        InnerShadow inner = new InnerShadow();
        inner.setRadius(29.5);
        inner.setHeight(15);
        inner.setWidth(15);
        inner.setChoke(0.4);
        inner.setColor(is);

        Reflection ref = new Reflection();
        ref.setFraction(0.5);

        inner.setInput(ref);
        drop.setInput(inner);
        btn.setEffect(drop);

        hover.setOnMouseEntered((MouseEvent event)
                -> {
            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(127);
            dropShadow.setHeight(150);
            dropShadow.setWidth(255);
            dropShadow.setSpread(0.5);
            dropShadow.setColor(ds2);

            InnerShadow innerShadow = new InnerShadow();
            innerShadow.setRadius(29.5);
            innerShadow.setHeight(15);
            innerShadow.setWidth(15);
            innerShadow.setChoke(0.4);
            innerShadow.setColor(is2);

            Reflection reflection = new Reflection();
            reflection.setFraction(0.5);

            innerShadow.setInput(reflection);
            dropShadow.setInput(innerShadow);
            btn.setEffect(dropShadow);
        });

        hover.setOnMouseExited((MouseEvent event)
                -> {
            DropShadow drop2 = new DropShadow();
            drop2.setRadius(127);
            drop2.setHeight(150);
            drop2.setWidth(255);
            drop2.setSpread(0.5);
            drop2.setColor(ds);

            InnerShadow inner2 = new InnerShadow();
            inner2.setRadius(29.5);
            inner2.setHeight(15);
            inner2.setWidth(15);
            inner2.setChoke(0.4);
            inner2.setColor(is);

            Reflection ref2 = new Reflection();
            ref2.setFraction(0.5);

            inner2.setInput(ref2);
            drop2.setInput(inner2);
            btn.setEffect(drop2);
        });

    }
//set màu cho label ds2 is2 là màu khi hover
    public void setShadowLB(Color ds, Color is, Color ds2, Color is2, Label btn, AnchorPane hover) {

        DropShadow drop = new DropShadow();
//        drop.setOffsetY(-50);
        drop.setRadius(127);
        drop.setHeight(255);
        drop.setWidth(255);
        drop.setSpread(0.5);
        drop.setColor(ds);

        InnerShadow inner = new InnerShadow();
        inner.setRadius(29.5);
        inner.setHeight(15);
        inner.setWidth(15);
        inner.setChoke(0.4);
        inner.setColor(is);

        Reflection ref = new Reflection();
        ref.setFraction(0.5);

        inner.setInput(ref);
        drop.setInput(inner);
        btn.setEffect(drop);

        hover.setOnMouseEntered((MouseEvent event)
                -> {
            DropShadow dropShadow = new DropShadow();
//            dropShadow.setOffsetY(-50);
            dropShadow.setRadius(127);
            dropShadow.setHeight(150);
            dropShadow.setWidth(255);
            dropShadow.setSpread(0.5);
            dropShadow.setColor(ds2);

            InnerShadow innerShadow = new InnerShadow();
            innerShadow.setRadius(29.5);
            innerShadow.setHeight(15);
            innerShadow.setWidth(15);
            innerShadow.setChoke(0.4);
            innerShadow.setColor(is2);

            Reflection reflection = new Reflection();
            reflection.setFraction(0.5);

            innerShadow.setInput(reflection);
            dropShadow.setInput(innerShadow);
            btn.setEffect(dropShadow);
        });

        hover.setOnMouseExited((MouseEvent event)
                -> {
            DropShadow drop2 = new DropShadow();
//            drop2.setOffsetY(-50);
            drop2.setRadius(127);
            drop2.setHeight(150);
            drop2.setWidth(255);
            drop2.setSpread(0.5);
            drop2.setColor(ds);

            InnerShadow inner2 = new InnerShadow();
            inner2.setRadius(29.5);
            inner2.setHeight(15);
            inner2.setWidth(15);
            inner2.setChoke(0.4);
            inner2.setColor(is);

            Reflection ref2 = new Reflection();
          
            ref2.setFraction(0.5);

            inner2.setInput(ref2);
            drop2.setInput(inner2);
            btn.setEffect(drop2);
        });

    }
    
    //set màu cho label ds2 is2 là màu khi hover
    public void setShadowLB2(Color ds, Color is, Color ds2, Color is2, Label btn, AnchorPane hover) {

        DropShadow drop = new DropShadow();
//        drop.setOffsetY(-50);
        drop.setRadius(127);
        drop.setHeight(255);
        drop.setWidth(255);
        drop.setSpread(0.5);
        drop.setColor(ds);

        InnerShadow inner = new InnerShadow();
        inner.setRadius(29.5);
        inner.setHeight(15);
        inner.setWidth(15);
        inner.setChoke(0.4);
        inner.setColor(is);

//        Reflection ref = new Reflection();
//        ref.setFraction(0.5);
//
//        inner.setInput(ref);
        drop.setInput(inner);
        btn.setEffect(drop);

        hover.setOnMouseEntered((MouseEvent event)
                -> {
            DropShadow dropShadow = new DropShadow();
//            dropShadow.setOffsetY(-50);
            dropShadow.setRadius(127);
            dropShadow.setHeight(150);
            dropShadow.setWidth(255);
            dropShadow.setSpread(0.5);
            dropShadow.setColor(ds2);

            InnerShadow innerShadow = new InnerShadow();
            innerShadow.setRadius(29.5);
            innerShadow.setHeight(15);
            innerShadow.setWidth(15);
            innerShadow.setChoke(0.4);
            innerShadow.setColor(is2);

//            Reflection reflection = new Reflection();
//            reflection.setFraction(0.5);
//
//            innerShadow.setInput(reflection);
            dropShadow.setInput(innerShadow);
            btn.setEffect(dropShadow);
        });

        hover.setOnMouseExited((MouseEvent event)
                -> {
            DropShadow drop2 = new DropShadow();
//            drop2.setOffsetY(-50);
            drop2.setRadius(127);
            drop2.setHeight(150);
            drop2.setWidth(255);
            drop2.setSpread(0.5);
            drop2.setColor(ds);

            InnerShadow inner2 = new InnerShadow();
            inner2.setRadius(29.5);
            inner2.setHeight(15);
            inner2.setWidth(15);
            inner2.setChoke(0.4);
            inner2.setColor(is);

//            Reflection ref2 = new Reflection();
//          
//            ref2.setFraction(0.5);
//
//            inner2.setInput(ref2);
            drop2.setInput(inner2);
            btn.setEffect(drop2);
        });

    }
}
