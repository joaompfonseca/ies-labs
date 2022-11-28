package ies.lab3.e3.moviequotes.repository;

import ies.lab3.e3.moviequotes.model.Quote;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
}