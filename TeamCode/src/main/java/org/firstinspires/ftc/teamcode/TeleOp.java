package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Basic Teleop 1", group="Opmode")

/*
Miscarea rotilor
    - pt rotire, se inverseaza directiea motorului din partea in care este indreptat rstickul,
    cele doua motoare mergand in sensuri opuse

    - lstick axa y - miscare fata spate
    - rstrick axa x - rotire

Miscarea servourilor pt brate
    -left_bumper si right_bumper pt pornirea / oprirea miscarii servourilor din stanga / dreapta
    -se misca sus jos, in loop

 */
public class TeleOp extends OpMode
{
    private Hardware hardware;
    private boolean left_servo_running = false;
    private boolean right_servo_running = false;

    private boolean direction = true; // directia bratului, false = merge spre 0, ture = merge spre 90

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


        if(gamepad1.left_bumper)
            left_servo_running = !left_servo_running;

        if(gamepad1.right_bumper)
            right_servo_running = !right_servo_running;


        if(left_servo_running)
        {
            direction = hardware.left_servo.getPosition() <= Specifications.max_pos;

            if(direction)
                hardware.left_servo.setPosition(Specifications.max_pos);
            else hardware.left_servo.setPosition(0);
        }

        if(right_servo_running)
        {
            direction = hardware.right_servo.getPosition() <= Specifications.max_pos;

            if(direction)
                hardware.right_servo.setPosition(Specifications.max_pos);
            else hardware.right_servo.setPosition(0);
        }
    }

    //movement for 2 wheel drive on the back, 1 dead wheel in the front
    //if rstick to left, reverse left motor to rotate left
    //if rstick to right, reverse right motor to rotate right
    private void move_wheels(float x_movement, float rotate, double speed)
    {
        //TODO: possible error
        double power1 = x_movement * speed; //power for left wheel
        double power2 = x_movement * speed; //power for right wheel

        //negative is left, positive is right
        //reverse direction of wheel
        if(rotate < 0)
        {
            power1 = -power1 * rotate;
            //hardware.left_motor_wheel.setDirection(DcMotorSimple.Direction.FORWARD);
        }
        if(rotate > 0)
        {
            power2 = -power2 * rotate;
            //hardware.right_motor_wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        }

        hardware.left_motor_wheel.setPower(power1);
        hardware.right_motor_wheel.setPower(power2);
    }
}
