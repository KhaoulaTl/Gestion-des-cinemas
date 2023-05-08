package org.sid.cinema.web;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.sid.cinema.dao.FilmRepository;
import org.sid.cinema.dao.TicketRepository;
import org.sid.cinema.entities.Film;
import org.sid.cinema.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import lombok.Data;

@RestController
public class CinemaRestController {
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private TicketRepository ticketRepository;
	@GetMapping(path = "/imageFilm/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	// La méthode image permet de récupérer l'image d'un film en se basant sur son ID. 
	//Elle utilise le type MIME MediaType.IMAGE_JPEG_VALUE 
	//pour produire une réponse contenant l'image en question.
	
	public byte[] image(@PathVariable(name = "id") Long id) throws Exception {
		Film f = filmRepository.findById(id).orElse(null);
		if (f == null) {
			throw new Exception("Film not found with id " + id);
		}
		String photoName = f.getPhoto();
		File file = new File(System.getProperty("user.home") + "/cinema/images/" + photoName + ".jpg");
		Path path = Paths.get(file.toURI());
		return Files.readAllBytes(path);
	}
	
	//La méthode payerTickets permet de réserver des tickets pour une projection de film.
	//Elle prend en entrée un objet TicketFrom contenant le nom du client 
	//et la liste des IDs des tickets à réserver
	
	@PostMapping("/payerTickets")
	@Transactional
	public List<Ticket> payerTickets(@RequestBody TicketFrom ticketFrom) {
		List<Ticket> listTickets = new ArrayList<>();
		ticketFrom.getTickets().forEach(idTicket -> {
			System.out.println(idTicket);
			Ticket ticket = ticketRepository.findById(idTicket).orElse(null);
			if (ticket == null) {
				throw new RuntimeException("Ticket not found with id " + idTicket);
			}
			ticket.setNomClient(ticketFrom.getNomClient());
			ticket.setReserve(true);
			ticketRepository.save(ticket);
			listTickets.add(ticket);
		});
		return listTickets;
	}
}
@Data
class TicketFrom {
	private String nomClient;
	private List<Long> tickets = new ArrayList<>();

	public String getNomClient() {
		return nomClient;
	}

	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}

	public List<Long> getTickets() {
	    return tickets;
	}
}
