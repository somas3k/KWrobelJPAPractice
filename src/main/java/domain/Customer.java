package domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer extends Company {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int customerId;

    private Double discount;

    public Customer() {
    }

    public Customer(String companyName, String street, String city, String zipCode, Double discount) {
        super(companyName, street, city, zipCode);
        this.discount = discount;
    }
}
