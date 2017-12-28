package domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
//@Table(name = "COMPANIES")
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
//@SecondaryTable(name = "ADDRESSES")
public abstract class Company {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int id;

//    @Embedded
//    private Address address;

    private String companyName;

    //@Column(table = "ADDRESSES")
    private String street;

    //@Column(table = "ADDRESSES")
    private String city;

    //@Column(table = "ADDRESSES")
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
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
    //    @Override
//    public String toString() {
//        return "Company{" +
//                "id=" + id +
//                ", address=" + address +
//                ", companyName='" + companyName + '\'' +
//                '}';
//    }


    public String getCompanyName() {
        return companyName;
    }

    public int getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }
}
