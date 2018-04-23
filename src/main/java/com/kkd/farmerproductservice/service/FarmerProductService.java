package com.kkd.farmerproductservice.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kkd.farmerproductservice.FarmerProductServiceApplication;
import com.kkd.farmerproductservice.model.FarmerProductBean;
import com.kkd.farmerproductservice.repository.FarmerProductRepository;

@Service
public class FarmerProductService {

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Autowired
	private FarmerProductRepository farmerProductRepository;

	// rabbitMq message generator
	public void produceMsg(String msg) {
		// using the template defining the needed parameters- exchange name,key and
		// message
		amqpTemplate.convertAndSend(FarmerProductServiceApplication.EXCHANGE_NAME,
				FarmerProductServiceApplication.ROUTING_KEY, msg);
		System.out.println("Send msg = " + msg);
	}

	// Generating KKD_PRODUCT_ID
	public void generateKkdProductId(FarmerProductBean farmerProductBean) {
		if (farmerProductRepository.count() == 0) {
			farmerProductBean.setProductId("KKDPROD" + 3000);
		} else {
			String idNumber = null;
			String lastKKDId = farmerProductRepository.findAll().get((int) (farmerProductRepository.count() - 1))
					.getProductId();
			Pattern pattern = Pattern.compile("[0-9]+");
			Matcher matcher = pattern.matcher(lastKKDId);
			while (matcher.find()) {
				idNumber = matcher.group();
			}
			farmerProductBean.setProductId("KKDPROD" + (Integer.parseInt(idNumber) + 1));
		}
	}

}
