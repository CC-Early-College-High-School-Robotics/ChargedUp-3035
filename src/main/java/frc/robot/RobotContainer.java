// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.Drive;
import frc.robot.commands.Drive.SwerveDriveTeleop;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    // private final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
    private final Drive drive = new Drive();;
    // private final HorizontalExtension hExtension;
    // private final VerticalExtension vExtension;
    
    // Replace with CommandPS4Controller or CommandJoystick if needed
    private final CommandXboxController driver =
        new CommandXboxController(DroidRageConstants.Gamepad.DRIVER_CONTROLLER_PORT);
    private final CommandXboxController operator =
        new CommandXboxController(DroidRageConstants.Gamepad.OPERATOR_CONTROLLER_PORT);
    
    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
        // new Trigger(exampleSubsystem::exampleCondition)
            // .onTrue(new ExampleCommand(exampleSubsystem));
    
        
        // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
        // // cancelling on release.
        // driver.b().onTrue(drive.recalibrateHeading());
        // driver.a().onTrue(drive.toggleFieldOriented());
        // driver.rightBumper().onTrue(new SlowSwerveDriveTeleop(drive, driver, drive::isFieldOriented, 0.5));
        // drive.setDefaultCommand(new SlowSwerveDriveTeleop(drive, driver, drive::isFieldOriented, 1));
    
        driver.a().onTrue(drive.resetHeading());
        driver.back().onTrue(drive.toggleFieldOriented());
    
        SmartDashboard.putNumber("driver value", driver.getRightX());
        drive.setDefaultCommand(new SwerveDriveTeleop(
            drive, 
            driver::getLeftX, 
            driver::getLeftY, 
            driver::getRightX,
            drive::isFieldOriented)
        );
    
        // operator.rightTrigger().whileTrue(new IntakeGround(hExtension, vExtension));
        // operator.leftTrigger().whileTrue(new IntakeHigh(hExtension, vExtension));
        // operator.a().whileTrue(new Ground(hExtension, vExtension));
        // operator.x().whileTrue(new Mid(hExtension, vExtension));
        // operator.y().whileTrue(new High(hExtension, vExtension));
    }
    
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        // return Autos.exampleAuto(exampleSubsystem);
        
        // TrajectoryConfig trajectoryConfig = new TrajectoryConfig(
        //   Drive.Constants.Auto.MAX_SPEED_METERS_PER_SECOND, 
        //   Drive.Constants.Auto.MAX_ACCELERATION_METERS_PER_SECOND)
        //     .setKinematics(Drive.Constants.DRIVE_KINEMATICS);
        
        // Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
        //   new Pose2d(),
        //   List.of(
        //     new Translation2d(1,0),
        //     new Translation2d(1, -1)
        //   ), 
        //   new Pose2d(2, -1, Rotation2d.fromDegrees(180)),
        //   trajectoryConfig
        //   );
        
        // PIDController xController = new PIDController(Drive.Constants.Auto.TRANSLATIONAL_KP, 0, 0);
        // PIDController yController = new PIDController(Drive.Constants.Auto.TRANSLATIONAL_KP, 0, 0);
        // ProfiledPIDController thetaController = new ProfiledPIDController(
        //   Drive.Constants.Auto.THETA_KP, 
        //   0, 
        //   0, 
        //   Drive.Constants.Auto.THETA_CONSTRAINTS);
        // thetaController.enableContinuousInput(-Math.PI, Math.PI);
        
        // SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(
        //   trajectory,
        //   drive::getPose,
        //   Drive.Constants.DRIVE_KINEMATICS,
        //   xController,
        //   yController,
        //   thetaController,
        //   drive::setModuleStates,
        //   drive
        // );
        
        
        // return new SequentialCommandGroup(
        //   new InstantCommand(() -> drive.resetOdometry(trajectory.getInitialPose())),
        //   swerveControllerCommand,
        //   new InstantCommand(() -> {
        //       drive.stop();
        //       xController.close();
        //       yController.close();
        //     }
        //   )
        // );
        return new InstantCommand();
    }
}
