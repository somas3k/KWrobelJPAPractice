package domain;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CUSTOMERS")
public class Customer extends Company {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int customerId;

    private Double discount;

    @OneToMany(mappedBy = "customer")
    private Set<Transaction> transactions;

    public Customer() {
    }

    public Customer(String companyName, String street, String city, String zipCode, Double discount) {
        super(companyName, street, city, zipCode);
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "discount=" + discount +
                "} " + super.toString();
    }

    void connectWithTransaction(Transaction t){
        if(transactions == null){
            transactions = new HashSet<>();
        }
        transactions.add(t);
    }


    public Double getDiscount() {
        return discount;
    }
}
