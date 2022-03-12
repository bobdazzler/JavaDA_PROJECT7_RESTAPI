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
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;
@RunWith(SpringRunner.class)
@SpringBootTest
public class CurveControllerTest {
	@Mock
	RedirectAttributes attribute;
	@Mock
	Model model;
	@Mock
	MockHttpServletRequest request;
	@Mock
	CurvePointService curvePointService;
	@InjectMocks
	CurveController curveController;
	@Mock
	BindingResult result;
	@Test
	public void testHomeWhenHttpRequestIsNull() {
		HttpSession sessionMock = Mockito.mock(HttpSession.class);
		Mockito.when(request.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute("userId")).thenReturn(null);
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
		CurvePoint curvePoint1 = new CurvePoint(19, 100d, 300d);
		List<CurvePoint> curvePointList = new ArrayList<>();
		curvePointList.add(curvePoint1);
		curvePointList.add(curvePoint);
		when(curvePointService.gettingCurvePointByUserId(Mockito.anyInt())).thenReturn(curvePointList);
		ModelAndView modelAndView = curveController.home(request, model);
		assertEquals(modelAndView.getViewName(),"/");
		
	}
	@Before
	public void setUp() {
		HttpSession sessionMock = Mockito.mock(HttpSession.class);
		Mockito.when(request.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute("userId")).thenReturn(1);
	}
	@Test
	public void testHomeWhenHttpRequestExist() {
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
		CurvePoint curvePoint1 = new CurvePoint(19, 100d, 300d);
		List<CurvePoint> curvePointList = new ArrayList<>();
		curvePointList.add(curvePoint1);
		curvePointList.add(curvePoint);
		when(curvePointService.gettingCurvePointByUserId(Mockito.anyInt())).thenReturn(curvePointList);
		ModelAndView modelAndView = curveController.home(request, model);
		assertEquals(modelAndView.getViewName(),"curvePoint/list");
		
	}
	@Test
	public void testAddBidForm() {
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
		ModelAndView modelAndView = curveController.addBidForm(curvePoint);
		assertEquals(modelAndView.getViewName(),"curvePoint/add");
	}

	@Test
	public void testValidate() {
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
		when(result.hasErrors()).thenReturn(false);
		when(curvePointService.saveCurvePoint(curvePoint)).thenReturn(curvePoint);
		ModelAndView modelAndView = curveController.validate(curvePoint, result, model, request);
		assertEquals(modelAndView.getViewName(),"redirect:/curvePoint/list");
	}
	@Test
	public void testValidateWhenErrorExist() {
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
		when(result.hasErrors()).thenReturn(true);
		when(curvePointService.saveCurvePoint(curvePoint)).thenReturn(curvePoint);
		ModelAndView modelAndView = curveController.validate(curvePoint, result, model, request);
		assertEquals(modelAndView.getViewName(),"curvePoint/add");
	}

	@Test
	public void testShowUpdateForm() {
		CurvePoint curvePoint = new CurvePoint(1,10, 10d, 30d);
		when(curvePointService.getCurvePointById(Mockito.anyInt())).thenReturn(curvePoint);
		ModelAndView modelAndView = curveController.showUpdateForm(1, model);
		assertEquals(modelAndView.getViewName(),"curvePoint/update");
	}

	@Test
	public void testUpdateBid() {
		CurvePoint curvePoint = new CurvePoint(1,10, 10d, 30d);
		when(curvePointService.saveCurvePoint(Mockito.any())).thenReturn(curvePoint);
		ModelAndView modelAndView = curveController.updateBid(1, curvePoint, result, request, model);
		assertEquals(modelAndView.getViewName(),"redirect:/curvePoint/list");
		
	}

	@Test
	public void testDeleteBid() {
		CurvePoint curvePoint = new CurvePoint(1,10, 10d, 30d);
		when(curvePointService.getCurvePointById(Mockito.anyInt())).thenReturn(curvePoint);
		doNothing().when(curvePointService).deleteCurvePoint(curvePoint);
		curveController.deleteBid(1, model, request);
		verify(curvePointService,times(1)).deleteCurvePoint(curvePoint);
	}

}
