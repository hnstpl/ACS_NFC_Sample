package com.acs.btdemo.testing;

public class Acr1255 {
    Apdu _apdu;

    public enum KEYTYPES {
        ACR1281_KEYTYPE_A(96),
        ACR1281_KEYTYPE_B(97);
        
        private int value;

        private KEYTYPES(int value) {
            this.value = value;
        }
    }

    public enum KEY_STRUCTURE {
        VOLATILE((byte) 0),
        NON_VOLATILE((byte) 32);
        
        private byte value;

        private KEY_STRUCTURE(byte value) {
            this.value = value;
        }
    }

    public Apdu get_apdu() {
        return this._apdu;
    }

    public void loadAuthKey(KEY_STRUCTURE keyStructure, byte keyNumber, byte[] key) throws Exception {
        if (keyStructure == KEY_STRUCTURE.NON_VOLATILE && keyNumber > (byte) 31) {
            throw new Exception("Key number is invalid");
        } else if (key.length != 6) {
            throw new Exception("Invalid key length");
        } else {
            this._apdu = new Apdu();
            this._apdu.setCommand(new byte[]{(byte) -1, (byte) -126, keyStructure.value, keyNumber, (byte) 6});
            this._apdu.setSendData(key);
        }
    }

    public void authenticate(byte blockNum, KEYTYPES keyType, byte KeyNum) throws Exception {
        if (KeyNum > (byte) 32) {
            throw new Exception("Key number is invalid");
        }
        this._apdu = new Apdu();
        this._apdu.setCommand(new byte[]{(byte) -1, (byte) -122, (byte) 0, (byte) 0, (byte) 5});
        this._apdu.setSendData(new byte[]{(byte) 1, (byte) 0, blockNum, (byte) keyType.value, KeyNum});
    }
}
