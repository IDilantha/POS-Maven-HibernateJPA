package db;

import entity.Customer;
import entity.Item;
import entity.Order;
import entity.OrderDetail;
import javafx.scene.control.Alert;
import lk.ijse.dep.crypto.DEPCrypt;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class JPAUtil {
    private static String username;
    private static String password;
    private static String db;
    private static String port;
    private static String ip;

    private static EntityManagerFactory entityManagerFactory = buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory() {
        File file = new File("src/main/resources/application.properties");
        Properties properties = new Properties();

        try(FileInputStream fis = new FileInputStream(file)){
            properties.load(fis);
        }catch(Exception e){
            new Alert(Alert.AlertType.ERROR,"FIS EKE AULA");
        }
        username = DEPCrypt.decode(properties.getProperty("hibernate.connection.username"),"123");
        password = DEPCrypt.decode(properties.getProperty("hibernate.connection.password"),"123");
        db = properties.getProperty("pos.db");
        port = properties.getProperty("pos.port");
        ip = properties.getProperty("pos.ip");

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("POS-HibernateJPA", properties);
        return entityManagerFactory;
    }

    public static EntityManagerFactory getEntityManagerFactory(){
        return entityManagerFactory;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getDb() {
        return db;
    }

    public static String getPort() {
        return port;
    }

    public static String getIp() {
        return ip;
    }
}
