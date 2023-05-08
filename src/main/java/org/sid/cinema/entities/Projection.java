package org.sid.cinema.entities;

import java.util.Collection;
import java.util.Date;

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
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Projection {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Date dateProjection;
    private double prix;
    @ManyToOne
    private Salle salle;
    @ManyToOne
    private Film film;
    @OneToMany(mappedBy="projection")
    private Collection<Ticket> tickets;
    
    @ManyToOne
    private Seance seance;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Collection<Ticket> getTickets() {
        return tickets;
    }
    public void setTickets(Collection<Ticket> tickets) {
        this.tickets = tickets;
    }
    public Film getFilm() {
        return film;
    }
    public Seance getSeance() {
        return seance;
    }
    public void setSeance(Seance seance) {
        this.seance = seance;
    }
    public Date getDateProjection() {
        return dateProjection;
    }
    public void setDateProjection(Date dateProjection) {
        this.dateProjection = dateProjection;
    }
    public double getPrix() {
        return prix;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }
    public Salle getSalle() {
        return salle;
    }
    public void setSalle(Salle salle) {
        this.salle = salle;
    }
	public void setFilm(Film film) {
		this.film = film;
	}
}
