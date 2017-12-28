package domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "COMPANIES")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@SecondaryTable(name = "ADDRESSES")
public abstract class Company {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int id;

//    @Embedded
//    private Address address;

    private String companyName;

    @Column(table = "ADDRESSES")
    private String street;

    @Column(table = "ADDRESSES")
    private String city;

    @Column(table = "ADDRESSES")
    private String zipCode;

    public Company() {
    }

    Company(String companyName, String street, String city, String zipCode) {
        this.companyName = companyName;
//        address = new Address(street, city);
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

//    public Company(String companyName, Address address){
//        this.companyName = companyName;
//        this.address = address;
//    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }


    @Override
    public String toString(){
        return "Company{categoryName=\"" + companyName + "\"; street=\"" + street + "\" city=\"" + city + "\"}";
    }

//    @Override
//    public String toString() {
//        return "Company{" +
//                "id=" + id +
//                ", address=" + address +
//                ", companyName='" + companyName + '\'' +
//                '}';
//    }
}
