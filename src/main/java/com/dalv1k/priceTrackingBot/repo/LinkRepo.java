package com.dalv1k.priceTrackingBot.repo;

import com.dalv1k.priceTrackingBot.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
public interface LinkRepo extends JpaRepository<Link, Integer> {

    ArrayList<Link> getLinkByUserId(int userId);
    Link getLinkById(int id);
    @Transactional
    Link deleteById(int id);

}
