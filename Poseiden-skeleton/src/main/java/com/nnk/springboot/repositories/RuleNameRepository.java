package com.nnk.springboot.repositories;
import com.nnk.springboot.domain.RuleName;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
	 @Query("SELECT ruleName FROM RuleName ruleName WHERE ruleName.userId = ?1")
		List<RuleName> getRuleNameByUserID(int userID);
}
