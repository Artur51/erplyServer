package com.middleware.erply.model.product.bulk;

import lombok.ToString;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BulkResultData {
    public ArrayList<BulkResult> results;
}
