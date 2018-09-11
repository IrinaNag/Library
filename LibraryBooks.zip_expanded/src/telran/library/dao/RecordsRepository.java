package telran.library.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.library.entities.Record;

public interface RecordsRepository extends JpaRepository<Record, Long> {

}
