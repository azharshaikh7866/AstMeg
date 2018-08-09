package com.meganet.pan.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meganet.pan.model.RequestDetail;

@Repository
public interface RequestDetailsRepository extends JpaRepository<RequestDetail, Long>{

	
	
}
