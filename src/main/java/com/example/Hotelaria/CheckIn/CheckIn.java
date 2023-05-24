package com.example.Hotelaria.CheckIn;

import com.example.Hotelaria.Hospede.Hospede;
import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@SuppressWarnings("ALL")
@Entity
@Table(name = "checkIn")
public class CheckIn {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;

    private Boolean isAdicionalVeiculo = false;
    @ManyToOne
    @JoinColumn(name = "hospede_id", referencedColumnName = "id")
    private Hospede hospede;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
    }

    public Boolean getIsAdicionalVeiculo() {
        return isAdicionalVeiculo;
    }

    public void setIsAdicionalVeiculo(Boolean isAdicionalVeiculo) {
        this.isAdicionalVeiculo = isAdicionalVeiculo;
    }

    public CheckIn(LocalDate dataEntrada, LocalDate dataSaida, Hospede hospede) {
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.hospede = hospede;
    }

    public CheckIn(){

    }

    public Double calcularValorTotal() {
        long dias = ChronoUnit.DAYS.between(dataEntrada, dataSaida);
        int diasDeSemana = 0;
        int diasDeFimDeSemana = 0;

        LocalDate data = dataEntrada;
        for (int i = 0; i < dias; i++) {
            if (isFimDeSemana(data)) {
                diasDeFimDeSemana++;
            } else {
                diasDeSemana++;
            }
            data = data.plusDays(1);
        }

        double valorTotal = (diasDeSemana * 120.00) + (diasDeFimDeSemana * 150.00);
        if (isAdicionalVeiculo) {
            int diasAdicionalVeiculo = (int) ChronoUnit.DAYS.between(dataSaida, dataEntrada);
            double valorAdicionalVeiculo = (diasAdicionalVeiculo * 15.00);
            if (isFimDeSemana(dataSaida)) {
                valorAdicionalVeiculo += 20.00;
            } else {
                LocalDateTime horarioLimite = LocalDateTime.of(dataSaida, LocalTime.of(16, 30));
                if (horarioLimite.isBefore(LocalDateTime.now())) {
                    valorAdicionalVeiculo += 15.00;
                }
            }
            valorTotal += valorAdicionalVeiculo;
        }

        return valorTotal;
    }

    private boolean isFimDeSemana(LocalDate data) {
        return data.getDayOfWeek() == DayOfWeek.SATURDAY || data.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

}
