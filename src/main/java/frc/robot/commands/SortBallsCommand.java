package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.ObjectDetector;

public class SortBallsCommand extends CommandBase {
    private final ExampleSubsystem m_subsystem;
    private final ObjectDetector m_detector;

    // Конструктор должен принимать эти два параметра
    public SortBallsCommand(ExampleSubsystem subsystem, ObjectDetector detector) {
        m_subsystem = subsystem;
        m_detector = detector;
        addRequirements(m_subsystem);
    }
}
