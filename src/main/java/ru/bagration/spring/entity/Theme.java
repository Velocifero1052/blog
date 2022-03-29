package ru.bagration.spring.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "themes")
public class Theme {

    @Transient
    private final String sequenceName = "themes_id_seq";

    @Id
    @SequenceGenerator(name = sequenceName, sequenceName = sequenceName, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequenceName)
    private Long id;

    @Column(name = "public_id")
    private String publicId;

    @Column(name = "name")
    private String name;

    @Column(name = "age_category")
    private Integer ageCategory;

}
