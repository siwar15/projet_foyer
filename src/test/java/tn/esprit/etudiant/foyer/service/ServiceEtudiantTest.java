package tn.esprit.etudiant.foyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;
import tn.esprit.tpfoyer.service.EtudiantServiceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Pour contrôler l'ordre des tests
public class ServiceEtudiantTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    private Etudiant etudiant;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        etudiant = new Etudiant(1L, "John", "Doe", 12345678L, new Date(), null);
    }

    @Test
    public void testRetrieveAllEtudiants() {
        // Arrange
        List<Etudiant> etudiantList = Arrays.asList(etudiant);
        when(etudiantRepository.findAll()).thenReturn(etudiantList);

        // Act
        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        // Assert
        assertEquals(1, result.size());
        assertEquals(etudiant, result.get(0));
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    public void testRetrieveEtudiant() {
        // Arrange
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        // Act
        Etudiant result = etudiantService.retrieveEtudiant(1L);

        // Assert
        assertEquals(etudiant, result);
        verify(etudiantRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddEtudiant() {
        // Arrange
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantService.addEtudiant(etudiant);

        // Assert
        assertEquals(etudiant, result);
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    public void testModifyEtudiant() {
        // Arrange
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantService.modifyEtudiant(etudiant);

        // Assert
        assertEquals(etudiant, result);
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    public void testRemoveEtudiant() {
        // Act
        etudiantService.removeEtudiant(1L);

        // Assert
        verify(etudiantRepository, times(1)).deleteById(1L);
    }
}

