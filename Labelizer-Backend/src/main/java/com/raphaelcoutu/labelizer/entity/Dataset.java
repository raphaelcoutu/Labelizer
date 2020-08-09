package com.raphaelcoutu.labelizer.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "dataset")
public class Dataset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dataset", fetch = FetchType.LAZY)
    private List<Label> labels;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dataset", fetch = FetchType.LAZY)
    private List<Photo> photos;
}
