package com.nnk.springboot.service;
import java.util.List;

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
}
