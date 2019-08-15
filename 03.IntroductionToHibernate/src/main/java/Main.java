import entities.Student;
import org.hibernate.Session;
import utils.HibernateUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;


public class Main {
    public static void main(String[] args) {



        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Student student = new Student();
        student.setName("Teo");
        student.setRegistrationDate(new Date());
        em.persist(student);
        em.getTransaction().commit();


        Session session = HibernateUtils.getSession();
        session.beginTransaction();

//        student.setName("Peshko");
//        student.setRegistrationDate(new Date());
//        session.save(student);
        System.out.println(session.get(Student.class,1));

        session.getTransaction().commit();
        session.close();
    }}
