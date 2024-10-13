package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;
import tn.esprit.tpfoyer.service.ReservationServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllReservations() {
        // Arrange
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        when(reservationRepository.findAll()).thenReturn(List.of(reservation1, reservation2));

        // Act
        List<Reservation> result = reservationService.retrieveAllReservations();

        // Assert
        assertEquals(2, result.size());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveReservation() {
        // Arrange
        Reservation reservation = new Reservation();
        when(reservationRepository.findById("1")).thenReturn(Optional.of(reservation));

        // Act
        Reservation result = reservationService.retrieveReservation("1");

        // Assert
        assertNotNull(result);
        verify(reservationRepository, times(1)).findById("1");
    }

    @Test
    void testAddReservation() {
        // Arrange
        Reservation reservation = new Reservation();
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        // Act
        Reservation result = reservationService.addReservation(reservation);

        // Assert
        assertEquals(reservation, result);
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void testModifyReservation() {
        // Arrange
        Reservation reservation = new Reservation();
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        // Act
        Reservation result = reservationService.modifyReservation(reservation);

        // Assert
        assertEquals(reservation, result);
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void testRemoveReservation() {
        // Act
        reservationService.removeReservation("1");

        // Assert
        verify(reservationRepository, times(1)).deleteById("1");
    }

    @Test
    void testFindReservationsByDateAndStatus() {
        // Arrange
        Date date = new Date();
        boolean status = true;
        when(reservationRepository.findAllByAnneeUniversitaireBeforeAndEstValide(date, status))
                .thenReturn(List.of());

        // Act
        List<Reservation> result = reservationService.trouverResSelonDateEtStatus(date, status);

        // Assert
        assertNotNull(result);
        verify(reservationRepository, times(1))
                .findAllByAnneeUniversitaireBeforeAndEstValide(date, status);
    }
}
