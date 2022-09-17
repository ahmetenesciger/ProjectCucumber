		// ********************************* Elements ***********************************
		String btn_Element1										= "#id";
		String btn_Element2										= ".class";
		String btn_Element3										= "button[type='submit']";
		String btn_Element4										= "@xpath";

		// ios özel
		String btn_Element5										= "$iOSClassChain";
		
		
		// android özel
		String btn_Element6										= "*resourceId";
		String btn_Element7										= "$text";
		
		
		
		/*  
		---- BASIC
		wait(5);								= işlem 5 saniye bekler.
		waitLoadingImage(); 					= sayfa geçişlerindeki "loading" imajının bitmesini bekler
		waitClickable(btn_Element);				= parametre olarak gönderilen element tıklanabilir olana kadar bekler.
		throwFail("hata mesaji");				= hata fırlatır ve senaryo koşumunu durdurur.
		back();									= geri tuşu(android)
		scroll(Direction.UP);					= ekranı kaydırır
		swipe(207,355,207, 175, 250);			= ekran üzerindeki belirli koordinatlara kaydırma yapılır.
		
		click(btn_Element);						= seçilen elemente dokunur(tiklar) 
		writeText(btn_Element, "text")			= elementin içine yazı yazmamızı sağlar.
		readText(btn_Element);					= elementin text degerini okur
		mouseOver(btn_Element);					= mouse ile elementin üzerine gelir.(tıklama yapmaz)
		
		assertEquals(btn_Element, "text");		= elementin içindeki yazanın "text" ile ayni olup olmadigini kontol eder
		assertNotEquals(btn_Element, "text");	= elementin içindeki yazının "text" ile farkli olup olmadigini kontol eder.
		assertText(text1, text2);				= iki string degerin ayni olup olmadiğini kontrol eder
		assertContains(btn_Element,"text");		= elementin text'inde girilen deger yazisi varmi kontrol eder
		assertTrue(btn_Element);				= elementin ekranda göründüğünü kontrol eder. (anlık)
		assertFound(btn_Element);				= elementin ekranda göründüğünü kontrol eder. (elementi 30 sn boyunca arar)
		
		exists(Btn_Element, second);			= elementin ekranda olup olmadıgını kontrol eder. true, false döndürür.
		getSize(Btn_Element);					= ekranda elementten kaç tane olduğunu gösterir. 
		getWidth(btn_Element);					= elementin genişlik değerini verir
		getHeight(btn_Element);					= elementin yükseklik değerini verir
		getLocationXY(btn_Element);				= elementin ekrandaki koordinatlarını verir.
		
		int i = 0;
		while(!exists(element, 1) && i < swipeCount){
		scroll(Direction.UP);
		i++;
		}
		assertTrue(element);
		return this;							= element ekranda görünene kadar ekranı scroll yapar
	