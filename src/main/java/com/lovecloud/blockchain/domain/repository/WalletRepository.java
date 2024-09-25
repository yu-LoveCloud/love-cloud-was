package com.lovecloud.blockchain.domain.repository;


import com.lovecloud.blockchain.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {


}
