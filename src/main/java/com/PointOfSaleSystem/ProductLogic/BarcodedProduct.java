package com.PointOfSaleSystem.ProductLogic;

import com.PointOfSaleSystem.StoreDatabase.InventoryDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.List;

public class BarcodedProduct {

    // Define the instance variables
    private InventoryDatabase inventoryDB;
    private ObjectId objectId;
    private String productName;
    private long productUPC;
    private int productID;
    private String productCategory;
    private int productQuantity;
    private double price;
    private int stockQuantity;
    private double shoeSize;
    private String clothingSize;
    private String colour;
    private List<Document> productVariants;
    private int salesAssociateID;
    private boolean discountApplied;
    private String lineComment;

    // Define the class constructor
    public BarcodedProduct(long productUPC) {
        this.productUPC = productUPC;

        // Initialise database and collection for product inventory
        accessInventoryDatabaseAndCollection();

        // Set the product details base on the provided upc
        setProductDetails();
    }

    // Note: Possible bug when create an instance of BarcodedProduct
    //       User may enter a colour or size that does not exist in the database
    // Fix: Through GUI; only display appropriate colours and sizes associated with the product
    public BarcodedProduct(int productID, String colour, String clothingSize) {
        this.productID = productID;
        this.colour = colour;
        this.clothingSize = clothingSize;

        // Initialise database and collection for product inventory
        accessInventoryDatabaseAndCollection();

        // Set the product details base on the provided productID
        setProductDetails();
    }

    public BarcodedProduct(int productID, String colour, int shoeSize) {
        this.productID = productID;
        this.colour = colour;
        this.shoeSize = shoeSize;

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

        // Set the object id, name, price and category of the product
        this.objectId = matchedProduct.getObjectId("_id");
        this.productName = matchedProduct.getString("abbreviation");
        this.productCategory = matchedProduct.getString("category");
        this.price = matchedProduct.getDouble("price");
        this.productQuantity = 1;

        // Get the product variants
        this.productVariants = (List<Document>) matchedProduct.get("variants");

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
                if(productVariant.get("colour").equals(colour) && (productVariant.get("size").equals(clothingSize)
                || productVariant.get("size").equals(shoeSize))) {
                    this.productUPC = productVariant.getLong("upc");
                    this.stockQuantity = productVariant.getInteger("stock_quantity");
                }
            }
        }

        // Update stock quantity once we've scanned the item
        // updateStockQuantity();
    }

    // Decrease the value of stock quantity once we create an instance of the barcoded product
    private void updateStockQuantity() {
        for(Document productVariant: productVariants) {
            if(productVariant.get("upc").equals(productUPC)) {
                this.stockQuantity -= 1;   // decrease the value of the current stock quantity

                // Define the filter/query using the object id and product upc
                Document updateDocQuery = new Document()
                        .append("_id", objectId)
                        .append("variants.upc", productUPC);

                // Outline the update(s) to the document
                // Update the stock quantity of the product
                Bson update = Updates.set("variants.$[elem].stock_quantity", stockQuantity);

                // Update document
                UpdateResult result = inventoryDB.getInventoryCollection().updateOne(
                        updateDocQuery,
                        update,
                        new UpdateOptions().arrayFilters(Arrays.asList(
                                Filters.eq("elem.upc", productUPC)
                        ))
                );

                // Testing purposes
                System.out.println("Modified document count: " + result.getModifiedCount());
            }
        }
    }

    // Define the getter methods
    public long getProductUPC() { return productUPC; }
    public int getProductID() { return productID; }
    public String getProductName() { return productName; }
    public String getProductCategory() { return productCategory; }
    public int getProductQuantity() { return productQuantity; }
    public double getPrice() { return price; }
    public String getColour() { return colour; }
    public String getClothingSize() { return clothingSize; }
    public double getShoeSize() { return shoeSize; }
    public int getStockQuantity() { return stockQuantity; }
    public int getSalesAssociateID() { return salesAssociateID; }

    // Define setter methods
    public void setDiscountApplied(boolean discountApplied) { this.discountApplied = discountApplied; }
    public void setPrice(double price) { this.price = price; }
    public void setProductQuantity(int quantity) { this.productQuantity = quantity; }
    public void setSalesAssociateID(int associateID) { this.salesAssociateID = associateID; }
}


