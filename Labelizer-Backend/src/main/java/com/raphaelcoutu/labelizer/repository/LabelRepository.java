package com.raphaelcoutu.labelizer.repository;

import com.raphaelcoutu.labelizer.entity.Label;
import com.raphaelcoutu.labelizer.entity.Photo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabelRepository extends CrudRepository<Label, Long> {
    List<Label> findLabelsByDataset_Id(Long datasetId);
}
