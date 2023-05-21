package com.example.Hotelaria;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospedeRepository extends CrudRepository<Hospede, Long> {
}
