package com.hsu.backend.repository;

import com.hsu.backend.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ReferenceDataRepository extends JpaRepository<Item, Long> {
}
