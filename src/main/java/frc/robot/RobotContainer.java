package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.DrivetrainSubsystem;

// Robotun parçalarının (subsystem) ve kumandanın tanımlandığı ve birbirine bağlandığı yer.
public class RobotContainer {

  // Robotun sürüş alt birimi.
  private final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();

  // Xbox kumandası.
  private final CommandXboxController driverController =
      new CommandXboxController(OIConstants.kDriverControllerPort);

  public RobotContainer() {
    configureBindings();
  }

  // Kumanda ile robotun parçalarını bağlayan yer.
  private void configureBindings() {
    // Başka bir komut çalışmıyorsa sürüş komutu sürekli çalışır (default command).
    // Sol çubuk sol tarafı, sağ çubuk sağ tarafı sürer.
    // Çubuğu ileri itince Y değeri negatif geldiği için başına eksi koyuyoruz.
    // applyDeadband: Çok küçük joystick hareketlerini yok sayar.
    drivetrainSubsystem.setDefaultCommand(
        drivetrainSubsystem.tankDriveCommand(
            () -> -MathUtil.applyDeadband(driverController.getLeftY(), OIConstants.kDeadband),
            () -> -MathUtil.applyDeadband(driverController.getRightY(), OIConstants.kDeadband)));
  }

  // Otonom (maç başında joystick olmadan) çalışacak komut. Şimdilik boş.
  public Command getAutonomousCommand() {
    return Commands.none();
  }
}
