package domain;

import exceptions.NotEnoughUnitsInStock;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TRANSACTIONS")
public class Transaction {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column(name = "transactionId")
    private int transactionNumber;

    private Integer quantity;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Product> products;

    public Transaction() {
    }

    public Transaction(int quantity){
        this.quantity = quantity;
    }

    private void addToOrder(int productQuantity){
        quantity+=productQuantity;
    }

    public void addProductToOrder(Product p, int productQuantity) throws NotEnoughUnitsInStock {
        if(products == null) products = new HashSet<>();

        p.takeFromStock(productQuantity);
        addToOrder(productQuantity);
        products.add(p);
        p.addToOrder(this);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Set<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionNumber=" + transactionNumber +
                ", quantity=" + quantity +
                '}';
    }


}
