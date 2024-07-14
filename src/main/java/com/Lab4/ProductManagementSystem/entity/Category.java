package com.Lab4.ProductManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference // Prevents infinite recursion
    private Set<Product> products = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonManagedReference // Handles bidirectional serialization
    private Category parent;

    @Column(name = "categories_tree_json", columnDefinition = "json")
    @Transient
    private String categories_tree_json;

    public void setCategoriesTreeJson(String json) {
        this.categories_tree_json = json;
    }

    // Constructors, getters, and setters
}
