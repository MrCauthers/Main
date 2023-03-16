package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.controller.PIDController;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ArmPivotSubsystem;

public class ArmPivotJoyPIDCmd extends CommandBase {
    private final ArmPivotSubsystem armPivotSubsystem;
    private final PIDController pidController;
    private final Supplier<Double> heightFunction;
    private double setpoint = 0;
   
    public ArmPivotJoyPIDCmd(ArmPivotSubsystem armPivotSubsystem, Supplier<Double> heightFunction) {
        this.armPivotSubsystem = armPivotSubsystem;
        this.pidController = new PIDController(Constants.ArmPivotConstants.kP,Constants.ArmPivotConstants.kI,Constants.ArmPivotConstants.kD);//NEED TO CHANGE PID VALUES!!!!!
        this.heightFunction = heightFunction;
        addRequirements(armPivotSubsystem);
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pidController.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
 
    setpoint = (3 + (-1*(heightFunction.get()) * 4));

    pidController.setSetpoint(setpoint);

    double speed = (pidController.calculate(armPivotSubsystem.getEncoderMeters()));
    armPivotSubsystem.setMotor(speed);
    System.out.println("ArmPivotJoyPIDCmd started");
    System.out.println("speed = " + speed);
    System.out.println("encoder = " + armPivotSubsystem.getEncoderMeters());
    System.out.println("setpoint = " + setpoint);
    System.out.println("heightFunction = "+ heightFunction.get());

}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    armPivotSubsystem.setMotor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

}
