package com.nnk.springboot.controllers;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;
@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleNameControllerTest {
	@Mock
	RedirectAttributes attribute;
	@Mock
	Model model;
	@Mock
	MockHttpServletRequest request;
	@Mock
	BindingResult result;
	@Mock
	RuleNameService ruleNameService;
	@InjectMocks
	RuleNameController ruleNameController;
	@Test
	public void testHomewhenLogInRequestIsNull() {
		HttpSession sessionMock = Mockito.mock(HttpSession.class);
		Mockito.when(request.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute("userId")).thenReturn(null);
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		RuleName rule1 = new RuleName("Rule1 Name", "Description1", "Json1", "Template1", "SQL1", "SQL1 Part");
		List<RuleName> ruleNameList = new ArrayList<>();
		ruleNameList.add(rule1);
		ruleNameList.add(rule);
		when(ruleNameService.listOfRuleNameByUserId(Mockito.anyInt())).thenReturn(ruleNameList);
		ModelAndView modelAndView = ruleNameController.home(model, request);
		assertEquals(modelAndView.getViewName(),"/");
	}
	@Before
	public void setUp() {
		HttpSession sessionMock = Mockito.mock(HttpSession.class);
		Mockito.when(request.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute("userId")).thenReturn(1);
	}
	@Test
	public void testHomewhenLogInRequestExist() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		RuleName rule1 = new RuleName("Rule1 Name", "Description1", "Json1", "Template1", "SQL1", "SQL1 Part");
		List<RuleName> ruleNameList = new ArrayList<>();
		ruleNameList.add(rule1);
		ruleNameList.add(rule);
		when(ruleNameService.listOfRuleNameByUserId(Mockito.anyInt())).thenReturn(ruleNameList);
		ModelAndView modelAndView = ruleNameController.home(model, request);
		assertEquals(modelAndView.getViewName(),"ruleName/list");
	}
	@Test
	public void testshowRuleForm() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		ModelAndView modelAndView = ruleNameController.showRuleForm(rule);
		assertEquals(modelAndView.getViewName(),"ruleName/add");		
	}
	@Test
	public void testValidateWhenThereIsNoError() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		when(result.hasErrors()).thenReturn(false);
		when(ruleNameService.saveRuleName(Mockito.any())).thenReturn(rule);
		ModelAndView modelAndView = ruleNameController.validate(rule, result, model, request);
		assertEquals(modelAndView.getViewName(),"ruleName/list");
	}
	@Test
	public void testValidateWhenThereisError() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		when(result.hasErrors()).thenReturn(true);
		when(ruleNameService.saveRuleName(Mockito.any())).thenReturn(rule);
		ModelAndView modelAndView = ruleNameController.validate(rule, result, model, request);
		assertEquals(modelAndView.getViewName(),"ruleName/add");
	}
	@Test
	public void testShowUpdateForm() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		when(ruleNameService.getRuleNameById(Mockito.anyInt())).thenReturn(rule);
		ModelAndView modelAndView = ruleNameController.showUpdateForm(1, request, model);
		assertEquals(modelAndView.getViewName(),"ruleName/update");
	}
	@Test
	public void testUpdateRuleName() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		when(ruleNameService.saveRuleName(Mockito.any())).thenReturn(rule);
		ModelAndView modelAndView = ruleNameController.updateRuleName(1, rule, result, model, request);
		assertEquals(modelAndView.getViewName(),"redirect:/ruleName/list");
	}
	@Test
	public void testDeleteRuleName() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		when(ruleNameService.getRuleNameById(Mockito.anyInt())).thenReturn(rule);
		doNothing().when(ruleNameService).deleteRuleName(rule);
		ruleNameController.deleteRuleName(1, model, request);
		verify(ruleNameService,times(1)).deleteRuleName(rule);
	}
}
