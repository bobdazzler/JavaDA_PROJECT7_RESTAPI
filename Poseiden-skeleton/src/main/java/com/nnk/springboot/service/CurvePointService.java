package com.nnk.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@Service
public class CurvePointService {
	/**
	 * injects curvePointRepository
	 */
	@Autowired
	CurvePointRepository curvePointRepository;

	/**
	 * 
	 * @param userId
	 * @return return a list of CuvePoint by userId
	 */
	public List<CurvePoint> gettingCurvePointByUserId(int userId) {
		return curvePointRepository.getCurvePointByUserID(userId);
	}

	/**
	 * 
	 * @param curvePoint
	 * @return save the curvePoint object
	 */
	public CurvePoint saveCurvePoint(CurvePoint curvePoint) {
		return curvePointRepository.save(curvePoint);
	}

	/**
	 * 
	 * @param curvePoint delete the curvePoint object from db
	 */
	public void deleteCurvePoint(CurvePoint curvePoint) {
		curvePointRepository.delete(curvePoint);
	}

	/**
	 * 
	 * @param curvePointId
	 * @return a curvePoint by the curvePointId or throw an error if the
	 *         curvePointId does not exist
	 */
	public CurvePoint getCurvePointById(int curvePointId) {
		Optional<CurvePoint> optional = curvePointRepository.findById(curvePointId);
		CurvePoint curvePoint = null;
		if (optional.isPresent()) {
			curvePoint = optional.get();
		} else {
			throw new RuntimeException(" BidList not found for id :: " + curvePointId);
		}
		return curvePoint;
	}
}
