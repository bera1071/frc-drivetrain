package frc.robot;

// Projedeki bütün sabit sayılar burada durur.
// Bir şeyi değiştirmek istersen (motor numarası, hız gibi) sadece burayı değiştirirsin.
public final class Constants {

  // Sürüş sistemi (drivetrain) ile ilgili sayılar.
  public static final class DrivetrainConstants {
    // SPARK MAX CAN ID'leri. Gerçek robotta REV Hardware Client ile kontrol et.
    public static final int kLeftFrontId = 1;
    public static final int kLeftRearId = 2;
    public static final int kRightFrontId = 3;
    public static final int kRightRearId = 4;

    // CIM motorlar için akım limiti (amper). Motorun aşırı yüklenip zarar görmesini önler.
    // CIM fırçalı (brushed) motor olduğu için "secondary current limit" kullanıyoruz.
    public static final double kCurrentLimitAmps = 40.0;

    // Sağ ve sol motorlar robotta ters yönde durur, bu yüzden bir tarafı ters çeviriyoruz.
    // Robot ileri komutunda geri giderse bu iki değeri değiştir.
    public static final boolean kLeftInverted = false;
    public static final boolean kRightInverted = true;
  }

  // Intake (oyun parçasını robotun içine alan mekanizma) ile ilgili sayılar.
  public static final class IntakeConstants {
    // Intake motorunun SPARK MAX CAN ID'si (NEO motor).
    public static final int kMotorId = 5;

    // Intake çalışırken verilecek hız (-1.0 ile 1.0 arası). Parça ters girerse eksi yap.
    public static final double kIntakeSpeed = 0.6;

    // NEO fırçasız (brushless) motor olduğu için smart current limit kullanılır (amper).
    public static final int kCurrentLimitAmps = 40;
  }

  // Shooter (parçayı atan mekanizma) ile ilgili sayılar. 2 adet NEO motor var.
  public static final class ShooterConstants {
    // Lider motor ve onu takip eden motorun SPARK MAX CAN ID'leri.
    public static final int kLeaderMotorId = 6;
    public static final int kFollowerMotorId = 7;

    // Atış sırasında verilecek hız (-1.0 ile 1.0 arası).
    public static final double kShootSpeed = 0.8;

    // İki motor birbirine bakıyorsa ikinci motorun ters dönmesi gerekir.
    // Tekerlekler zıt yönde dönüyorsa bunu değiştir.
    public static final boolean kFollowerInverted = false;

    // NEO için akım limiti (amper).
    public static final int kCurrentLimitAmps = 40;
  }

  // Kumanda (operator interface) ile ilgili sayılar.
  public static final class OIConstants {
    // Kumandanın bilgisayardaki numarası. İlk bağlanan kumanda genelde 0 olur.
    public static final int kDriverControllerPort = 0;

    // Joystick bırakılınca tam sıfır göstermeyebilir. 0.08'den küçük hareketleri yok sayıyoruz.
    public static final double kDeadband = 0.08;
  }
}
