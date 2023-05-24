package com.example.Hotelaria.TestesUnit;

import com.example.Hotelaria.CheckIn.CheckIn;
import com.example.Hotelaria.CheckIn.CheckInRepository;
import com.example.Hotelaria.Hospede.Hospede;
import com.example.Hotelaria.Hospede.HospedeController;
import com.example.Hotelaria.Hospede.HospedeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HospedeControllerTests {

    @Mock
    private HospedeRepository hospedeRepository;

    @Mock
    private CheckInRepository checkInRepository;

    @InjectMocks
    private HospedeController hospedeController;

    @Test
    public void testGetAllHospedes() {
        Hospede hospede1 = new Hospede();
        hospede1.setId(1L);
        hospede1.setNome("João");

        Hospede hospede2 = new Hospede();
        hospede2.setId(2L);
        hospede2.setNome("Maria");

        List<Hospede> hospedes = new ArrayList<>();
        hospedes.add(hospede1);
        hospedes.add(hospede2);

        when(hospedeRepository.findAll()).thenReturn(hospedes);

        List<Hospede> result = hospedeController.getAllHospedes();

        assertEquals(2, result.size());
        assertEquals("João", result.get(0).getNome());
        assertEquals("Maria", result.get(1).getNome());
    }

    @Test
    public void testGetHospedeById() {
        Hospede hospede = new Hospede();
        hospede.setId(1L);
        hospede.setNome("João");

        when(hospedeRepository.findById(1L)).thenReturn(Optional.of(hospede));

        Hospede result = hospedeController.getHospedeById(1L);

        assertEquals("João", result.getNome());
    }

    @Test
    public void testCreateHospede() {
        Hospede hospede = new Hospede();
        hospede.setId(1L);
        hospede.setNome("João");

        when(hospedeRepository.save(any(Hospede.class))).thenReturn(hospede);

        Hospede result = hospedeController.createHospede(hospede);

        assertEquals("João", result.getNome());
    }

    @Test
    public void testUpdateHospede() {
        Hospede hospede = new Hospede();
        hospede.setId(1L);
        hospede.setNome("João");

        when(hospedeRepository.findById(1L)).thenReturn(Optional.of(hospede));
        when(hospedeRepository.save(any(Hospede.class))).thenReturn(hospede);

        Hospede hospedeAtualizado = new Hospede();
        hospedeAtualizado.setNome("Maria");

        Hospede result = hospedeController.updateHospede(1L, hospedeAtualizado);

        assertEquals("Maria", result.getNome());
    }

    @Test
    public void testDeleteHospede() {
        Hospede hospede = new Hospede();
        hospede.setId(1L);
        hospede.setNome("João");

        when(hospedeRepository.findById(1L)).thenReturn(Optional.of(hospede));

        ResponseEntity<Void> response = hospedeController.deleteHospede(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(hospedeRepository, times(1)).delete(hospede);
    }

    // Adicione mais testes para os outros métodos do HospedeController

    @Test
    public void testGetValorTotalHospede() {
        Hospede hospede = new Hospede();
        hospede.setId(1L);

        CheckIn checkIn1 = new CheckIn();
        checkIn1.setHospede(hospede);
        checkIn1.setDataEntrada(LocalDate.now());
        checkIn1.setDataSaida(LocalDate.now().plusDays(1));

        CheckIn checkIn2 = new CheckIn();
        checkIn2.setHospede(hospede);
        checkIn2.setDataEntrada(LocalDate.now().minusDays(2));
        checkIn2.setDataSaida(LocalDate.now().minusDays(1));

        List<CheckIn> checkIns = Arrays.asList(checkIn1, checkIn2);

        when(checkInRepository.findByHospedeId(anyLong())).thenReturn(checkIns);

        double result = hospedeController.getValorTotalHospede(1L);

        assertEquals(240.0, result);
    }

    @Test
    public void testGetValorUltimaHospedagem() {
        Hospede hospede = new Hospede();
        hospede.setId(1L);

        CheckIn checkIn1 = new CheckIn();
        checkIn1.setHospede(hospede);
        checkIn1.setDataEntrada(LocalDate.now().minusDays(2));
        checkIn1.setDataSaida(LocalDate.now().minusDays(1));

        CheckIn checkIn2 = new CheckIn();
        checkIn2.setHospede(hospede);
        checkIn2.setDataEntrada(LocalDate.now().minusDays(1));
        checkIn2.setDataSaida(LocalDate.now());

        List<CheckIn> checkIns = Arrays.asList(checkIn1, checkIn2);

        when(checkInRepository.findByHospedeIdOrderByDataEntradaDesc(anyLong())).thenReturn(checkIns);

        double result = hospedeController.getValorUltimaHospedagem(1L);

        assertEquals(120.0, result);
    }
}