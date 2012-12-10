package yuku.alkitab.base.model;

/**
 * Alkitab resource identifier -- 2012-01-16: not really.
 * @author yuku
 *
 * ari is a 32bit integer.
 * LSB is bit 0, MSB is bit 31.
 * 
 * bit 31..24 is not used, always 0x00
 * bit 23..16 is book number, 0 to 255. 0 is Genesis, 65 is Revelation, 66 and above is defined elsewhere
 * bit 15..8 is chapter number, starts from 1. 0 is undefined or refers to the whole book
 * bit 7..0 is verse number, starts from 1. 0 is undefined or refers to the whole chapter
 */
public class Ari {
	public static int encode(int kitab, int pasal, int ayat) {
		return (kitab & 0xff) << 16 | (pasal & 0xff) << 8 | (ayat & 0xff);
	}
	
	public static int encode(int kitab, int pasal_ayat) {
		return (kitab & 0xff) << 16 | (pasal_ayat & 0xffff);
	}
	
	public static int encodeWithKp(int ariKp, int ayat) {
		return (ariKp & 0x00ffff00) | (ayat & 0xff);
	}
	
	/** 0..255 
	 * kitab berbasis-0 (kejadian == 0)
	 * */
	public static int toBook(int ari) {
		return (ari & 0x00ff0000) >> 16;
	}
	
	/** 1..255 
	 * pasal berbasis-1 yang dikembalikan
	 * */
	public static int toChapter(int ari) {
		return (ari & 0x0000ff00) >> 8;
	}
	
	/** 1..255 
	 * ayat berbasis-1 yang dikembalikan
	 * */
	public static int toVerse(int ari) {
		return (ari & 0x000000ff);
	}
	
	/**
	 * Gabungan kitab dan pasal. Ayat diset ke 0.
	 */
	public static int toKitabPasal(int ari) {
		return (ari & 0x00ffff00);
	}

    /** Similar to Integer.parseInt() but supports 0x and won't throw any exception when failed */
    public static int parseInt(String s, int def) {
        if (s == null || s.length() == 0) return def;

        // need to trim?
        if (s.charAt(0) == ' ' || s.charAt(s.length() - 1) == ' ') {
            s = s.trim();
        }

        // 0x?
        if (s.startsWith("0x")) {
            try {
                return Integer.parseInt(s.substring(2), 16);
            } catch (NumberFormatException e) {
                return def;
            }
        }

        // normal decimal
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return def;
        }
    }
}
