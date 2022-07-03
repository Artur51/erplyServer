package com.middleware.erply.model.product;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "products")
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@FieldNameConstants
public class ProductResponse extends Product {
    @Id
    private Integer id;
    @Column
    private Date added; // timestamp
    @Column
    private String addedby;
}
