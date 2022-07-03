package com.middleware.erply.model.product.bulk;
import lombok.ToString;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BulkResult {
    public String message;
    public int resourceId;
    public int resultId;
}
