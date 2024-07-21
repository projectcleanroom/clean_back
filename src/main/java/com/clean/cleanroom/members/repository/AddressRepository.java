package com.clean.cleanroom.members.repository;

import com.clean.cleanroom.members.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
