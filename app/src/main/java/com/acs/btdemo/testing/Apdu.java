package com.acs.btdemo.testing;

public class Apdu {
    private int _LengthExpected = 0;
    private byte[] _ReceiveData;
    private byte[] _SendData;
    private byte[] _Sw;
    private byte _bCla;
    private byte _bIns;
    private byte _bP1;
    private byte _bP2;
    private byte _bP3;

    public byte getCla() {
        return this._bCla;
    }

    public void setCla(byte bCla) {
        this._bCla = bCla;
    }

    public byte getIns() {
        return this._bIns;
    }

    public void setIns(byte bIns) {
        this._bIns = bIns;
    }

    public byte getP1() {
        return this._bP1;
    }

    public void setP1(byte bP1) {
        this._bP1 = bP1;
    }

    public byte getP2() {
        return this._bP2;
    }

    public void setP2(byte bP2) {
        this._bP2 = bP2;
    }

    public byte getP3() {
        return this._bP3;
    }

    public void setP3(byte bP3) {
        this._bP3 = bP3;
    }

    public byte[] getSendData() {
        return this._SendData;
    }

    public void setSendData(byte[] sendData) {
        this._SendData = sendData;
    }

    public byte[] getReceiveData() {
        return this._ReceiveData;
    }

    public void setReceiveData(byte[] receiveData) {
        this._ReceiveData = receiveData;
    }

    public byte[] getSw() {
        return this._Sw;
    }

    public void setSw(byte[] sw) {
        this._Sw = sw;
    }

    public void setCommand(byte[] cmd) throws Exception {
        if (cmd.length != 5) {
            throw new Exception("Invalid command");
        }
        setCla(cmd[0]);
        setIns(cmd[1]);
        setP1(cmd[2]);
        setP2(cmd[3]);
        setP3(cmd[4]);
    }

    public byte[] getCommand(boolean isCommandOnly) {
        if (isCommandOnly) {
            return new byte[]{getCla(), getIns(), getP1(), getP2(), getP3()};
        }
        byte[] cmd;
        if (getSendData() != null) {
            cmd = new byte[(getSendData().length + 5)];
        } else {
            cmd = new byte[5];
        }
        System.arraycopy(new byte[]{getCla(), getIns(), getP1(), getP2(), getP3()}, 0, cmd, 0, 5);
        if (getSendData() == null) {
            return cmd;
        }
        System.arraycopy(getSendData(), 0, cmd, 5, getSendData().length);
        return cmd;
    }
}
