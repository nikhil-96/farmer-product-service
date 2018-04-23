package com.kkd.farmerproductservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import org.bson.Document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kkd.farmerproductservice.FarmerProductServiceApplication;
import com.kkd.farmerproductservice.model.FarmerProductBean;
import com.kkd.farmerproductservice.repository.FarmerProductRepository;
import com.kkd.farmerproductservice.service.FarmerProductService;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
public class FarmerProductController {

	private static final Logger log = LoggerFactory.getLogger(FarmerProductServiceApplication.class);

	@Autowired
	private FarmerProductRepository farmerProductRepository;

	@Autowired
	private FarmerProductService farmerProductService;

	// Adding farmer products into database
	@PostMapping("/farmer/product")
	@HystrixCommand(fallbackMethod = "addFarmerProductFallback")
	@ApiOperation("To add the farmer products into database")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully added farmer product into database"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<Optional<FarmerProductBean>> addFarmerProduct(@Valid @RequestBody FarmerProductBean product) {

		farmerProductService.generateKkdProductId(product);

		farmerProductRepository.save(product);
		farmerProductService.produceMsg("Adding farmer product into farmer-product-db");
		return ResponseEntity.status(HttpStatus.CREATED).body(farmerProductRepository.findById(product.getProductId()));
	}

	// Fallback method for the above mapping
	@ApiOperation("In case of addFarmerProduct fallback")
	public ResponseEntity<Optional<FarmerProductBean>> addFarmerProductFallback(
			@RequestBody FarmerProductBean product) {
		farmerProductService.produceMsg("Error in adding farmer product into farmer-product-db");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

	// Getting all products of a particular farmer from database
	@GetMapping("/farmer/{farmer_id}/product")
	@HystrixCommand(fallbackMethod = "getFarmerProductFallback")
	@ApiOperation("To get all products of particular farmer from database")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieve all products of a farmer from database"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<List<FarmerProductBean>> getFarmerProduct(@PathVariable("farmer_id") String farmer_id) {
		farmerProductService.produceMsg("Getting all products of a particular farmer");
		return ResponseEntity.status(HttpStatus.OK).body(farmerProductRepository.findAllBykkdFarmId(farmer_id));
	}

	// Fallback method for the above mapping
	@ApiOperation("In case of getFarmerProduct fallback")
	public ResponseEntity<List<FarmerProductBean>> getFarmerProductFallback(@PathVariable String farmer_id) {
		farmerProductService.produceMsg("Error in getting all products of a particular farmer");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

	// Updating a product into database
	@PutMapping("/farmer/product")
	@HystrixCommand(fallbackMethod = "updateFarmerProductFallback")
	@ApiOperation("To update a particular product of a farmer into database")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Successfully updated a farmer product into database"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<FarmerProductBean> updateFarmerProduct(@RequestBody FarmerProductBean product) {

		farmerProductService.produceMsg("Updating a product into farmer-product-db");
		String product_id = product.getProductId();
		Optional<FarmerProductBean> updatedProduct = null;
		if (farmerProductRepository.findById(product_id).isPresent()) {
			farmerProductRepository.save(product);
			updatedProduct = farmerProductRepository.findById(product_id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedProduct.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	// Fallback method for the above mapping
	@ApiOperation("In case of updateFarmerProduct fallback")
	public ResponseEntity<FarmerProductBean> updateFarmerProductFallback(@RequestBody FarmerProductBean product) {
		farmerProductService.produceMsg("Error in updating a product into farmer-product-db");
		Optional<FarmerProductBean> updatedProduct = null;
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

	// Deleting a product from database
	@DeleteMapping("/farmer/product/{product_id}")
	@HystrixCommand(fallbackMethod = "deleteFarmerProductFallback")
	@ApiOperation("To delete a particular product of a farmer from database")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Successfully deleted farmer product from database"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<Boolean> deleteFarmerProduct(@PathVariable("product_id") String product_id) {
		farmerProductService.produceMsg("Deleting a product from farmer-product-db");
		Optional<FarmerProductBean> product = farmerProductRepository.findById(product_id);
		if (product.isPresent()) {
			farmerProductRepository.delete(product.get());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(true);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
	}

	// Fallback method for the above mapping
	@ApiOperation("In case of deleteFarmerProduct fallback")
	public ResponseEntity<Boolean> deleteFarmerProductFallback(@PathVariable String product_id) {
		farmerProductService.produceMsg("Error in deleting a product from farmer-product-db");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
	}

	@SuppressWarnings("finally")
	@GetMapping("/search-key/{location}/{searchKey}")
	@HystrixCommand(fallbackMethod = "getSearchProductsFallback")
	@ApiOperation("To get all products on basis of search results using location and productName")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieve all products on basis of search results using location and productName from database"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<List<Document>> getSearchProducts(@PathVariable(value = "searchKey") String searchKey,
			@PathVariable(value = "location") String location) {

		MongoClient mongoClient;
		MongoDatabase database;
		MongoCollection<Document> collection;
		Boolean caseSensitive = false;
		Boolean diacriticSensitive = false;
		List<Document> searchResults = new ArrayList<Document>();
		try {
			farmerProductService.produceMsg("Getting search results...");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		mongoClient = new MongoClient(new MongoClientURI("mongodb://10.151.60.164:27017"));
		database = mongoClient.getDatabase("kkdDb");
		collection = database.getCollection("farmer_products");

		collection.createIndex(
				new Document("productName", "text").append("description", "text").append("\"cities\"", "text"),
				new IndexOptions());

		try {
			MongoCursor<Document> cursor = null;
			cursor = collection.find(new Document("$text",
					new Document("$search", searchKey).append("$caseSensitive", new Boolean(caseSensitive))
							.append("$diacriticSensitive", new Boolean(diacriticSensitive))).append("cities", location))
					.iterator();

			while (cursor.hasNext()) {
				Document article = cursor.next();
				// System.out.println(article.get("productName"));

				searchResults.add(article);
				// System.out.println(searchResults.get(0));
			}

			cursor.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			collection.dropIndexes();
			mongoClient.close();
			return ResponseEntity.status(HttpStatus.OK).body(searchResults);
		}
	}

	// Fallback method for the above mapping
	@ApiOperation("In case of getSearchProducts fallback")
	public ResponseEntity<List<Document>> getSearchProductsFallback(@PathVariable(value = "searchKey") String searchKey,
			@PathVariable(value = "location") String location) {
		farmerProductService.produceMsg("Error in getting search results from farmer-product-db");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

}
