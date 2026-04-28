package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ExampleSubsystem;

public class DriveCommand extends CommandBase {
    private final ExampleSubsystem m_subsystem;

    // Конструктор должен принимать ОДИН параметр типа ExampleSubsystem
    public DriveCommand(ExampleSubsystem subsystem) {
        m_subsystem = subsystem;
        addRequirements(m_subsystem);
    }
}
