package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

// Robotun parçalarının (subsystem) ve kumandanın tanımlandığı ve birbirine bağlandığı yer.
public class RobotContainer {

  // Robotun parçaları.
  private final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();

  // PlayStation tarzı kumanda (kare, daire gibi tuşlar bunda var).
  private final CommandPS4Controller driverController =
      new CommandPS4Controller(OIConstants.kDriverControllerPort);

  public RobotContainer() {
    configureBindings();
  }

  // Kumanda tuşlarını robotun parçalarına bağlayan yer.
  private void configureBindings() {
    // Sürüş: Başka bir komut çalışmıyorsa sürekli çalışır (default command).
    // Sol çubuk sol tarafı, sağ çubuk sağ tarafı sürer.
    // Çubuğu ileri itince Y değeri negatif geldiği için başına eksi koyuyoruz.
    // applyDeadband: Çok küçük joystick hareketlerini yok sayar.
    drivetrainSubsystem.setDefaultCommand(
        drivetrainSubsystem.tankDriveCommand(
            () -> -MathUtil.applyDeadband(driverController.getLeftY(), OIConstants.kDeadband),
            () -> -MathUtil.applyDeadband(driverController.getRightY(), OIConstants.kDeadband)));

    // KARE tuşuna basılı tutulduğu sürece intake motoru (motor 5) çalışır, bırakınca durur.
    driverController.square().whileTrue(intakeSubsystem.intakeCommand());

    // DAİRE tuşuna basılı tutulduğu sürece shooter çalışır, bırakınca durur.
    driverController.circle().whileTrue(shooterSubsystem.shootCommand());
  }

  // Otonom (maç başında joystick olmadan) çalışacak komut. Şimdilik boş.
  public Command getAutonomousCommand() {
    return Commands.none();
  }
}
