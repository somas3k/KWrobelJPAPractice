package domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SUPPLIERS")
public class Supplier {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int id;

    @Embedded
    private Address address;

    private String companyName;
//    private String street;
//    private String city;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.PERSIST)
    private Set<Product> productSet;

    public Supplier() {
    }

    public Supplier(String companyName, String street, String city) {
        this.companyName = companyName;
        address = new Address(street, city);
//        this.street = street;
//        this.city = city;
    }

    public Supplier(String companyName, Address address){
        this.companyName = companyName;
        this.address = address;
    }

//    public void setStreet(String street) {
//        this.street = street;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }

    public void addProduct(Product product){
        product.setSupplier(this);
        if(productSet == null) productSet = new HashSet<>();
        productSet.add(product);
    }

    public Set<Product> getProductSet() {
        return productSet;
    }

//    @Override
//    public String toString(){
//        return "Supplier{categoryName=\"" + companyName + "\"; street=\"" + street + "\" city=\"" + city + "\"}";
//    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", address=" + address +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
