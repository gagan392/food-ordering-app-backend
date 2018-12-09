package org.upgrad.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="states")
public class States {

    @Id
    @Column(name="id")
    private int id;

    @Column(name="state_name")
    private String state_name;

    public States(){}



    public States(int id, String state_name) {
        this.id = id;

        this.state_name = state_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }



    @Override
    public String toString() {
        return "States[" +
                "id=" + id +
                ", stateName='" + state_name + '\'' +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        States states = (States) o;
        return id == states.id &&
                Objects.equals(state_name, states.state_name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, state_name);
    }
}
