package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {
	 @Query("SELECT curvePoint FROM CurvePoint curvePoint WHERE curvePoint.userId = ?1")
		List<CurvePoint> getCurvePointByUserID(int userID);
}
