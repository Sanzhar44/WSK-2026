package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.ObjectDetector;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.SortBallsCommand;


public class DriveMotor extends CommandBase
{
  private static final ExampleSubsystem o_subsystem = RobotContainer.o_subsystem;
  private static final ObjectDetector o_detector = RobotContainer.o_detector;

  public final double maxSpeed =                     0.4;
  public final double minSpeed =                     0.16;
  public int stateAutomatic =                        0;
  public double angleRobot =                         0;
  public double numberBlackBand =                    0;
  double targetAngle = 0;  
  boolean angleLocked = false;
  boolean start;

  private SortBallsCommand sorter;
  private DriveCommand drive;

  
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
  sorter = new SortBallsCommand(o_subsystem, o_detector);
  drive = new DriveCommand(o_subsystem);
  o_subsystem.resetLiftEncoder();
  o_subsystem.resetEncoder();
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
