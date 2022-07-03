package com.middleware.erply.model.product.bulk;

import com.middleware.erply.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BulkUpdateProductRequest {
    public List<? extends Product> requests;
}
