package com.acs.btdemo.testing;

import android.text.InputFilter;
import android.text.Spanned;

import com.acs.bluetooth.BluetoothReader;
import com.acs.bluetooth.BuildConfig;

import java.util.Locale;

public class Helper {
    public static String toHexString(byte[] buffer) {
        String bufferString = BuildConfig.FLAVOR;
        if (buffer != null) {
            for (byte aBuffer : buffer) {
                String hexChar = Integer.toHexString(aBuffer & BluetoothReader.CARD_STATUS_POWER_SAVING_MODE);
                if (hexChar.length() == 1) {
                    hexChar = "0" + hexChar;
                }
                bufferString = bufferString + hexChar.toUpperCase(Locale.US) + " ";
            }
        }
        return bufferString;
    }

    public static String byteArrayToString(byte[] data, int length) {
        String str = BuildConfig.FLAVOR;
        int indx = 0;
        while ((data[indx] & BluetoothReader.CARD_STATUS_POWER_SAVING_MODE) != 0) {
            str = str + ((char) (data[indx] & BluetoothReader.CARD_STATUS_POWER_SAVING_MODE));
            indx++;
            if (indx == length) {
                break;
            }
        }
        return str;
    }

    public static String byteArrayToString(byte[] data) {
        String convertedString = BuildConfig.FLAVOR;
        for (byte b : data) {
            convertedString = convertedString + ((char) b);
        }
        return convertedString;
    }

    public static byte[] stringToByteArray(String data, int length) {
        byte[] tmpData = new byte[length];
        for (int i = 0; i < data.length(); i++) {
            tmpData[i] = data.getBytes()[i];
        }
        return tmpData;
    }

    public static String byteAsString(byte[] data, boolean spaceinbetween) {
        String tmpStr = BuildConfig.FLAVOR;
        if (data == null) {
            return BuildConfig.FLAVOR;
        }
        for (byte aData : data) {
            tmpStr = tmpStr + String.format(spaceinbetween ? "%02X " : "%02X", new Object[]{Byte.valueOf(aData)});
        }
        return tmpStr;
    }

    public static byte[] intToByte(int number) {
        return new byte[]{(byte) ((number >> 24) & BluetoothReader.CARD_STATUS_POWER_SAVING_MODE), (byte) ((number >> 16) & BluetoothReader.CARD_STATUS_POWER_SAVING_MODE), (byte) ((number >> 8) & BluetoothReader.CARD_STATUS_POWER_SAVING_MODE), (byte) (number & BluetoothReader.CARD_STATUS_POWER_SAVING_MODE)};
    }

    public static boolean byteArrayIsEqual(byte[] array1, byte[] array2, int length) {
        if (array1.length < length || array2.length < length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean byteArrayIsEqual(byte[] array1, byte[] array2) {
        return byteArrayIsEqual(array1, array2, array2.length);
    }

    public static int byteToInt(byte[] data) {
        byte[] holder = new byte[4];
        if (data == null) {
            return -1;
        }
        System.arraycopy(data, 0, holder, 4 - data.length, data.length);
        return ((((holder[0] & BluetoothReader.CARD_STATUS_POWER_SAVING_MODE) << 24) + ((holder[1] & BluetoothReader.CARD_STATUS_POWER_SAVING_MODE) << 16)) + ((holder[2] & BluetoothReader.CARD_STATUS_POWER_SAVING_MODE) << 8)) + (holder[3] & BluetoothReader.CARD_STATUS_POWER_SAVING_MODE);
    }

    public static byte[] getBytes(String stringBytes, String delimeter) {
        String[] arrayStr = stringBytes.split(delimeter);
        byte[] bytesResult = new byte[arrayStr.length];
        for (int i = 0; i < arrayStr.length; i++) {
            bytesResult[i] = Byte.parseByte(arrayStr[i]);
        }
        return bytesResult;
    }

    public static byte[] getBytes(String stringBytes) {
        String formattedString = BuildConfig.FLAVOR;
        int counter = 0;
        if (stringBytes.trim() == BuildConfig.FLAVOR) {
            return null;
        }
        for (int i = 0; i < stringBytes.length(); i++) {
            if (stringBytes.charAt(i) != ' ') {
                if (counter > 0 && counter % 2 == 0) {
                    formattedString = formattedString + " ";
                }
                formattedString = formattedString + stringBytes.charAt(i);
                counter++;
            }
        }
        return getBytes(formattedString, " ");
    }

    public static InputFilter getHexInputFilter() {
        return new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (!source.toString().equals(BuildConfig.FLAVOR)) {
                    if (Character.isLetterOrDigit(source.charAt(start)) && Character.isLetter(source.charAt(start)) && Character.toUpperCase(source.charAt(start)) != 'A' && Character.toUpperCase(source.charAt(start)) != 'B' && Character.toUpperCase(source.charAt(start)) != 'C' && Character.toUpperCase(source.charAt(start)) != 'D' && Character.toUpperCase(source.charAt(start)) != 'E' && Character.toUpperCase(source.charAt(start)) != 'F') {
                        return BuildConfig.FLAVOR;
                    }
                    int i = start;
                    while (i < end) {
                        if (!Character.isLetterOrDigit(source.charAt(i)) && !Character.isSpaceChar(source.charAt(i)) && source.charAt(i) != '-' && source.charAt(i) != '.' && source.charAt(i) != '!') {
                            return BuildConfig.FLAVOR;
                        }
                        i++;
                    }
                }
                return null;
            }
        };
    }
}
