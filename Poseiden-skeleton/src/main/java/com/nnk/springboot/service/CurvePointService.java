package com.nnk.springboot.service;
import java.util.List;
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
}
