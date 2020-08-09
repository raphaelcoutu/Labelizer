package com.raphaelcoutu.labelizer.repository;

import com.raphaelcoutu.labelizer.entity.Dataset;
import org.springframework.data.repository.CrudRepository;

public interface DatasetRepository extends CrudRepository<Dataset, Long> {
}
