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
    private int stockQuantity;
    private double shoeSize;
    private String clothingSize;
    private String colour;

    // Define the class constructor
    public BarcodedProduct(long productUPC) {
        this.productUPC = productUPC;

        // Initialise database and collection for product inventory
        accessInventoryDatabaseAndCollection();

        // Set the product details base on the provided upc
        setProductDetails();
    }

    public BarcodedProduct(int productID, String colour, String clothingSize) {
        this.productID = productID;
        this.colour = colour;
        this.clothingSize = clothingSize;

        // Initialise database and collection for product inventory
        accessInventoryDatabaseAndCollection();

        // Set the product details base on the provided productID
        setProductDetails();
    }

    // Get the inventory database and collection
    private void accessInventoryDatabaseAndCollection() {

        // Get instance of the inventory database
        inventoryDB = InventoryDatabase.getInstance();

        // Initialise and get the "inventory" collection
        inventoryDB.initialiseInventoryCollection();
    }

    // Set product details if the scanned barcode
    private void setProductDetails() {

        // Get the matched product
        Document matchedProduct = inventoryDB.getMatchingProduct();

        // Set the name, price and category of the product
        this.productName = matchedProduct.getString("abbreviation");
        this.productCategory = matchedProduct.getString("category");
        this.price = matchedProduct.getDouble("price");

        // Get the product variants
        List<Document> productVariants = (List<Document>) matchedProduct.get("variants");

        // Loop through the variants and find the variants matching the provided upc
        for(Document productVariant : productVariants) {

            // Enter if-block if productID has not been initialised
            if(productID == 0) {
                if(productVariant.get("upc").equals(productUPC)) {

                    this.productID = matchedProduct.getInteger("product_id");
                    this.colour = productVariant.getString("colour");
                    this.stockQuantity = productVariant.getInteger("stock_quantity");

                    if(productCategory.equals("Softgoods")) {
                        this.clothingSize = productVariant.getString("size");
                    } else if(productCategory.equals("Footwear")) {
                        this.shoeSize = productVariant.getDouble("size");
                    }

                }
            }

            // Enter else-block if productID has been initialised
            else {
                if(productVariant.get("colour").equals(colour) && productVariant.get("size").equals(clothingSize)) {
                    this.productUPC = productVariant.getLong("upc");
                    this.stockQuantity = productVariant.getInteger("stock_quantity");
                }
            }
          }

    }

    // Define the getter methods
    public long getProductUPC() { return productUPC; }
    public int getProductID() { return productID; }
    public String getProductName() { return productName; }
    public String getProductCategory() { return productCategory; }
    public double getPrice() { return price; }
    public String getColour() { return colour; }
    public String getClothingSize() { return clothingSize; }
    public double getShoeSize() { return shoeSize; }
    public int getStockQuantity() { return stockQuantity; }

}


