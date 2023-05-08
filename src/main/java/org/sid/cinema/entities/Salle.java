package org.sid.cinema.entities;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Salle {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int nombrePlace;
    @ManyToOne
    private Cinema cinema;
    @OneToMany(mappedBy="salle")
    private Collection<Place> places;
    @OneToMany(mappedBy="salle")
    private Collection<Projection> projections;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Collection<Place> getPlaces() {
        return places;
    }
    public void setPlaces(Collection<Place> places) {
        this.places = places;
    }
    public Collection<Projection> getProjections() {
        return projections;
    }
    public void setProjections(Collection<Projection> projections) {
        this.projections = projections;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getNombrePlace() {
        return nombrePlace;
    }
    public void setNombrePlace(int nombrePlace) {
        this.nombrePlace = nombrePlace;
    }
    public Cinema getCinema() {
        return cinema;
    }
    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }
}
