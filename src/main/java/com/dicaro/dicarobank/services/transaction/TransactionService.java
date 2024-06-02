package com.dicaro.dicarobank.services.transaction;

import com.dicaro.dicarobank.dto.transaction.BizumDto;
import com.dicaro.dicarobank.dto.transaction.IssueTransactionDto;
import com.dicaro.dicarobank.dto.transaction.TransactionDto;
import com.dicaro.dicarobank.model.AppUser;
import com.dicaro.dicarobank.model.Transaction;

import java.util.List;

/**
 * Interface for transaction service
 */
public interface TransactionService {
    List<TransactionDto> getOutgoingTransactionsByAccountId(String appUserDni);
    List<TransactionDto> getIncomingTransactionsByAccountId(String appUserDni);
    Transaction issueTransaction(AppUser appUser, IssueTransactionDto issueTransactionDto);
    void issueBizum(AppUser appUser, BizumDto bizumDto);
}
