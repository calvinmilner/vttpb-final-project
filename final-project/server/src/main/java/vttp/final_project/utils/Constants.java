package vttp.final_project.utils;

public class Constants {
    public static final String SPOONCULAR_API_SEARCH = "https://api.spoonacular.com/recipes/complexSearch";
    public static final String SPOONCULAR_API_RECIPE_INFORMATION = "https://api.spoonacular.com/recipes/{id}/information";
    public static final String SPOONCULAR_IMG_PREFIX = "https://img.spoonacular.com/ingredients_100x100/";
    public static final String SQL_GET_PRODUCT = "SELECT product_id, product_name, price, display_unit, image_url FROM product WHERE product_name LIKE ? ORDER BY price LIMIT 20";
    public static final String SQL_CREATE_ORDER = "INSERT INTO purchase_orders (order_id, name, email, address, phone, total) VALUES (?,?,?,?,?,?)";
    public static final String SQL_CREATE_ORDER_ITEMS = "INSERT INTO items (order_id, product_id, product_name, price, display_unit, image_url, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";
}
