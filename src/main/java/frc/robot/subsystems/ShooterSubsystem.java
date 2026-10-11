package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

// Parçayı atan mekanizma. Açısı sabit, 2 adet NEO motor var (SPARK MAX ile sürülür).
public class ShooterSubsystem extends SubsystemBase {

  // Lider motor: komutları biz buna veririz. Takipçi motor onu taklit eder.
  private final SparkMax leaderMotor =
      new SparkMax(ShooterConstants.kLeaderMotorId, MotorType.kBrushless);
  private final SparkMax followerMotor =
      new SparkMax(ShooterConstants.kFollowerMotorId, MotorType.kBrushless);

  // Robot açılırken bir kez çalışır ve motorları hazırlar.
  public ShooterSubsystem() {
    // İki motor için ortak ayarlar.
    SparkMaxConfig commonConfig = new SparkMaxConfig();
    commonConfig
        // Shooter tekerleği dururken serbest dönsün, ani fren yapmasın.
        .idleMode(IdleMode.kCoast)
        // NEO için akım limiti.
        .smartCurrentLimit(ShooterConstants.kCurrentLimitAmps);

    // Lider motorun ayarları.
    SparkMaxConfig leaderConfig = new SparkMaxConfig();
    leaderConfig.apply(commonConfig);

    // Takipçi motor lideri takip eder. İkinci parametre: ters dönsün mü?
    SparkMaxConfig followerConfig = new SparkMaxConfig();
    followerConfig.apply(commonConfig).follow(leaderMotor, ShooterConstants.kFollowerInverted);

    // Ayarları motor kontrolcülerine gönder ve kalıcı olarak kaydet.
    leaderMotor.configure(leaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    followerMotor.configure(followerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  // Shooter'ı verilen hızda döndürür (-1.0 ile 1.0 arası). Takipçi motor kendiliğinden uyar.
  public void setSpeed(double speed) {
    leaderMotor.set(speed);
  }

  // Shooter'ı durdurur.
  public void stop() {
    leaderMotor.stopMotor();
  }

  // Bu komut çalıştığı sürece shooter döner, komut bitince (buton bırakılınca) durur.
  public Command shootCommand() {
    return runEnd(() -> setSpeed(ShooterConstants.kShootSpeed), this::stop);
  }
}
