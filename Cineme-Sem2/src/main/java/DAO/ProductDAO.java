/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.Product;
import Utils.HibernateUtils;
import java.time.Month;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author DONG
 */
public class ProductDAO extends GenericDAO<Product, String> {

    public List<Object[]> countOrderProduct(int year,int month) {
        int count = 0;
        Session ses = HibernateUtils.getFACTORY().openSession();
        ses.getTransaction().begin();
//        String hql = "SELECT A.productName, COUNT(*) FROM Product A inner join "
//                + "ProductBill B ON A.productId = B.product.productId  GROUP BY B.productName";
        String hql = "Select C.productName,  C.type, C.price,sum(A.quantity) FROM "
                + "Bill B inner join ProductBill A ON A.bill.billID = B.billID "
                + "inner join Product C on C.productId = A.product.productId ";
        Query query;
        if (month == 0) {
            hql += "where year(B.printDate) = :year group by C.productName , C.price, C.type";
            query = ses.createQuery(hql);
            query.setParameter("year", year);
        } else {
            hql += " where month(B.printDate) = :month and year(B.printDate) = :year group by C.productName , C.price, C.type";
            query = ses.createQuery(hql);
            query.setParameter("month", month);
            query.setParameter("year", year);
        }
        List<Object[]> list = query.getResultList();
        ses.getTransaction().commit();
        ses.close();
        if (list.isEmpty()) {
            System.out.println("Khong co san pham nao duoc mua");
        }
//        list.forEach((t) -> {
//            System.out.println(t[0] + ": " + t[1] + " " + t[2] + " " + t[3]);
//        });
        return list;
    }

//    public static void main(String[] args) {
//        try {
//            ProductDAO dao = new ProductDAO();
//
//            dao.countOrderProduct(Month.APRIL.getValue());
//        } catch (Exception ex) {
//            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
