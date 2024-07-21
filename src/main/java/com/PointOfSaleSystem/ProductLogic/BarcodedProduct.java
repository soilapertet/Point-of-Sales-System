package com.PointOfSaleSystem.ProductLogic;

import com.PointOfSaleSystem.StoreDatabase.InventoryDatabase;
import org.bson.Document;

import java.util.List;

public class BarcodedProduct {

    // Define the instance variables
    private InventoryDatabase inventoryDB;
    private String productName;
    private long productUPC;
    private int productID;
    private String productCategory;
    private double price;
    private int quantity;
    private double shoeSize;
    private String clothingSize;
    private String colour;

    // Define the class constructor
    public BarcodedProduct(long productUPC) {
        this.productUPC = productUPC;

        // Get instance of the inventory database
        inventoryDB = InventoryDatabase.getInstance();

        // Initialise and get the "inventory" collection
        inventoryDB.initialiseInventoryCollection();

        // Set the product details base on the provided upc
        setProductDetails();
    }

    // Set product details if the scanned barcode
    private void setProductDetails() {

        boolean isProductInDB = inventoryDB.isProductUPCInDB(productUPC);
        Document matchedProduct;

        // Get the matched product
        matchedProduct = inventoryDB.getMatchingProduct();

        // Set the name, price and category of the product
        this.productName = matchedProduct.getString("abbreviation");
        this.productCategory = matchedProduct.getString("category");
        this.productID = matchedProduct.getInteger("product_id");
        this.price = matchedProduct.getDouble("price");

        // Get the product variants
        List<Document> productVariants = (List<Document>) matchedProduct.get("variants");

        // Loop through the variants and find the variants matching the provided upc
        for(Document productVariant : productVariants) {
            if(productVariant.get("upc").equals(productUPC)) {
                this.colour = productVariant.getString("colour");
                this.quantity = productVariant.getInteger("stock_quantity");
                if(productCategory.equals("Softgoods")) {
                    this.clothingSize = productVariant.getString("size");
                } else if(productCategory.equals("Footwear")) {
                    this.shoeSize = productVariant.getDouble("size");
                }
            }
          }

    }

    // Define the getter methods
    public long getProductUPC() { return productUPC; }
    public String getProductName() { return productName; }
    public String getProductCategory() { return productCategory; }
    public int getProductID() { return productID; }
    public double getPrice() { return price; }
    public String getColour() { return colour; }
    public String getClothingSize() { return clothingSize; }
    public double getShoeSize() { return shoeSize; }

}


