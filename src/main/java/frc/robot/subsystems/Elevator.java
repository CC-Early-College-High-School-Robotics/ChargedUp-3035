package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DroidRageConstants;

public class Elevator extends SubsystemBase {
    public static class Constants {
        public static final double GEAR_RATIO = 1 / 1;
        public static final double GEAR_DIAMETER_METERS = 0.0481; // 1.893 inches
        public static final double ROT_TO_METER = (GEAR_RATIO * Math.PI * GEAR_DIAMETER_METERS);
    }

    private enum Position {
        START(0,0),//TODO:Input Values

        INTAKELOW(0,0),
       
        LOWCONE(0, 0),
        LOWCUBE(0,0),
        
        MIDCONE(0,0),
        MIDCUBE(0,0),
        
        HIGHCUBE(0,0),
        HIGHCONE(0,0),

        INTAKEHIGH(0,0)
        
        ;

        private final double verticalMeters;
        private final double horizontalMeters;

        private Position(double verticalMeters, double horizontalMeters) {
            this.verticalMeters = verticalMeters;
            this.horizontalMeters = horizontalMeters;
        }
    }
    
    private final CANSparkMax leftElevator, rightElevator, horizMotor;
    private final RelativeEncoder encoder;
    private final PIDController vertController;
    private final PIDController horizController;
    private volatile Position position;
    
    public Elevator() {
        leftElevator = new CANSparkMax(0, MotorType.kBrushless);
        rightElevator = new CANSparkMax(0, MotorType.kBrushless);//TODO: Where is it plugged in?
        horizMotor = new CANSparkMax(0, MotorType.kBrushless);

        leftElevator.setIdleMode(IdleMode.kBrake);
        rightElevator.setIdleMode(IdleMode.kBrake);
        horizMotor.setIdleMode(IdleMode.kBrake);

        rightElevator.follow(leftElevator, true);
  
        encoder = leftElevator.getEncoder();  //TODO: Where is it plugged in?
        encoder.setPositionConversionFactor(Constants.ROT_TO_METER);

        vertController = new PIDController(0, 0, 0);
        vertController.setTolerance(0.10); // meters
        horizController = new PIDController(0, 0, 0);
        horizController.setTolerance(0.10); // meters

        setPosition(Position.START);
    }

    @Override
    public void periodic() {
        DroidRageConstants.putNumber("Vertical Elevator/Height", encoder.getPosition());

        vertController.setPID(
                DroidRageConstants.getNumber("Vertical Eleveator/P", vertController.getP()),
                DroidRageConstants.getNumber("Vertical Eleveator/I", vertController.getI()),
                DroidRageConstants.getNumber("Vertical Eleveator/D", vertController.getD())
            );
            vertController.setTolerance(
                DroidRageConstants.getNumber("Vertical Eleveator/Tolerance", vertController.getPositionTolerance())
            );

            horizController.setPID(
                DroidRageConstants.getNumber("Horizontal Eleveator/P", horizController.getP()),
                DroidRageConstants.getNumber("Horizontal Eleveator/I", horizController.getI()),
                DroidRageConstants.getNumber("Horizontal Eleveator/D", horizController.getD())
            );
            horizController.setTolerance(
                DroidRageConstants.getNumber("Horizontal Eleveator/Tolerance", horizController.getPositionTolerance())
            );
    }

    @Override
    public void simulationPeriodic() {
        periodic();
    }

    public double getTargetVerticalHeight() {
        return DroidRageConstants.getNumber("Vertical Elevator/Position/"+ position.name(), position.verticalMeters);
    }

    public double getTargetHorizontalDistance() {
        return DroidRageConstants.getNumber("Horizontal Elevator/Position/"+ position.name(), position.horizontalMeters);
    }

    private CommandBase setPosition(Position position) {
        return runOnce(() -> {
            this.position = position;
            vertController.setSetpoint(getTargetVerticalHeight());
            DroidRageConstants.putString("Vertical Elevator/Position", position.name());

            horizController.setSetpoint(getTargetHorizontalDistance());
        });
    }

    public CommandBase moveIntakeLow() {
        return setPosition(Position.INTAKELOW);
    }
    public CommandBase moveIntakeHigh() {
        return setPosition(Position.INTAKEHIGH);
    }

    public CommandBase moveLow() {
        switch (TrackedElement.get()) {
            case CONE:
                return setPosition(Position.LOWCONE);
            case CUBE:
            case NONE:
            default:
                return setPosition(Position.LOWCUBE);
        }
    }

    public CommandBase moveMid() {
        switch (TrackedElement.get()) {
            case CONE:
                return setPosition(Position.MIDCONE);
            case CUBE:
            case NONE:
            default:
                return setPosition(Position.MIDCUBE);
        }
    }

    public CommandBase moveHigh() {
        switch (TrackedElement.get()) {
            case CONE:
                return setPosition(Position.HIGHCONE);
            case CUBE:
            case NONE:
            default:
                return setPosition(Position.HIGHCUBE);
        }
    }


    // public CommandBase moveToPosition() {
    //     return runOnce(() -> leftElevator.set(vertController.calculate(encoder.getPosition())));
    // }
}  