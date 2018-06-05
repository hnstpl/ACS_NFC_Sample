package com.acs.btdemo.testing;

public class MifareClassic {
    Apdu _apdu;

    public enum KEYTYPES {
        ACR122_KEYTYPE_A(96),
        ACR122_KEYTYPE_B(97);
        
        private final int _id;

        private KEYTYPES(int id) {
            this._id = id;
        }
    }

    public enum VALUEBLOCKOPERATION {
        STORE(0),
        INCREMENT(1),
        DECREMENT(2);
        
        private final int _id;

        private VALUEBLOCKOPERATION(int id) {
            this._id = id;
        }
    }

    public Apdu get_apdu() {
        return this._apdu;
    }

    public void valueBlock(byte blockNum, VALUEBLOCKOPERATION transType, int amount) throws Exception {
        this._apdu = new Apdu();
        this._apdu.setCommand(new byte[]{(byte) -1, (byte) -41, (byte) 0, blockNum, (byte) 5});
        this._apdu.setSendData(new byte[]{(byte) transType._id, Helper.intToByte(amount)[0], Helper.intToByte(amount)[1], Helper.intToByte(amount)[2], Helper.intToByte(amount)[3]});
    }

    public void store(byte blockNumber, int amount) throws Exception {
        valueBlock(blockNumber, VALUEBLOCKOPERATION.STORE, amount);
    }

    public void decrement(byte blockNumber, int amount) throws Exception {
        valueBlock(blockNumber, VALUEBLOCKOPERATION.DECREMENT, amount);
    }

    public void increment(byte blockNumber, int amount) throws Exception {
        valueBlock(blockNumber, VALUEBLOCKOPERATION.INCREMENT, amount);
    }

    public int inquireAmount(byte blockNum) throws Exception {
        this._apdu = new Apdu();
        this._apdu.setCommand(new byte[]{(byte) -1, (byte) -79, (byte) 0, blockNum, (byte) 4});
        if (this._apdu.getSw()[0] == (byte) -112) {
            return Helper.byteToInt(this._apdu.getReceiveData());
        }
        throw new Exception("Read value failed");
    }

    public void restoreAmount(byte sourceBlock, byte targetBlock) throws Exception {
        this._apdu = new Apdu();
        this._apdu.setCommand(new byte[]{(byte) -1, (byte) -41, (byte) 0, sourceBlock, (byte) 2});
        this._apdu.setSendData(new byte[]{(byte) 3, targetBlock});
    }

    public void readBinaryBlock(byte blockNum, byte length) throws Exception {
        byte[] tmpArray = new byte[16];
        this._apdu = new Apdu();
        this._apdu.setCommand(new byte[]{(byte) -1, (byte) -80, (byte) 0, blockNum, length});
        this._apdu.setSendData(new byte[0]);
    }

    public void updateBinaryBlock(byte blockNum, byte[] data, byte length) throws Exception {
        if (data.length > 48) {
            throw new Exception("Data has invalid length");
        } else if (length % 16 != 0) {
            throw new Exception("Data length must be multiple of 16");
        } else {
            this._apdu = new Apdu();
            this._apdu.setCommand(new byte[]{(byte) -1, (byte) -42, (byte) 0, blockNum, length});
            this._apdu.setSendData(new byte[data.length]);
            this._apdu.setSendData(data);
        }
    }

    public String getErrorMessage(byte[] sw1sw2) {
        if (sw1sw2.length < 2) {
            return "Unknown Status Word (" + Helper.byteAsString(sw1sw2, false) + ")";
        }
        if (sw1sw2[0] == (byte) 99 && sw1sw2[1] == (byte) 0) {
            return "Command failed";
        }
        return "Unknown Status Word (" + Helper.byteAsString(sw1sw2, false) + ")";
    }
}
