package com.startrip.codebase.domain.curation.repository;

import com.startrip.codebase.domain.curation.entity.CurationObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CurationObjectRepository extends JpaRepository<CurationObject, UUID> {

}
