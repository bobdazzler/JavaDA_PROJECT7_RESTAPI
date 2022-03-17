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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BidListControllerTest {
	@Mock
	RedirectAttributes attribute;
	@Mock
	Model model;
	@Mock
	MockHttpServletRequest request;
	@Mock
	BidService bidService;
	@InjectMocks
	BidListController bidListController;
	@Mock
	BindingResult result;

	@Test
	public void testHomePageOfUserWhenRequestIsNull() {
		HttpSession sessionMock = Mockito.mock(HttpSession.class);
		Mockito.when(request.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute("userId")).thenReturn(null);
		List<BidList> listOfBids = new ArrayList<>();
		BidList bid = new BidList("Account Test", "Type Test", 10d);
		BidList bid2 = new BidList("Account Test2", "Type Test2", 100d);
		listOfBids.add(bid2);
		listOfBids.add(bid);
		when(bidService.gettingBidListByUserId(Mockito.anyInt())).thenReturn(listOfBids);
		ModelAndView modelAndView = bidListController.home(request, model);
		assertEquals("/", modelAndView.getViewName());
	}

	@Before
	public void setUp() {
		HttpSession sessionMock = Mockito.mock(HttpSession.class);
		Mockito.when(request.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute("userId")).thenReturn(1);
	}

	@Test
	public void testHome() {
		List<BidList> listOfBids = new ArrayList<>();
		BidList bid = new BidList("Account Test", "Type Test", 10d);
		BidList bid2 = new BidList("Account Test2", "Type Test2", 100d);
		listOfBids.add(bid2);
		listOfBids.add(bid);
		when(bidService.gettingBidListByUserId(Mockito.anyInt())).thenReturn(listOfBids);
		ModelAndView modelAndView = bidListController.home(request, model);
		assertEquals("bidList/list", modelAndView.getViewName());
	}

	@Test
	public void testAddBidForm() {
		BidList bid = new BidList("Account Test", "Type Test", 10d);
		ModelAndView modelAndView = bidListController.addBidForm(bid);
		assertEquals(modelAndView.getViewName(),"bidList/add");
	}

	@Test
	public void testValidate() {
		BidList bid = new BidList("Account Test", "Type Test", 10d);
		when(result.hasErrors()).thenReturn(false);
		when(bidService.saveBidList(Mockito.any())).thenReturn(bid);
		ModelAndView modelAndView = bidListController.validate(bid, result, model, request);
		assertEquals(modelAndView.getViewName(),"redirect:/bidList/list");
	}
	@Test
	public void testValidateWhenThereIsError() {
		BidList bid = new BidList("Account Test", "Type Test", 10d);
		when(result.hasErrors()).thenReturn(true);
		when(bidService.saveBidList(Mockito.any())).thenReturn(bid);
		ModelAndView modelAndView = bidListController.validate(bid, result, model, request);
		assertEquals(modelAndView.getViewName(),"bidList/add");
	}

	@Test
	public void testShowUpdateForm() {
		Integer id = 1;
		BidList bid = new BidList(1,"Account Test", "Type Test", 10d);
		when(bidService.getBidListById(Mockito.anyInt())).thenReturn(bid);
		ModelAndView modelAndView = bidListController.showUpdateForm(id, model);
		assertEquals(modelAndView.getViewName(),"bidList/update");
	}

	@Test
	public void testUpdateBid() {
		BidList bid = new BidList(1,"Account Test", "Type Test", 10d);
		when(bidService.saveBidList(Mockito.any())).thenReturn(bid);
		ModelAndView modelAndView = bidListController.updateBid(1, bid, result, request, model);
		assertEquals(modelAndView.getViewName(),"redirect:/bidList/list");
	}

	@Test
	public void testDeleteBid() {
		BidList bid = new BidList(1,"Account Test", "Type Test", 10d);
		when(bidService.getBidListById(Mockito.anyInt())).thenReturn(bid);
		doNothing().when(bidService).deleteBid(bid);
		bidListController.deleteBid(1, model, request);
		verify(bidService,times(1)).deleteBid(bid);
	}

}
