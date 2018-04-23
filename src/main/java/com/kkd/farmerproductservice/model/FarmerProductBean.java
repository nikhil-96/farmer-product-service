package com.kkd.farmerproductservice.model;

import java.util.Arrays;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Document(collection = "farmer_products")
@ApiModel(description = "All details about farmer products")
public class FarmerProductBean {

	@Id
	@ApiModelProperty(notes = "Unique id for the farmer product")
	private String productId;
	@NotNull
	@ApiModelProperty(notes = "Unique id of the farmer")
	private String kkdFarmId;
	@NotNull
	@ApiModelProperty(notes = "Image Url for farmer product")
	private String imageUrl;
	@NotNull
	@ApiModelProperty(notes = "Name for the farmer product")
	private String productName;
	@NotNull
	@ApiModelProperty(notes = "Description for the farmer product")
	private String description;
	@NotNull
	@ApiModelProperty(notes = "Price for the farmer product")
	private Double price;
	@NotNull
	@ApiModelProperty(notes = "Bulkorder price for the farmer product")
	private Double bulkOrderPrice;
	@NotNull
	@ApiModelProperty(notes = "Quantity for the farmer product")
	private Double quantity;
	@NotNull
	@ApiModelProperty(notes = "Boolean if farmer product is available or not")
	private Boolean available;
	@ApiModelProperty(notes = "Cities where farmer can deliver the products")
	private String[] cities;

	public FarmerProductBean() {
		super();
	}

	public FarmerProductBean(String productId, @NotNull String kkdFarmId, @NotNull String imageUrl,
			@NotNull String productName, @NotNull String description, @NotNull Double price,
			@NotNull Double bulkOrderPrice, @NotNull Double quantity, @NotNull Boolean available, String[] cities) {
		super();
		this.productId = productId;
		this.kkdFarmId = kkdFarmId;
		this.imageUrl = imageUrl;
		this.productName = productName;
		this.description = description;
		this.price = price;
		this.bulkOrderPrice = bulkOrderPrice;
		this.quantity = quantity;
		this.available = available;
		this.cities = cities;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getKkdFarmId() {
		return kkdFarmId;
	}

	public void setKkdFarmId(String kkdFarmId) {
		this.kkdFarmId = kkdFarmId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getBulkOrderPrice() {
		return bulkOrderPrice;
	}

	public void setBulkOrderPrice(Double bulkOrderPrice) {
		this.bulkOrderPrice = bulkOrderPrice;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public String[] getCities() {
		return cities;
	}

	public void setCities(String[] cities) {
		this.cities = cities;
	}

	@Override
	public String toString() {
		return "FarmerProductBean [productId=" + productId + ", kkdFarmId=" + kkdFarmId + ", imageUrl=" + imageUrl
				+ ", productName=" + productName + ", description=" + description + ", price=" + price
				+ ", bulkOrderPrice=" + bulkOrderPrice + ", quantity=" + quantity + ", available=" + available
				+ ", cities=" + Arrays.toString(cities) + "]";
	}

}
