package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DroidRageConstants;

public class VerticalExtension extends SubsystemBase {
    private enum Position {
        GROUND(DroidRageConstants.getNumber("VE_GROUND", 0)),
        MID(DroidRageConstants.getNumber("VE_MID", 0)),
        HIGH(DroidRageConstants.getNumber("VE_HIGH", 0)),
        INTAKE(DroidRageConstants.getNumber("VE_INTAKE", 0)),
        ;

        private volatile double height_meters;

        private Position(double height_meters) {
            this.height_meters = height_meters;
        }
    }

    private final CANSparkMax elevMotor, elevMotorTwo;
    private final DutyCycleEncoder elevEncoder;
    private final PIDController elevController;

    
    public VerticalExtension() {
        elevMotor = new CANSparkMax(0, MotorType.kBrushless);
  
        elevEncoder = new DutyCycleEncoder(0);  //TODO: Where is it plugged in?
        elevController = new PIDController(0, 0, 0);
        elevController.setTolerance(5);
        elevMotor.setIdleMode(IdleMode.kBrake);

        elevMotorTwo = new CANSparkMax(0, MotorType.kBrushless);//TODO: Where is it plugged in?
        elevMotorTwo.setIdleMode(IdleMode.kBrake);
        elevMotorTwo.follow(elevMotor, true);
    
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Vertical Encoder Pos", elevEncoder.get());
    }
  
    @Override
    public void simulationPeriodic() {
        periodic();
    }

    public void setPosition(Position position, double height_meters) {
        position.height_meters = height_meters;
    }

    private CommandBase move(Position level) {
        return runOnce(() -> elevController.setSetpoint(level.height_meters));
    }

    public CommandBase moveGround() {
        return move(Position.GROUND);
    }

    public CommandBase moveMid() {
        return move(Position.MID);
    }

    public CommandBase moveHigh() {
        return move(Position.HIGH);
    }

    public CommandBase moveIntake() {
        return move(Position.INTAKE);
    }
}  
