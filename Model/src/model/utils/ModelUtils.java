package model.utils;

import java.io.UnsupportedEncodingException;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import java.sql.SQLException;

import java.sql.Timestamp;

import java.util.Calendar;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import javax.el.ELContext;
import javax.el.ValueExpression;

import oracle.adf.share.ADFContext;

import oracle.igf.ids.util.Base64;

import oracle.jbo.ViewObject;
import oracle.jbo.server.DBTransaction;
import oracle.jbo.server.SequenceImpl;
import oracle.jbo.domain.Number;

/**
 * Author: LinhNM
 * Created: 01/19/2014
 */
public class ModelUtils {
    public ModelUtils() {
        super();
    }

    /**
     * @param strSequence - Name of sequence
     * @param dbTrans - dbtransaction of current context
     * @return - Next number of sequence
     */
    public Number getSequenceNumber(String strSequence, DBTransaction dbTrans) {
        SequenceImpl seq = new SequenceImpl(strSequence, dbTrans);
        return seq.getSequenceNumber();
    }

    public Object getValueExpression(String ex) {
        ELContext elcon = ADFContext.getCurrent().getELContext();
        return ADFContext.getCurrent().getExpressionFactory().createValueExpression(elcon, ex,
                                                                                    Object.class).getValue(elcon);
    }

    public void setValueExpression(String ex, Object value) {
        ELContext elcon = ADFContext.getCurrent().getELContext();
        ValueExpression valueExp =
            ADFContext.getCurrent().getExpressionFactory().createValueExpression(elcon, ex, Object.class);
        valueExp.setValue(elcon, value);
    }


    public Date getCurrentDateUtils() {
        return Calendar.getInstance().getTime();
    }

    public java.sql.Date getCurrentDateSql() {
        return new java.sql.Date(Calendar.getInstance().getTime().getTime());
    }

    public oracle.jbo.domain.Date getCurrentDateOraDomain() {
        return new oracle.jbo.domain.Date(getCurrentDateSql());
    }

    public Timestamp getCurrentTimestamp() {
        return new Timestamp(getCurrentDateUtils().getTime());
    }


    
    public String getUpFirstStr(String s) {
        String[] splitS = s.split(" ");
        String newVl = "";
        for (int i = 0; i < splitS.length; i++) {
            String strChar = splitS[i].trim();
            if (strChar.length() > 0) {
                strChar = strChar.substring(0, 1).toUpperCase() + strChar.substring(1);
                newVl += strChar + " ";
            }
        }
        return newVl;
    }

    public String getCurrentUser() {
        Object objUser = getValueExpression("#{sessionBean.username}");
        if (objUser == null) {
            objUser = "admin";
        }
        return objUser.toString();
    }

    private String Pasre3NumberToString(String n) {
        String[] tram = {
            "", "M\u1ED8T", "HAI", "BA", "B\u1ED0N", "N\u0102M", "S\u00C1U", "B\u1EA2Y", "T\u00C1M", "CH\u00CDN" };
        String[] chuc = {
            "LINH", "M\u01AF\u1EDCI", "HAI", "BA", "B\u1ED0N", "N\u0102M", "S\u00C1U", "B\u1EA2Y", "T\u00C1M",
            "CH\u00CDN"
        };
        String[] dv = {
            "", "M\u1ED8T", "HAI", "BA", "B\u1ED0N", "N\u0102M", "S\u00C1U", "B\u1EA2Y", "T\u00C1M", "CH\u00CDN" };
        String result = "";
        if (n.length() == 0) {
            return result;
        }
        if (n.length() == 1) {
            n = "0" + n;
        }
        if (n.length() == 2) {
            n = "0" + n;
        }
        if (n.charAt(0) == '0') {
            if (n.charAt(1) == '0') {
                result = dv[Integer.parseInt(n.substring(2))];
            } else {
                if (n.charAt(1) == '1') {
                    if (n.charAt(2) == '5') {
                        result = chuc[Integer.parseInt(n.substring(1, 2))] + " L\u0102M";
                    } else {
                        result = chuc[Integer.parseInt(n.substring(1, 2))] + " " + dv[Integer.parseInt(n.substring(2))];
                    }
                } else {
                    if (n.charAt(2) == '5') {
                        result = chuc[Integer.parseInt(n.substring(1, 2))] + " M\u01AF\u01A0I " + "L\u0102M";
                    } else if (n.charAt(2) == '1') {
                        result = chuc[Integer.parseInt(n.substring(1, 2))] + " M\u01AF\u01A0I " + "M\u1ED0T";
                    } else {
                        result =
                            chuc[Integer.parseInt(n.substring(1, 2))] + " M\u01AF\u01A0I " +
                            dv[Integer.parseInt(n.substring(2))];
                    }
                }
            }
        } else {
            if (n.charAt(1) == '0') {
                if (n.charAt(2) == '0') {
                    result = "";
                } else {
                    result = chuc[Integer.parseInt(n.substring(1, 2))] + " " + dv[Integer.parseInt(n.substring(2))];
                }
            } else {
                if (n.charAt(1) == '1') {
                    if (n.charAt(2) == '5') {
                        result = chuc[Integer.parseInt(n.substring(1, 2))] + " L\u0102M";
                    } else {
                        result = chuc[Integer.parseInt(n.substring(1, 2))] + " " + dv[Integer.parseInt(n.substring(2))];
                    }
                } else {
                    if (n.charAt(2) == '5') {
                        result = chuc[Integer.parseInt(n.substring(1, 2))] + " M\u01AF\u01A0I " + "L\u0102M";
                    } else if (n.charAt(2) == '1') {
                        result = chuc[Integer.parseInt(n.substring(1, 2))] + " M\u01AF\u01A0I " + "M\u1ED0T";
                    } else {
                        result =
                            chuc[Integer.parseInt(n.substring(1, 2))] + " M\u01AF\u01A0I " +
                            dv[Integer.parseInt(n.substring(2))];
                    }
                }
            }
            result = tram[Integer.parseInt(n.substring(0, 1))] + " TR\u0102M " + result;
        }
        return result.toLowerCase();
    }

    public String ParseNumberToString(String n) {
        System.out.println(n + "__");
        if (n.equals("0")) {
            return "";
        }
        String temp = "";
        String result = "";
        String[] hang = { "", "NGH\u00CCN", "TRI\u1EC6U", "T\u1EF6" };
        int pos = 0;
        boolean ck = true;
        if (n.charAt(n.length() - 1) != '0') {
            ck = false;
        }
        for (int i = n.length(); i >= 0; i = i - 3) {
            if (i <= 3) {
                temp = n.substring(0, i);
            } else {
                temp = n.substring(i - 3, i);
            }
            if (Pasre3NumberToString(temp) != "") {
                result = Pasre3NumberToString(temp) + " " + hang[pos] + " " + result;
            }
            if (pos < hang.length - 1) {
                pos++;
            }
        }
        if (result.equals("")) {
            result = "";
        } else {
            if (ck)
                result = result.trim() + " \u0110\u1ED2NG CH\u1EB4N";
            else
                result = result.trim() + " \u0110\u1ED2NG ";
        }
        result = result.toLowerCase();
        result = result.substring(0, 1).toUpperCase() + result.substring(1, result.length() );
        return result;
    }

    public boolean isInRole(String role) {
        return ADFContext.getCurrent().getSecurityContext().isUserInRole(role);
    }

    public String encryptString(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException,
                                                   InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,
                                                   InvalidAlgorithmParameterException, IllegalBlockSizeException,
                                                   BadPaddingException {
        byte[] iv = { 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0 };
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        KeySpec spec = new PBEKeySpec(str.toCharArray(), "salts".getBytes("UTF-8"), 65536, 128);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret, new IvParameterSpec(iv));
        byte[] ciphertext = cipher.doFinal("text".getBytes("UTF-8"));
        String plaintext = new Base64().encode(ciphertext); //new String(cipher.doFinal(ciphertext), "UTF-8");
        return plaintext;
    }


    public String AvoidInjection(String strParam) {
        strParam = strParam.trim();
        strParam = strParam.replaceAll("'", "''");
        strParam = strParam.replaceAll("--", " - ");
        return strParam;
    }
    
    public static String trimAtt(String value){
        return value != null ? value.trim():null;
    }
    
    public boolean checkRoleDetail(ViewObject Eview,ViewObject viewCheck){
        System.out.println("getValueExpression(\"#{sessionBean.responsibility}\"):"+getValueExpression("#{sessionBean.responsibility}"));
        if(getValueExpression("#{sessionBean.responsibility}").toString().equals("1")){
            viewCheck.setNamedWhereClauseParam("pDepartmentId", getValueExpression("#{sessionBean.department}"));
            viewCheck.setNamedWhereClauseParam("pId", Eview.getCurrentRow().getAttribute("Id"));
            viewCheck.executeQuery();
            if(viewCheck.getRowCount()>0){
                return true;
            }else{
                return false;
            }
        }else if(getValueExpression("#{sessionBean.responsibility}").toString().equals("2")){
            viewCheck.setNamedWhereClauseParam("pUsername", getValueExpression("#{sessionBean.username}"));
            viewCheck.setNamedWhereClauseParam("pId", Eview.getCurrentRow().getAttribute("Id"));
            viewCheck.executeQuery();
            if(viewCheck.getRowCount()>0){
                return true;
            }else{
                return false;
            }
        }else if(getValueExpression("#{sessionBean.responsibility}").toString().equals("3")){
            return true;
        }
        return true;
    }
}
