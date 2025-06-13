module org.menus.finanzas {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.desktop;
    requires layout;
    requires kernel;
    requires org.junit.jupiter.api;
    requires junit;


    opens BBDD to org.hibernate.orm.core;
    opens org.menus.finanzas to javafx.fxml;

    exports org.menus.finanzas;
    exports BBDD;

}

