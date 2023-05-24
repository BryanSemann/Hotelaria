package com.example.Hotelaria.TestesUnit;

import com.example.Hotelaria.CheckIn.CheckIn;
import com.example.Hotelaria.CheckIn.CheckInController;
import com.example.Hotelaria.CheckIn.CheckInRepository;
import com.example.Hotelaria.Hospede.Hospede;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CheckInControllerTests {

    @Mock
    private CheckInRepository checkInRepository;

    @InjectMocks
    private CheckInController checkInController;

    @Test
    public void testGetCheckInsByHospede() {
        CheckIn checkIn1 = new CheckIn();
        checkIn1.setId(1L);
        checkIn1.setHospede(new Hospede());

        CheckIn checkIn2 = new CheckIn();
        checkIn2.setId(2L);
        checkIn2.setHospede(new Hospede());

        List<CheckIn> checkIns = Arrays.asList(checkIn1, checkIn2);

        when(checkInRepository.findByHospedeNomeContainingOrHospedeDocumentoContainingOrHospedeTelefoneContaining(anyString(), anyString(), anyString()))
                .thenReturn(checkIns);

        List<CheckIn> result = checkInController.getCheckInsByHospede("searchTerm");

        assertEquals(2, result.size());
        assertEquals(checkIn1, result.get(0));
        assertEquals(checkIn2, result.get(1));
    }

    @Test
    public void testGetCheckInsConcluidos() {
        CheckIn checkIn1 = new CheckIn();
        checkIn1.setId(1L);
        checkIn1.setDataSaida(LocalDate.now().minusDays(2));

        CheckIn checkIn2 = new CheckIn();
        checkIn2.setId(2L);
        checkIn2.setDataSaida(LocalDate.now().minusDays(1));

        List<CheckIn> checkIns = Arrays.asList(checkIn1, checkIn2);

        when(checkInRepository.findByDataSaidaBefore(any(LocalDate.class))).thenReturn(checkIns);

        List<CheckIn> result = checkInController.getCheckInsConcluidos();

        assertEquals(2, result.size());
        assertEquals(checkIn1, result.get(0));
        assertEquals(checkIn2, result.get(1));
    }

    @Test
    public void testGetCheckInsAtivos() {
        CheckIn checkIn1 = new CheckIn();
        checkIn1.setId(1L);
        checkIn1.setDataSaida(LocalDate.now().plusDays(1));

        CheckIn checkIn2 = new CheckIn();
        checkIn2.setId(2L);
        checkIn2.setDataSaida(LocalDate.now().plusDays(2));

        List<CheckIn> checkIns = Arrays.asList(checkIn1, checkIn2);

        when(checkInRepository.findByDataSaidaAfter(any(LocalDate.class))).thenReturn(checkIns);

        List<CheckIn> result = checkInController.getCheckInsAtivos();

        assertEquals(2, result.size());
        assertEquals(checkIn1, result.get(0));
        assertEquals(checkIn2, result.get(1));
    }

    @Test
    public void testCreateCheckIn() {
        CheckIn checkIn = new CheckIn();
        checkIn.setId(1L);

        when(checkInRepository.save(any(CheckIn.class))).thenReturn(checkIn);

        CheckIn result = checkInController.createCheckIn(checkIn);

        assertEquals(checkIn, result);
    }

    @Test
    public void testGetCheckInById() {
        CheckIn checkIn = new CheckIn();
        checkIn.setId(1L);

        when(checkInRepository.findById(1L)).thenReturn(Optional.of(checkIn));

        CheckIn result = checkInController.getCheckInById(1L);

        assertEquals(checkIn, result);
    }

    @Test
    public void testGetCheckInById_ThrowsNoSuchElementException() {
        when(checkInRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            checkInController.getCheckInById(1L);
        });
    }

    @Test
    public void testUpdateCheckIn() {
        CheckIn existingCheckIn = new CheckIn();
        existingCheckIn.setId(1L);

        CheckIn updatedCheckIn = new CheckIn();
        updatedCheckIn.setId(1L);
        updatedCheckIn.setDataEntrada(LocalDate.now());

        when(checkInRepository.findById(1L)).thenReturn(Optional.of(existingCheckIn));
        when(checkInRepository.save(any(CheckIn.class))).thenReturn(updatedCheckIn);

        CheckIn result = checkInController.updateCheckIn(1L, updatedCheckIn);

        assertEquals(updatedCheckIn, result);
    }

    @Test
    public void testUpdateCheckIn_ThrowsNoSuchElementException() {
        when(checkInRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            checkInController.updateCheckIn(1L, new CheckIn());
        });
    }

    @Test
    public void testDeleteCheckIn() {
        CheckIn checkIn = new CheckIn();
        checkIn.setId(1L);

        when(checkInRepository.findById(1L)).thenReturn(Optional.of(checkIn));

        checkInController.deleteCheckIn(1L);

        verify(checkInRepository, times(1)).delete(checkIn);
    }

    @Test
    public void testDeleteCheckIn_ThrowsNoSuchElementException() {
        when(checkInRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            checkInController.deleteCheckIn(1L);
        });
    }

    @Test
    public void testGetAllCheckIns() {
        CheckIn checkIn1 = new CheckIn();
        checkIn1.setId(1L);

        CheckIn checkIn2 = new CheckIn();
        checkIn2.setId(2L);

        List<CheckIn> checkIns = Arrays.asList(checkIn1, checkIn2);

        when(checkInRepository.findAll()).thenReturn(checkIns);

        List<CheckIn> result = checkInController.getAllCheckIns();

        assertEquals(2, result.size());
        assertEquals(checkIn1, result.get(0));
        assertEquals(checkIn2, result.get(1));
    }
}

