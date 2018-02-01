package smartcard.redone.com.mykad;

import android.util.Log;

/**
 * Created by user on 2/16/2016.
 */
public class MyKad {
    public String TAG = "MyKad";
    public CardReader myReader;
    public Helper myHelper;
    public MyKad_JPN myKad_jpn;
    public MyKad_Data myKad_data;

    public MyKad(CardReader cardReader) {
        this.myReader = cardReader;
    }

    public CardReader getCardReader() {
        return this.myReader;
    }

    public boolean checkCard(CardReader.ConnectProgress connectProgress) throws Exception {
        //ATR length
        if (connectProgress.atrLength >= 11) {
            //Log.d(TAG, "Check card true " + connectProgress.atr);
            return true;
        } else {
            //Log.d(TAG, "Check card false" + connectProgress.atr);
            return false;
        }
    }

    public MyKad_Data GetMyKadDetail(){
        myKad_data = new MyKad_Data();

        myKad_data.SetName(Helper.nameSanitizor(this.readMyKad(MyKad_JPN.JPN[1], MyKad_JPN.KPTName[0], MyKad_JPN.KPTName[1], true))); // need to clean since it return duplicate name with $ sign
        myKad_data.SetNric(this.readMyKad(MyKad_JPN.JPN[1], MyKad_JPN.IC_NUMBER[0], MyKad_JPN.IC_NUMBER[1], true));
        myKad_data.SetCitizenship(this.readMyKad(MyKad_JPN.JPN[1], MyKad_JPN.CITIZENSHIP[0], MyKad_JPN.CITIZENSHIP[1], true));
        myKad_data.SetDateOfBirth(Helper.toDate(this.readMyKad(MyKad_JPN.JPN[1], MyKad_JPN.DOB[0], MyKad_JPN.DOB[1], false))); //convert to format dd/mm/yyyy
        myKad_data.SetGender(this.readMyKad(MyKad_JPN.JPN[1], MyKad_JPN.GENDER[0], MyKad_JPN.GENDER[1],true));
        myKad_data.SetRace(this.readMyKad(MyKad_JPN.JPN[1], MyKad_JPN.RACE[0], MyKad_JPN.RACE[1],true));


        // JPN_1_4
        myKad_data.SetAddress1(this.readMyKad(MyKad_JPN.JPN[4], MyKad_JPN.ADDRESS_1[0], MyKad_JPN.ADDRESS_1[1],true));
        myKad_data.SetAddress2(this.readMyKad(MyKad_JPN.JPN[4], MyKad_JPN.ADDRESS_2[0], MyKad_JPN.ADDRESS_2[1],true));
        myKad_data.SetAddress3(this.readMyKad(MyKad_JPN.JPN[4], MyKad_JPN.ADDRESS_3[0], MyKad_JPN.ADDRESS_3[1],true));
        myKad_data.SetPostcode(this.readMyKad(MyKad_JPN.JPN[4], MyKad_JPN.POSTCODE[0], MyKad_JPN.POSTCODE[1],false).substring(0,5));// take only first 5 digit 43200
        myKad_data.SetState(this.readMyKad(MyKad_JPN.JPN[4], MyKad_JPN.STATE[0], MyKad_JPN.STATE[1],true));
        myKad_data.SetCity(this.readMyKad(MyKad_JPN.JPN[4], MyKad_JPN.CITY[0], MyKad_JPN.CITY[1],true));
        return myKad_data;
    }

    /* Using 5 step in reading MyKad
    Reference , https://forum.lowyat.net/index.php?s=684c18f20b1cb2afce4733afce81c5d0&showtopic=355950&st=20&p=11151482&#entry11151482
        1) Select Application
        2) Get Response
        3) Set Length
        4) Select Info
        5) Read Info

        function useJPN : using step 1 and 2
        function readMyKad : looping step 3, 4, 5
    */

    public boolean selectApplicationJPN (){
        //Log.d(TAG, "----> Starting of Application Selection");
        byte[] commandApp = myHelper.stringToByteArray(myKad_jpn.SELECT_JPN_APPLICATION);
        //Log.d(TAG, "1 - command select application " + commandApp);

        CardReader.TransmitProgress respondApp = myReader.sendApdu(commandApp);
        String respondStr = Helper.byteToHexString(respondApp.response, respondApp.responseLength);

        if(respondStr.endsWith("6105")) {
            byte[] commandRespond = myHelper.stringToByteArray(myKad_jpn.SELECT_APPLICATION_GET_RESPONSE);
            //Log.d(TAG, "2 - command get respond  " + commandRespond);

            CardReader.TransmitProgress respondRespond = myReader.sendApdu(commandRespond);
            respondStr = Helper.byteToHexString(respondRespond.response, respondRespond.responseLength);

            if(respondStr.endsWith("9000")) {
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public String readMyKad(String jpn,  String str_length, String str_offset, boolean convert)  {
        //Log.d(TAG, "----> Starting of Reading MyKad Info");
        String result = "";
        short length = Short.parseShort(str_length,16);
        //Log.d(TAG," Length of item is " + length);


        byte[] commandLength = myHelper.stringToByteArray(myKad_jpn.SET_LENGTH + str_length + " 00");

        //Log.d(TAG, "3 - command setLength " + commandLength);

        CardReader.TransmitProgress respondLength = myReader.sendApdu(commandLength);
        String respondStr = Helper.byteToHexString(respondLength.response, respondLength.responseLength);

        //Log.d(TAG, "Check if end with 9108 ? " + respondStr);
        if (respondStr.endsWith("9108")) {
            byte[] commandSelectInfo = myHelper.stringToByteArray(myKad_jpn.SELECT_INFO + jpn + str_offset + str_length + " 00");
            //Log.d(TAG, "4 - command selectInfo " + commandSelectInfo);

            CardReader.TransmitProgress respondSelectInfo = myReader.sendApdu(commandSelectInfo);
            respondStr = Helper.byteToHexString(respondSelectInfo.response, respondSelectInfo.responseLength);

            byte[] commandReadInfo = myHelper.stringToByteArray(myKad_jpn.READ_INFO + str_length);
            //Log.d(TAG, "5 - command readInfo " + commandReadInfo);

            CardReader.TransmitProgress respondReadInfo = myReader.sendApdu(commandReadInfo);
            //Log.e(TAG, "respondReadInfo length was " + respondReadInfo.responseLength);
            //respondStr = Helper.byteToHexString(respondReadInfo.response, respondReadInfo.responseLength);
            //byteToHexString and byteAsString is similar, but byteToHexString is shorter to use but less accurate
            respondStr = Helper.byteAsString(respondReadInfo.response, 0, respondReadInfo.responseLength,false);

            Log.d(TAG, "checking respond contains 9000 ? " + respondStr);
            int lengthComm = respondStr.length() - 4;
            if (lengthComm <= 0) {
                return null;
            }

            if (respondStr.endsWith("9000")) {
                respondStr = respondStr.substring(0, lengthComm);

                //some result do no need conversion
                if (convert) {
                    // start to convert to byte
                    byte[] tempConvert = myHelper.toByteArray(respondStr);
                    result += myHelper.toReadableString(tempConvert, (short) 0, length);
                } else {
                    result += respondStr;
                }
            }

        }

        Log.d(TAG, "After Covert is " + result);
        return result;
    }
}
