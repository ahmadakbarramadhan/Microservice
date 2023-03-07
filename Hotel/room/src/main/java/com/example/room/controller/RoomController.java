package com.example.room.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.room.model.Room;
import com.example.room.repository.RoomRepository;

@EnableWebMvc
@RestController
@RequestMapping(path = "/room")
public class RoomController {
	@Autowired
	RoomRepository roomRepository;
	
	@GetMapping(path = "/findAll")
	public @ResponseBody List<Room> getGuests() throws Exception {
		List<Room> findAllRooms = roomRepository.findAll();
		return findAllRooms;
	}
	
	@GetMapping(path = "find/{id}")
	public @ResponseBody Optional<Room> findGuestById(@PathVariable("id") Integer id) {
		Optional<Room> roomData = roomRepository.findById(id);
		return roomData;
	}
	
	@GetMapping(path = "findByRoomNumber/{roomNumber}")
	public @ResponseBody String findGuestByRoomNumber(@PathVariable("roomNumber") Integer roomNumber) {
		String roomStatus = roomRepository.findRoomNumberStatus(roomNumber);
		return roomStatus;
	}
	
	@PutMapping(path = "/update/{id}")
	public @ResponseBody ResponseEntity<Room> updateGuest(@PathVariable("id") Integer id,
			@RequestBody Room room) throws Exception {
		Optional<Room> roomData = roomRepository.findById(id);
			if (roomData.isPresent()) {
				Room updatedRoom = roomData.get();
				updatedRoom.setId(room.getId());
				updatedRoom.setRoomType(room.getRoomType());
				updatedRoom.setRoomNumber(room.getRoomNumber());
				updatedRoom.setAvailibility(room.getAvailibility());
				return new ResponseEntity<>(roomRepository.save(updatedRoom), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
	}
	
	@PostMapping(path = "/create")
	public @ResponseBody ResponseEntity<Room> addGuest(@RequestBody Room room) throws Exception {
			try {
				Room newRoom = new Room();
				newRoom.setId(UUID.randomUUID().hashCode());
				newRoom.setRoomType(room.getRoomType());
				newRoom.setRoomNumber(room.getRoomNumber());
				newRoom.setAvailibility(room.getAvailibility());
				return new ResponseEntity<>(roomRepository.save(newRoom), HttpStatus.OK);
			} catch (Exception e){
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	@DeleteMapping(path = "/delete/{id}")
	public @ResponseBody ResponseEntity<Room> deleteGuestById(@PathVariable("id") Integer id) throws Exception {
		try {
			roomRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
