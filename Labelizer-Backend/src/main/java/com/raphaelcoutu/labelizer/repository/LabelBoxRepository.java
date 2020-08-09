package com.raphaelcoutu.labelizer.repository;

import com.raphaelcoutu.labelizer.entity.LabelBox;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelBoxRepository extends RefreshableCrudRepository<LabelBox, Long> {
}
