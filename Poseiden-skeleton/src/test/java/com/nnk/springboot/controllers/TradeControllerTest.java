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
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeControllerTest {
	@Mock
	RedirectAttributes attribute;
	@Mock
	Model model;
	@Mock
	MockHttpServletRequest request;
	@Mock
	BindingResult result;
	@InjectMocks
	TradeController tradeController;
	@Mock
	TradeService tradeService;

	@Test
	public void testHomeWhenRequestIsNull() {
		HttpSession sessionMock = Mockito.mock(HttpSession.class);
		Mockito.when(request.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute("userId")).thenReturn(null);
		Trade trade = new Trade(2, "Trade Account", "Type");
		Trade trade1 = new Trade(3, "Trade Account1", "Type1");
		List<Trade> tradingList = new ArrayList<>();
		tradingList.add(trade1);
		tradingList.add(trade);
		when(tradeService.listOfTradeByAUser(Mockito.anyInt())).thenReturn(tradingList);
		ModelAndView modelAndView = tradeController.home(model, request);
		assertEquals(modelAndView.getViewName(), "/");
	}

	@Before
	public void setUp() {
		HttpSession sessionMock = Mockito.mock(HttpSession.class);
		Mockito.when(request.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute("userId")).thenReturn(1);
	}

	@Test
	public void testHomeWhenRequestExist() {
		Trade trade = new Trade(2, "Trade Account", "Type");
		Trade trade1 = new Trade(3, "Trade Account1", "Type1");
		List<Trade> tradingList = new ArrayList<>();
		tradingList.add(trade1);
		tradingList.add(trade);
		when(tradeService.listOfTradeByAUser(Mockito.anyInt())).thenReturn(tradingList);
		ModelAndView modelAndView = tradeController.home(model, request);
		assertEquals(modelAndView.getViewName(), "trade/list");
	}

	@Test
	public void testAddUser() {
		Trade trade = new Trade(2, "Trade Account", "Type");
		ModelAndView modelAndView = tradeController.addUser(trade);
		assertEquals(modelAndView.getViewName(), "trade/add");

	}

	@Test
	public void testValidatewhenThereIsNoError() {
		Trade trade = new Trade(2, "Trade Account", "Type");
		when(result.hasErrors()).thenReturn(false);
		when(tradeService.saveATrade(Mockito.any())).thenReturn(trade);
		ModelAndView modelAndView = tradeController.validate(trade, result, model, request);
		assertEquals(modelAndView.getViewName(), "trade/list");
	}

	@Test
	public void testValidatewhenThereIsError() {
		Trade trade = new Trade(2, "Trade Account", "Type");
		when(result.hasErrors()).thenReturn(true);
		when(tradeService.saveATrade(Mockito.any())).thenReturn(trade);
		ModelAndView modelAndView = tradeController.validate(trade, result, model, request);
		assertEquals(modelAndView.getViewName(), "trade/add");
	}

	@Test
	public void testShowUpdateForm() {
		Trade trade = new Trade(2, "Trade Account", "Type");
		when(tradeService.getTradeById(Mockito.anyInt())).thenReturn(trade);
		ModelAndView modelAndView = tradeController.showUpdateForm(2, model, request);
		assertEquals(modelAndView.getViewName(),"trade/update");
	}

	@Test
	public void testUpdateTrade() {
		Trade trade = new Trade(2, "Trade Account", "Type");
		when(tradeService.saveATrade(Mockito.any())).thenReturn(trade);
		ModelAndView modelAndView = tradeController.updateTrade(2, trade, result, request, model);
		assertEquals(modelAndView.getViewName(),"redirect:/]trade/list");
	}

	@Test
	public void testDeleteTrade() {
		Trade trade = new Trade(2, "Trade Account", "Type");
		when(tradeService.getTradeById(Mockito.anyInt())).thenReturn(trade);
		doNothing().when(tradeService).deleteATrade(trade);
		tradeController.deleteTrade(1, model, request);
		verify(tradeService,times(1)).deleteATrade(trade);
	}

}
