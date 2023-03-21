/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;


import Utils.HibernateUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javafx.scene.control.Alert;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;

    // Tất cả các hàm KHÔNG được gọi trong 1 phiên session khác.
public abstract class GenericDAO<T,A> //T là tên class, A là kiểu dữ liệu của id
{
     /**
     * @param A is id
     * @param T is class name
     */
    private String messAdd;
    private String messUpdate;
    private String messDelete;
    private String messGetbyID;
    private String messGetAll;
//1.    Hàm Insert dữ liệu, chỉ cần truyền 1 đối tượng "T" không cần liên kết với với database
    public void add(T object) throws Exception {
        Session session = HibernateUtils.getFACTORY().openSession();
        try {
            session.getTransaction().begin();
            session.save(object);
            session.getTransaction().commit();
            setMessAdd("Thêm dữ liệu thành công");
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
            setMessAdd("Thêm dữ liệu không thành công");
        } finally {
            session.close();
        }
    }
   
    
//2.    Hàm update dữ liệu, T bắt buộc phải có trang thái Transient or Detached (Không được liên kết với database)
    // Đối tượng T có thể tạo bằng từ khoá "New"  hoặc được lấy bởi hàm getByID() tự vieeesrt ở bên dưới, rồi truyền thẳng vào hàm này
    // Nếu đối tượng được lấy bới hàm mặc định get() của nó thì chỉ cần cài đặt 
    //  các giá trị mới cho T rồi đóng session là nó sẽ tự động update và không cần dùng hàm này
    public void update(T object) throws Exception {
        Session session = HibernateUtils.getFACTORY().openSession();
        try {
            session.getTransaction().begin();
            session.update(object);
            session.getTransaction().commit();
            setMessUpdate("Chỉnh sửa lữ liệu thành công");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
            setMessDelete("Chỉnh sửa dữ liệu không thành công");
        } finally {
            session.close();
        }
    }
    
    
 //3.   Hàm xoá 1 dòng dữ liệu có id tương ứng, T bắt buộc phải ở trạng thái Transient or Detached (Không được liên kết với database)
    public void delete(A id, Class<T> clazz) throws Exception {
        Session session = HibernateUtils.getFACTORY().openSession();
        try {
            T b = session.find(clazz, id);
            session.getTransaction().begin();
            session.delete(b);
            session.getTransaction().commit();
            setMessDelete("Xóa dữ liệu thành công");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
            setMessDelete("Xóa dữ không liệu thành công");
        } finally {
            session.close();
        }
    }

    
 //4.   Lấy dòng dữ liệu có id tương ứng
    public  T getById(A id, Class<T> clazz) throws Exception {
        Session session = HibernateUtils.getFACTORY().openSession();
        T b = null;
        try {
            b = session.find(clazz, id);
            if (b == null) {
                throw new Exception();
            }
            setMessGetbyID("Lấy dữ  liệu thành công");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            setMessGetbyID("Lấy dữ liệu không thành công");
        } finally {
            session.close();

        }
        return b;
    }
    
    
 //5.   Hàm lấy danh sách tất cả dữ liệu trong 1 bảng
    public List<T> getAll(String className) throws Exception{

        List<T> list = new LinkedList<>();
        Session session = HibernateUtils.getFACTORY().openSession();
        try {
            if (!"FilmGenre".contains(className)) {
                throw new Exception();
            }
            session.getTransaction().begin();
            var hql = "FROM " + className;
            Query query = session.createQuery(hql);
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
    
    
// 6.   Lấy danh sách các row của 1 "Column" có chứa chuỗi "value"
    public List<T> getByColumn(String className,String Column,String value) throws Exception{
        List<T> list = new ArrayList<>();
        Session session = HibernateUtils.getFACTORY().openSession();
        try {
            session.getTransaction().begin();
            var hql = "FROM " + className + " WHERE " + Column + " LIKE '%" + value + "%'";
            Query query = session.createQuery(hql);
            list= query.getResultList();
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

    /**
     * @return the messGetAll
     */
    public String getMessGetAll() {
        return messGetAll;
    }

    /**
     * @param messGetAll the messGetAll to set
     */
    public void setMessGetAll(String messGetAll) {
        this.messGetAll = messGetAll;
    }

    /**
     * @return the messAdd
     */
    public String getMessAdd() {
        return messAdd;
    }

    /**
     * @param messAdd the messAdd to set
     */
    public void setMessAdd(String messAdd) {
        this.messAdd = messAdd;
    }

    /**
     * @return the messUpdate
     */
    public String getMessUpdate() {
        return messUpdate;
    }

    /**
     * @param messUpdate the messUpdate to set
     */
    public void setMessUpdate(String messUpdate) {
        this.messUpdate = messUpdate;
    }

    /**
     * @return the messDelete
     */
    public String getMessDelete() {
        return messDelete;
    }

    /**
     * @param messDelete the messDelete to set
     */
    public void setMessDelete(String messDelete) {
        this.messDelete = messDelete;
    }

    /**
     * @return the messGetbyID
     */
    public String getMessGetbyID() {
        return messGetbyID;
    }

    /**
     * @param messGetbyID the messGetbyID to set
     */
    public void setMessGetbyID(String messGetbyID) {
        this.messGetbyID = messGetbyID;
    }

}
