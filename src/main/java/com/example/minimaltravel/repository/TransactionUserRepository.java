package com.example.minimaltravel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.minimaltravel.dto.BalanceDTO;
import com.example.minimaltravel.model.TransactionUser;

import jakarta.transaction.Transactional;

public interface TransactionUserRepository extends JpaRepository<TransactionUser, Long> {
	List<TransactionUser> findByTransaction_TransactionId(Long transactionId);

    @Modifying
    @Transactional
    @Query("DELETE FROM TransactionUser tu WHERE tu.transaction.transactionId = :transactionId")
    void deleteByTransaction_TransactionId(Long transactionId);

    // Calcula los balances finales entre cada uno de los usuarios
    @Query(value = """
        with raw_data as (
            select 
                allt.creditor_user_id as creditorUserId,
                cred.user_name as creditorUserName,
                deb.user_id as debtorUserId,
                deb.user_name as debtorUserName,
                sum(tran.amount) as amount
            from minimal_travel.transaction_user as tran
            left join minimal_travel.transaction allt using(transaction_id)
            left join  minimal_travel.user cred 
                on cred.user_id = allt.creditor_user_id
            left join  minimal_travel.user deb
                on deb.user_id = tran.user_id
            where allt.creditor_user_id != deb.user_id
            group by 1,2,3,4
        )

        select 
            t.creditorUserId,
            t.creditorUserName,
            t.debtorUserId,
            t.debtorUserName,
            case
                when inv.amount is null
                    then t.amount
                when inv.amount - t.amount < 0
                    then abs(inv.amount - t.amount)
                end as amount
        from raw_data t
        left join raw_data inv
            on t.creditorUserId = inv.debtorUserId
            and t.debtorUserId = inv.creditorUserId
        having amount is not null
        """, nativeQuery = true)
    List<BalanceDTO> getBalances();
}
