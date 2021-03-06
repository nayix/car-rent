package com.car.rent.constant;

import com.car.rent.constant.response.ResultCode;
import com.car.rent.domain.Car;
import com.car.rent.exception.Asserts;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author nayix
 * @date 2020/6/28 19:49
 */
@Getter
@AllArgsConstructor
public enum State {
    // normal
    NORMAL("Normal"),

    // used
    USED("Used"),

    // malfunction
    MALFUNCTION("Malfunction")
    ;

    private final String state;

    public static List<String> toStringList() {
        return Arrays.stream(State.values()).map(State::getState).collect(Collectors.toList());
    }

    public static boolean isNormal(Car car) {
        return NORMAL.state.equals(car.getState());
    }

    public static void free(Car car) {
        if (car == null) {
            Asserts.fail(ResultCode.NOTFOUND);
        } else if (USED.state.equals(car.getState())) {
            Asserts.fail(ResultCode.USING);
        }
    }

    public static void unFree(String stateStr) {
        if (USED.state.equals(stateStr)) {
            Asserts.fail(ResultCode.TO_USING_FORBIDDEN);
        }
    }
}
