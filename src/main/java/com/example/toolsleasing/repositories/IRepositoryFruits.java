package com.example.toolsleasing.repositories;

import com.example.toolsleasing.model.CHighCostReportItem;
import com.example.toolsleasing.model.CFruit;
import com.example.toolsleasing.model.CPopularCountriesReportItem;
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
    List<CHighCostReportItem> topExpensiveFruits();

    @Query(
            value = """
                SELECT country,
                       count(*) cnt
                  FROM fruit_store
              GROUP BY country
              ORDER BY cnt DESC;
            """,
            nativeQuery = true)
    List<CPopularCountriesReportItem> supplyPopularCountries();
}