package com.example.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.BillEntity;

public interface InBillRes extends JpaRepository<BillEntity, Integer>{
    
    BillEntity findOneById(Integer id);
}
