package com.nnk.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@Service
public class BidService {
	/**
	 * inject bidListRepository
	 */
	@Autowired
	BidListRepository bidListRepository;

	/**
	 * get bidList of users by user id
	 * 
	 * @param userId
	 * @return BidList
	 */
	public List<BidList> gettingBidListByUserId(int userId) {
		return bidListRepository.getBidListByUserID(userId);
	}

	/**
	 * save a bidList
	 * 
	 * @param bidList
	 * @return saved bidList
	 */
	public BidList saveBidList(BidList bidList) {
		return bidListRepository.save(bidList);
	}

	/**
	 * delete bidList
	 * 
	 * @param BidList
	 */
	public void deleteBid(BidList BidList) {
		bidListRepository.delete(BidList);
	}

	/**
	 * find bidList by bidListId if found return BidList else throw a runTimeError
	 * 
	 * @param bidListId
	 * @return
	 */
	public BidList getBidListById(int bidListId) {
		Optional<BidList> optional = bidListRepository.findById(bidListId);
		BidList bidList = null;
		if (optional.isPresent()) {
			bidList = optional.get();
		} else {
			throw new RuntimeException(" BidList not found for id :: " + bidListId);
		}
		return bidList;
	}
}
