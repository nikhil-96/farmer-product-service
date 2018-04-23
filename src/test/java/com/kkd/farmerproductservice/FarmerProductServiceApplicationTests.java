/*package com.kkd.farmerproductservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.kkd.farmerproductservice.model.FarmerProductBean;
import com.kkd.farmerproductservice.repository.FarmerProductRepository;
import com.kkd.farmerproductservice.service.FarmerProductService;

@RunWith(SpringRunner.class)
@WebMvcTest
public class FarmerProductServiceApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	FarmerProductRepository farmerProductRepository;

	@MockBean
	FarmerProductService farmerProductService;

	List<FarmerProductBean> farmerProduct = new ArrayList<FarmerProductBean>();
	FarmerProductBean mockFarmerProduct;
	String expectedPositive;
	String expectedNegative;

	@Before
	public void before() {
		mockFarmerProduct = new FarmerProductBean("kkdprod2001", "kkdfarm04",
				"http://www.alansfruitandveg.co.uk/wp-content/uploads/2016/11/new-potatoes.jpg", "Onions",
				"They are very good.", (double) 45, (double) 9.5, (double) 50, true);

		farmerProduct.add(mockFarmerProduct);

		expectedPositive = "[{" + "\"productId\":\"kkdprod2001\"," + "\"kkdFarmId\":\"kkdfarm04\","
				+ "\"imageUrl\":\"http://www.alansfruitandveg.co.uk/wp-content/uploads/2016/11/new-potatoes.jpg\","
				+ "\"productName\":\"Onions\"," + "\"description\":\"They are very good.\"," + "\"price\":45.0,"
				+ "\"bulkOrderPrice\":9.5," + "\"quantity\":50.0," + "\"available\":true" + "}]";

		expectedNegative = "{" + "\"productId\":\"kkdprod2002\"," + "\"kkdFarmId\":\"kkdfarm04\","
				+ "\"imageUrl\":\"http://www.alansfruitandveg.co.uk/wp-content/uploads/2016/11/new-potatoes.jpg\","
				+ "\"productName\":\"Onions\"," + "\"description\":\"They are very good.\"," + "\"price\":45.0,"
				+ "\"bulkOrderPrice\":9.5," + "\"quantity\":50.0," + "\"available\":true" + "}";
	}
	
	 * POSITIVE TEST CASE FOR METHOD : getFarmerProduct() Method
	 

	@Test
	public void getFarmerProductTestP() throws Exception {
		Mockito.when(farmerProductRepository.findAllBykkdFarmId(Mockito.anyString())).thenReturn(farmerProduct);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/kkdFarm04/product")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		// System.out.println(result.getResponse());
		assertEquals(expectedPositive, result.getResponse().getContentAsString());
		assertEquals(200, result.getResponse().getStatus());
	}

	
	 * NEGATIVE TEST CASE FOR METHOD : getFarmerProduct() Method
	 

	@Test
	public void getFarmerProductTestN() throws Exception {
		Mockito.when(farmerProductRepository.findAllBykkdFarmId(Mockito.anyString())).thenReturn(farmerProduct);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/kkdFarm04/product")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		// System.out.println(result.getResponse());
		assertNotEquals(expectedNegative, result.getResponse().getContentAsString());
		assertEquals(200, result.getResponse().getStatus());
	}

	
	 * POSITIVE TEST CASE FOR METHOD : addFarmerProduct() Method
	 

	
	 * @Test public void addFarmerProductTestP() throws Exception {
	 * Mockito.when(farmerProductRepository.save(Mockito.any(FarmerProductBean.class
	 * ))).thenReturn(mockFarmerProduct);
	 * Mockito.when(farmerProductRepository.findById(Mockito.anyString())).
	 * thenReturn(Optional.of(mockFarmerProduct)); RequestBuilder requestBuilder =
	 * MockMvcRequestBuilders.post("/product").accept(MediaType.APPLICATION_JSON)
	 * .content(expectedPositive).contentType(MediaType.APPLICATION_JSON); MvcResult
	 * result = mockMvc.perform(requestBuilder).andReturn();
	 * //System.out.println(result.getResponse()); assertEquals(expectedPositive,
	 * result.getResponse().getContentAsString()); assertEquals(201,
	 * result.getResponse().getStatus()); }
	 

	
	 * NEGATIVE TEST CASE FOR METHOD : addFarmerProduct() Method
	 

	
	 * @Test public void addFarmerProductTestN() throws Exception {
	 * Mockito.when(farmerProductRepository.findAllBykkdFarmId(Mockito.anyString()))
	 * .thenReturn(farmerProduct); RequestBuilder requestBuilder =
	 * MockMvcRequestBuilders.get("/farmer/kkdFarm04/product").accept(MediaType.
	 * APPLICATION_JSON); MvcResult result =
	 * mockMvc.perform(requestBuilder).andReturn();
	 * System.out.println(result.getResponse()); assertNotEquals(expectedNegative,
	 * result.getResponse().getContentAsString()); assertEquals(200,
	 * result.getResponse().getStatus()); }
	 
}
*/