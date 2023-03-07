package com.example.guest.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.guest.model.Guest;
import com.example.guest.repository.GuestRepository;

@EnableWebMvc
@RestController
@RequestMapping(path = "/guest")
public class GuestController {
	@Autowired
	GuestRepository guestRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping(path = "/findAll")
	public @ResponseBody List<Guest> getGuests() throws Exception {
		List<Guest> findAllGuests = guestRepository.findAll();
		return findAllGuests;
	}
	
	@GetMapping(path = "find/{id}")
	public @ResponseBody Optional<Guest> findGuestById(@PathVariable("id") Integer id) {
		Optional<Guest> guestData = guestRepository.findById(id);
		return guestData;
	}
	
	@PutMapping(path = "/update/{id}")
	public @ResponseBody ResponseEntity<Guest> updateGuest(@RequestHeader("guest-auth-key") String authorization,
			@PathVariable("id") Integer id,
			@RequestBody Guest guest) throws Exception {
		Optional<Guest> guestData = guestRepository.findById(id);
			if (guestData.isPresent()) {
				Guest updatedGuest = guestData.get();
				updatedGuest.setId(guest.getId());
				updatedGuest.setName(guest.getName());
				updatedGuest.setPhoneNumber(guest.getPhoneNumber());
				updatedGuest.setRoomNumber(guest.getRoomNumber());
				return new ResponseEntity<>(guestRepository.save(updatedGuest), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
	}
	
	@GetMapping(path = "/getRoomStatus/{roomNumber}")
	public String getRoomStatus(@PathVariable("roomNumber") Integer roomNumber) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return restTemplate.exchange("http://localhost:8097/room/findByRoomNumber/"+ roomNumber, HttpMethod.GET,
				entity, String.class).getBody().toString();
	}
	
	@PostMapping(path = "/create")
	public @ResponseBody ResponseEntity<Guest> addGuest(@RequestHeader("guest-auth-key") String authorization, 
			@RequestBody Guest guest) throws Exception {
			try {
				Guest newGuest = new Guest();
				newGuest.setId(UUID.randomUUID().hashCode());
				newGuest.setName(guest.getName());
				newGuest.setPhoneNumber(guest.getPhoneNumber());
				String roomStatus = getRoomStatus(guest.getRoomNumber());
				System.out.println("STATUS = " + roomStatus);
				if ("AVAILABLE".equals(roomStatus)) {
					newGuest.setRoomNumber(guest.getRoomNumber());
				} else {
					return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				return new ResponseEntity<>(guestRepository.save(newGuest), HttpStatus.OK);
			} catch (Exception e){
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	@DeleteMapping(path = "/delete/{id}")
	public @ResponseBody ResponseEntity<Guest> deleteGuestById(@PathVariable("id") Integer id) throws Exception {
		try {
			guestRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
