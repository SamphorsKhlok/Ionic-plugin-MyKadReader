package smartcard.redone.com.mykad;


public class Helper {

    public static boolean byteArrayIsEqual(byte[] bArr, byte[] bArr2, int i) {
        if (bArr.length < i || bArr2.length < i) {
            return false;
        }
        for (int i2 = 0; i2 < i; i2++) {
            if (bArr[i2] != bArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    public static String byteArrayToString(byte[] bArr) {
        String str = "";
        for (byte b : bArr) {
            str = new StringBuilder(String.valueOf(str)).append((char) b).toString();
        }
        return str;
    }

    public static String byteAsString(byte[] bArr, int i, int i2, boolean z) {
        byte[] bArr2;
        int i3;
        int i4 = 0;
        if (bArr.length < i + i2) {
            bArr2 = new byte[i2];
        } else {
            bArr2 = new byte[i2];
        }
        for (i3 = 0; i3 < i2; i3++) {
            bArr2[i3] = bArr[i3];
        }
        byte[] bArr3 = new byte[i2];
        for (i3 = 0; i3 < i2; i3++) {
            bArr3[i3] = bArr2[i3];
        }
        byte[] bArr4 = new byte[i2];
        while (i4 < i2) {
            bArr4[i4] = bArr3[i + i4];
            i4++;
        }
        return byteAsString(bArr4, z);
    }

    public static String byteAsString(byte[] bArr, boolean z) {
        if (bArr == null) {
            return "";
        }
        String str = "";
        for (int i = 0; i < bArr.length; i++) {
            str = new StringBuilder(String.valueOf(str)).append(String.format("%02X", new Object[]{Byte.valueOf(bArr[i])})).toString();
            if (z) {
                str = new StringBuilder(String.valueOf(str)).append(" ").toString();
            }
        }
        return str;
    }

    public static String byteToHexString(byte[] bArr, int i) {
        String obj = "";
        for (int i2 = 0; i2 < i; i2++) {
            String toHexString = Integer.toHexString(bArr[i2] & 255);
            if (toHexString.length() == 1) {
                toHexString = "0" + toHexString;
            }
            if (i2 % 32 == 0 && obj != "") {
                obj = "";
            }
            obj = new StringBuilder(String.valueOf(obj)).append(toHexString.toUpperCase()).append(" ").toString();
        }
        return obj.replace(" ","");
    }

    public static byte[] getBytes(String str, String str2) {
        String[] split = str.split(str2);
        byte[] bArr = new byte[split.length];
        int i = 0;
        while (i < split.length) {
            try {
                bArr[i] = Byte.parseByte(split[i]);
                i++;
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return bArr;
    }


    public static byte[] stringToByteArray(String str) {
        str = str.replace(" ","");

        byte[] bArr = new byte[(str.length() / 2)];
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = Integer.valueOf(Integer.parseInt(str.substring(i * 2, (i * 2) + 2), 16)).byteValue();
        }
        return bArr;
    }


    public static String toHexString(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            stringBuilder.append(Integer.toHexString(b).substring(1));
        }
        return stringBuilder.toString();
    }

    public static byte[] toByteArray(String str) {
        byte[] bArr;
        try {
            int length = str.length();
            if (length == 0) {
                return null;
            }
            bArr = new byte[(length / 2)];
            for (int i = 0; i < length; i = (i + 1) + 1) {
                bArr[i / 2] = (byte) Integer.parseInt(str.substring(i, i + 2), 16);
            }
            return bArr;
        } catch (Throwable e) {
            return null;
        }
    }

    public static String toReadableString (byte[] bArr, short offset, short length){
        int i = 0;
        byte[] obj = new byte[length];
        short tempLength = (short) (bArr.length - offset);
        if (length >= tempLength) {
            i = tempLength;
        }else {
            i = length;
        }
        System.arraycopy(bArr, offset, obj, 0, i);

        return new String(obj).trim();
    }

    public static String toDate (String dateStr){
        String tempDate = dateStr.substring(6,8)+ "/" + dateStr.substring(4,6) +  "/"+dateStr.substring(0,4) ;
        return tempDate;
    }

    public static  String nameSanitizor (String name) {
        String tempName = name.replaceAll("[ ]{2,}", " ");
        tempName = tempName.substring(0,tempName.length()/2).replace("$","");

        return tempName;
    }

}
