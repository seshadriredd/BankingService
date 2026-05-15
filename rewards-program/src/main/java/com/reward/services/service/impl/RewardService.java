package com.reward.services.service.impl;

import java.util.List;

import com.reward.services.model.CustomerResponse;
import com.reward.services.model.RewardResponse;

public interface RewardService {

    RewardResponse getRewardsByCustomerId(Long customerId);

    List<RewardResponse> getAllCustomerRewards();

    List<CustomerResponse> getAllCustomers();

    int calculateRewardPoints(double amount);
}