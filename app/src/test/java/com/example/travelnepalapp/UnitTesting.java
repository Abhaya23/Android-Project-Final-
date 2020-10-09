package com.example.travelnepalapp;

import android.content.SharedPreferences;

import com.example.travelnepalapp.BusinessLogic.Commentquery;
import com.example.travelnepalapp.BusinessLogic.Feedbackquery;
import com.example.travelnepalapp.BusinessLogic.Loginquery;
import com.example.travelnepalapp.BusinessLogic.Registerquery;
import com.example.travelnepalapp.Models.Authtoken;
import com.example.travelnepalapp.Models.UserModel;

import org.junit.Assert;
import org.junit.Test;

import retrofit2.http.Body;
//import org.junit.Assert;




public class UnitTesting {
    String success;
    @Test
    public  void testLogin(){
        Loginquery loginquery=new Loginquery();
         Boolean result= loginquery.checkUser("r@r.com","rr");

        Assert.assertEquals(true,result);
    }
     @Test
    public  void logintest(){
        Loginquery loginquery=new Loginquery();
         Boolean result= loginquery.checkUser("r@r.co","rr");

        Assert.assertEquals(true,result);
    }

    @Test
    public  void testregister(){

        UserModel userModel=new UserModel("asd","asdasd","asd","asd","asd");
        Registerquery registerquery=new Registerquery();

         Boolean result= registerquery.register(userModel);

        Assert.assertEquals(true,result);
    }
    @Test
    public  void testfeedback(){

        Feedbackquery feedbackquery=new Feedbackquery();

         Boolean result= feedbackquery.addfeedback("ad","asd","asd","asd");

        Assert.assertEquals(true,result);
    }
  @Test
    public  void testcomment(){
      Commentquery commentquery=new Commentquery();
      Boolean result = commentquery.addcomment("5d1848c0e83d2015afa4cee6","Ma ni Janu parne","5d1847f6e83d2015afa4cee3","asdasd","asdasdasd");
      Assert.assertEquals(true,result);
    }

}