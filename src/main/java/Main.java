import domain.*;
import domain.Transaction;
import exceptions.NotEnoughUnitsInStock;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class Main {

    private static final EntityManagerFactory emf;

    static{
        emf = Persistence.createEntityManagerFactory("KWrobelJPAPractice");
    }

    private static void populateData(){
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
        Supplier s2 = new Supplier("Warzywniak", "Łomżyńska 4", "Sosnowiec", "41-219", "1111-1111-2222-2222-3333-4444");
        em.persist(s2);
        s2.addProduct(p1); s2.addProduct(p2); s2.addProduct(p3); s2.addProduct(p4);

        Product p5 = new Product("Pralka", 20, c2);
        Product p6 = new Product("Lodówka", 10, c2);
        Product p7 = new Product("Mikser", 50, c2);

//        //em.persist(p5); em.persist(p6); em.persist(p7);

        Supplier s3 = new Supplier("LG", "1 Maja 3", "Warszawa", "00-012");
        Customer cu1 = new Customer("Auchan", "Zuzanny 20", "Sosnowiec", "41-200", 0.12);
        Customer cu2 = new Customer("BI1", "Nowa 3", "Czeladź", "44-100", 0.08);
        em.persist(cu1);
        em.persist(cu2);
        em.persist(s3);
        s3.addProduct(p5);
        s3.addProduct(p6);
        s3.addProduct(p7);
//
        Transaction o1 = new Transaction(cu1);
        Transaction o2 = new Transaction(cu2);
        Transaction o3 = new Transaction(cu1);
        em.persist(o1);
        em.persist(o2);
        em.persist(o3);
        try {
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
        }
        catch (NotEnoughUnitsInStock e){
            e.printStackTrace();
        }
        etx.commit();
//        System.out.println();
//        String suppName = "Warzywniak";
//        Supplier s = em.createQuery("from Supplier as s where s.companyName=:name", Supplier.class).setParameter("name", suppName).getSingleResult();
//        System.out.println();
//        System.out.println(s);
//        System.out.println();
//        String custName = "Auchan";
//        Customer c = em.createQuery("from Customer as c where c.companyName=:name", Customer.class).setParameter("name", custName).getSingleResult();
//        System.out.println();
//        System.out.println(c);
//        int transId = 7;
//        //Company s = em.createQuery("from Company as s where s.companyName=:name", Company.class).setParameter("name", suppName).getSingleResult();
//        Transaction t = em.createQuery("from Transaction as t where t.id=:id", Transaction.class).setParameter("id", transId).getSingleResult();
//        System.out.println();
//        for(Product p : t.getProducts()){
//            System.out.println(p);
//        }
//        System.out.println();
//        String productName = "Pomidor";
//        Product p = em.createQuery("from Product as product where product.productName=:name", Product.class).setParameter("name", productName).getSingleResult();
//
//        System.out.println();
//        for(Transaction tt : p.getTransactions()){
//            System.out.println(tt);
//        }
        em.close();
    }

    private static String prepareTable(String type){
        StringBuilder sb = new StringBuilder();
        EntityManager em = emf.createEntityManager();
        switch(type){
            case "customers":
                List<Customer> customers = em.createQuery("from Customer", Customer.class).getResultList();
                sb.append("<tr><th>ID</th><th>COMPANY_NAME</th><th>STREET</th><th>ZIP_CODE</th><th>CITY</th><th>DISCOUNT</th></tr>");
                for(Customer c : customers){
                    sb.append("<tr><th>").append(c.getId()).append("</th><th>").append(c.getCompanyName()).append("</th><th>").
                            append(c.getStreet()).append("</th><th>").append(c.getZipCode()).append("</th><th>").
                            append(c.getCity()).append("</th><th>").append(c.getDiscount()).append("</th></tr>");
                }
                break;
            case "products":
                List<Product> products = em.createQuery("from Product", Product.class).getResultList();
                sb.append("<tr><th>ID</th><th>PRODUCT_NAME</th><th>UNITS_IN_STOCK</th><th>CATEGORY_ID</th><th>SUPPLIER_ID</th></tr>");
                for(Product p : products){
                    sb.append("<tr><th>").append(p.getProductId()).append("</th><th>").append(p.getProductName()).append("</th><th>").
                            append(p.getUnitsInStock()).append("</th><th>").append(p.getCategory().getCategoryID()).append("</th><th>").
                            append(p.getSupplier().getId()).append("</th></tr>");
                }
                break;
            case "suppliers":
                List<Supplier> suppliers = em.createQuery("from Supplier", Supplier.class).getResultList();
                sb.append("<tr><th>ID</th><th>COMPANY_NAME</th><th>STREET</th><th>ZIP_CODE</th><th>CITY</th><th>BANK_ACCOUNT_NUMBER</th></tr>");
                for(Supplier s : suppliers){
                    sb.append("<tr><th>").append(s.getId()).append("</th><th>").append(s.getCompanyName()).append("</th><th>").
                            append(s.getStreet()).append("</th><th>").append(s.getZipCode()).append("</th><th>").
                            append(s.getCity()).append("</th><th>").append(s.getBankAccountNumber()).append("</th></tr>");
                }
                break;
            case "transactions":
                List<Transaction> transactions = em.createQuery("from Transaction ", Transaction.class).getResultList();
                sb.append("<tr><th>TRANSACTION_ID</th><th>QUANTITY</th><th>CUSTOMER_ID</th></tr>");
                for(Transaction t : transactions){
                    sb.append("<tr><th>").append(t.getTransactionNumber()).append("</th><th>").append(t.getQuantity()).append("</th><th>").
                            append(t.getCustomer().getId()).append("</th></tr>");
                }
                break;
        }
        em.close();
        return sb.toString();
    }

    public static void main(final String[] args){
        populateData();
        staticFileLocation("/public");

        get("/", (request, response) -> {
            Map<String, String> model = new HashMap<>();
            model.put("template", "templates/hello.vtl" );
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        get("/show_table", (request, response) -> {
            String type = request.queryParams("table");

            Map<String, String> model = new HashMap<>();
            model.put("type", type);
            model.put("table", prepareTable(type));
            model.put("template", "templates/show_table.vtl" );
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        get("/order_form", (request, response) -> {
            Map<String, String> model = new HashMap<>();
            EntityManager em = emf.createEntityManager();
            List<Customer> customers = em.createQuery("from Customer", Customer.class).getResultList();
            List<Product> products = em.createQuery("from Product", Product.class).getResultList();
            em.close();
            StringBuilder sb1 = new StringBuilder();
            for(Customer c : customers){
                sb1.append("<option value=\"").append(c.getCompanyName()).append("\">").append(c.getCompanyName()).append("</option>\n");
            }
            StringBuilder sb2 = new StringBuilder();
            for(Product p : products){
                sb2.append("<option value=\"").append(p.getProductName()).append("\">").append(p.getProductName()).append("</option>\n");
            }
            model.put("options_1", sb1.toString());
            model.put("options_2", sb2.toString());
            model.put("template", "templates/order_form.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());


        get("/check_order", (request, response) -> {
            Map<String, String> model = new HashMap<>();
            String companyName = request.queryParams("customer");
            String productName = request.queryParams("product");
            int units = Integer.parseInt(request.queryParams("units"));
            EntityManager em = emf.createEntityManager();
            EntityTransaction etx = em.getTransaction();
            etx.begin();
            Product p = em.createQuery("from Product as p where p.productName=:name", Product.class).setParameter("name", productName).getSingleResult();
            Customer c = em.createQuery("from Customer as c where c.companyName=:name", Customer.class).setParameter("name", companyName).getSingleResult();
            Transaction t = new Transaction(c);
            try{
                t.addProductToOrder(p, units);
            }
            catch (NotEnoughUnitsInStock e){
                model.put("template", "templates/error_with_order.vtl");
                return new ModelAndView(model, "templates/layout.vtl");
            }
            em.persist(t);
            etx.commit();
            em.close();
            model.put("customer", companyName);
            model.put("product", productName);
            model.put("units", Integer.toString(units));
            model.put("template", "templates/order.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());
    }
}