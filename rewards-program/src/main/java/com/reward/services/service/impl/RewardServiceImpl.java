package com.reward.services.service.impl;

import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.reward.services.exception.CustomerNotFoundException;
import com.reward.services.model.CustomerResponse;
import com.reward.services.model.MonthlyReward;
import com.reward.services.model.RewardResponse;
import com.reward.services.model.Transaction;
import com.reward.services.repository.TransactionRepository;

@Service
public class RewardServiceImpl implements RewardService {

	private final TransactionRepository repository;

	@Value("${rewards.last.months}")
	private int rewardMonths;

	public RewardServiceImpl(TransactionRepository repository) {
		this.repository = repository;
	}

	@Override
	public RewardResponse getRewardsByCustomerId(Long customerId) {

		LocalDate endDate = LocalDate.now();
		LocalDate startDate = endDate.minusMonths(rewardMonths);

		List<Transaction> transactions = repository.findByCustomerIdAndTransactionDateBetween(customerId, startDate,
				endDate);

		if (transactions.isEmpty()) {
			throw new CustomerNotFoundException("Customer not found");
		}

		return buildRewardResponse(transactions);
	}

	@Override
	public List<RewardResponse> getAllCustomerRewards() {

		LocalDate endDate = LocalDate.now();
		LocalDate startDate = endDate.minusMonths(rewardMonths);

		List<Transaction> transactions = repository.findByTransactionDateBetween(startDate, endDate);

		Map<Long, List<Transaction>> customerTransactions = transactions.stream()
				.collect(Collectors.groupingBy(Transaction::getCustomerId));

		return customerTransactions.values().stream().map(this::buildRewardResponse)
				.sorted(Comparator.comparing(RewardResponse::getCustomerId)).toList();
	}

	@Override
	public List<CustomerResponse> getAllCustomers() {

		LocalDate endDate = LocalDate.now();
		LocalDate startDate = endDate.minusMonths(rewardMonths);

		List<Transaction> transactions = repository.findByTransactionDateBetween(startDate, endDate);

		return transactions.stream()
				.collect(Collectors.toMap(Transaction::getCustomerId,
						transaction -> new CustomerResponse(transaction.getCustomerId(), transaction.getCustomerName()),
						(existing, duplicate) -> existing, LinkedHashMap::new))
				.values().stream().toList();
	}

	private RewardResponse buildRewardResponse(List<Transaction> transactions) {

		Map<Month, Integer> monthlyRewards = transactions.stream()
				.collect(Collectors.groupingBy(transaction -> transaction.getTransactionDate().getMonth(),
						Collectors.summingInt(transaction -> calculateRewardPoints(transaction.getAmount()))));

		List<MonthlyReward> monthlyRewardList = monthlyRewards.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.map(entry -> new MonthlyReward(entry.getKey().toString(), entry.getValue())).toList();

		int totalPoints = monthlyRewardList.stream().mapToInt(MonthlyReward::getPoints).sum();

		Transaction transaction = transactions.get(0);

		return new RewardResponse(transaction.getCustomerId(), transaction.getCustomerName(), monthlyRewardList,
				totalPoints);
	}

	@Override
	public int calculateRewardPoints(double amount) {

		if (amount <= 50) {
			return 0;
		}

		if (amount <= 100) {
			return (int) (amount - 50);
		}

		return (int) (((amount - 100) * 2) + 50);
	}
}