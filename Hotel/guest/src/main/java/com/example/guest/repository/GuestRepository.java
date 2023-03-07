package com.example.guest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.guest.model.Guest;

public interface GuestRepository extends JpaRepository<Guest, Integer>{

}
