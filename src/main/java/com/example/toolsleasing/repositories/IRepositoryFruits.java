package com.example.toolsleasing.repositories;

import com.example.toolsleasing.model.C2ReportItem;
import com.example.toolsleasing.model.CReportItem;
import com.example.toolsleasing.model.CFruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IRepositoryFruits extends JpaRepository<CFruit, Long> {
    //CrudRepository
    @Query(
            value = """
                SELECT name,
                       price
                  FROM fruit_store
              ORDER BY price DESC
                 LIMIT 5;
            """,
            nativeQuery = true)
    List<CReportItem> topExpensiveFruits();

    @Query(
            value = """
                SELECT country,
                       count(*) cnt
                  FROM fruit_store
              GROUP BY country
              ORDER BY cnt DESC;
            """,
            nativeQuery = true)
    List<C2ReportItem> supplyPopularCountries();
}