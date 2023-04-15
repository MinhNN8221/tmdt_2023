package com.example.service.imp;

import java.util.*;

import javax.transaction.Transactional;

import com.example.entity.ProductEntity;
import com.example.entity.UserEntity;
import com.example.respository.InProductRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.BillDetailEntity;
import com.example.entity.BillEntity;
import com.example.respository.InBillDetailRes;
import com.example.service.InBillDetailService;

@Service
public class BillDetailService implements InBillDetailService{
	
	@Autowired
	private InBillDetailRes billdetail;

	@Autowired
	private InProductRes productRes;

	@Override
	@Transactional
	public void save(BillDetailEntity billDetailEntity) {
		// TODO Auto-generated method stub
		billdetail.save(billDetailEntity);
	}

	@Override
	public List<BillDetailEntity> findByBill(BillEntity bill) {
		// TODO Auto-generated method stub
		return billdetail.findByBill(bill);
	}

	public List<BillDetailEntity> findByUser(UserEntity user){
		return billdetail.findByUser(user);
	}

	public List<Float> getChartData(Date start, Date end){
		List<Float> dataChart=new ArrayList<>();
		Calendar calendar=Calendar.getInstance();
		List<Float> res=new ArrayList<>();
		for(int i=0; i<13; i++){
			res.add(0f);
		}
		List<BillDetailEntity> billDetailEntities=billdetail.findByTime(start, end);
		for(BillDetailEntity billDetail: billDetailEntities){
			ProductEntity productEntity=productRes.findOneById(billDetail.getPro_id());
			float tong=billDetail.getQuantity()*productEntity.getPrice();
			calendar.setTime(billDetail.getBill().getCreate_time());
			int month=calendar.get(Calendar.MONTH)+1;
			res.set(month, res.get(month)+tong);
		}
		for (int i=1; i<=12; i++){
			dataChart.add(res.get(i));
		}
		return  dataChart;
	}

	public float[] getChartDataTNY(Date start, Date end){
		float data[]=new float[3];
		Calendar calendar=Calendar.getInstance();
		int currentToday=calendar.get(Calendar.DATE);
		float dataToday=0; float dataYear=0; float dataYesterday=0;
		List<BillDetailEntity> billDetailEntities=billdetail.findByTime(start, end);
		for(BillDetailEntity billDetail:billDetailEntities){
			ProductEntity productEntity=productRes.findOneById(billDetail.getPro_id());
			float tong=billDetail.getQuantity()*productEntity.getPrice();
			dataYear+=tong;
			calendar.setTime(billDetail.getBill().getCreate_time());
			int today=calendar.get(Calendar.DATE);
			if(today==currentToday) {
				dataToday+=tong;
			}
			if(today==currentToday-1){
				dataYesterday+=tong;
			}
		}
		data[0]=dataToday;
		data[1]=dataYesterday;
		data[2]=dataYear;
		return data;
	}


	public List<Integer> getPieChartData(Date start, Date end){
		List<Integer> pieChart=new ArrayList<>();
		Map<String, Integer> res=new TreeMap<>();
		res.put("Gạo", 0);
		res.put("Nấm", 0);
		res.put("Thịt", 0);
		res.put("Trái cây", 0);
		res.put("Trứng", 0);

		List<BillDetailEntity> billDetailEntities=billdetail.findByTime(start, end);
		for(BillDetailEntity billDetail: billDetailEntities){
			ProductEntity productEntity=productRes.findOneById(billDetail.getPro_id());
			int tong=billDetail.getQuantity();
			String categoryName=productEntity.getCategory().getName();
			res.put(categoryName, res.get(categoryName)+tong);
		}
		for(Map.Entry<String, Integer> value:res.entrySet()){
			pieChart.add(value.getValue());
		}
		return  pieChart;
	}
}
