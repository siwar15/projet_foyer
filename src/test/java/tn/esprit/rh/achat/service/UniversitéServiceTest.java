package tn.esprit.rh.achat.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer.TpFoyerApplication;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.UniversiteRepository;
import tn.esprit.tpfoyer.service.UniversiteServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Pour contrôler l'ordre des tests
public class UniversitéServiceTest {

    @InjectMocks
    UniversiteServiceImpl universiteService; // Service à tester

    @Mock
    UniversiteRepository universiteRepository; // Mocker le repository

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialiser les mocks
    }

    // 1. Test pour récupérer toutes les universités
    @Test
    @Order(1) // L'ordre du test
    public void testRetrieveAllUniversites() {
        // Données simulées
        Universite u1 = new Universite(1L, "Université de Test", "Bizerte", null);
        Universite u2 = new Universite(2L, "Université d'Exemple", "Bizerte2", null);

        // Simuler le comportement du repository
        when(universiteRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        // Appeler la méthode à tester
        List<Universite> universites = universiteService.retrieveAllUniversites();

        // Vérifications
        assertNotNull(universites);  // La liste ne doit pas être nulle
        assertEquals(2, universites.size());  // Il doit y avoir 2 universités dans la liste
        verify(universiteRepository, times(1)).findAll();  // Vérifier que findAll() est appelé une seule fois
    }

    // 2. Test pour récupérer une université par ID
    @Test
    @Order(2)
    public void testRetrieveUniversite() {
        Universite u1 = new Universite(1L, "Université de Test", "Bizerte", null);

        // Simuler le comportement du repository
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(u1));

        // Appeler la méthode à tester
        Universite universite = universiteService.retrieveUniversite(1L);

        // Vérifications
        assertNotNull(universite);
        assertEquals("Université de Test", universite.getNomUniversite());
        verify(universiteRepository, times(1)).findById(1L);  // Vérifier que findById() est appelé une seule fois
    }

    // 3. Test pour ajouter une université
    @Test
    @Order(3)
    public void testAddUniversite() {
        Universite u1 = new Universite(1L, "Université de Test", "Bizerte", null);

        // Simuler le comportement du repository
        when(universiteRepository.save(u1)).thenReturn(u1);

        // Appeler la méthode à tester
        Universite addedUniversite = universiteService.addUniversite(u1);

        // Vérifications
        assertNotNull(addedUniversite);
        assertEquals("Université de Test", addedUniversite.getNomUniversite());
        verify(universiteRepository, times(1)).save(u1);  // Vérifier que save() est appelé une seule fois
    }

    // 4. Test pour modifier une université
    @Test
    @Order(4)
    public void testModifyUniversite() {
        Universite modifiedUniversite = new Universite(1L, "Université Modifiée", "Tunis", null);

        // Simuler le comportement du repository
        when(universiteRepository.save(modifiedUniversite)).thenReturn(modifiedUniversite);

        // Appeler la méthode à tester
        Universite updatedUniversite = universiteService.modifyUniversite(modifiedUniversite);

        // Vérifications
        assertNotNull(updatedUniversite);
        assertEquals("Université Modifiée", updatedUniversite.getNomUniversite());
        verify(universiteRepository, times(1)).save(modifiedUniversite);  // Vérifier que save() est appelé une seule fois
    }

    // 5. Test pour supprimer une université
    @Test
    @Order(5)
    public void testRemoveUniversite() {
        Long universiteId = 1L;

        // Appeler la méthode à tester
        universiteService.removeUniversite(universiteId);

        // Vérifications
        verify(universiteRepository, times(1)).deleteById(universiteId);  // Vérifier que deleteById() est appelé une seule fois
    }
}
