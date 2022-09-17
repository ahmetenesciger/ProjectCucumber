@content
Feature: Android Content Regression

	@install @yukle
  Scenario:  TC0.1 - App Kurulum
		* android - Uygulama cihaz üzerinden kaldırılır ve tekrar yüklenir

  Scenario:  TC1.1 - Maç Kartı(PRE) Header Takım İsmi, Tarih/Saat, İstatistik Bilgilerinin Gelmesinin Kontrolü
    * android - Login Ekranında "user" ile sisteme giriş yapılır.
		* android - İlk Giriş sayfasında Bilyoner logosunun geldiği kontrol edilir.