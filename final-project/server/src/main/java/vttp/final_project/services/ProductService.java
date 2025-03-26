package vttp.final_project.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.final_project.models.Ingredient;
import vttp.final_project.models.Product;
import vttp.final_project.repositories.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProductByName(List<Ingredient> ingredients) {
        List<Product> allProducts = new ArrayList<>();
        for(Ingredient ingredient : ingredients) {
            String name = ingredient.getName();
            String category = ingredient.getCategory();
            List<Product> products = productRepository.searchProgressively(name, category);
            allProducts.addAll(products);
        }
        return allProducts;
    }
}
