package com.middleware.erply.services;

import com.github.javafaker.Faker;
import com.middleware.erply.model.product.Product;
import com.middleware.erply.model.product.bulk.BulkUpdateProductRequest;
import com.middleware.erply.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class ProductInstanceGenerationService {
    final Faker faker = new Faker();

    public BulkUpdateProductRequest generateRequest(
            Supplier<? extends Product> supplier,
            int count) {
        BulkUpdateProductRequest request = new BulkUpdateProductRequest();
        ArrayList<Product> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(generateProduct(supplier));
        }
        request.setRequests(list);
        return request;
    }

    @SneakyThrows
    public Product generateProduct(
            Supplier<? extends Product> supplier) {
        Product product = supplier.get();
        initFields(product);
        product.setGroupId(1);
        return product;
    }

    public void updateStringFields(
            List<? extends Product> products) {
        for (Product product : products) {
            updateStringFields(product);
        }
    }

    public void updateStringFields(
            Product product) {
        List<Field> list = getDeclaredFields(product);

        for (Field f : list) {
            if (f.getType() == String.class) {
                String city = faker.address().city();
                Utils.setField(f, product, city);
            }
        }
    }

    private List<Field> getDeclaredFields(
            Object object) {
        ArrayList<Field> list = new ArrayList<>();
        Class<?> class1 = object.getClass();
        while (class1 != Object.class) {
            Field[] declaredFields = class1.getDeclaredFields();
            list.addAll(Arrays.asList(declaredFields));
            class1 = class1.getSuperclass();
        }
        return list;
    }

    public void initFields(
            List<? extends Product> products) {
        for (Product product : products) {
            initFields(product);
        }
    }

    public void initFields(
            Product product) {
        List<Field> list = getDeclaredFields(product);

        for (Field f : list) {
            if (f.getType() == Boolean.class) {
                Utils.setField(f, product, false);
            } else if (f.getType() == String.class) {
                String city = faker.address().city();
                Utils.setField(f, product, city);
            } else if (Number.class.isAssignableFrom(f.getType())) {
                Utils.setField(f, product, 0);
            }
        }
    }
}
