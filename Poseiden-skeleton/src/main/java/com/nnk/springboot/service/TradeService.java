package com.nnk.springboot.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@Service
public class TradeService {
	@Autowired
	TradeRepository tradeRepository;
	public List<Trade> listOfTradeByAUser(int userId){
		return tradeRepository.getTradeListByUserID(userId);
	}
	public Trade saveATrade(Trade trade) {
		return tradeRepository.save(trade);
	}
	public void deleteATrade(Trade trade) {
		tradeRepository.delete(trade);
	}
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
