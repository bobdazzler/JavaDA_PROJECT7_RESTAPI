package com.nnk.springboot.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@Service
public class BidService {
	@Autowired
	BidListRepository bidListRepository;
	public List<BidList> gettingBidListByUserId(int userId){
		return bidListRepository.getBidListByUserID(userId);
	}
	public BidList saveBidList(BidList bidList) {
		return bidListRepository.save(bidList);
	}
	public void deleteBid(BidList BidList) {
	 bidListRepository.delete(BidList);
	}
	public BidList getBidListById(int bidListId) {
		Optional <BidList> optional = bidListRepository.findById(bidListId);
		BidList bidList = null;
		if(optional.isPresent()) {
			bidList = optional.get();
		}else { 
			throw new RuntimeException(" BidList not found for id :: " + bidListId);
		}
		return bidList;
	}
}
