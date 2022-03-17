package com.nnk.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

@Service
public class RuleNameService {
	/**
	 * injects ruleNameRepository
	 */
	@Autowired
	RuleNameRepository ruleNameRepository;

	/**
	 * 
	 * @param userId
	 * @return list of RuleName objects by user id
	 */

	public List<RuleName> listOfRuleNameByUserId(int userId) {
		return ruleNameRepository.getRuleNameByUserID(userId);
	}

	/**
	 * 
	 * @param ruleName
	 * @return save ruleName object
	 */
	public RuleName saveRuleName(RuleName ruleName) {
		return ruleNameRepository.save(ruleName);
	}

	/**
	 * 
	 * @param ruleName delete ruleName object provided
	 */
	public void deleteRuleName(RuleName ruleName) {
		ruleNameRepository.delete(ruleName);
	}

	/**
	 * 
	 * @param id
	 * @return RuleName by id else throw and error id RuleName id does not exist
	 */

	public RuleName getRuleNameById(int id) {
		Optional<RuleName> optional = ruleNameRepository.findById(id);
		RuleName ruleName = null;
		if (optional.isPresent()) {
			ruleName = optional.get();
		} else {
			throw new RuntimeException("RuleName not found by " + id);
		}
		return ruleName;
	}

}
