package com.devshowcase.entity;
import jakarta.persistence.*; import lombok.*; import java.util.*;
@Entity @Table(name="technologies") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Technology { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(nullable=false,unique=true) private String name; @ManyToMany(mappedBy="technologies") @Builder.Default private Set<Project> projects=new HashSet<>(); }
