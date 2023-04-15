package com.example.service;

import java.util.Date;
import java.util.List;

import com.example.entity.BillDetailEntity;
import com.example.entity.BillEntity;
import com.example.entity.UserEntity;

public interface InBillDetailService {
    void save(BillDetailEntity billDetailEntity);
    List<BillDetailEntity> findByBill(BillEntity bill);
    List<BillDetailEntity> findByUser(UserEntity user);

    List<Float> getChartData(Date start, Date end);
    float[] getChartDataTNY(Date start, Date end);
    List<Integer> getPieChartData(Date start, Date end);
}
