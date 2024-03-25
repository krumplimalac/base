package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;

import static org.mockito.Mockito.*;

public class TrainSensorTest {
    TrainController mockTC;
    TrainUser mockTU;
    TrainSensorImpl trainSensor;

    @Before
    public void before() {
        mockTC = mock(TrainController.class);
        mockTU = mock(TrainUser.class);
        trainSensor = new TrainSensorImpl(mockTC, mockTU);
    }

    @Test
    public void CheckSpeedLimitChange() {
        int speedLimit = 20;
        trainSensor.overrideSpeedLimit(speedLimit);
        verify(mockTC,times(1)).setSpeedLimit(speedLimit);
    }

    @Test
    public void CheckSpeedLimitOverMax() {
        int speedLimit = 600;
        trainSensor.overrideSpeedLimit(speedLimit);
        verify(mockTU,times(1)).setAlarmState(true);
    }

    @Test
    public void CheckSpeedLimitMinSpeed() {
        int speedLimit = -10;
        trainSensor.overrideSpeedLimit(speedLimit);
        verify(mockTU,times(1)).setAlarmState(true);
    }

    @Test
    public void CheckSpeedLimitMiddle() {
        int speedLimit = 10;
        trainSensor.overrideSpeedLimit(speedLimit);
        verify(mockTU,times(0)).setAlarmState(true);
    }
}
