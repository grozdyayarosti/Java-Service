package com.example.toolsleasing.repositories;

import com.example.toolsleasing.model.CTool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IRepositoryTools extends JpaRepository<CTool, Long> {
    //CrudRepository
}