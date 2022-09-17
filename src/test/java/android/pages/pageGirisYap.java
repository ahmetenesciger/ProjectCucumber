package android.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.cucumber.java.Scenario;
import page.PageBaseAndroid;
import utilities.Configuration;
import utilities.MappedLogin;

import static org.testng.Assert.fail;

import utilities.SoftAssert;

public class pageGirisYap extends PageBaseAndroid {
	AndroidDriver<AndroidElement> androidDriver;
	MappedLogin mappedLogin;

	// ********* Constructor *********
		public pageGirisYap(Object[] args) {
			super((AndroidDriver<AndroidElement>)args[0]);
			scenario = (Scenario)args[1];
			stepNumber = (int)args[2];
			assertPool = (SoftAssert)args[3];
			mappedLogin = new MappedLogin();
		}

		// ********************************* Elements ***********************************
		String lbl_Baslik										= "#toolbarTitle";
		String txt_TextBoxsName									= "#floatingHintText";
		String loginEkrani_CheckBoxNames						= "@//android.widget.LinearLayout/android.widget.TextView";
		String loginEkrani_CheckBoxs							= "@../android.widget.Switch";
		String loginEkrani_Buttons								= "@//android.widget.Button";
		String loginEkrani_SifremiUnuttum						= "#textViewForgetPassword";
		String loginEkrani_ParmakIziKullanmakIstemiyorum		= "#textViewDoNotUseFingerPrint";

		String txt_TcKimlikUyeNo								= "#editTextIdentity";
		String txt_SifreSecim									= "#inputLayoutPassword";
		String txt_Sifre										= "#editTextPassword";
		String btn_GirisYap										= "#buttonSignIn";
		String btn_Password										= "#passwordButton";
		String btn_HemenUyeOl									= "#textViewSignUp";

	// *********Page Methods*********
	public pageGirisYap Baslik_Kontrol(String text) {
		assertEquals(lbl_Baslik, text);
		return this;
	}

	public pageGirisYap TcNo_Tikla() {
		click(txt_TcKimlikUyeNo);
		return this;
	}

	public pageGirisYap Sifre_Tikla() {
		click(txt_SifreSecim);
		return this;
	}

	public pageGirisYap GirisYap_Buton_PasifMi() {
		assertAttribute(btn_GirisYap, "enabled", "false");
		return this;
	}

	public pageGirisYap GirisYap_Buton_AktifMi() {
		assertAttribute(btn_GirisYap, "enabled", "true");
		return this;
	}

	public pageGirisYap HemenUyeOl_Tikla() {
		click(btn_HemenUyeOl);
		return this;
	}

	public pageGirisYap Password_Tikla() {
		click(btn_Password);
		return this;
	}

	public pageGirisYap Password_AktifMi() {
		assertAttribute(txt_Sifre, "password", "true");
		return this;
	}

	public pageGirisYap Password_PasifMi() {
		assertAttribute(txt_Sifre, "password", "false");
		return this;
	}

	public pageGirisYap TcNo_DegerGir(String text) {
		writeText(txt_TcKimlikUyeNo, text);
		return this;
	}

	public pageGirisYap Sifre_DegerGir(String text) {
		writeText(txt_Sifre, text);
		return this;
	}

	public pageGirisYap TextBox_Kontrol(String text) {
		int size = getSize(txt_TextBoxsName);
		for (int i = 0; i < size; i++) {
			if (readText(txt_TextBoxsName, i).equals(text)) {
				return this;
			}
		}
		fail(text + "' adındaki metin bulunamadı.");
		return this;
	}

	public pageGirisYap kontrol_btn_GirisYap() {
		assertFound(btn_GirisYap);
		return this;
	}

	public pageGirisYap Sifremi_Unuttum_Buton_Kontrol() {
		assertFound(loginEkrani_SifremiUnuttum);
		return this;
	}

	public pageGirisYap Sifremi_Unuttum_Buton_Tikla() {
		click(loginEkrani_SifremiUnuttum);
		return this;
	}

	public pageGirisYap Parmak_Izi_Kullanmak_Istemiyorum_Tikla() {
		if(exists(loginEkrani_ParmakIziKullanmakIstemiyorum, 2)) 
		click(loginEkrani_ParmakIziKullanmakIstemiyorum);
		return this;
	}

	public pageGirisYap Giris_Basarili(String user_role) {
		if (user_role.equals("user")) {
			if(Configuration.getInstance().app_package().contains("beta"))
				user_role = "test" + user_role;
			else
				user_role = "prod" + user_role;	
		}
		writeText(txt_TcKimlikUyeNo, mappedLogin.get(user_role)[0]);
		click(txt_SifreSecim);
		writeText(txt_Sifre, mappedLogin.get(user_role)[1]);
		click(btn_GirisYap);
		return this;
	}

	public pageGirisYap Klavye_Gizle(){
		wait(1);
		hideKeyboard();
		return this;
	}
}