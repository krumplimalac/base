package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.time.LocalTime;

public class TrainSensorImpl implements TrainSensor {

	private TrainController controller;
	private TrainUser user;
	private int speedLimit = 5;
	private Table<String,Integer,Integer> tachograph = HashBasedTable.create();

	public TrainSensorImpl(TrainController controller, TrainUser user) {
		this.controller = controller;
		this.user = user;
	}

	@Override
	public void addToTachograph() {
		tachograph.put(LocalTime.now().toString(),controller.getJoystickPosition(),controller.getReferenceSpeed());
	}

	@Override
	public Table<String,Integer,Integer> getTachograpch(){
		return tachograph;
	}

	@Override
	public int getSpeedLimit() {
		return speedLimit;
	}

	@Override
	public void overrideSpeedLimit(int speedLimit) {
		if(speedLimit < 0 || speedLimit > 500 || speedLimit < controller.getReferenceSpeed()*0.5){
			user.setAlarmState(true);
		}
		this.speedLimit = speedLimit;
		controller.setSpeedLimit(speedLimit);
	}

}
