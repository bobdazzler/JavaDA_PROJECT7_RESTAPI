package com.nnk.springboot.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@Service
public class BidService {
	@Autowired
	BidListRepository bidListRepository;
	public List<BidList> gettingBidList(){
		return bidListRepository.findAll();
	}
	public List<BidList> gettingBidListByUserId(int userId){
		return bidListRepository.getBidListByUserID(userId);
	}
	public BidList saveBidList(BidList bidList) {
		return bidListRepository.save(bidList);
	}
	public void deleteBid(BidList BidList) {
	 bidListRepository.delete(BidList);
	}
}
