module com.group2.cineme.sem2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires hibernate.entitymanager;
    requires java.naming;
    requires java.base;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires java.persistence;
    requires com.jfoenix;
    requires tornadofx;
    requires javassist;
    requires fontawesomefx;
    opens Utils;
    opens POJO;
    opens com.group2.cineme.sem2 to javafx.fxml;
    exports com.group2.cineme.sem2;
}
