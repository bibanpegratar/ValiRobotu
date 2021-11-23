package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hardware
{
    private HardwareMap hardwareMap;

    //motoare roti
    public DcMotor left_motor_wheel = null;
    public DcMotor right_motor_wheel = null;

    //servo brate
    public Servo left_servo = null;
    public Servo right_servo = null;

    //constructor
    public Hardware(HardwareMap hardwareMap)
    {
        this.hardwareMap = hardwareMap;
        initialize();
    }

    private void initialize()
    {
        left_servo = hardwareMap.get(Servo.class, "servoL");
        right_servo = hardwareMap.get(Servo.class, "servoR");
        left_servo.setPosition(0.0);
        right_servo.setPosition(0.0);

        setDefaultStateMotor(left_motor_wheel, "m0", DcMotorSimple.Direction.FORWARD); // roata stanga
        setDefaultStateMotor(right_motor_wheel, "m1", DcMotorSimple.Direction.FORWARD); // roata dreapta
    }

    private void setDefaultStateMotor(DcMotor motor, String nume, DcMotorSimple.Direction direction)
    {
        motor = hardwareMap.get(DcMotor.class, nume);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setPower(0);
        motor.setDirection(direction);
    }
}
