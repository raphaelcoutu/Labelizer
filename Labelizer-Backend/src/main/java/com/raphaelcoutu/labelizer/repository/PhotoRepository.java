package com.raphaelcoutu.labelizer.repository;

import com.raphaelcoutu.labelizer.entity.Photo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends CrudRepository<Photo, Long> {
    List<Photo> findPhotosByDataset_Id(Long datasetId);
    Photo findFirstByDataset_IdAndVerifiedFalse(@Param("datasetId") Long datasetId);
    Photo findFirstByDataset_IdAndIdNotAndVerifiedFalse(@Param("datasetId") Long datasetId, @Param("id") Long exceptId);

    @Query("select distinct p from Photo p join fetch p.labelBoxes lb where lb.label.id in :labelIds and p.verified = true")
    List<Photo> findPhotosWithLabels(@Param("labelIds") List<Long> labelIds);

    @Query("select distinct p from Photo p join fetch p.labelBoxes lb where lb.label.id not in :labelIds and p.verified = true")
    List<Photo> findPhotosWithoutLabels(@Param("labelIds") List<Long> labelIds);
}
