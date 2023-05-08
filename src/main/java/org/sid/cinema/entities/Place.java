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
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Place {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)

	private Long id;
	private int numero;
	private double longitude,latidude,altitude;
	@ManyToOne
	private Salle salle;
	@OneToMany(mappedBy="place")
	private Collection<Ticket> tickets;
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatidude() {
		return latidude;
	}
	public void setLatidude(double latidude) {
		this.latidude = latidude;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	public void setSalle(Salle salle2) {
		// TODO Auto-generated method stub
		
	}
}