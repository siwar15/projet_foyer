package tn.esprit.tpfoyer;


import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;
import tn.esprit.tpfoyer.service.ChambreServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class ChambreServiceImplMock {
    @Mock
    ChambreRepository chambreRepository;

    @InjectMocks
    ChambreServiceImpl chambreService;

    Chambre chambre = new Chambre(1L, 1, TypeChambre.SIMPLE, null, null);

    List<Chambre> listChambres = new ArrayList<Chambre>() {
        {
            add(new Chambre(2L, 2, TypeChambre.SIMPLE, null, null));
            add(new Chambre(3L, 3, TypeChambre.SIMPLE, null, null));
        }
    };

    // Test for retrieving a single chambre by ID
    @Test
    public void testRetrieveChambre() {

        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));
        Chambre result = chambreService.retrieveChambre(1L);
        assertEquals(chambre, result);
        verify(chambreRepository, times(1)).findById(1L);
    }

    // Test for retrieving all chambres
    @Test
    public void testRetrieveAllChambres() {

        when(chambreRepository.findAll()).thenReturn(listChambres);
        List<Chambre> result = chambreService.retrieveAllChambres();
        assertEquals(listChambres.size(), result.size());
        assertEquals(listChambres, result);
        verify(chambreRepository, times(1)).findAll();
    }

    // Test for adding a new chambre
    @Test
    public void testAddChambre() {

        when(chambreRepository.save(chambre)).thenReturn(chambre);
        Chambre result = chambreService.addChambre(chambre);
        assertEquals(chambre, result);
        verify(chambreRepository, times(1)).save(chambre);
    }

    // Test for removing a chambre by ID
    @Test
    public void testRemoveChambre() {

        doNothing().when(chambreRepository).deleteById(1L);
        chambreService.removeChambre(1L);
        verify(chambreRepository, times(1)).deleteById(1L);
    }
}
