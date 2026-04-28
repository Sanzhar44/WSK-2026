package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.DriveMotor;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.ObjectDetector;

public class RobotContainer {
    public static ExampleSubsystem o_subsystem;
    public static ObjectDetector o_detector;
    private final DriveMotor m_autoCommand;

    public RobotContainer() {

        o_subsystem = new ExampleSubsystem();
        o_detector = new ObjectDetector("10.0.2.2", 5800);
        m_autoCommand = new DriveMotor(o_subsystem, o_detector);
      
    }

    public Command getAutonomousCommand() {
        return m_autoCommand;
    }
}
