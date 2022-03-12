package com.nnk.springboot.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@Service
public class CurvePointService {
	@Autowired
	CurvePointRepository curvePointRepository;
	public List<CurvePoint>gettingCurvePointByUserId(int userId) {
		return curvePointRepository.getCurvePointByUserID(userId);
	}
	public CurvePoint saveCurvePoint(CurvePoint curvePoint) {
		return curvePointRepository.save(curvePoint);
	}
	public void deleteCurvePoint(CurvePoint curvePoint) {
		curvePointRepository.delete(curvePoint);
	}
	public CurvePoint getCurvePointById(int curvePointId) {
		Optional <CurvePoint> optional = curvePointRepository.findById(curvePointId);
		CurvePoint curvePoint = null;
				if(optional.isPresent()) {
					curvePoint = optional.get();
				}else { 
					throw new RuntimeException(" BidList not found for id :: " + curvePointId);
				}
				return curvePoint;
	}
}
