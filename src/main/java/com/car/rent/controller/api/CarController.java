package com.car.rent.controller.api;

import com.car.rent.domain.License;
import com.car.rent.service.LicenseService;
import com.car.rent.vo.CarVO;
import com.car.rent.vo.CommonResult;
import com.car.rent.constant.State;
import com.car.rent.service.CarService;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import static com.car.rent.utils.PageUtils.getPageable;
import static com.car.rent.utils.VerifyUtils.*;

/**
 * @author nayix
 * @date 2020/7/1 21:58
 */
@Api(tags = "车辆管理")
@RestController
@RequestMapping(value = "/api/v1/cars")
public class CarController {

    @Resource
    private CarService carService;
    @Resource
    private LicenseService licenseService;

    @ApiOperation(value = "添加车辆", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lid", value = "车牌区号ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "number", value = "车牌号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "price", value = "单价", required = true, dataType = "Long")
    })
    @PostMapping("/admin")
    private CommonResult<Long> addCar(@RequestParam Integer lid, @RequestParam String number, @RequestParam Long price) {
        adminVerify();
        notNullVerify(lid, number, price);
        long cid = carService.addCar(lid, number, price);
        return CommonResult.success(cid);
    }

    @ApiOperation(value = "删除车辆", httpMethod = "DELETE")
    @ApiImplicitParam(name = "cid", value = "车辆id", required = true, dataType = "Long")
    @DeleteMapping("/admin")
    private CommonResult<?> deleteCar(@RequestParam Long cid) {
        adminVerify();
        notNullVerify(cid);
        carService.deleteCar(cid);
        return CommonResult.success();
    }

    @ApiOperation(value = "更新车辆信息", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid", value = "车辆id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "lid", value = "车牌区号ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "number", value = "车牌号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "price", value = "价格", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "state", value = "车辆状态", required = true, dataType = "String")
    })
    @PutMapping("/admin")
    private CommonResult<?> updatePrice(@RequestParam Long cid, @RequestParam Integer lid, @RequestParam String number, @RequestParam Long price, @RequestParam String state) {
        adminVerify();
        notNullVerify(cid, lid, number, price);
        stringVerify(state, State.toStringList());
        carService.updateCar(cid, lid, number, price, state);
        return CommonResult.success();
    }

    @ApiOperation(value = "获取车辆状态列表", httpMethod = "GET")
    @GetMapping("/states")
    private CommonResult<List<String>> getStates() {
        return CommonResult.success(State.toStringList());
    }

    @ApiOperation(value = "获取车辆信息", httpMethod = "GET")
    @ApiImplicitParam(name = "cid", value = "车辆id", required = true, dataType = "Long")
    @GetMapping
    private CommonResult<CarVO> getCar(@RequestParam Long cid) {
        notNullVerify(cid);
        CarVO carVO = carService.getCar(cid);
        return CommonResult.success(carVO);
    }

    @ApiOperation(value = "获取车辆列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "页尺寸", dataType = "Integer")
    })
    @GetMapping("/list")
    private CommonResult<List<CarVO>> getCarList(@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        Pageable pageable = getPageable(pageIndex, pageSize);
        return CommonResult.success(carService.getCarList(pageable));
    }

    @ApiOperation(value = "获取区号列表", httpMethod = "GET")
    @GetMapping("/licenses")
    private CommonResult<List<License>> getLicenses() {
        List<License> licenseList = licenseService.getLicenseList();
        return CommonResult.success(licenseList);
    }
}
