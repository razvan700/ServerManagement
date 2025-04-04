package com.jetbrains.servermanager.repository;

import com.jetbrains.servermanager.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServerRepository extends JpaRepository<Server, Long> {
    @Query(value = "SELECT * FROM Server WHERE ip = :ipAddress", nativeQuery = true)
    Server findByIpAddress(String ipAddress);
}
