package com.meganet.pan.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meganet.pan.model.RequestDetail;
import com.meganet.pan.repository.RequestDetailsRepository;
/*import com.meganet.pan.repository.RequestDetailRepository;*/
import com.meganet.pan.services.RequestDetailService;

@Service
public class RequestDetailServiceImpl implements RequestDetailService {
	
	@Autowired
	private RequestDetailsRepository reqRepository;
	
	@Override
	public RequestDetail save(RequestDetail request) {
		return reqRepository.save(request);
	}

}
