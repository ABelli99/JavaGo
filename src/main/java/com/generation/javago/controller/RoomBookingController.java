package com.generation.javago.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.generation.javago.model.dto.roombooking.RoomBookingDTOFull;
import com.generation.javago.model.entity.RoomBooking;
import com.generation.javago.model.repository.RoomBookingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Controller per gestire le operazioni relative alle prenotazioni delle stanze.
 */
@CrossOrigin
@RestController
public class RoomBookingController {

    @Autowired
    RoomBookingRepository rbRepo;

    /**
     * Ottiene tutte le prenotazioni delle stanze.
     */
    @GetMapping("/bookings")
    public List<RoomBookingDTOFull> getAllRoomBookings() {
        return rbRepo.findAll().stream()
                .map(RoomBookingDTOFull::new)
                .collect(Collectors.toList());
    }

    /**
     * Aggiunge una nuova prenotazione della stanza.
     */
    
    @PostMapping("/bookings")
    public ResponseEntity<RoomBookingDTOFull> addRoomBooking(@RequestBody RoomBookingDTOFull roomBookingDTO) {
        RoomBooking roomBooking = roomBookingDTO.revertToRoomBooking();
        RoomBooking savedRoomBooking = rbRepo.save(roomBooking);
        return new ResponseEntity<>(new RoomBookingDTOFull(savedRoomBooking), HttpStatus.CREATED);
    }

    /**
     * Ottiene una specifica prenotazione della stanza in base all'ID fornito.
     */
    
    @GetMapping("/bookings/{id}")
    public ResponseEntity<RoomBookingDTOFull> getRoomBookingById(@PathVariable Integer id) {
        RoomBooking roomBooking = rbRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Room booking not found with id: " + id));
        return ResponseEntity.ok(new RoomBookingDTOFull(roomBooking));
    }

    /**
     * Aggiorna una prenotazione della stanza esistente in base all'ID fornito.
     */
    
    @PutMapping("/bookings/{id}")
    public ResponseEntity<RoomBookingDTOFull> updateRoomBooking(@PathVariable Integer id, @RequestBody RoomBookingDTOFull roomBookingDTO) {
        if (!rbRepo.existsById(id)) {
            throw new NoSuchElementException("Room booking not found with id: " + id);
        }
        RoomBooking roomBooking = roomBookingDTO.revertToRoomBooking();
        roomBooking.setId(id);
        RoomBooking updatedRoomBooking = rbRepo.save(roomBooking);
        return ResponseEntity.ok(new RoomBookingDTOFull(updatedRoomBooking));
    }

    /**
     * Elimina una prenotazione della stanza in base all'ID fornito.
     */
    
    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<String> deleteRoomBooking(@PathVariable Integer id) {
        if (!rbRepo.existsById(id)) {
            throw new NoSuchElementException("Room booking not found with id: " + id);
        }
        rbRepo.deleteById(id);
        return ResponseEntity.ok("Room booking with id " + id + " has been deleted.");
    }
    
    /**
     * Ottiene le prenotazioni delle stanze per un intervallo specifico di date.
     */
    @GetMapping("/bookings/date/{startDate}/{endDate}")
    public ResponseEntity<List<RoomBookingDTOFull>> getRoomBookingsByDateRange(@PathVariable String startDate, @PathVariable String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<RoomBooking> roomBookings = rbRepo.findAll();
        List<RoomBookingDTOFull> filteredRoomBookings = roomBookings.stream()
                .filter(booking -> booking.isBetween(start, end))
                .map(RoomBookingDTOFull::new)
                .collect(Collectors.toList());
        if (filteredRoomBookings.isEmpty()) {
            throw new NoSuchElementException("Nessuna prenotazione trovata nell'intervallo di date specificato: " + startDate + " - " + endDate);
        }
        return ResponseEntity.ok(filteredRoomBookings);
    }
    
    /**
     * Trova una prenotazione in base alle date di checkin e checkout specificate.
     */
    @GetMapping("/bookings/checkinout")
    public ResponseEntity<RoomBookingDTOFull> getRoomBookingByCheckInCheckOut(@RequestParam("checkInDate") String checkInDate, @RequestParam("checkOutDate") String checkOutDate) {
        LocalDate checkIn = LocalDate.parse(checkInDate);
        LocalDate checkOut = LocalDate.parse(checkOutDate);
        List<RoomBooking> roomBookings = rbRepo.findAll();
        for (RoomBooking booking : roomBookings) {
            if (booking.getCheck_in_date().isEqual(checkIn) && booking.getCheck_out_date().isEqual(checkOut)) {
                return ResponseEntity.ok(new RoomBookingDTOFull(booking));
            }
        }
        throw new NoSuchElementException("Nessuna prenotazione trovata per le date di check-in: " + checkInDate + " e check-out: " + checkOutDate);
    }

    
    /**
     * Ottiene tutte le prenotazioni relative a una specifica stanza in base al nome della stanza.
     */
    
    @GetMapping("/bookings/room/{roomName}")
    public ResponseEntity<List<RoomBookingDTOFull>> getRoomBookingsByRoomName(@PathVariable String roomName) {
        List<RoomBooking> roomBookings = rbRepo.findAll();
        List<RoomBookingDTOFull> filteredRoomBookings = roomBookings.stream()
                .filter(booking -> booking.getRoom().getRoom_name().equals(roomName))
                .map(RoomBookingDTOFull::new)
                .collect(Collectors.toList());
        if (filteredRoomBookings.isEmpty()) {
            throw new NoSuchElementException("Nessuna prenotazione trovata per la stanza: " + roomName);
        }
        return ResponseEntity.ok(filteredRoomBookings);
    }
    
    /**
     * Ottiene tutte le prenotazioni effettuate da un utente specifico in base all'indirizzo email dell'utente.
     */
    @GetMapping("/bookings/user/{userEmail}")
    public ResponseEntity<List<RoomBookingDTOFull>> getRoomBookingsByUserEmail(@PathVariable String userEmail) {
        List<RoomBooking> roomBookings = rbRepo.findAll();
        List<RoomBookingDTOFull> filteredRoomBookings = roomBookings.stream()
                .filter(booking -> booking.getUser().getEmail().equals(userEmail))
                .map(RoomBookingDTOFull::new)
                .collect(Collectors.toList());
        if (filteredRoomBookings.isEmpty()) {
            throw new NoSuchElementException("Nessuna prenotazione trovata per l'utente con email: " + userEmail);
        }
        return ResponseEntity.ok(filteredRoomBookings);
    }
    
    /**
     * Ottiene tutte le prenotazioni in corso o future in base alla disponibilità delle stanze.
     */
    @GetMapping("/bookings/disponibilita")
    public ResponseEntity<List<RoomBookingDTOFull>> getRoomBookingsByDisponibilta() {
        LocalDate currentDate = LocalDate.now();
        List<RoomBooking> roomBookings = rbRepo.findAll();
        List<RoomBookingDTOFull> filteredRoomBookings = roomBookings.stream()
                .filter(booking -> booking.getCheck_out_date().isAfter(currentDate))
                .map(RoomBookingDTOFull::new)
                .collect(Collectors.toList());
        if (filteredRoomBookings.isEmpty()) {
            throw new NoSuchElementException("Nessuna prenotazione in corso o futura trovata in base alla disponibilità delle stanze.");
        }
        return ResponseEntity.ok(filteredRoomBookings);
    }


}
