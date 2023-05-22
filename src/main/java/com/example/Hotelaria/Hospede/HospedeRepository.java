package com.example.Hotelaria.Hospede;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospedeRepository extends JpaRepository<Hospede, Long> {
    Hospede findByIdOrNomeOrDocumentoOrTelefone(Long id,String nome, String documento, String telefone);
}
