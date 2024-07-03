package com.PointOfSaleSystem.StoreDatabase;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StoreDatabase {

    // Initialise instance variables
    private List storeEmployees = new ArrayList();
    private List storeManagers = new ArrayList();
    private List softGoodsAssociates = new ArrayList();
    private List hardGoodsAssociates = new ArrayList();
    private List storeCashiers = new ArrayList();
    private List ecomAssociates = new ArrayList();

}

