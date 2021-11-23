package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Basic Teleop 1", group="Opmode")

public class TeleOp extends OpMode
{
    private Hardware hardware;
    private boolean left_servo_running = false;
    private boolean right_servo_running = false;

    public void init()
    {
        hardware = new Hardware(hardwareMap);
        hardware.left_servo.setPosition(0);
        hardware.right_servo.setPosition(0);
    }

    public void start()
    {

    }

    public void loop()
    {
        float x_movement = gamepad1.left_stick_x; //negative is up, positive is down
        float rotate = gamepad1.right_stick_y; //negative is left, positive is right

        move_wheels(x_movement, rotate, Specifications.wheel_speed);

        //start/stop movement of left servo
        if(gamepad1.left_bumper)
            left_servo_running = left_servo_running ? false : true;
        if(left_servo_running)
            move_servo(hardware.left_servo);

        //start/stop movement of right servo
        if(gamepad1.right_bumper)
            right_servo_running = right_servo_running ? false : true;
        if(right_servo_running)
            move_servo(hardware.right_servo);
    }

    //movement for 2 wheel drive on the back, 1 dead wheel in the front
    //if rstick to left, reverse left motor to rotate left
    //if rstick to right, reverse right motor to rotate right
    private void move_wheels(float x_movement, float rotate, double speed)
    {

        //TODO: possible error
        double power1 = (x_movement + rotate) / 2 * speed; //power for left wheel
        double power2 = (x_movement + rotate) / 2 * speed; //power for right wheel

        hardware.left_motor_wheel.setDirection(DcMotorSimple.Direction.FORWARD);
        hardware.right_motor_wheel.setDirection(DcMotorSimple.Direction.FORWARD);

        //negative is left, positive is right
        if(rotate < 0)
            hardware.left_motor_wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        if(rotate > 0)
            hardware.right_motor_wheel.setDirection(DcMotorSimple.Direction.REVERSE);

        hardware.left_motor_wheel.setPower(power1);
        hardware.right_motor_wheel.setPower(power2);
    }

    //moves a servo up and down
    //TODO: possilble implementation error
    private void move_servo(Servo servo)
    {
        double current_pos = servo.getPosition();
        if(current_pos >= 1) servo.setDirection(Servo.Direction.REVERSE);
        if(current_pos <= 0) servo.setDirection(Servo.Direction.FORWARD);
        servo.setPosition(current_pos + 0.1);
    }
}
