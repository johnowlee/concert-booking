package hhplus.concert.representer.api.transaction;

import hhplus.concert.application.transaction.data.query.UserTransactionQuery;
import hhplus.concert.application.transaction.usecase.FindUserTransactionsUseCase;
import hhplus.concert.core.transaction.domain.model.Transaction;
import hhplus.concert.representer.api.RestApiResponse;
import hhplus.concert.representer.api.transaction.response.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class TransactionController {

    private final FindUserTransactionsUseCase findUserTransactionsUseCase;
    private final TransactionControllerMapper mapper;

    @GetMapping("/{userId}/transactions")
    public RestApiResponse<List<TransactionResponse>> fetchTransactions(@PathVariable Long userId,
                                                                        @PageableDefault(
                                                                                size = 10,
                                                                                sort = "transactionDateTime",
                                                                                direction = Sort.Direction.DESC) Pageable pageable) {
        UserTransactionQuery query = mapper.toTransactionsByUserIdQuery(userId, pageable);
        Page<Transaction> transactions = findUserTransactionsUseCase.execute(query);
        return RestApiResponse.ok(mapper.toTransactionResponseList(transactions));
    }
}
