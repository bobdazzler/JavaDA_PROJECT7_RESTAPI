package com.nnk.springboot.service;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
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
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeServiceTest {
	@InjectMocks
	TradeService tradeService;

	@Mock
	TradeRepository tradeRepository;

	@Test
	public void testListOfTradeByAUser() {
		Trade trade = new Trade(2, "Trade Account", "Type");
		Trade trade1 = new Trade(3, "Trade Account1", "Type1");
		List<Trade> tradingList = new ArrayList<>();
	tradingList.add(trade1);
	tradingList.add(trade);
	when(tradeRepository.getTradeListByUserID(Mockito.anyInt())).thenReturn(tradingList);
	List<Trade> methodUnderTest = tradeService.listOfTradeByAUser(1);
	assertEquals(methodUnderTest,tradingList);
	}

	@Test
	public void testSaveATrade() {
		Trade trade = new Trade(2, "Trade Account", "Type");
		when(tradeRepository.save(Mockito.any())).thenReturn(trade);
		Trade methodUnderTest = tradeService.saveATrade(trade);
		assertEquals(trade,methodUnderTest);
	}

	@Test
	public void testDeleteATrade() {
		Trade trade = new Trade(2, "Trade Account", "Type");
		doNothing().when(tradeRepository).delete(trade);
		tradeService.deleteATrade(trade);
		verify(tradeRepository,times(1)).delete(trade);
	}
	@Test
	public void testGetTradeByIdWhenTradeNameExist() {
		Optional<Trade> optional = Optional.of(new Trade(2, "Trade Account", "Type"));
		Trade trade = optional.get();
		when(tradeRepository.findById(Mockito.anyInt())).thenReturn(optional);
		Trade methodUnderTest = tradeService.getTradeById(1);
		assertEquals(methodUnderTest,trade);
	}
	@Test
	public void testGetTradeByIdWhenTradeIsNull() {
		Optional<Trade> optional = null;
		when(tradeRepository.findById(Mockito.anyInt())).thenReturn(optional);
		assertThrows(RuntimeException.class, () -> tradeService.getTradeById(1));
		
	}
}
