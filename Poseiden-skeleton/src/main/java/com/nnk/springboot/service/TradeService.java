package com.nnk.springboot.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@Service
public class TradeService {
	/**
	 * injects tradeRepository
	 */
	@Autowired
	TradeRepository tradeRepository;
	/**
	 * 
	 * @param userId
	 * @return list of Trade by user id
	 */
	public List<Trade> listOfTradeByAUser(int userId){
		return tradeRepository.getTradeListByUserID(userId);
	}
	/**
	 * 
	 * @param trade
	 * @return save trade of param 
	 */
	public Trade saveATrade(Trade trade) {
		return tradeRepository.save(trade);
	}
	/**
	 * delete trade provided in parameter 
	 * @param trade
	 */
	public void deleteATrade(Trade trade) {
		tradeRepository.delete(trade);
	}
	/**
	 * 
	 * @param id
	 * @return trade by id or if not found throw an error
	 */
	public Trade getTradeById(int id) {
		Optional <Trade> optional = tradeRepository.findById(id);
		Trade trade = null;
		if(optional.isPresent()) {
			trade = optional.get();
		}else {
			throw new RuntimeException("Trade not found by id " + id);
		}
		return trade;
	}
}
