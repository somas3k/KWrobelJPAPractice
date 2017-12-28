package domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import exceptions.NotEnoughUnitsInStock;

@Entity
@Table(name = "PRODUCTS")
public class Product {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int productId;


    private String productName;
    private Integer unitsInStock;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="SUPPLIER_ID")
    private Supplier supplier;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="categoryId")
    private Category category;

    @ManyToMany(mappedBy = "products", cascade = CascadeType.PERSIST)
    private Set<Transaction> transactions;

    public Product() {
    }

    public Product(String productName, int unitsOnStock) {
        this.productName = productName;
        this.unitsInStock = unitsOnStock;
    }

    public Product(String productName, Integer unitsInStock, Category category) {
        this.productName = productName;
        this.unitsInStock = unitsInStock;
        this.category = category;
        category.addProductToCategory(this);
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Company getSupplier() {
        return supplier;
    }

    @Override
    public String toString(){
        return "Product{productName=\"" + productName + "\"; unitsOnStock=\"" + unitsInStock + "\"}";
    }

    private boolean isAvailableToOrder(int quantity) {
        return quantity <= unitsInStock;
    }

    public void takeFromStock(int quantity) throws NotEnoughUnitsInStock {
        if(isAvailableToOrder(quantity)){
            unitsInStock-=quantity;
        }
        else throw new NotEnoughUnitsInStock();
    }

    void addToOrder(Transaction o){
        if(transactions == null) transactions = new HashSet<>();
        transactions.add(o);
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductId() {
        return productId;
    }

    public Integer getUnitsInStock() {
        return unitsInStock;
    }

    public Category getCategory() {
        return category;
    }
}
