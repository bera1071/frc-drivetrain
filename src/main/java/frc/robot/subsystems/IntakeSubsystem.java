package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

// Oyun parçasını robotun içine alan mekanizma. 1 adet NEO motor var (SPARK MAX ile sürülür).
public class IntakeSubsystem extends SubsystemBase {

  // NEO fırçasız (brushless) bir motor olduğu için MotorType.kBrushless kullanılır.
  // Yanlış motor tipi yazmak motora zarar verebilir.
  private final SparkMax motor = new SparkMax(IntakeConstants.kMotorId, MotorType.kBrushless);

  // Robot açılırken bir kez çalışır ve motoru hazırlar.
  public IntakeSubsystem() {
    SparkMaxConfig config = new SparkMaxConfig();
    config
        // Butonu bırakınca motor hemen dursun.
        .idleMode(IdleMode.kBrake)
        // NEO için akım limiti.
        .smartCurrentLimit(IntakeConstants.kCurrentLimitAmps);

    // Ayarları motor kontrolcüsüne gönder ve kalıcı olarak kaydet.
    motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  // Motoru verilen hızda döndürür (-1.0 ile 1.0 arası).
  public void setSpeed(double speed) {
    motor.set(speed);
  }

  // Motoru durdurur.
  public void stop() {
    motor.stopMotor();
  }

  // Bu komut çalıştığı sürece intake döner, komut bitince (buton bırakılınca) motor durur.
  public Command intakeCommand() {
    return runEnd(() -> setSpeed(IntakeConstants.kIntakeSpeed), this::stop);
  }
}
