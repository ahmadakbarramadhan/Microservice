package com.example.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.room.model.Room;

public interface RoomRepository extends JpaRepository<Room, Integer>{
	
	@Query("SELECT r.availibility FROM Room r WHERE r.roomNumber = ?1")
	String findRoomNumberStatus(Integer roomNumber);

}
