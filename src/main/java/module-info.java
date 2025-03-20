module com.hsf302.spring.javafx01 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.hsf302.spring.javafx01 to javafx.fxml;
    exports com.hsf302.spring.javafx01;
    opens com.hsf302.spring.javafx01.dto to javafx.fxml;
    exports com.hsf302.spring.javafx01.dto;
    opens com.hsf302.spring.javafx01.entity to javafx.fxml, org.hibernate.orm.core; // Sửa dòng này
    exports com.hsf302.spring.javafx01.entity;
}