package com.nnk.springboot.repositories;
import com.nnk.springboot.domain.BidList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer>  {

	 @Query("SELECT bidList FROM BidList bidList WHERE bidList.userId = ?1")
		List<BidList> getBidListByUserID(int userID);
}
