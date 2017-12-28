package domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
//@Table(name = "SUPPLIERS")
public class Supplier extends Company {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int supplierId;

    private String bankAccountNumber;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.PERSIST)
    private Set<Product> productSet;

    public Supplier() {
    }

    public Supplier(String companyName, String street, String city, String zipCode, String bankAccountNumber){
        super(companyName, street, city, zipCode);
        this.bankAccountNumber = bankAccountNumber;
    }

    public Supplier(String companyName, String street, String city, String zipCode) {
        super(companyName, street, city, zipCode);
    }

    public void addProduct(Product product){
        product.setSupplier(this);
        if(productSet == null) productSet = new HashSet<>();
        productSet.add(product);
    }

    public Set<Product> getProductSet() {
        return productSet;
    }

}
