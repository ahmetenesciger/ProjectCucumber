package utilities;

public class MappedPages {
	public String[] get(String page) {

		String[] pageInfo = new String[1]; //[title]

		switch (page.toLowerCase()) {
		case "anasayfa":
//			String[] largerPageInfo = Arrays.copyOf(pageInfo, 2);
//			largerPageInfo[0] = "Bilyoner | İddaa | İddaa Sonuçları | Spor Toto | Milli Piyango";
			pageInfo[0] = "Bilyoner | İddaa | İddaa Sonuçları | Spor Toto | Milli Piyango";
			return pageInfo;
		case "uye-ol":
			pageInfo[0] = "Hemen üye ol, eğlenceye ortak ol!";
			return pageInfo;
		case "giris-yap":
			pageInfo[0] = "Kullanıcı Girişi | Bilyoner";
			return pageInfo;
		case "mac-karti/futbol/oranlar":
			pageInfo[0] = "Bilyoner.com | Maç Kartı";
			return pageInfo;			

		default:
			return null;
		}
		
	}
}