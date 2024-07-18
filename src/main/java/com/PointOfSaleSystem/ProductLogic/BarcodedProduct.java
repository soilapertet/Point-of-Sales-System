package com.PointOfSaleSystem.ProductLogic;

import com.PointOfSaleSystem.StoreDatabase.InventoryDatabase;
import org.bson.Document;

import java.util.List;

public class BarcodedProduct {

    // Define the instance variables
    private InventoryDatabase inventoryDB;
    private String productName;
    private long productUPC;
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
    }

    // Set product details if the scanned barcode is in the inventory database
    public void setProductDetails() {

        boolean isProductInDB = inventoryDB.isProductUPCInDB(productUPC);
        Document matchedProduct;

        if(isProductInDB) {

            // Get the matched product
            matchedProduct = inventoryDB.getMatchingProduct();

            // Set the name, price and category of the product
            this.productName = matchedProduct.getString("abbreviation");
            this.price = matchedProduct.getDouble("price");
            this.productCategory = matchedProduct.getString("category");

            // Get the product variants
            List<Document> productVariants = (List<Document>) matchedProduct.get("variants");

            // Loop through the variants and find the variants matching the provided upc
            for(Document productVariant : productVariants) {
                if(productVariant.get("upc").equals(productUPC)) {
                    if(productCategory.equals("Softgoods")) {
                        this.colour = productVariant.getString("colour");
                        this.clothingSize = productVariant.getString("size");
                    }
                }
          }
        } else {
            System.err.println("Product could not be found in database");
        }
    }

    // Define the getter methods
    public long getProductUPC() { return productUPC; }

    public static void main(String[] args) {
        long upc = 5411188113006L;
        BarcodedProduct barcodedProduct = new BarcodedProduct(upc);
        barcodedProduct.setProductDetails();
        System.out.println(barcodedProduct.productName);
        System.out.println(barcodedProduct.price);
        System.out.println(barcodedProduct.colour);
        System.out.println(barcodedProduct.clothingSize);
    }
}


