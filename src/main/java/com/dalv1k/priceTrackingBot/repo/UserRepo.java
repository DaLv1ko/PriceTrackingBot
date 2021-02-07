package com.dalv1k.priceTrackingBot.repo;

import com.dalv1k.priceTrackingBot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    User getUserByChatId(long chatId);

    User getUserById (int id);

    
}
