package org.sid.cinema.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.sid.cinema.entities.Film;
import java.util.stream.Stream;

import org.sid.cinema.dao.CinemaRepository;
import org.sid.cinema.dao.FilmRepository;
import org.sid.cinema.dao.PlaceRepository;
import org.sid.cinema.dao.TicketRepository;
import org.sid.cinema.dao.ProjectionRepository;
import org.sid.cinema.dao.SalleRepository;
import org.sid.cinema.dao.VilleRepository;
import org.sid.cinema.dao.SeanceRepository;
import org.sid.cinema.dao.CategorieRepository;
import org.sid.cinema.entities.Ville;
import org.sid.cinema.entities.Cinema;
import org.sid.cinema.entities.Ticket;
import org.sid.cinema.entities.Place;
import org.sid.cinema.entities.Salle;
import org.sid.cinema.entities.Projection;
import org.sid.cinema.entities.Seance;
import org.sid.cinema.entities.Categorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
//une implémentation d'un service d'initialisation de données
@Service
@Transactional
public class CinemaInitServiceImpl  implements ICinemaInitService{
	@Autowired
	private VilleRepository villeRepository;
	@Autowired
	private CinemaRepository cinemaRepository;
	@Autowired
	private SalleRepository salleRepository;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired 
	private SeanceRepository seanceRepository;
	@Autowired 
	private ProjectionRepository projectionRepository;
	@Autowired
	private FilmRepository filmRepository;
	@Autowired 
	private CategorieRepository categorieRepository;
	@Autowired 
	private TicketRepository ticketRepository;
	
	//initialise les villes dans lesquelles les cinémas sont situés en créant des objets Ville 
	//et en les sauvegardant en utilisant VilleRepository
	@Override
	public void initVilles() {
		Stream.of("Ben Arous","Monastir","Sousse","Tunisie").forEach(nameVille->{
			Ville ville=new Ville();
			ville.setName(nameVille);
			villeRepository.save(ville);
		});
		
	}

	//nitialise les cinémas en créant des objets Cinema pour chaque ville
	//en leur attribuant un nom et un nombre aléatoire de salles, 
	//puis en les sauvegardant en utilisant CinemaRepository
	@Override
	@Transactional
	public void initCinemas() {
		villeRepository.findAll().forEach(v-> {
		Stream.of("Pathé Azur City","Ciné Star","Le Palace","Le colisé")
		.forEach(nameCinema->{
			Cinema cinema=new Cinema();
			cinema.setName(nameCinema);
			cinema.setNombreSalles(3+(int)(Math.random()*7));
			cinema.setVille(v);
			cinemaRepository.save(cinema);
			});	
		});	
	}

	//initialise les salles de cinéma en créant des objets Salle
	//en leur attribuant un nom, un nombre aléatoire de places et en les associant au cinéma correspondant, 
	//puis en les sauvegardant en utilisant SalleRepository
	@Override
	public void initSalles() {
		cinemaRepository.findAll().forEach(cinema ->{
			for(int i = 0; i < cinema.getNombreSalles(); i++) {
				Salle salle = new Salle();
				salle.setName("Salle "+(i+1));
				salle.setCinema(cinema);
				salle.setNombrePlace(15+(int)(Math.random()*20));
				salleRepository.save(salle);
				
			}
		});
	}
//nitialise les places de chaque salle de cinéma
	@Override
	public void initPlaces() {
		salleRepository.findAll().forEach(salle->{
			for(int i=0;i<salle.getNombrePlace();i++) {
				Place place = new Place();
				place.setNumero(i+1);
				place.setSalle(salle);
				placeRepository.save(place);
				
			}
		});
	}
//nitialise les séances en créant des objets Seance pour chaque horaire prédéfini, 
	//en les sauvegardant en utilisant SeanceRepository.
	@Override
	public void initSeances() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Stream.of("12:00","15:00","19:00","21:00").forEach(s->{
			Seance seance = new Seance();
			try {
				seance.setHeureDebut(dateFormat.parse(s));
				seanceRepository.save(seance);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Override
	public void initCategories() {
		Stream.of("Drama","Comédie","Action","Fiction").forEach(cat->{
			Categorie categorie = new Categorie();
			categorie.setName(cat);
			categorieRepository.save(categorie);
		});
		
	}

	@Override
	public void initfilms() {
		double[] durees = new double[] {1,1.5,2,2.5,3};
		List<Categorie> categories = categorieRepository.findAll();
		Stream.of("Cat Women","Forrest Gump","Game Of Thrones","Green Book","Seigneur Des Anneaux","Spaider Man")
		.forEach(titreFilm->{
			Film film = new Film();
			film.setTitre(titreFilm);
			film.setDuree(durees[new Random().nextInt(durees.length)]);
			film.setPhoto(titreFilm.replaceAll(" ", "")+".jpg");
			film.setCategorie(categories.get(new Random().nextInt(categories.size())));
			filmRepository.save(film);
		});
		
	}

	@Override
	@PostConstruct
	public void initProjections() {
	    double[] prices = new double[] {30,50,60,70,90,100};
	    List<Film> films = filmRepository.findAll();
	    villeRepository.findAll().forEach(ville -> {
			List<Cinema> cinemas = (List<Cinema>) ville.getCinemas();
	        if (cinemas != null) {
	            cinemas.forEach(cinema -> {
	                List<Salle> salles = (List<Salle>) cinema.getSalles();
	                if (salles != null) {
	                    salles.forEach(salle -> {
	                        int index = new Random().nextInt(films.size()) ;
	                    	Film film = films.get(index);
	                            seanceRepository.findAll().forEach(seance -> {
	                                Projection projection = new Projection();
	                                projection.setDateProjection(new Date());
	                                projection.setFilm(film);
	                                projection.setPrix(prices[new Random().nextInt(prices.length)]);
	                                projection.setSeance(seance);
	                                projection.setSalle(salle);
	                                projectionRepository.save(projection);
	                            });
	                    });
	                }
	            });
	        }
	    });
	}
	@Override
	public void initTickets() {
		projectionRepository.findAll().forEach(p->{
			((Salle) p.getSalle()).getPlaces().forEach(place->{
				Ticket ticket = new Ticket();
				ticket.setPlace(place);
				ticket.setPrix(p.getPrix());
				ticket.setProjection(p);
				ticket.setReserve(false);
				ticketRepository.save(ticket);
			});
		});
	}
}
