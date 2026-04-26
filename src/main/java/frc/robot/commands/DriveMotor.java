package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.ObjectDetector;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveMotor extends CommandBase
{
  private static final ExampleSubsystem o_subsystem = RobotContainer.o_subsystem;
  
  public final double maxSpeed =                     0.4;
  public final double minSpeed =                     0.16;
  public int stateAutomatic =                        0;
  public double angleRobot =                         0;
  public double numberBlackBand =                    0;
  double targetAngle = 0;  
  boolean angleLocked = false;
  boolean start;

  

  
public void rotateTheRobot(double targetAngle)
  {
    double currentAngle = o_subsystem.getYaw();
    double speed = 0.3;
    angleRobot = targetAngle;
    if(currentAngle < 0 && angleRobot != 0) currentAngle = currentAngle + 360;

    if(Math.abs(targetAngle - currentAngle) > 0.15)
    {
      if(Math.abs(targetAngle - currentAngle) < 10) speed=0.15;
      currentAngle = o_subsystem.getYaw();

      if(currentAngle < 0 && angleRobot != 0) currentAngle = currentAngle + 360;

      if(currentAngle < targetAngle)
      {
        o_subsystem.setMotorLeft(speed /2);
        o_subsystem.setMotorRight(speed / 2);
        o_subsystem.setMotorBack(speed);
      }
      else
      {
        o_subsystem.setMotorLeft(-speed);
        o_subsystem.setMotorRight(-speed);
        o_subsystem.setMotorBack(-speed);
      }
    }
    else
    {
      o_subsystem.setMotorLeft(0);
      o_subsystem.setMotorRight(0);
      o_subsystem.setMotorBack(0);
      o_subsystem.TimerStop();
      stateAutomatic++;
    }
  }

public void goForwardDistance(double dist)
    {
      double tickLeftF = o_subsystem.getEncoderLeft();
      double tickRightF = o_subsystem.getEncoderRight();

      double MID = (tickLeftF + (tickRightF * -1)) / 2;

      double lSpeed = maxSpeed;
      double rSpeed = maxSpeed;

      double distance_to_tick = dist * 9.5;

      if(MID >= distance_to_tick)
      {
        o_subsystem.TimerStop();
        stateAutomatic++;
      }

      else
    {
        if(MID >= (dist-10) * 9.5) {
          lSpeed = minSpeed;
          rSpeed = minSpeed;
        } else {
          lSpeed = maxSpeed;
          rSpeed = maxSpeed;
        }

        double currentAngle = o_subsystem.getYaw();

        if(currentAngle < 0 && angleRobot != 0) currentAngle = currentAngle + 360;

        if(currentAngle < angleRobot) {
          double angleDifference = (angleRobot) - (currentAngle);
          rSpeed= rSpeed-(angleDifference/90);
        }

        else if(currentAngle > angleRobot) {
          double angleDifference = (angleRobot) - (currentAngle);
          lSpeed= lSpeed+(angleDifference/90);
        }

        else 
        {
          lSpeed = maxSpeed;
          rSpeed = maxSpeed;
        }

        o_subsystem.setMotorLeft(lSpeed);
        o_subsystem.setMotorRight(-rSpeed);
      }
    }

public void goBackDistance(double dist){
    double tickLeftF = o_subsystem.getEncoderLeft();
    double tickRightF = o_subsystem.getEncoderRight();

    double MID = (tickRightF + (tickLeftF * -1)) / 2;

    double lSpeed = maxSpeed;
    double rSpeed = maxSpeed;

    double distance_to_tick = dist * 9.5;

    if(MID >= distance_to_tick)
    {
      o_subsystem.TimerStop();
      stateAutomatic++;
    }

    else
    {
      if(MID >= (dist-10) * 9.5) {
        lSpeed = minSpeed;
        rSpeed = minSpeed;
      } else {
        lSpeed = maxSpeed;
        rSpeed = maxSpeed;
      }

      double currentAngle = o_subsystem.getYaw();

      if(currentAngle < 0 && angleRobot != 0) currentAngle = currentAngle + 360;

      if(currentAngle < angleRobot) {
        double angleDifference = (angleRobot) - (currentAngle);
        lSpeed= lSpeed-(angleDifference/90);
      }

      else if(currentAngle > angleRobot) {
        double angleDifference = (angleRobot) - (currentAngle);
        rSpeed= rSpeed+(angleDifference/90);
      }

      else {
        lSpeed = maxSpeed;
        rSpeed = maxSpeed;
      }

      o_subsystem.setMotorLeft(-lSpeed);
      o_subsystem.setMotorRight(rSpeed);
    }
  }

public void goForwardSharp(double dist){
    double lSpeed = 0.3;
    double rSpeed = 0.3;
    double currentAngle = o_subsystem.getYaw();

    if(currentAngle < 0 && angleRobot != 0) currentAngle = currentAngle + 360;

    if(currentAngle < angleRobot) {
      double angleDifference = (angleRobot) - (currentAngle);
      rSpeed= lSpeed-(angleDifference/90);
    }

    else if(currentAngle > angleRobot) {
      double angleDifference = (angleRobot) - (currentAngle);
      lSpeed= rSpeed+(angleDifference/90);
    }

    double slowdown_factor = Math.max(0, Math.min(1.0, (o_subsystem.getForwardSharp() - dist + 10) / dist));


    o_subsystem.setMotorLeft(lSpeed * slowdown_factor);
    o_subsystem.setMotorRight(-rSpeed * slowdown_factor);

    if(o_subsystem.getForwardSharp() < dist)
    {
      o_subsystem.TimerStop();
      stateAutomatic++;
    }
     
  }

public void goBackSharp(double dist){
    double lSpeed = 0.3;
    double rSpeed = 0.3;
    double currentAngle = o_subsystem.getYaw();

    if(currentAngle < 0 && angleRobot != 0) currentAngle = currentAngle + 360;

    if(currentAngle < angleRobot) {
      double angleDifference = (angleRobot) - (currentAngle);
      lSpeed= lSpeed-(angleDifference/90);
    }

    else if(currentAngle > angleRobot) {
      double angleDifference = (angleRobot) - (currentAngle);
      rSpeed= rSpeed+(angleDifference/90);
    }

    double slowdown_factor = Math.max(0, Math.min(1.0, (o_subsystem.getBackSharp() - dist + 10) / dist));


    o_subsystem.setMotorLeft(-lSpeed * slowdown_factor);
    o_subsystem.setMotorRight(rSpeed * slowdown_factor);

    if(o_subsystem.getBackSharp() < dist)
    {
       o_subsystem.TimerStop();
      stateAutomatic++;
    } 
  }

public void goLeftSonic(double dist)
  {
    double sonic = o_subsystem.getDistanceSonicLeft();

    double lSpeed = maxSpeed / 2;
    double rSpeed = maxSpeed / 2;
    double bSpeed = maxSpeed;

    double currentAngle = o_subsystem.getYaw();
    if (currentAngle < 0) currentAngle += 360;
    
    double angleDifference = angleRobot - currentAngle;
    if (angleDifference > 180) angleDifference -= 360;
    if (angleDifference < -180) angleDifference += 360;

    bSpeed += (angleDifference / 90);

    double slowdown_factor = Math.max(0, Math.min(1.0, (sonic - dist + 10) / dist));

    o_subsystem.setMotorLeft(-lSpeed * slowdown_factor);
    o_subsystem.setMotorRight(-rSpeed * slowdown_factor);
    o_subsystem.setMotorBack(bSpeed * slowdown_factor);

    if(sonic < dist)
    {
      o_subsystem.TimerStop();
      stateAutomatic++;
    } 
  }

public void goRightSonic(double dist)
  {
    double sonic = o_subsystem.getDistanceSonicRight();

    double lSpeed = maxSpeed / 2;
    double rSpeed = maxSpeed / 2;
    double bSpeed = maxSpeed;

    double currentAngle = o_subsystem.getYaw();
    if (currentAngle < 0) currentAngle += 360;
    
    double angleDifference = angleRobot - currentAngle;
    if (angleDifference > 180) angleDifference -= 360;
    if (angleDifference < -180) angleDifference += 360;

    bSpeed -= (angleDifference / 90);

    double slowdown_factor = Math.max(0, Math.min(1.0, (sonic - dist + 10) / dist));
    double minSpeed = 0.2;
    slowdown_factor = Math.max(minSpeed, slowdown_factor);

    o_subsystem.setMotorLeft(lSpeed * slowdown_factor);
    o_subsystem.setMotorRight(rSpeed * slowdown_factor);
    o_subsystem.setMotorBack(-bSpeed * slowdown_factor);

    if(sonic < dist)
    {
      o_subsystem.TimerStop();
      stateAutomatic++;
    } 
  }

//паралельная езда
public void goForwardWithBackSharp(double dist)
  {
    double lSpeed = maxSpeed;
    double rSpeed = maxSpeed;
    double currentAngle = o_subsystem.getYaw();


    if(currentAngle < 0 && angleRobot != 0) currentAngle = currentAngle + 360;

    if(currentAngle < angleRobot) {
      double angleDifference = (angleRobot) - (currentAngle);
      rSpeed= lSpeed-(angleDifference/90);
  }

    else if(currentAngle > angleRobot) {
      double angleDifference = (angleRobot) - (currentAngle);
      lSpeed= rSpeed+(angleDifference/90);
    }

    if(o_subsystem.getBackSharp() >= dist - 15)
    {
      o_subsystem.setMotorLeft(0.15);
      o_subsystem.setMotorRight(-0.15);
    }

    o_subsystem.setMotorLeft(lSpeed);
    o_subsystem.setMotorRight(-rSpeed);

    if(o_subsystem.getBackSharp() >= dist)
    {
      o_subsystem.TimerStop();
      stateAutomatic++;
    }
   
  }

public void goLeftWithRightSonic(double dist)
  {
    double sonic = o_subsystem.getDistanceSonicRight();

    double lSpeed = maxSpeed / 2;
    double rSpeed = maxSpeed / 2;
    double bSpeed = maxSpeed;

    double currentAngle = o_subsystem.getYaw();
    if (currentAngle < 0) currentAngle += 360;
    
    double angleDifference = angleRobot - currentAngle;
    if (angleDifference > 180) angleDifference -= 360;
    if (angleDifference < -180) angleDifference += 360;

    bSpeed += (angleDifference / 90);

    if(sonic > dist - 10)
    {
      lSpeed = maxSpeed / 2;
      rSpeed = maxSpeed / 2;
      bSpeed = maxSpeed;
    } 

    o_subsystem.setMotorLeft(-lSpeed);
    o_subsystem.setMotorRight(-rSpeed);
    o_subsystem.setMotorBack(bSpeed);


    if(sonic >= dist)
    {
      o_subsystem.TimerStop();
      stateAutomatic++;
    } 
  }

public void goRigtWithLeftSonic(double dist)
  {
    double sonic = o_subsystem.getDistanceSonicLeft();

    double lSpeed = maxSpeed / 2;
    double rSpeed = maxSpeed / 2;
    double bSpeed = maxSpeed;

    double currentAngle = o_subsystem.getYaw();
    if (currentAngle < 0) currentAngle += 360;
    
    double angleDifference = angleRobot - currentAngle;
    if (angleDifference > 180) angleDifference -= 360;
    if (angleDifference < -180) angleDifference += 360;

    bSpeed -= (angleDifference / 90);

    if(sonic > dist - 10)
    {
      lSpeed = maxSpeed * 0.25;
      rSpeed = maxSpeed * 0.25;
      bSpeed = maxSpeed * 0.5;
    } 

    o_subsystem.setMotorLeft(lSpeed);
    o_subsystem.setMotorRight(rSpeed);
    o_subsystem.setMotorBack(-bSpeed);

    if(sonic > dist)
    {
      o_subsystem.TimerStop();
      stateAutomatic++;
    } 
  }

  public void alignToVision(double offsetX) {
    double speed = 0.2; 
    double tolerance = 10; 

    double leftSpeed = 0;
    double rightSpeed = 0;
    double backSpeed = 0;

    if (Math.abs(offsetX) > tolerance) {
        if (offsetX > 0) {
            leftSpeed = speed / 2;   
            rightSpeed = speed / 2;
            backSpeed = -speed;          
        } else {
            leftSpeed = -speed / 2;
            rightSpeed = -speed / 2;
            backSpeed = speed;
        }
    } else {
        o_subsystem.TimerStop();
        stateAutomatic++;
        return;
    }

    o_subsystem.setMotorLeft(leftSpeed);
    o_subsystem.setMotorRight(rightSpeed);
    o_subsystem.setMotorBack(backSpeed);
}

public void moveLift(int state){
  if(state == 1){
    o_subsystem.setMotorGear(0.35);
    if(o_subsystem.getButtonState("up")){
      o_subsystem.setMotorGear(0);
      stateAutomatic++;
    }
  }

  if(state == 2){
    o_subsystem.setMotorGear(0.35);
    if(o_subsystem.getButtonState("up")){
      o_subsystem.setMotorGear(-0.2);
      Timer.delay(4);
      o_subsystem.setMotorGear(0);
      stateAutomatic++;
    }
  }

  if(state == 3){
    o_subsystem.setMotorGear(-0.2);
    if(o_subsystem.getButtonState("down")){
      o_subsystem.setMotorGear(0);
      stateAutomatic++;
    }
  }}


  public DriveMotor(ExampleSubsystem subsystem, ObjectDetector detector){
  addRequirements(o_subsystem);
  sorter = new SortBallsCommand(subsystem, detector);
  drive = new DriveCommand(subsystem);
  m_subsystem = subsystem;
  m_detector = detector;
  m_subsystem.resetLiftEnc();
  m_subsystem.resetEnc();
}



    @Override
  public void initialize() {
    stateAutomatic = 0;
    o_subsystem.resetYaw();
    o_subsystem.resetEncoder();
    o_subsystem.setButtonLed("Running",false);
    o_subsystem.setButtonLed("Stopped",true);
    o_subsystem.setButtonLed("Start",true);
    o_subsystem.setButtonLed("Stop",true);
    o_subsystem.setButtonLed("Reset",true);
    Timer.delay(0.5);   
  }

  

  @Override
  public void execute()
  {
    if(o_subsystem.getButtonState("Start")){

      start = true;
      stateAutomatic = 0; 
      
      o_subsystem.setServoLift(0);
      o_subsystem.resetEncoder();
      o_subsystem.resetYaw();

      double yaw = o_subsystem.getYaw();
      if (yaw < 0) yaw += 360;
      angleRobot = yaw;
      
      o_subsystem.setButtonLed("Running",true);
      o_subsystem.setButtonLed("Stopped",false); 
    }

    if(o_subsystem.getButtonState("Stop")){
      start = false;
      stateAutomatic = -1;
      o_subsystem.setButtonLed("Running",false);
      o_subsystem.setButtonLed("Stopped",true);
      o_subsystem.resetEncoder();
      o_subsystem.resetYaw();
      o_subsystem.TimerStop();

    }

    if(start) 
    {
      if(o_subsystem.getButtonState("Stop")){
        start = false;
        stateAutomatic = -1;
        o_subsystem.setButtonLed("Running",false);
        o_subsystem.setButtonLed("Stopped",true);
        o_subsystem.resetEncoder();
        o_subsystem.resetYaw();
        o_subsystem.TimerStop();

      }

      
    switch(stateAutomatic)
    {
      case 0:
      break;

      default:
      o_subsystem.setMotorLeft(0);
      o_subsystem.setMotorRight(0);
      o_subsystem.setMotorBack(0);
      o_subsystem.TimerStop();  

      o_subsystem.setButtonLed("Running",false);
      o_subsystem.setButtonLed("Stopped",true);
      
      
    }
  }
}

  @Override
  public void end(final boolean interrupted) {  
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
