# Multi Modüllü Compose Kripto Uygulama

Bu proje, modern Android geliştirme tekniklerini kullanarak oluşturulmuş çok modüllü bir kripto para takip uygulamasıdır.

## Proje Özellikleri

- **MVVM ile Clean Architecture**: Sürdürülebilir ve test edilebilir kod yapısı
- **Coroutines & Flow**: Asenkron işlemler ve reaktif programlama
- **Navigation Components**: Tek aktivite, çoklu fragment yapısı
- **Hilt for Dependency Injection**: Bağımlılıkların yönetimi
- **Retrofit**: RESTful API istekleri
- **Firebase Auth & Firestore**: Kimlik doğrulama ve bulut veritabanı
- **Room Database**: Yerel verilerin saklanması
- **Glide**: Görüntü yükleme ve önbelleğe alma
- **WorkManager**: Arka plan görevlerinin planlanması
- **Internal Notification**: Uygulama içi bildirim sistemi

## Ekran Görüntüleri

> **Not:** Bu kısımda yer alan ekran görüntüleri örnektir. `screenshots` klasöründeki dosyaları kendi uygulamanızın ekran görüntüleriyle değiştirin.

<p align="center">
  <img src="screenshots/screenshot1.png" width="250" alt="Ana Ekran">
  <img src="screenshots/screenshot2.png" width="250" alt="Detay Ekranı">
</p>

## Özellikler

- Kripto paraları listele ve detaylarını görüntüle
- Favori kripto paraları kaydet ve takip et
- Modern ve kullanıcı dostu arayüz (Material 3 tasarım)
- Çok modüllü mimari yapısı

## Teknoloji Yığını

- **Dil:** Kotlin
- **Kullanıcı Arayüzü:** Jetpack Compose
- **Mimarı Yapı:** Clean Architecture & MVVM
- **Bağımlılık Enjeksiyonu:** Dagger-Hilt
- **Asenkron İşlemler:** Coroutines & Flow
- **Ağ İşlemleri:** Retrofit
- **Yerel Veri Depolama:** Room
- **Görüntü Yükleme:** Coil
- **Navigasyon:** Jetpack Navigation Compose

## Proje Yapısı

Proje aşağıdaki ana modüllerden oluşmaktadır:

- **app:** Ana uygulama modülü - navigasyon ve genel uygulama bileşenlerini içerir
- **common:** Ortak bileşenler, yardımcı sınıflar ve uzantılar için paylaşılan modül
- **feature:** Özellik modüllerini içeren ana klasör
  - **home:** Ana ekran özelliği

Her modül Clean Architecture prensipleri uygulanarak yapılandırılmıştır:
- **presentation:** UI bileşenleri, ViewModeller
- **domain:** Kullanım durumları, modeller ve repository arayüzleri
- **data:** Repository implementasyonları, veri kaynakları ve DTO'lar

## Kurulum ve Çalıştırma

1. Projeyi klonlayın:
```
git clone https://github.com/username/MultiModuleComposeCryptoApp.git
```

2. Android Studio'da projeyi açın ve Gradle senkronizasyonunu tamamlayın

3. Bir emülatör veya gerçek cihaz seçin ve uygulamayı çalıştırın

## Geliştirme Rehberi

Yeni bir özellik eklemek için:

1. `feature` klasörü altında yeni bir modül oluşturun
2. Modülü `settings.gradle.kts` dosyasına ekleyin
3. Clean Architecture patern yapısını takip edin (presentation, domain, data)
4. Ana uygulama modülündeki navigasyon grafiğine yeni ekranı ekleyin

## Lisans

[Lisans bilgisi buraya eklenecek]