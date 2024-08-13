package com.clean.cleanroom.members.repository;

import com.clean.cleanroom.members.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByEmail(String email);
    List<Address> findAllByEmail(String email);
    Optional<Address> findByEmailAndId(String email, Long id);
}
