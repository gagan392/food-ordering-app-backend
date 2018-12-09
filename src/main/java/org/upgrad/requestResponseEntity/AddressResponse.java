package org.upgrad.requestResponseEntity;

import org.upgrad.models.States;

import java.util.Objects;

public class AddressResponse {

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AddressResponse[" +
                "id=" + id +
                ", flat_buil_number='" + flat_buil_number + '\'' +
                ", locality='" + locality + '\'' +
                ", city='" + city + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", state={" + state.getId()+"," +state.getStateName()+
                "}]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressResponse that = (AddressResponse) o;
        return id == that.id &&
                Objects.equals(flat_buil_number, that.flat_buil_number) &&
                Objects.equals(locality, that.locality) &&
                Objects.equals(city, that.city) &&
                Objects.equals(zipcode, that.zipcode) &&
                Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, flat_buil_number, locality, city, zipcode, state);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlat_buil_number() {
        return flat_buil_number;
    }

    public void setFlat_buil_number(String flat_buil_number) {
        this.flat_buil_number = flat_buil_number;
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

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }

    private int id;
    private String flat_buil_number;
    private String locality;
    private String city;
    private String zipcode;
    private States  state;
}
