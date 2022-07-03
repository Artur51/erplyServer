package com.middleware.erply.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.middleware.erply.utils.JsonNodeConverter;
import com.middleware.erply.utils.JsonNodeUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.UUID;

@Data
@Entity
@Table(name = "product_groups")
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JsonIgnore
    private UUID uuid;

    @Column(name = "group_id")
    private int id;

    @Column
    private int added;
    @Column
    private String addedby;
    @Column
    private int changed;
    @Column
    private String changedby;
    @Column
    @Convert(converter = JsonNodeConverter.class)
    private JsonNode description;

    @Column(name = "location_tax_rates")
    private ArrayList<LocationTaxRate> locationTaxRates;
    @Column
    @Convert(converter = JsonNodeConverter.class)
    private JsonNode name;
    @Column(name = "non_discountable")
    private int nonDiscountable;
    @Column(name = "order_id")
    private int order;
    @Column(name = "parent_id")
    private int parentId;
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
