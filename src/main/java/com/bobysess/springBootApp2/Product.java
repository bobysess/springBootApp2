package com.bobysess.springBootApp2;

import java.math.BigDecimal;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RevisionNumber;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Audited
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    @RevisionNumber
    private Long id;

    private String name;
    private BigDecimal price;   
}
