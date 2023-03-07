package frc.robot.commands.ElevatorCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pivot;

public class IntakeCube extends SequentialCommandGroup {
    //TODO: Maybe make a position where the pivot iks downwards so it doesn't roll away
    public IntakeCube(Pivot pivot, Intake intake, double wait) {
        addCommands(
            intake.runOpen(),
            intake.runIntakeFor(wait),
            pivot.moveHold()
        );
    }
}
