package com.techelevator;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

public class VendingMachingCLITests {

	VendingMachineCLI cli = new VendingMachineCLI(null);
	
	@Test
	public void quartersTest() throws Exception {
		BigDecimal five = new BigDecimal(5.00);
		
		cli.money.setMoneyInput(five);
	
		cli.finishTransaction();
		cli.getQuarters();
		
		assertEquals(20, cli.getQuarters());
		}
	
	
		
	}


