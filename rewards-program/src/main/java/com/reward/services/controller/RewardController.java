package com.reward.services.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reward.services.model.CustomerResponse;
import com.reward.services.model.RewardResponse;
import com.reward.services.service.impl.RewardService;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    private final RewardService rewardService;

    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    // API 1 - Get all customer rewards
    @GetMapping("/allRewards")
    public List<RewardResponse> getAllRewards() {

        return rewardService.getAllCustomerRewards();
    }

    // API 2 - Get rewards by customer id
    @GetMapping("/{customerId}")
    public RewardResponse getRewards(
            @PathVariable Long customerId) {

        return rewardService.getRewardsByCustomerId(customerId);
    }

    // API 3 - Get all customers
    @GetMapping("/allCustomers")
    public List<CustomerResponse> getAllCustomers() {

        return rewardService.getAllCustomers();
    }
}
