package com.example.respository;

import java.util.Date;
import java.util.List;

import com.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.BillDetailEntity;
import com.example.entity.BillEntity;

public interface InBillDetailRes extends JpaRepository<BillDetailEntity, Integer>{
    
	@Query("SELECT b FROM BillDetailEntity b WHERE b.bill = :bill")
	List<BillDetailEntity> findByBill(@Param("bill") BillEntity bill);
	List<BillDetailEntity> findByUser(UserEntity userEntity);

	@Query("SELECT a FROM BillDetailEntity a left join BillEntity b on a.bill.id=b.id where b.create_time >= :startDate AND b.create_time <= :endDate")
	List<BillDetailEntity> findByTime(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
