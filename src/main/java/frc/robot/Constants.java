package frc.robot;

// Projedeki bütün sabit sayılar burada durur.
// Bir şeyi değiştirmek istersen (motor numarası gibi) sadece burayı değiştirirsin.
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

  // Joystick (operator interface) ile ilgili sayılar.
  public static final class OIConstants {
    // Kumandanın bilgisayardaki numarası. İlk bağlanan kumanda genelde 0 olur.
    public static final int kDriverControllerPort = 0;

    // Joystick bırakılınca tam sıfır göstermeyebilir. 0.08'den küçük hareketleri yok sayıyoruz.
    public static final double kDeadband = 0.08;
  }
}
