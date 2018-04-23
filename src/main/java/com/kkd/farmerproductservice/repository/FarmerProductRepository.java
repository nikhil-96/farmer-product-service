package com.kkd.farmerproductservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kkd.farmerproductservice.model.FarmerProductBean;

public interface FarmerProductRepository extends MongoRepository<FarmerProductBean, String> {

	public List<FarmerProductBean> findAllBykkdFarmId(String kkdFarmId);
}
