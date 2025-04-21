package com.example.keymanager.repository;

import com.example.keymanager.entity.ApiKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApiKeysRepository extends JpaRepository<ApiKeys,Long> {
    Optional<ApiKeys> findByApiName(String apiName);
    List<ApiKeys> findByApiNameIn(List<String> apiNames);
}
