package com.middleware.erply.model.product;

import com.middleware.erply.utils.JsonNodeUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.middleware.erply.utils.JsonNodeConverter;

@Data
@Entity
@Table(name = "product_groups")
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductGroupRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JsonIgnore
    private UUID id;

    @Column(name = "main_id")
    private int mainId;

    @Column
    @Convert(converter = JsonNodeConverter.class)
    private JsonNode description;
    @Column
    @Convert(converter = JsonNodeConverter.class)
    private JsonNode name;

    @Column(name = "non_discountable")
    private int nonDiscountable;
    @Column(name = "quick_books_credit_account")
    private String quickBooksCreditAccount;
    @Column(name = "quick_books_debit_account")
    private String quickBooksDebitAccount;
    @Column(name = "reward_points")
    private int rewardPoints;
    @Column(name = "show_in_webshop")
    private int showInWebshop;

    public String getName(
            String tag) {
        return JsonNodeUtils.getTextValue(getName(), tag);
    }

    public void setName(
            String tag,
            String value) {
        ObjectNode nameNode = JsonNodeUtils.createNode(tag, value);
        setName(nameNode);
    }

    public String getDescription(
            String tag) {
        return JsonNodeUtils.getTextValue(getDescription(), tag);
    }

    public void setDescription(
            String tag,
            String value) {
        ObjectNode nameNode = JsonNodeUtils.createNode(tag, value);
        setDescription(nameNode);
    }
}
