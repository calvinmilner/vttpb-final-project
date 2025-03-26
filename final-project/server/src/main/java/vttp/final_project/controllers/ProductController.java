package vttp.final_project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import vttp.final_project.models.Ingredient;
import vttp.final_project.models.Product;
import vttp.final_project.services.ProductService;

@Controller
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @PostMapping("/searchBatch")
    @ResponseBody
    public ResponseEntity<List<Product>> getProductsByName(@RequestBody List<Ingredient> ingredients) {
        return ResponseEntity.ok(productService.getProductByName(ingredients));
    }
}
