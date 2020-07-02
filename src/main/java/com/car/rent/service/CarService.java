package com.car.rent.service;

import com.car.rent.dto.CarDTO;
import com.car.rent.enums.constants.State;

/**
 * @author nayix
 * @date 2020/6/30 16:24
 */
public interface CarService {
    /**
     * 新增车辆
     * @param price
     * @return
     */
    CarDTO addCar(int price);

    /**
     * 删除车辆
     * @param cid
     * @return
     */
    int deleteCar(long cid);

    /**
     * 更新价格
     * @param cid
     * @param price
     * @return
     */
    int updatePrice(long cid, int price);

    /**
     * 更新状态
     * @param cid
     * @param state
     * @return
     */
    int updateState(long cid, String state);

    /**
     * 获取车辆信息
     * @param cid
     * @return
     */
    CarDTO getCar(long cid);

    /**
     * 获取车辆状态
     * @param cid
     * @return
     */
    String getCarState(long cid);
}