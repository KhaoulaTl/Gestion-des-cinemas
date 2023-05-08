package org.sid.cinema.entities;
import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.AllArgsConstructor;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Cinema {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private double longitude, latitude,altitude;
	private int nombreSalles;
	@OneToMany(mappedBy="cinema")
	private Collection<Salle> salles;
	
	public Collection<Salle> getSalles() {
		return salles;
	}
	public void setSalles(Collection<Salle> salles) {
		this.salles = salles;
	}
	@ManyToOne
	private Ville ville;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public int getNombreSalles() {
		return nombreSalles;
	}
	public void setNombreSalles(int nombreSalles) {
		this.nombreSalles = nombreSalles;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	public Ville getVille() {
		return ville;
	}
	public void setVille(Ville ville) {
		this.ville = ville;
	}
}
	

