package frc.robot.commands.Manual;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.DroidRageConstants;
import frc.robot.subsystems.Elevator;

public class ManualElevator extends CommandBase {
    public static class Constants {    }

    private final Elevator elevator;
    // private final CommandXboxController operator;
    private final Supplier<Double> xElevator;
    private final Supplier<Double> yElevator;
    
    public ManualElevator(Supplier<Double> xElevator, Supplier<Double> yElevator, Elevator elevator) {
        this.elevator = elevator;
        this.xElevator = xElevator;
        this.yElevator = yElevator;

        addRequirements(elevator);
    }

    @Override
    public void initialize() { }

    @Override
    public void execute() {
        double x = xElevator.get();
        double y = -yElevator.get();
        if (Math.abs(y) < DroidRageConstants.Gamepad.OPERATOR_STICK_DEADZONE) y = 0;
        if (Math.abs(y) < DroidRageConstants.Gamepad.OPERATOR_STICK_DEADZONE) x = 0;
        elevator.setTargetPositionsManually(x, y);
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}

