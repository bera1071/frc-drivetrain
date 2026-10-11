
package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;
import java.util.function.DoubleSupplier;

// Robotun sürüş kısmı (tank drive). Solda 2, sağda 2 CIM motor var.
public class DrivetrainSubsystem extends SubsystemBase {

  // Motorlar private: Diğer dosyalar motorları doğrudan değiştiremez.
  // Sadece aşağıda tanımladığımız metotlar (tankDrive, stop gibi) kullanılabilir.
  // CIM motorlar fırçalı (brushed) olduğu için MotorType.kBrushed kullanılır.
  // Yanlış motor tipi yazmak motora zarar verebilir.
  private final SparkMax leftFront =
      new SparkMax(DrivetrainConstants.kLeftFrontId, MotorType.kBrushed);
  private final SparkMax leftRear =
      new SparkMax(DrivetrainConstants.kLeftRearId, MotorType.kBrushed);
  private final SparkMax rightFront =
      new SparkMax(DrivetrainConstants.kRightFrontId, MotorType.kBrushed);
  private final SparkMax rightRear =
      new SparkMax(DrivetrainConstants.kRightRearId, MotorType.kBrushed);

  // WPILib'in hazır tank drive aracı. Sadece ön (lider) motorları veriyoruz,
  // arka motorlar aşağıda öndekileri takip edecek.
  private final DifferentialDrive differentialDrive = new DifferentialDrive(leftFront, rightFront);

  // Robot açılırken bir kez çalışır ve motorları hazırlar.
  public DrivetrainSubsystem() {
    // Dört motor için ortak ayarlar: fren modu ve akım limiti.
    SparkMaxConfig commonConfig = new SparkMaxConfig();
    commonConfig
        // Joystick bırakılınca motor serbest kalmasın, fren yapsın.
        .idleMode(IdleMode.kBrake)
        // Motorun çekebileceği en fazla akımı sınırla.
        .secondaryCurrentLimit(DrivetrainConstants.kCurrentLimitAmps);

    // Lider (ön) motorların ayarları: ortak ayarlar + yön (sağ taraf ters).
    SparkMaxConfig leftFrontConfig = new SparkMaxConfig();
    leftFrontConfig.apply(commonConfig).inverted(DrivetrainConstants.kLeftInverted);

    SparkMaxConfig rightFrontConfig = new SparkMaxConfig();
    rightFrontConfig.apply(commonConfig).inverted(DrivetrainConstants.kRightInverted);

    // Arka motorlar öndekini takip eder: sol ön ne yapıyorsa sol arka da aynısını yapar.
    SparkMaxConfig leftRearConfig = new SparkMaxConfig();
    leftRearConfig.apply(commonConfig).follow(leftFront);

    SparkMaxConfig rightRearConfig = new SparkMaxConfig();
    rightRearConfig.apply(commonConfig).follow(rightFront);

    // Ayarları motor kontrolcülerine gönder.
    // kResetSafeParameters: eski bir ayar kalmışsa sıfırla.
    // kPersistParameters: ayarları kalıcı olarak kaydet (robot kapanıp açılsa da kalır).
    leftFront.configure(leftFrontConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightFront.configure(rightFrontConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    leftRear.configure(leftRearConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightRear.configure(rightRearConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  // Bu alt birimin her döngüde yapması gereken bir şey yok.
  @Override
  public void periodic() {}

  // Tank drive: sol ve sağ taraf ayrı kontrol edilir.
  // Değerler -1.0 (tam geri) ile 1.0 (tam ileri) arasındadır, 0 durmak demektir.
  public void tankDrive(double left, double right) {
    differentialDrive.tankDrive(left, right);
  }

  // Motorları durdurur.
  public void stop() {
    differentialDrive.stopMotor();
  }

  // Joystick değerlerini sürekli okuyup robotu süren komut.
  // DoubleSupplier: Sabit bir sayı değil, "her seferinde güncel değeri ver" diyen bir yapı.
  // Joystick sürekli değiştiği için bunu kullanıyoruz.
  public Command tankDriveCommand(DoubleSupplier left, DoubleSupplier right) {
    // run(...) içindeki işi her döngüde (saniyede yaklaşık 50 kez) tekrar eder.
    return run(() -> tankDrive(left.getAsDouble(), right.getAsDouble()));
  }
}
