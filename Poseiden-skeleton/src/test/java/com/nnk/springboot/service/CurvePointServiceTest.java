package com.nnk.springboot.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurvePointServiceTest {
	@InjectMocks
	CurvePointService curvePointService;
	@Mock
	CurvePointRepository curvePointRepository;

	@Test
	public void testGettingCurvePointByUserId() {
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
		CurvePoint curvePoint1 = new CurvePoint(19, 100d, 300d);
		List<CurvePoint> curvePointList = new ArrayList<>();
		curvePointList.add(curvePoint1);
		curvePointList.add(curvePoint);
		when(curvePointRepository.getCurvePointByUserID(Mockito.anyInt())).thenReturn(curvePointList);
		List<CurvePoint> methodUnderTest = curvePointService.gettingCurvePointByUserId(1);
		assertEquals(methodUnderTest, curvePointList);
	}

	@Test
	public void testSaveCurvePoint() {
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
		when(curvePointRepository.save(Mockito.any(CurvePoint.class))).thenReturn(curvePoint);
		CurvePoint methodUnderTest = curvePointService.saveCurvePoint(curvePoint);
		assertEquals(methodUnderTest, curvePoint);
	}

	@Test
	public void testDeleteCurvePoint() {
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
		doNothing().when(curvePointRepository).delete(curvePoint);
		curvePointService.deleteCurvePoint(curvePoint);
		verify(curvePointRepository, times(1)).delete(curvePoint);
	}
	@Test
	public void testGetBidListById() {
		Optional <CurvePoint> optional = Optional.of(new CurvePoint(1,10, 10d, 30d));
		CurvePoint curvePoint = optional.get();
		when(curvePointRepository.findById(Mockito.anyInt())).thenReturn(optional);
		CurvePoint methodUnderTest = curvePointService.getCurvePointById(1);
		assertEquals(curvePoint,methodUnderTest);
	}
	@Test
	public void testGetBidListByIdWhenIdISNull() {
		Optional <CurvePoint> optional = null;
		when(curvePointRepository.findById(Mockito.anyInt())).thenReturn(optional);
		assertThrows(RuntimeException.class, () -> curvePointService.getCurvePointById(1));
	}

}
