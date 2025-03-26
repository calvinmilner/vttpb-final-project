package vttp.final_project.repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp.final_project.models.Product;

@Repository
public class ProductRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Product> searchProgressively(String name, String category) {

    Query categoryQuery = new Query(Criteria.where("group").is(category));
    List<Document> matchedCategories = mongoTemplate.find(categoryQuery, Document.class, "category");

    List<Integer> allowedCategoryIds = matchedCategories.stream()
        .map(doc -> doc.getInteger("id"))
        .collect(Collectors.toList());

    if (allowedCategoryIds.isEmpty()) {
        System.out.println("No categories found for group: " + category);
        return Collections.emptyList();
    }

    String[] keywords = name.toLowerCase().split("\\s+");

    for (int i = keywords.length; i > 0; i--) {
        List<String> subset = Arrays.asList(keywords).subList(0, i);

        Query productQuery;

        if (subset.size() == 1) {
            productQuery = new Query(
                new Criteria().andOperator(
                    Criteria.where("name").regex("\\b" + subset.get(0) + "\\b", "i"),
                    Criteria.where("id_category_primary").in(allowedCategoryIds)
                )
            ).with(Sort.by(Sort.Direction.ASC, "price")).limit(10);
        } else {
            List<Criteria> regexCriterias = new ArrayList<>();
            for (String keyword : subset) {
                regexCriterias.add(Criteria.where("name").regex("\\b" + keyword + "\\b", "i"));
            }

            Criteria combined = new Criteria().andOperator(
                new Criteria().andOperator(regexCriterias.toArray(new Criteria[0])),
                Criteria.where("id_category_primary").in(allowedCategoryIds)
            );

            productQuery = new Query(combined).with(Sort.by(Sort.Direction.ASC, "price")).limit(10);
        }

        List<Document> docs = mongoTemplate.find(productQuery, Document.class, "products");

        if (!docs.isEmpty()) {
            return docs.stream().map(Product::fromDocument).collect(Collectors.toList());
        }
    }

    return Collections.emptyList();
}

}
