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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
@RunWith(SpringRunner.class)
@SpringBootTest
public class BidServiceTest {
	@InjectMocks
	BidService bidServiceMock;
	@Mock
	BidListRepository bidListRepositoryMock;
	@Test
	public void testGettingBidListByUserId() {
		List<BidList> listOfBids = new ArrayList<>();
		BidList bid = new BidList("Account Test", "Type Test", 10d);
		BidList bid2 = new BidList("Account Test2", "Type Test2", 100d);
		listOfBids.add(bid2);
		listOfBids.add(bid);
		when(bidListRepositoryMock.getBidListByUserID(Mockito.anyInt())).thenReturn(listOfBids);
		List<BidList> methodTest = bidServiceMock.gettingBidListByUserId(1);
		assertEquals(listOfBids,methodTest);
	}

	@Test
	public void testSaveBidList() {
		BidList bid = new BidList("Account Test", "Type Test", 10d);
		when(bidListRepositoryMock.save(Mockito.any(BidList.class))).thenReturn(bid);
		BidList methodUnderTest = bidServiceMock.saveBidList(bid);
		assertEquals(methodUnderTest.getAccount(), bid.getAccount());
	}

	@Test
	public void testDeleteBid() {
		BidList bid = new BidList("Account Test", "Type Test", 10d);
		doNothing().when(bidListRepositoryMock).delete(bid);
		bidServiceMock.deleteBid(bid);
		verify(bidListRepositoryMock,times(1)).delete(bid);
	}
	@Test 
	public void getBidListByIdWhenBidListExist() {
		Optional<BidList> bid = Optional.of(new BidList("Account Test", "Type Test", 10d));
		BidList bidItem = bid.get();
		when(bidListRepositoryMock.findById(Mockito.anyInt())).thenReturn(bid);
		BidList methodUnderTest =bidServiceMock.getBidListById(1);
		assertEquals(methodUnderTest,bidItem);
	}
	@Test
	public void getBidListByIdWhenBidListIsNull() {
		Optional<BidList> bid = null;
		when(bidListRepositoryMock.findById(Mockito.anyInt())).thenReturn(bid);
		assertThrows(RuntimeException.class, () -> bidServiceMock.getBidListById(1));
	}

}
