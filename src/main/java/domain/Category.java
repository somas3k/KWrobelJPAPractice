package domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CATEGORIES")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int categoryID;

    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
    private Set<Product> products;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public void addProductToCategory(Product p){
        if(products == null) products = new HashSet<>();
        products.add(p);
    }
}
