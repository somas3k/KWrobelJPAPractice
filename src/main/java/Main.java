import domain.*;
import domain.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
//    private static final SessionFactory ourSessionFactory;
//
//    static {
//        try {
//            Configuration configuration = new Configuration();
//            configuration.configure();
//
//            ourSessionFactory = configuration.buildSessionFactory();
//        } catch (Throwable ex) {
//            throw new ExceptionInInitializerError(ex);
//        }
//    }
//
//    public static Session getSession() throws HibernateException {
//        return ourSessionFactory.openSession();
//    }
    private static final EntityManagerFactory emf;

    static{
        emf = Persistence.createEntityManagerFactory("KWrobelJPAPractice");
    }

    public static void main(final String[] args) throws Exception {
        EntityManager em = emf.createEntityManager();

        EntityTransaction etx = em.getTransaction();
        etx.begin();

        Category c1 = new Category("Warzywa");
        Category c2 = new Category("Sprzęt AGD");
        em.persist(c1); em.persist(c2);

        Product p1 = new Product("Pomidor", 10,c1);
        Product p2 = new Product("Ogórek", 30,c1);
        Product p3 = new Product("Buraki", 25,c1);
        Product p4 = new Product("Ziemniaki", 42,c1);
        //em.persist(p1); em.persist(p2); em.persist(p3); em.persist(p4);


        Supplier s2 = new Supplier("Warzywniak", "Łomżyńska 4", "Sosnowiec", "41-219");
        //em.persist(s2);
        s2.addProduct(p1); s2.addProduct(p2); s2.addProduct(p3); s2.addProduct(p4);

        Product p5 = new Product("Pralka", 20, c2);
        Product p6 = new Product("Lodówka", 10, c2);
        Product p7 = new Product("Mikser", 50, c2);

        //em.persist(p5); em.persist(p6); em.persist(p7);

        Supplier s3 = new Supplier("LG", "1 Maja 3", "Warszawa", "00-012");
        //em.persist(s3);
        s3.addProduct(p5);
        s3.addProduct(p6);
        s3.addProduct(p7);

        Transaction o1 = new Transaction(0);
        Transaction o2 = new Transaction(0);
        Transaction o3 = new Transaction(0);
        //em.persist(o1);
        //em.persist(o2);
        //em.persist(o3);

        o1.addProductToOrder(p1, 2);
        o1.addProductToOrder(p5, 4);
        o1.addProductToOrder(p3, 5);
        o1.addProductToOrder(p6, 2);
        o2.addProductToOrder(p1, 3);
        o2.addProductToOrder(p2, 12);
        o2.addProductToOrder(p4, 20);
        o3.addProductToOrder(p1, 1);
        o3.addProductToOrder(p4, 10);
        o3.addProductToOrder(p5, 4);
        o3.addProductToOrder(p7, 1);

        etx.commit();
        System.out.println();
        //String suppName = "Warzywniak";
        int transId = 7;
        //Company s = em.createQuery("from Company as s where s.companyName=:name", Company.class).setParameter("name", suppName).getSingleResult();
        Transaction t = em.createQuery("from Transaction as t where t.id=:id", Transaction.class).setParameter("id", transId).getSingleResult();
        System.out.println();
        for(Product p : t.getProducts()){
            System.out.println(p);
        }
        System.out.println();
        String productName = "Pomidor";
        Product p = em.createQuery("from Product as product where product.productName=:name", Product.class).setParameter("name", productName).getSingleResult();

        System.out.println();
        for(Transaction tt : p.getTransactions()){
            System.out.println(tt);
        }
        em.close();

    }
}