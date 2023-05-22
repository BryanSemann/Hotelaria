package com.example.Hotelaria.CheckIn;

import com.example.Hotelaria.Hospede.Hospede;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "checkIn")
public class CheckIn {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date dataEntrada;
    private Date dataSaida;

    private Boolean adicVeiculo;
    @ManyToOne
    @JoinColumn(name = "hospede_id", referencedColumnName = "id")
    private Hospede hospede;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
    }

    public Boolean getAdicVeiculo() {
        return adicVeiculo;
    }

    public void setAdicVeiculo(Boolean adicVeiculo) {
        this.adicVeiculo = adicVeiculo;
    }

    public CheckIn(Date dataEntrada, Date dataSaida, Hospede hospede) {
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.hospede = hospede;
    }

    public CheckIn(){

    }
}
