package com.reward.services.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.reward.services.exception.CustomerNotFoundException;
import com.reward.services.model.CustomerResponse;
import com.reward.services.model.RewardResponse;
import com.reward.services.service.impl.RewardServiceImpl;

@SpringBootTest
public class RewardServiceTest {

	@Autowired
	private RewardServiceImpl rewardService;

	// =========================
	// Reward Calculation Tests
	// =========================

	@Test
	void shouldReturnZeroPointsForAmountLessThan50() {

		assertEquals(0, rewardService.calculateRewardPoints(25));
	}

	@Test
	void shouldReturnZeroPointsFor50() {

		assertEquals(0, rewardService.calculateRewardPoints(50));
	}

	@Test
	void shouldReturnOnePointFor51() {

		assertEquals(1, rewardService.calculateRewardPoints(51));
	}

	@Test
	void shouldReturn25PointsFor75() {

		assertEquals(25, rewardService.calculateRewardPoints(75));
	}

	@Test
	void shouldReturn49PointsFor99() {

		assertEquals(49, rewardService.calculateRewardPoints(99));
	}

	@Test
	void shouldReturn50PointsFor100() {

		assertEquals(50, rewardService.calculateRewardPoints(100));
	}

	@Test
	void shouldReturn52PointsFor101() {

		assertEquals(52, rewardService.calculateRewardPoints(101));
	}

	@Test
	void shouldReturn90PointsFor120() {

		assertEquals(90, rewardService.calculateRewardPoints(120));
	}

	@Test
	void shouldReturn250PointsFor200() {

		assertEquals(250, rewardService.calculateRewardPoints(200));
	}

	@Test
	void shouldReturn350PointsFor250() {

		assertEquals(350, rewardService.calculateRewardPoints(250));
	}

	// =========================
	// Customer Reward Tests
	// =========================

	@Test
	void shouldGetCustomerRewards() {

		RewardResponse response = rewardService.getRewardsByCustomerId(101L);

		assertEquals(101L, response.getCustomerId());

		assertEquals("Seshadri", response.getCustomerName());

		assertFalse(response.getMonthlyRewards().isEmpty());
	}

	@Test
	void shouldThrowExceptionForInvalidCustomer() {

		assertThrows(CustomerNotFoundException.class, () -> rewardService.getRewardsByCustomerId(999L));
	}

	@Test
	void shouldGetAllCustomerRewards() {

		List<RewardResponse> responses = rewardService.getAllCustomerRewards();

		assertFalse(responses.isEmpty());
	}

	@Test
	void shouldGetAllCustomers() {

		List<CustomerResponse> customers = rewardService.getAllCustomers();

		assertFalse(customers.isEmpty());
	}

	@Test
	void shouldNotIncludeOldCustomerTransactions() {

		List<CustomerResponse> customers = rewardService.getAllCustomers();

		boolean oldCustomerExists = customers.stream().anyMatch(customer -> customer.getCustomerId().equals(106L));

		assertFalse(oldCustomerExists);
	}

	@Test
	void shouldReturnUniqueCustomersOnly() {

		List<CustomerResponse> customers = rewardService.getAllCustomers();

		long count = customers.stream().filter(customer -> customer.getCustomerId().equals(101L)).count();

		assertEquals(1, count);
	}
}