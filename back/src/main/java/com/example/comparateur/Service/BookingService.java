
/*
package com.example.comparateur.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Utils.ApiResponse;
import com.example.comparateur.Entity.Booking;
import com.example.comparateur.Entity.BookingStatus;
import com.example.comparateur.Entity.Voiture;
import com.example.comparateur.Repository.BookingRepository;
import com.example.comparateur.Repository.VoitureRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // ✅ Create a new booking (voitureId passed as ID)
    @Transactional
    public ResponseEntity<ApiResponse> createBooking(Long voitureId, Booking booking) {
        try {
            // Check if voiture exists
            Optional<Voiture> voiture = voitureRepository.findById(voitureId);
            if (voiture.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Invalid voiture_id"));
            }

            // Validate booking details
            if (booking.getStartDate() == null || booking.getEndDate() == null) {
                return ResponseEntity.status(400).body(new ApiResponse(false, "Booking must have startDate and endDate."));
            }

            // Assign voiture to booking and save
            booking.setVoiture(voiture.get());
            booking.setBookingStatus(BookingStatus.PENDING);
            Booking savedBooking = bookingRepository.save(booking);

            // Notify admin about new booking
            String adminMessage = "🚗 New booking request: " + savedBooking.getCarName() +
                                " by " + savedBooking.getUsername() +
                                " (Email: " + savedBooking.getUserEmail() + ")";
            messagingTemplate.convertAndSend("/topic/booking-requests", adminMessage);

            return ResponseEntity.ok().body(new ApiResponse(true, "Your voiture is booked", savedBooking));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse(false, "Internal server error: " + e.getMessage()));
        }
    }

    // ✅ Get a single booking by ID
    public ResponseEntity<ApiResponse> getOneBooking(Long id) {
        try {
            Optional<Booking> booking = bookingRepository.findById(id);
            return booking.map(value -> ResponseEntity.ok().body(new ApiResponse(true, "Successful", value)))
                        .orElseGet(() -> ResponseEntity.status(404).body(new ApiResponse(false, "Booking not found")));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse(false, "Internal server error: " + e.getMessage()));
        }
    }

    // ✅ Get all bookings
    public ResponseEntity<Object> getAllBookings() {
        try {
            List<Booking> bookings = bookingRepository.findAll();
            return ResponseEntity.ok().body(new ApiResponse(true, "Successful", bookings));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse(false, "Internal server error: " + e.getMessage()));
        }
    }

    // ✅ Update booking
    @Transactional
    public ResponseEntity<Object> updateBooking(Long id, Booking updatedBooking) {
        try {
            Optional<Booking> optionalBooking = bookingRepository.findById(id);
            if (optionalBooking.isPresent()) {
                Booking booking = optionalBooking.get();

                // Update fields
                booking.setUsername(updatedBooking.getUsername());
                booking.setCarName(updatedBooking.getCarName());
                booking.setUserEmail(updatedBooking.getUserEmail());
                booking.setNbrJrs(updatedBooking.getNbrJrs());
                booking.setPhone(updatedBooking.getPhone());
                booking.setDescription(updatedBooking.getDescription());
                booking.setStartDate(updatedBooking.getStartDate());
                booking.setEndDate(updatedBooking.getEndDate());
                booking.setPickupLocation(updatedBooking.getPickupLocation());
                booking.setDropoffLocation(updatedBooking.getDropoffLocation());

                bookingRepository.save(booking);
                return ResponseEntity.ok().body(new ApiResponse(true, "Booking updated successfully", booking));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse(false, "Booking not found"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse(false, "Transaction failed: " + e.getMessage()));
        }
    }

    // ✅ Update booking status
    @Transactional
    public ResponseEntity<Object> updateBookingStatus(Long id, String status) {
        try {
            Optional<Booking> optionalBooking = bookingRepository.findById(id);
            if (optionalBooking.isPresent()) {
                Booking booking = optionalBooking.get();
                booking.setBookingStatus(Enum.valueOf(BookingStatus.class, status.toUpperCase()));

                bookingRepository.save(booking);
                return ResponseEntity.ok().body(new ApiResponse(true, "Booking status updated successfully", booking));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse(false, "Booking not found"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse(false, "Transaction failed: " + e.getMessage()));
        }
    }
}*/




package com.example.comparateur.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Utils.ApiResponse;
import com.example.comparateur.Entity.Booking;
import com.example.comparateur.Entity.BookingStatus;
import com.example.comparateur.Repository.BookingRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // ✅ WebSocket Messaging

    // ✅ Create a new booking (Independent from `Voiture`)
    @Transactional
    public ResponseEntity<Object> createBooking(Booking booking) {
        try {
            // Validate booking details
            if (booking.getStartDate() == null || booking.getEndDate() == null) {
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Booking must have startDate and endDate."));
            }

            booking.setBookingStatus(BookingStatus.PENDING);
            Booking savedBooking = bookingRepository.save(booking);

            // ✅ Notify Admin about new booking
            String adminMessage = "🚗 New booking request: " + savedBooking.getCarName() +
                                " by " + savedBooking.getUsername() +
                                " (Email: " + savedBooking.getUserEmail() + ")";
            messagingTemplate.convertAndSend("/topic/booking-requests", adminMessage);

            return ResponseEntity.ok().body(new ApiResponse(true, "Your booking is confirmed", savedBooking));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse(false, "Internal server error: " + e.getMessage()));
        }
    }

    // ✅ Get all bookings
    public ResponseEntity<Object> getAllBookings() {
        try {
            List<Booking> bookings = bookingRepository.findAll();
            return ResponseEntity.ok().body(new ApiResponse(true, "Successful", bookings));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse(false, "Internal server error: " + e.getMessage()));
        }
    }

    // ✅ Get a single booking by ID
    public ResponseEntity<Object> getOneBooking(Long id) {
        try {
            Optional<Booking> booking = bookingRepository.findById(id);
            if (booking.isPresent()) {
                return ResponseEntity.ok().body(new ApiResponse(true, "Successful", booking.get()));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse(false, "Booking not found"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse(false, "Internal server error: " + e.getMessage()));
        }
    }

    // ✅ Update a booking
    @Transactional
    public ResponseEntity<Object> updateBooking(Long id, Booking updatedBooking) {
        try {
            Optional<Booking> optionalBooking = bookingRepository.findById(id);
            if (optionalBooking.isPresent()) {
                Booking booking = optionalBooking.get();

                // Update fields
                booking.setUsername(updatedBooking.getUsername());
                booking.setCarName(updatedBooking.getCarName());
                booking.setUserEmail(updatedBooking.getUserEmail());
                booking.setNbrJrs(updatedBooking.getNbrJrs());
                booking.setPhone(updatedBooking.getPhone());
                booking.setDescription(updatedBooking.getDescription());
                booking.setStartDate(updatedBooking.getStartDate());
                booking.setEndDate(updatedBooking.getEndDate());
                booking.setPickupLocation(updatedBooking.getPickupLocation());
                booking.setDropoffLocation(updatedBooking.getDropoffLocation());

                bookingRepository.save(booking);
                return ResponseEntity.ok().body(new ApiResponse(true, "Booking updated successfully", booking));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse(false, "Booking not found"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse(false, "Transaction failed: " + e.getMessage()));
        }
    }

    // ✅ Update booking status
    @Transactional
    public ResponseEntity<Object> updateBookingStatus(Long id, String status) {
        try {
            Optional<Booking> optionalBooking = bookingRepository.findById(id);
            if (optionalBooking.isPresent()) {
                Booking booking = optionalBooking.get();
                booking.setBookingStatus(Enum.valueOf(BookingStatus.class, status.toUpperCase()));

                bookingRepository.save(booking);
                return ResponseEntity.ok().body(new ApiResponse(true, "Booking status updated successfully", booking));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse(false, "Booking not found"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse(false, "Transaction failed: " + e.getMessage()));
        }
    }

    // ✅ Delete a booking
    @Transactional
    public ResponseEntity<Object> deleteBooking(Long id) {
        try {
            if (bookingRepository.existsById(id)) {
                bookingRepository.deleteById(id);
                return ResponseEntity.ok().body(new ApiResponse(true, "Booking deleted successfully"));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse(false, "Booking not found"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse(false, "Error deleting booking: " + e.getMessage()));
        }
    }
}




