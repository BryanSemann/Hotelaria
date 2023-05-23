package com.example.Hotelaria.CheckIn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    List<CheckIn> findByHospedeNomeContainingOrHospedeDocumentoContainingOrHospedeTelefoneContaining(String searchTerm, String searchTerm1, String searchTerm2);

    List<CheckIn> findByDataSaidaBefore(LocalDate hoje);

    List<CheckIn> findByDataSaidaAfter(LocalDate hoje);

    List<CheckIn> findByHospedeId(Long id);

    List<CheckIn> findByHospedeIdOrderByDataEntradaDesc(Long id);
}
