package smartcard.redone.com.mykad;

/**
 * Created by user on 2/16/2016.
 */
public class MyKad_JPN {

    //constant                                                  length, offset
    static final String KPTName[]                           = { "C8", "00 00" };
    static final String IC_NUMBER[]                         = { "0D", "11 01" };
    static final String GENDER[]                            = { "01", "1E 01" };
    static final String OLD_IC[]                            = { "08", "1F 01" };
    static final String DOB[]                               = { "04", "27 01" };
    static final String BIRTH_PLACE[]                       = { "19", "2B 01" };
    static final String CITIZENSHIP[]                       = { "12", "48 01" };
    static final String RACE[]                              = { "19", "5A 01" };
    static final String RELIGION[]                          = { "0B", "73 01" };

    static final String ADDRESS_1[]                         = { "1E", "03 00" };
    static final String ADDRESS_2[]                         = { "1E", "21 00" };
    static final String ADDRESS_3[]                         = { "1E", "3F 00" };
    static final String POSTCODE[]                           = { "03", "5D 00"};
    static final String CITY[]                              = { "19", "60 00" };
    static final String STATE[]                             = { "1E", "79 00" };

    static final String THUMBPRINT_RIGHT[]                  = { "FF", "17 00" };
    static final String THUMBPRINT_LEFT[]                   = { "FF", "6D 02" };

    static final String ATR                                 = "3B 67 00 00 73 20 00 6C 68 90 00";
    static final String SELECT_JPN_APPLICATION              = "00 A4 04 00 0A A0 00 00 00 74 4A 50 4E 00 10";
    static final String SELECT_APPLICATION_GET_RESPONSE     = "00 C0 00 00 05";

    static final String SET_LENGTH                          = "C8 32 00 00 05 08 00 00";
    static final String SELECT_INFO                         = "CC 00 00 00 08";
    static final String READ_INFO                           = "CC 06 00 00";

    static final String JPN[]                               = { "00 00 01 00", //just an empty so can get JPN[1] for JPN_1_1, look prettier
                                                                "01 00 01 00",
                                                                "02 00 01 00",
                                                                "03 00 01 00",
                                                                "04 00 01 00",
                                                                "05 00 01 00",
                                                                "06 00 01 00"};

}

/*reference from https://gist.github.com/amree/5251219

jpn-1-1
Offset  Length  Length  SDK Function Name        Description
       (Hex)   (Dec)
0000     03        3                          01 04 24
0003     96      150    JPN_OrgName              original name   //those slot dun have info C8  00 00 read starting from 0 and length 200
0099     50   30+30+20  JPN_GMPCName             GMPC name
00E9     28     20+20   JPN_KPTName              KPT name
0111     0D       13    JPN_IDNum                ID number
011E     01        1    JPN_Gender               gender
011F     08        8    JPN_OldIDNum             old ID number
0127     04        4    JPN_BirthDate            date of birth
012B     19       25    JPN_BirthPlace           place of birth
0144     04        4    JPN_DateIssued           date issued
0148     12       18    JPN_Citizenship          citizenship
015A     19       25    JPN_Race                 race
0173     0B       11    JPN_Religion             religion
017E     01        1    JPN_EastMalaysian        East Malaysian
017F     02        2    JPN_RJ                   RJ?
0181     02        2    JPN_KT                   KT?
0183     0B       11    JPN_OtherID              other ID
018E     01        1    JPN_Category             category
018F     01        1    JPN_CardVer              card version
0190     04        4    JPN_GreenCardExpiry      green card expiry date
0194     14       20    JPN_GreenCardNationality green card nationality
01A8     23       35                             All 00

jpn-1-2
0000     03        3                             01 40 03
0003    FA0     4000    JPN_Photo                JPEG photo
0FA3     08        8                             All 00

jpn-1-3
0000     03        3                             01 12 03
0003     14       20                             "R1L1",0,0...
0017    256      598    JPN_Thumb1               thumprint 1 (right thumb)
026D    256      598    JPN_Thumb2               thumprint 2 (left thumb)
04C3     08        8                             All 00

jpn-1-4
0000     03        3                             01 01 52
0003     1E       30    JPN_Address1             address line 1
0021     1E       30    JPN_Address2             address line 2
003F     1E       30    JPN_Address3             address line 3
005D     03        3    JPN_Postcode             postcode
0060     19       25    JPN_City                 city
0079     1E       30    JPN_State                state
0097     14       20                             FF 00 00...

jpn-1-5
0000     03        3                             01 12 00
0003     09        9    JPN_SocsoNum             socso number
000C     1F       31                             All 00

jpn-1-6
0000     03        3                             01 17 00
0003     0A       10    JPN_Locality             locality
000D     1E       30                             All 00
jpj-1-1
Offset  Length  Length  SDK Function Name        Description
       (Hex)   (Dec)
0000     03        3                             01 04 16
0003     01        1    JPJ_OwnerCategory        owner category
0004     0C       12    JPJ_LicenseType          licence type
0010     1E       30    JPJ_VehicleClass         vehicle class
002E     06        6    JPJ_PSVUsage             PSV usage
0034     96      150    JPJ_PSVDesc              PSV description
00CA     06        6    JPJ_GDLUsage             GDL usage
00D0     96      150    JPJ_GDLDesc              GDL description
0166     20       32    JPJ_ValidityPeriod       validity period
0186     14       20    JPJ_HandicappedReg       handicapped registration
019A     01        1    JPJ_KejaraPoints         kejara points
019B     01        1    JPJ_SuspensionNum        suspension number
019C     04        4    JPJ_LastKejaraUpdate     last kejara update
01A0     0B       11                             All 00
 */