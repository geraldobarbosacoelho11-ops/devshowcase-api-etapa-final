package com.devshowcase.entity;
import jakarta.persistence.*; import lombok.*; import java.util.*;
@Entity @Table(name="profiles") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Profile { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(nullable=false) private String name; @Column(nullable=false,unique=true) private String email; @Column(length=1000) private String bio; private String githubUrl; private String linkedinUrl; @OneToMany(mappedBy="profile",cascade=CascadeType.ALL,orphanRemoval=true) @Builder.Default private List<Project> projects=new ArrayList<>(); }
