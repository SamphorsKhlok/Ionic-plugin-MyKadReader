package smartcard.redone.com.mykad;

import org.apache.cordova.*;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.AsyncTask;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyKadPlugin extends CordovaPlugin {
    private String TAG = "CordovaPlugin";
    private static final int MAX_LINES = 25;
    public CardReader myCardReader;
    public MyKad myKad;
    public MyKad_Data myKad_data;
    private MyKad_JPN myKad_jpn;
    private boolean isReaderAutoDetect = false;
    public static Context mainContext;
    private CallbackContext callbackContext;

    private static final String[] stateStrings; // for reader status
    public String message = "";

    //for JSON Object as a return obj from read
    public JSONObject obj;
    private static final String MESSAGE = "Message";
    private static final String NAME = "Name";
    private static final String NRIC = "Nric";
    private static final String CITIZENSHIP = "Citizenship";
    private static final String CITY = "City";
    private static final String ADDRESS1 = "Address1";
    private static final String ADDRESS2 = "Address2";
    private static final String ADDRESS3 = "Address3";
    private static final String DOB = "DOB";
    private static final String GENDER = "Gender";
    private static final String RACE = "Race";
    private static final String POSTCODE = "Postcode";
    private static final String STATE = "State";

    static {
        stateStrings = new String[] {"Unknown", "Absent", "Present", "Swallowed", "Powered", "Negotiable", "Specific"};
    }

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        mainContext = this.cordova.getActivity().getApplicationContext();
        myCardReader = new CardReader(mainContext, -1);
        initializeCardReader();

        myKad = new MyKad(myCardReader);
        myKad_data = new MyKad_Data();


        Log.d(TAG,"Cordova initialization done !");
    }

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        //json obj to store all respond and pass to javascript
        JSONObject obj = new JSONObject();

        if (action.equals("greet")) {
            Log.d(TAG,"greet is requested");

            String name = data.getString(0);
            message = " Hello, " + name;
            obj.put(MESSAGE,message);
            //Log.d(TAG, "obj is " + obj);
            callbackContext.success(obj);

            return true;
        }else if (action.equals("read")) {
            try {
                //message += "I am Clicked !\n"
                //        + myCardReader.getReaderName() + "\n"
                //        + " Status : " + stateStrings[myCardReader.getState()];


                if(checkCard(myCardReader.connectToCard())){
                    if (myCardReader.getState() == 2 || myCardReader.getState() == 6 ) {
                        //message += "Reader is connected " + myKad.TAG;

                        if (myKad.selectApplicationJPN() == true) {
                            myKad_data = myKad.GetMyKadDetail();
                            /*
                            message += "Result is "
                                    + myKad_data.GetName() + "\n"
                                    + myKad_data.GetNric() + "\n"
                                    + myKad_data.GetCitizenship() + "\n"
                                    + myKad_data.GetCity() + "\n"
                                    + myKad_data.GetAddress1() + "\n"
                                    + myKad_data.GetAddress2() + "\n"
                                    + myKad_data.GetAddress3() + "\n"
                                    + myKad_data.GetDateOfBirth() + "\n"
                                    + myKad_data.GetGender() + "\n"
                                    + myKad_data.GetRace() + "\n"
                                    + myKad_data.GetPostcode() + "\n"
                                    + myKad_data.GetState() + "\n"
                            ;
                            */
                            obj.put(NAME,myKad_data.GetName());
                            obj.put(NRIC,myKad_data.GetNric());
                            obj.put(CITIZENSHIP,myKad_data.GetCitizenship());
                            obj.put(CITY,myKad_data.GetCity());
                            obj.put(ADDRESS1,myKad_data.GetAddress1());
                            obj.put(ADDRESS2,myKad_data.GetAddress2());
                            obj.put(ADDRESS3,myKad_data.GetAddress3());
                            obj.put(DOB,myKad_data.GetDateOfBirth());
                            obj.put(GENDER,myKad_data.GetGender());
                            obj.put(RACE,myKad_data.GetRace());
                            obj.put(POSTCODE,myKad_data.GetPostcode());
                            obj.put(STATE,myKad_data.GetState());
                        } else {
                            //message += "Using JPN Application is not supported";
                            obj.put(MESSAGE, "Using JPN Application is not supported");
                        }
                    } else {
                        //message += "No Card is connected !";
                        obj.put(MESSAGE, "No Card is connected !");
                    }
                }else{
                    //message += "Check card : Invalid";
                    obj.put(MESSAGE, "Check card : Invalid");
                }

                myCardReader.unpowerCard();
            } catch (Exception e) {
                //message += "Trying to read card failed "+ e;
                obj.put(MESSAGE, "Trying to read card failed " + e);
                myCardReader.unpowerCard();
            }

            callbackContext.success(obj);
            return true;
        }else if (action.equals("connect")) {
            initializeCardReader();
            if(myKad != null){
                myKad = new MyKad(myCardReader);
                //message += "I am IN !";
                obj.put(MESSAGE, "Card Reader is connected successfully!");
            }

            callbackContext.success(obj);
            return true;
        }else {
            return false;
        }
    }

    /*starting of function from MyKad.java*/
    private void initializeCardReader() {
        //message += TAG + "Initialisation started";
        myCardReader.detectUsbDevice(this.isReaderAutoDetect);
    }

    private boolean checkCard(CardReader.ConnectProgress connectProgress) {
        try {
            if (CardReader.isConnectedToReader) {
                if (myKad.checkCard(connectProgress)) {
                    //message += "check card true";
                    return true;
                }
                //message += "check card false due to " + connectProgress.atr;
                return false;
            }
            throw new Exception("Please connect reader");
        } catch (Exception e) {
            return false;
        }
    }

}
