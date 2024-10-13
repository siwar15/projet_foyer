package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;
import tn.esprit.tpfoyer.service.BlocServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*; // Assurez-vous que cela est importé
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Pour contrôler l'ordre des tests

public class ServiceBlocTest {
    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private BlocServiceImpl blocService;

    private Bloc bloc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bloc = new Bloc(1L, "Bloc A", 100L, null, null);
    }

    @Test
    public void testRetrieveAllBlocs() {
        // Arrange
        List<Bloc> blocList = Arrays.asList(bloc);
        when(blocRepository.findAll()).thenReturn(blocList);

        // Act
        List<Bloc> result = blocService.retrieveAllBlocs();

        // Assert
        assertEquals(1, result.size());
        assertEquals(bloc, result.get(0));
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    public void testRetrieveBloc() {
        // Arrange
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        // Act
        Bloc result = blocService.retrieveBloc(1L);

        // Assert
        assertEquals(bloc, result);
        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
    public void testRetrieveBlocNotFound() {
        // Arrange
        when(blocRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> blocService.retrieveBloc(1L));
        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddBloc() {
        // Arrange
        when(blocRepository.save(bloc)).thenReturn(bloc);

        // Act
        Bloc result = blocService.addBloc(bloc);

        // Assert
        assertEquals(bloc, result);
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    public void testModifyBloc() {
        // Arrange
        when(blocRepository.save(bloc)).thenReturn(bloc);

        // Act
        Bloc result = blocService.modifyBloc(bloc);

        // Assert
        assertEquals(bloc, result);
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    public void testRemoveBloc() {
        // Act
        blocService.removeBloc(1L);

        // Assert
        verify(blocRepository, times(1)).deleteById(1L);
    }
}

