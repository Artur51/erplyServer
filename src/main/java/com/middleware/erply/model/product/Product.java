package com.middleware.erply.model.product;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.middleware.erply.utils.JsonNodeConverter;
import com.middleware.erply.utils.JsonNodeUtils;

@Data
@NoArgsConstructor
@ToString
@MappedSuperclass
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Product {
    public static final String[] fieldNames = Stream.of(Product.class.getDeclaredFields()).map(Field::getName).toArray(String[]::new);

    @Column(name = "age_restriction")
    private Integer ageRestriction;

    @Column(name = "alcohol_percentage")
    private String alcoholPercentage;

    @Column(name = "alcohol_registry_number")
    private String alcoholRegistryNumber;

    @Column(name = "backup_id")
    private Integer backupId;

    @Column
    private String batches;

    @Column(name = "brand_id")
    private Integer brandId;

    @Column(name = "cashier_must_enter_price")
    @JsonFormat(shape = Shape.NUMBER)
    private Boolean cashierMustEnterPrice;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column
    private String code;

    @Column
    private String code2;

    @Column
    private String code3;

    @Column
    private String code5;

    @Column
    private String code6;

    @Column
    private String code7;

    @Column
    private String code8;

    @Column
    private Integer cost;

    @Column(name = "country_of_origin_id")
    private Integer countryOfOriginId;

    @Column(name = "delivery_time")
    private String deliveryTime;

    @Column(name = "deposit_fee_amount")
    private Integer depositFeeAmount;

    @Column(name = "deposit_fee_id")
    private Integer depositFeeId;

    @Column
    @Convert(converter = JsonNodeConverter.class)
    JsonNode description;

    @Column
    @Convert(converter = JsonNodeConverter.class)
    private JsonNode name;

    @Column(name = "displayed_in_webshop")
    private Integer displayedInWebshop;

    @Column(name = "excise_declaration_number")
    private String exciseDeclarationNumber;

    @Column(name = "extra_field1_id")
    private Integer extraField1Id;

    @Column(name = "extra_field2_id")
    private Integer extraField2Id;

    @Column(name = "extra_field3_id")
    private Integer extraField3Id;

    @Column(name = "extra_field4_id")
    private Integer extraField4Id;

    @Column(name = "family_id")
    private Integer familyId;

    @Column(name = "gross_weight")
    private Integer grossWeight;

    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "has_serial_numbers")
    private Integer hasSerialNumbers;

    @Column
    private Integer height;

    @Column(name = "is_gift_card")
    @JsonFormat(shape = Shape.NUMBER)
    private Boolean isGiftCard;

    @Column(name = "is_regular_gift_card")
    @JsonFormat(shape = Shape.NUMBER)
    private Boolean isRegularGiftCard;

    @Column(name = "labels_not_needed")
    @JsonFormat(shape = Shape.NUMBER)
    private Boolean labelsNotNeeded;

    @Column
    private Integer length;

    @Column(name = "location_in_warehouse_id")
    private Integer locationInWarehouseId;

    @Column(name = "location_in_warehouse_text")
    private String locationInWarehouseText;

    @Column(name = "manufacturer_name")
    private String manufacturerName;

    @Column(name = "net_weight")
    private Integer netWeight;

    @Column(name = "non_discountable")
    @JsonFormat(shape = Shape.NUMBER)
    private Boolean nonDiscountable;

    @Column(name = "non_refundable")
    @JsonFormat(shape = Shape.NUMBER)
    private Boolean nonRefundable;

    @Column(name = "non_stock_product")
    @JsonFormat(shape = Shape.NUMBER)
    private Boolean nonStockProduct;

    @Column(name = "packaging_type")
    private String packagingType;

    @Column(name = "packing_not_required")
    private Integer packingNotRequired;

    @Column(name = "parent_product_id")
    private Integer parentProductId;

    @Column
    private Integer price;

    @Column(name = "priority_group_id")
    private Integer priorityGroupId;

    @Column(name = "product_reorder_multiples")
    private Integer productReorderMultiples;

    @Column(name = "reward_points_not_allowed")
    @JsonFormat(shape = Shape.NUMBER)
    private Boolean rewardPointsNotAllowed;

    @Column(name = "sold_in_packages")
    private Integer soldInPackages;

    @Column
    private ProductStatus status = ProductStatus.ACTIVE;

    @Column(name = "suggested_retail_price")
    private Integer suggestedRetailPrice;

    @Column(name = "supplier_code")
    private String supplierCode;

    @Column(name = "supplier_id")
    private Integer supplierId;

    @Column(name = "tax_free")
    @JsonFormat(shape = Shape.NUMBER)
    private Boolean taxFree;

    @Column(name = "tax_rate_id")
    private Integer taxRateId;

    @Column
    private String type;

    @Column(name = "unit_id")
    private Integer unitId;

    @Column
    private Integer volume;

    @Column
    private Integer width;

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
