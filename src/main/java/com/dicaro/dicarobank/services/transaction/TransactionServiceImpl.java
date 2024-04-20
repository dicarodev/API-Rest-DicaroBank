package com.dicaro.dicarobank.services.transaction;

import com.dicaro.dicarobank.model.Transaction;
import com.dicaro.dicarobank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    @Override
    public Optional<List<Transaction>> getOriginTransactionsByAccountId(Long id) {
        //Mirar para recuperar solo los datos necesario(hacer stream y map y filter o ver como, devuelve muchos datos)

        return transactionRepository.findTransactionsByOriginAccountIdOrderByTransactionDate(id);
    }
}
