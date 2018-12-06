package org.upgrad.models;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "flat_buil_number")
    private int flatBuildNumber;

    @Column(name = "locality")
    private String locality;

    @Column(name = "city")
    private String city;

    @Column(name = "zipcode")
    private String zipcode;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private States states;

    public Address() {
    }

    public Address(int flatBuildNumber, String locality, String city, String zipcode, States states) {
        this.flatBuildNumber = flatBuildNumber;
        this.locality = locality;
        this.city = city;
        this.zipcode = zipcode;
        this.states = states;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFlatBuildNumber() {
        return flatBuildNumber;
    }

    public void setFlatBuildNumber(int flatBuildNumber) {
        this.flatBuildNumber = flatBuildNumber;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public States getStates() {
        return states;
    }

    public void setStates(States states) {
        this.states = states;
    }
}
