package com.nnk.springboot.service;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleNameServiceTest {
	@InjectMocks
	RuleNameService ruleNameService;
	@Mock
	RuleNameRepository ruleNameRepository;
	@Test
	public void testListOfRuleNameByUserId() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		RuleName rule1 = new RuleName("Rule1 Name", "Description1", "Json1", "Template1", "SQL1", "SQL1 Part");
		List<RuleName> ruleNameList = new ArrayList<>();
		ruleNameList.add(rule1);
		ruleNameList.add(rule);
		when(ruleNameRepository.getRuleNameByUserID(Mockito.anyInt())).thenReturn(ruleNameList);
		List<RuleName> methodUnderTest = ruleNameService.listOfRuleNameByUserId(1);
		assertEquals(ruleNameList,methodUnderTest);
	}

	@Test
	public void testSaveRuleName() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		when(ruleNameRepository.save(Mockito.any())).thenReturn(rule);
		RuleName methodUnderTest = ruleNameService.saveRuleName(rule);
		assertEquals(rule,methodUnderTest);
	}

	@Test
	public void testDeleteRuleName() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		doNothing().when(ruleNameRepository).delete(rule);
		ruleNameService.deleteRuleName(rule);
		verify(ruleNameRepository,times(1)).delete(rule);
	}

}
